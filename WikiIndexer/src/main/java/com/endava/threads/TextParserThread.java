package com.endava.threads;

import com.endava.model.ArticleEntity;
import com.endava.model.WordEntity;
import com.endava.services.TextParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TextParserThread extends Thread {

    private String title;
    private TextParserService textParserService;
    private List<ArticleEntity> articleEntityes;

    public TextParserThread(String title, TextParserService textParserService, List<ArticleEntity> articleEntityes) {
        this.title = title;
        this.textParserService = textParserService;
        this.articleEntityes = articleEntityes;
    }

    @Override
    public void run() {
        try {
            System.out.println("Thread " + Thread.currentThread().getId());
            if (textParserService == null) {
                System.out.println("TextParser is null");
            }
            ArticleEntity articleEntity = textParserService.getTopWords(title);
            articleEntityes.add(articleEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
