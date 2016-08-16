package com.endava.services;

import com.endava.dto.ArticleDTO;
import com.endava.model.ArticleEntity;


/**
 * Created by mistrate on 8/12/2016.
 */
public interface ArticleService {
    public ArticleDTO checkTitle(String title);


    public void insertArticle(ArticleDTO articleDTO);

}
