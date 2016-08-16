package com.endava.services.impl;

import com.endava.model.ArticleEntity;
import com.endava.model.WordEntity;
import com.endava.services.MainService;
import com.endava.services.MultiTitlesParser;
import com.endava.services.TextParserService;
import com.endava.threads.ArticleEntityList;
import com.endava.threads.TextParserThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
public class MainServiceImpl implements MainService {

    @Autowired
    private TextParserService textParserService;

    @Autowired
    private MultiTitlesParser multiTitlesParser;

    public ArticleEntity getWordsFromTitle(String title) {

        try {
            return textParserService.getTopWords(title);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArticleEntity getWordsFromFile(String fileName) {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        List<ArticleEntity> articleEntities = Collections.synchronizedList(new ArrayList<>());
        List<Integer> intList = Collections.synchronizedList(new ArrayList<>()) ;
        try {
            List<Thread> threads = new ArrayList<>();
            /* Get ArticleEntity from every article */
            //List<String> titles = multiTitlesParser.getTitles(fileName);
            multiTitlesParser.getTitles(fileName).stream()
                    .forEach(title -> {
                        Thread textParserThread = new Thread(new TextParserThread(title, textParserService, articleEntities, intList));
                        threads.add(textParserThread);
                        textParserThread.run();
                        //executorService.submit(new TextParserThread(title, textParserService, articleEntities, intList));
                    });
            //executorService.shutdown();
            //while (!executorService.isShutdown()) {}
            threads.stream().forEach(thread -> {
                try {
                    thread.join();
                } catch (Exception e) { }
            });
            System.out.println("Got " + articleEntities);
            System.out.println("Got int " + intList);
            /*        .map(s -> {
                        try {
                            return textParserService.getTopWords(s);
                        } catch (Exception e) {
                            return null;
                        }
                    })
                    .collect(Collectors.toList());*/
            /* Get top 10 words */
            List<WordEntity> wordEntities = articleEntities.stream()
                    .filter(e -> e != null)
                    .flatMap(e -> e.getWordList().stream())
                    .sorted(new Comparator<WordEntity>() {
                        @Override
                        public int compare(WordEntity o1, WordEntity o2) {
                            return o2.getNrAppar() - o1.getNrAppar();
                        }
                    })
                    .limit(10)
                    .collect(Collectors.toList());
            /* Get total time */
            long totalTime = articleEntities.stream()
                    .map(ArticleEntity::getTime)
                    .reduce(0l, (a, b) -> a + b);

            /* Save result */
            ArticleEntity articleEntity = new ArticleEntity();
            articleEntity.setTime(totalTime);
            articleEntity.setWordList(wordEntities);
            return articleEntity;
        } catch (IOException e) {
            return null;
        }
    }
}
