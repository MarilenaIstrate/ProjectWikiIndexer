package com.endava.services.impl;

import com.endava.services.CommonWordsCheckerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.HashSet;
import java.util.Set;

@Service
public class CommonWordsCheckerServiceImpl implements CommonWordsCheckerService {

    @Value("${wordsFile}")
    private String wordsFile;

    private Set<String> skipWordsSet;

    /**
     * Make a set of top 100 words
     */
    @PostConstruct
    private void CommonWordsCheckerService() {
        skipWordsSet = new HashSet<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(wordsFile))) {
            String word;
            while ((word = reader.readLine()) != null) {
                skipWordsSet.add(word);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isCommonWord(String word) {
        return skipWordsSet.contains(word);
    }
}
