package com.endava.services.impl;

import com.endava.model.ArticleEntity;
import com.endava.model.WordEntity;
import com.endava.services.CommonWordsCheckerService;
import com.endava.services.RequestService;
import com.endava.services.TextParserService;
import com.endava.services.TitleCheckServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.SystemEnvironmentPropertySource;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Time;
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
    TitleCheckServiceImpl titleCheckService;

    public ArticleEntity getTopWords(String title) throws IOException, ParserConfigurationException, SAXException {

        ArticleEntity articleEntity = titleCheckService.checkTitle(title);
        if (articleEntity != null) {
            return articleEntity;
        }
        long time = System.nanoTime();
        InputStream inputStream = requestService.requestTitle(title);
        if (inputStream != null) {
            /* Get contents of the 'extract' tag */
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputStream);
            NodeList nodeList = document.getElementsByTagName("extract");
            /* Split in words */
            String[] words = nodeList.item(0).getTextContent().split("[^\\w']+");

            /* Make map that count the words */
            Map<String, Integer> wordsMap = Arrays.stream(words).map(w -> w.toLowerCase())
                    .filter(w -> w.length() > 0 && w != "" && !commonWordsCheckerService.isCommonWord(w))
                    .collect(groupingBy(Function.identity(), summingInt(e -> 1)));
            /* Make ordered map */
            LinkedHashMap<String, Integer> sortedMap = wordsMap.entrySet().stream().
                    sorted(Map.Entry.comparingByValue(new Comparator<Integer>() {
                        @Override
                        public int compare(Integer i1, Integer i2) {
                            return i2 - i1;
                        }
                    }))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                            (e1, e2) -> e1, LinkedHashMap::new));

            time = System.nanoTime() - time;

            articleEntity = new ArticleEntity();
            articleEntity.setTitle(title);
            articleEntity.setTime(time);

            /* Add top 10 words to ArticleEntity*/
            List<WordEntity> wordEntities = new ArrayList<>();
            int count = 0;
            for(Map.Entry<String, Integer> entry : sortedMap.entrySet()) {
                WordEntity wordEntity = new WordEntity();
                wordEntity.setWord(entry.getKey());
                wordEntity.setNrAppar(entry.getValue());
                wordEntity.setArticleEntity(articleEntity);

                wordEntities.add(wordEntity);
                if (++count == 10)
                    break;
            }
            articleEntity.setWordList(wordEntities);

            titleCheckService.insertArticle(articleEntity);
            return articleEntity;
        }
        return null;
    }
}
