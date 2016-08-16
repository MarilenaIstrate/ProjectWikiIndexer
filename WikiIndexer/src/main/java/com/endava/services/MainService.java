package com.endava.services;

import com.endava.model.ArticleEntity;

public interface MainService {
    /**
     * Return top 10 words from the article given by title
     * @param title = title of the article
     * @return top 10 words
     */
    ArticleEntity getWordsFromTitle(String title);

    /**
     * Return top 10 words from the articles given in the file
     * @param fileName = name of the file containing article titles
     * @return top 10 words
     */
    ArticleEntity getWordsFromFile(String fileName);
}
