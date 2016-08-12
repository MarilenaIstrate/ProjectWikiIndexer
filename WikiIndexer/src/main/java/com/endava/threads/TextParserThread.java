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

@Component
@Scope("prototype")
public class TextParserThread extends Thread {

    @Autowired
    private TextParserService textParserService;

    private String title;
    private ArticleEntity articleEntity;

    private TextParserThread(String title, ArticleEntity articleEntity) {
        this.title = title;
        this.articleEntity = articleEntity;
    }

    @Override
    public void run() {
        /*try {
            ArticleEntity resultArticleEntity = textParserService.getTopWords(title);
            synchronized (articleEntity) {

                articleEntity.getWordList().addAll(resultArticleEntity.getWordList())
                if (articleEntity.getWordList().size() > 0) {
                    for (WordEntity wordEntity : resultArticleEntity.getWordList()) {

                    }
                }
            }

        } catch (Exception e) {

        }*/
    }

}
