package com.endava.threads;

import com.endava.model.ArticleEntity;
import com.endava.model.WordEntity;
import com.endava.services.TextParserService;
import com.sun.xml.internal.bind.v2.runtime.output.SAXOutput;
import com.sun.xml.internal.stream.util.ThreadLocalBufferAllocator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TextParserThread implements Runnable {

    private String title;
    private TextParserService textParserService;
    private List<ArticleEntity> articleEntities;
    private List<Integer> integerList;

    public TextParserThread(String title, TextParserService textParserService, List<ArticleEntity> articleEntities, List<Integer> intList) {
        this.title = title;
        this.textParserService = textParserService;
        this.articleEntities = articleEntities;
        this.integerList = intList;
    }

    @Override
    public void run() {
        try {
            System.out.println("Thread " + Thread.currentThread().getId());
            if (textParserService == null) {
                System.out.println("TextParser is null");
            }
            ArticleEntity articleEntity = textParserService.getTopWords(title);
            System.out.println("Adding " + articleEntity);
            articleEntities.add(articleEntity);
            integerList.add(Thread.currentThread().hashCode());
            System.out.println("Added " + articleEntities);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
