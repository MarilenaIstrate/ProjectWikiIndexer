package com.endava.services;

import com.endava.model.ArticleEntity;
import com.endava.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by mistrate on 8/11/2016.
 */
@Service
public class TitleCheckServiceImpl {

    @Autowired
    ArticleRepository articleRepository;

    @Transactional(readOnly = true)
    public ArticleEntity checkTitle(String title){
       return articleRepository.findByTitle(title);
    }

    @Transactional
    public void insertArticle(ArticleEntity articleEntity){
        articleRepository.save(articleEntity);
    }
}
