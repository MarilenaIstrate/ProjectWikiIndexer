package com.endava.services.impl;

import com.endava.dto.ArticleDTO;
import com.endava.dto.WordDTO;
import com.endava.services.MainService;
import com.endava.services.MultiTitlesParser;
import com.endava.services.TextParserService;
import com.endava.threads.TextParserThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class MainServiceImpl implements MainService {

    @Autowired
    private TextParserService textParserService;

    @Autowired
    private MultiTitlesParser multiTitlesParser;

    @Value("${nr_threads}")
    private Integer nrThreads;

    public ArticleDTO getWordsFromTitle(String title) {

        try {
            long time = System.nanoTime();
            ArticleDTO articleDTORet = textParserService.getWords(title);
            if (articleDTORet == null)
                return null;
            /* Make new article DTO */
            ArticleDTO articleDTO = new ArticleDTO();
            articleDTO.setFromDatabase(articleDTORet.isFromDatabase());
            articleDTO.setTitle(articleDTORet.getTitle());
            articleDTO.setWordList(articleDTORet.getWordList().stream()
                    .sorted(new Comparator<WordDTO>() {
                        @Override
                        public int compare(WordDTO w1, WordDTO w2) {
                            return w2.getNrAppar() - w1.getNrAppar();
                        }
                    })
                    .limit(10)
                    .collect(Collectors.toList()));
            articleDTO.setTime(System.nanoTime()-time);
            return articleDTO;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArticleDTO getWordsFromFile(MultipartFile fileName) {

        ExecutorService executorService = Executors.newFixedThreadPool(nrThreads);
        long time = System.nanoTime();
        /* Get ArticleDTO from every article */
        List<Future<ArticleDTO>> articleDTOs = multiTitlesParser.getTitles(fileName).stream()
                .map(title -> {
                    return executorService.submit(new TextParserThread(title, textParserService));
                })
                .collect(Collectors.toList());
        executorService.shutdown();
        List<ArticleDTO> articlesDTO = articleDTOs.stream()
                .map(entity -> {
                    try {
                        return entity.get();
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .filter(articleDTO -> articleDTO != null)
                .collect(Collectors.toList());

        /* Get source */
        boolean source = articlesDTO.stream()
                .map(ArticleDTO::isFromDatabase)
                .reduce(false, (a, b) -> a || b);

        /* Count words */
        Map<String, Integer> wordsMap = articlesDTO.stream()
                .map(article -> article.getWordList())
                .flatMap(word -> word.stream())
                .collect(Collectors.groupingBy(WordDTO::getWord, Collectors.summingInt(WordDTO::getNrAppar)));
        /* Get top 10 words */
        List<WordDTO> wordsDTO = wordsMap.entrySet().stream().
                sorted(Map.Entry.comparingByValue(new Comparator<Integer>() {
                    @Override
                    public int compare(Integer i1, Integer i2) {
                        return i2 - i1;
                    }
                }))
                .limit(10)
                .map(entry -> {
                    WordDTO wordDTO = new WordDTO();
                    wordDTO.setWord(entry.getKey());
                    wordDTO.setNrAppar(entry.getValue());
                    return wordDTO;
                })
                .collect(Collectors.toList());

        /* Save result */
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setTime(System.nanoTime() - time);
        articleDTO.setWordList(wordsDTO);
        articleDTO.setFromDatabase(source);
        return articleDTO;
    }
}
