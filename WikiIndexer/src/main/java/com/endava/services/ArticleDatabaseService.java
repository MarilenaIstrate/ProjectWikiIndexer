package com.endava.services;

import com.endava.dto.ArticleDTO;

public interface ArticleDatabaseService {

    /**
     * Checks if an article is in the database
     * @param title = title of the article
     * @return article data transfer object
     */
    public ArticleDTO checkTitle(String title);

    /**
     * Inserts article into database
     * @param articleDTO = article data transfer object
     */
    public void insertArticle(ArticleDTO articleDTO);
}
