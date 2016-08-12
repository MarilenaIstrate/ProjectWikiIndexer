package com.endava.services.impl;

import com.endava.model.ArticleEntity;
import com.endava.model.WordEntity;
import com.endava.services.CommonWordsCheckerService;
import com.endava.services.RequestService;
import com.endava.services.TextParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;

@Service
public class TextParserServiceImpl implements TextParserService {

    @Autowired
    RequestService requestService;

    @Autowired
    CommonWordsCheckerService commonWordsCheckerService;

    @Autowired
    ArticleServiceImpl titleCheckService;

    public ArticleEntity getTopWords(String title) throws IOException, ParserConfigurationException, SAXException {

        /* Replace white spaces in the title */
        title = title.replaceAll("\\s+", "_");

        /* Check if the article is in the database */
        ArticleEntity articleEntity = titleCheckService.checkTitle(title);
        if (articleEntity != null) {
            return articleEntity;
        }

        /* Get input stream containing the article body */
        InputStream inputStream = requestService.requestTitle(title);
        if (inputStream != null) {
            /* Start timer */
            long time = System.nanoTime();
            /* Get contents of the 'extract' tag */
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputStream);
            NodeList nodeList = document.getElementsByTagName("extract");
            /* Split in words */
            String[] words = nodeList.item(0).getTextContent().split("[^\\w']+");
            /* Close input stream */
            inputStream.close();

            /* Make map that count the words */
            Map<String, Integer> wordsMap = Arrays.stream(words).map(w -> w.toLowerCase())
                    .filter(w -> w.length() > 0 && w != "" && !commonWordsCheckerService.isCommonWord(w))
                    .collect(groupingBy(Function.identity(), summingInt(e -> 1)));
            /* Make ordered map */
            List<WordEntity> wordEntities = wordsMap.entrySet().stream().
                    sorted(Map.Entry.comparingByValue(new Comparator<Integer>() {
                        @Override
                        public int compare(Integer i1, Integer i2) {
                            return i2 - i1;
                        }
                    }))
                    .limit(10)
                    .map(entry -> {
                        WordEntity wordEntity = new WordEntity();
                        wordEntity.setWord(entry.getKey());
                        wordEntity.setNrAppar(entry.getValue());
                        return wordEntity;
                    })
                    .collect(Collectors.toList());

            time = System.nanoTime() - time;
            ///*
            //ArticleEntity articleEntity;
            //*/
            /* Save the result */
            articleEntity = new ArticleEntity();
            articleEntity.setTitle(title);
            articleEntity.setTime(time);

            /* Add top 10 words to ArticleEntity */
            //List<WordEntity> wordEntities = new ArrayList<>();
            //for(Map.Entry<String, Integer> entry : sortedMap.entrySet()) {
            //    WordEntity wordEntity = new WordEntity();
            //    wordEntity.setWord(entry.getKey());
            //    wordEntity.setNrAppar(entry.getValue());
            //    wordEntity.setArticleEntity(articleEntity);
            //
            //    wordEntities.add(wordEntity);
            //}
            articleEntity.setWordList(wordEntities);

            /* Save the results in the database */
            titleCheckService.insertArticle(articleEntity);
            return articleEntity;
        }
        return null;
    }
}
