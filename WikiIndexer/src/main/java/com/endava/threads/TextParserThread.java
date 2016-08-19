package com.endava.threads;

import com.endava.dto.ArticleDTO;
import com.endava.services.WordsCountService;

import java.util.concurrent.Callable;

public class TextParserThread implements Callable<ArticleDTO> {

    private String title;
    private WordsCountService wordsCountService;

    public TextParserThread(String title, WordsCountService wordsCountService) {
        this.title = title;
        this.wordsCountService = wordsCountService;
    }

    @Override
    public ArticleDTO call() throws Exception {
        try {
            return wordsCountService.countWords(title);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
