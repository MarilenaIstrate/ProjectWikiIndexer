package com.endava.services.impl;

import com.endava.dto.ArticleDTO;
import com.endava.dto.WordDTO;
import com.endava.model.ArticleEntity;
import com.endava.model.WordEntity;
import com.endava.services.MainService;
import com.endava.services.MultiTitlesParser;
import com.endava.services.TextParserService;
import com.endava.threads.TextParserThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Service
public class MainServiceImpl implements MainService {

    @Autowired
    private TextParserService textParserService;

    @Autowired
    private MultiTitlesParser multiTitlesParser;

    public ArticleDTO getWordsFromTitle(String title) {

        try {
            return textParserService.getTopWords(title);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArticleDTO getWordsFromFile(MultipartFile file) {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));) {
            String line = null;
            List<String> titles = new ArrayList<>();
            while((line = reader.readLine()) != null) {
                titles.add(line);
            }
            /* Get ArticleDTO from every article */
            List<Future<ArticleDTO>> articleDTOs = titles.stream()
                    .map(title -> {
                        return executorService.submit(new TextParserThread(title, textParserService));
                    })
                    .collect(Collectors.toList());
            executorService.shutdown();

            List<ArticleDTO> articleDTOList = articleDTOs.stream()
                    .map(entity -> {
                        try {
                            return entity.get();
                        } catch (Exception e) {
                            e.printStackTrace();
                            return null;
                        }
                    })
                    .collect(Collectors.toList());
            /* Get top 10 words */
            List<WordDTO> wordDTOs = articleDTOList.stream()
                    .filter(e -> e != null)
                    .flatMap(e -> e.getWordList().stream())
                    .sorted(new Comparator<WordDTO>() {
                        @Override
                        public int compare(WordDTO o1, WordDTO o2) {
                            return o2.getNrAppar() - o1.getNrAppar();
                        }
                    })
                    .limit(10)
                    .collect(Collectors.toList());
            /* Get total time */
            long totalTime = articleDTOList.stream()
                    .map(ArticleDTO::getTime)
                    .reduce(0l, (a, b) -> a + b);

            /* Save result */
            ArticleDTO articleDTO = new ArticleDTO();
            articleDTO.setTime(totalTime);
            articleDTO.setWordList(wordDTOs);
            return articleDTO;
        } catch (IOException e) {
            return null;
        }
    }
}
