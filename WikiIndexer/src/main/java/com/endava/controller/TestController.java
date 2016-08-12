package com.endava.controller;

import com.endava.model.ArticleEntity;
import com.endava.model.WordEntity;
import com.endava.repository.ArticleRepository;
import com.endava.services.MultiTitlesParser;
import com.endava.services.TextParserService;
import com.endava.services.TitleCheckServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class TestController {

    @Autowired
    MultiTitlesParser multiTitlesParser;

    @Autowired
    TextParserService textParserService;

    @Autowired
    TitleCheckServiceImpl titleCheckService;

    @RequestMapping(method = RequestMethod.GET)
    public void print() {
        try {
            List<String> titles = multiTitlesParser.getTitles("titles.txt");
            List<ArticleEntity> articleEntities = titles.stream()
                                    .map(s -> {
                                        try {
                                            return textParserService.getTopWords(s);
                                        } catch (Exception e) { }
                                        return null;
                                    })
                                    .collect(Collectors.toList());
            List<WordEntity> wordEntities = articleEntities.stream()
                    .flatMap(e -> e.getWordList().stream())
                    .sorted(new Comparator<WordEntity>() {
                        @Override
                        public int compare(WordEntity o1, WordEntity o2) {
                            return o2.getNrAppar() - o1.getNrAppar();
                        }
                    })
                    .limit(10)
                    .collect(Collectors.toList());
            long totalTime = articleEntities.stream()
                    .map(ArticleEntity::getTime)
                    .reduce(0l, (a, b) -> a + b);
            System.out.println(wordEntities);
            System.out.println("took " + totalTime + "ms");
        } catch (Exception e) {
            e.printStackTrace();
        }

      /*titleCheckService.checkTitle("Beach");
        ArticleEntity articleEntity = new ArticleEntity();
        articleEntity.setTitle("Test");
        articleEntity.setTime(11);

        titleCheckService.insertArticle(articleEntity);*/
    }
}
