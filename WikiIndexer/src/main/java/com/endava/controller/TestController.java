package com.endava.controller;

import com.endava.model.ArticleEntity;
import com.endava.repository.ArticleRepository;
import com.endava.services.TextParserService;
import com.endava.services.TitleCheckServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/")
public class TestController {

    @Autowired
    TextParserService textParserService;

    @Autowired
    TitleCheckServiceImpl titleCheckService;

    @RequestMapping(method = RequestMethod.GET)
    public void print() {
        try {
            System.out.println(textParserService.getTopWords("Beach"));
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
