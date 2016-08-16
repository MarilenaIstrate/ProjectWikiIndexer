package com.endava.threads;

import com.endava.dto.ArticleDTO;
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
import java.util.concurrent.Callable;

public class TextParserThread implements Callable<ArticleDTO> {

    private String title;
    private TextParserService textParserService;

    public TextParserThread(String title, TextParserService textParserService) {
        this.title = title;
        this.textParserService = textParserService;
    }

    @Override
    public ArticleDTO call() throws Exception {
        try {
            return textParserService.getTopWords(title);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
