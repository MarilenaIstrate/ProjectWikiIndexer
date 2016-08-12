package com.endava.services;

import com.endava.model.ArticleEntity;


/**
 * Created by mistrate on 8/12/2016.
 */
public interface ArticleService {
    public ArticleEntity checkTitle(String title);


    public void insertArticle(ArticleEntity articleEntity);

}
