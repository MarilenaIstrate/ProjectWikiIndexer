package com.endava.services.impl;

import com.endava.dto.ArticleDTO;
import com.endava.dto.WordDTO;
import com.endava.services.CommonWordsCheckerService;
import com.endava.services.WikiRequestService;
import com.endava.services.WordsCountService;
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
public class WordsCountServiceImpl implements WordsCountService {

    @Autowired
    WikiRequestService wikiRequestService;

    @Autowired
    CommonWordsCheckerService commonWordsCheckerService;

    @Autowired
    ArticleDatabaseServiceImpl articleService;

    public ArticleDTO countWords(String title) throws IOException, ParserConfigurationException, SAXException {

        if (title == null)
            return null;
        /* Replace white spaces in the title */
        title = title.replaceAll("\\s+", "_");
        /* Replace all '&' to preserve the URL */
        title = title.replaceAll("&", "%26");

        /* Check if the article is in the database */
        ArticleDTO articleDTO = articleService.checkTitle(title);
        if (articleDTO != null) {
            return articleDTO;
        }

        /* Get input stream containing the article body */
        InputStream inputStream = wikiRequestService.requestTitle(title);
        if (inputStream != null) {
            /* Get contents of the 'extract' tag */
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputStream);
            NodeList nodeList = document.getElementsByTagName("extract");
            if (nodeList == null || nodeList.item(0) == null) {
                return null;
            }

            /* Split in words */
            String[] words = nodeList.item(0).getTextContent().split("[^\\w']+");
            /* Close input stream */
            inputStream.close();

            /* Make map that count the words */
            Map<String, Integer> wordsMap = Arrays.stream(words).map(w -> w.toLowerCase())
                    .filter(w -> w.length() > 2 && !commonWordsCheckerService.isCommonWord(w))
                    .collect(groupingBy(Function.identity(), summingInt(e -> 1)));

            /* Make ArticleDTO */
            articleDTO = new ArticleDTO();
            articleDTO.setTitle(title);

            /* Make list of words */
            List<WordDTO> wordsDTO = wordsMap.entrySet().stream()
                    .map(entry -> {
                        WordDTO wordDTO = new WordDTO();
                        wordDTO.setWord(entry.getKey());
                        wordDTO.setNrAppar(entry.getValue());
                        return wordDTO;
                    })
                    .collect(Collectors.toList());

            /* Add words to ArticleDTO */
            articleDTO.setWordList(wordsDTO);

            /* Add source to ArticleDTO */
            articleDTO.setFromDatabase(false);

            /* Save the results in the database */
            articleService.insertArticle(articleDTO);
            return articleDTO;
        }
        return null;
    }
}
