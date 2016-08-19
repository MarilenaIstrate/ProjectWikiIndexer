package com.endava.services.impl;

import com.endava.dto.ArticleDTO;
import com.endava.dto.WordDTO;
import com.endava.services.MainService;
import com.endava.services.FileParserService;
import com.endava.services.WordsCountService;
import com.endava.threads.TextParserThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Service
public class MainServiceImpl implements MainService {

    @Autowired
    private WordsCountService wordsCountService;

    @Autowired
    private FileParserService fileParserService;

    @Value("${nr_threads}")
    private Integer nrThreads;

    /**
     * Get top ten words
     * @param wordsDTO list of words
     * @return list containing top ten words
     */
    private List<WordDTO> getTopTenWords(List<WordDTO> wordsDTO) {
        return wordsDTO.stream()
                .sorted(new Comparator<WordDTO>() {
                    @Override
                    public int compare(WordDTO w1, WordDTO w2) {
                        return w2.getNrAppar() - w1.getNrAppar();
                    }
                })
                .limit(10)
                .collect(Collectors.toList());
    }

    public List<ArticleDTO> getWordsFromTitle(String title) {

        try {
            long time = System.nanoTime();
            ArticleDTO articleDTORet = wordsCountService.countWords(title);
            if (articleDTORet == null)
                return null;
            /* Make new article DTO */
            ArticleDTO articleDTO = new ArticleDTO();
            articleDTO.setFromDatabase(articleDTORet.isFromDatabase());
            articleDTO.setTitle(articleDTORet.getTitle());
            articleDTO.setWordList(getTopTenWords(articleDTORet.getWordList()));
            articleDTO.setTime(System.nanoTime()-time);
            /* Make list containing the article */
            List<ArticleDTO> returnArticlesDTO = new ArrayList<>();
            returnArticlesDTO.add(articleDTO);
            return returnArticlesDTO;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<ArticleDTO> getWordsFromFile(MultipartFile fileName) {

        ExecutorService executorService = Executors.newFixedThreadPool(nrThreads);
        long time = System.nanoTime();
        /* Start thread from every title */
        List<Future<ArticleDTO>> articleDTOs = fileParserService.getTitles(fileName).stream()
                .map(title -> {
                    return executorService.submit(new TextParserThread(title, wordsCountService));
                })
                .collect(Collectors.toList());
        executorService.shutdown();
        /* Get ArticleDTO for every title */
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

        /* List of top ten words per article */
        List<ArticleDTO> returnArticlesDTO = articlesDTO.stream()
                .map(article -> {
                    ArticleDTO articleDTO = new ArticleDTO();
                    articleDTO.setTitle(article.getTitle());
                    articleDTO.setFromDatabase(article.isFromDatabase());
                    articleDTO.setWordList(getTopTenWords(article.getWordList()));
                    articleDTO.setTime(System.nanoTime() - time);
                    return articleDTO;
                })
                .collect(Collectors.toList());
        /* Return iff only one article */
        if (returnArticlesDTO.size() == 1) {
            return returnArticlesDTO;
        }

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
        returnArticlesDTO.add(articleDTO);
        return returnArticlesDTO;
    }
}
