package com.endava.threads;

import com.endava.model.ArticleEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ArticleEntityList {
    private List<ArticleEntity> articleEntities;

    public ArticleEntityList() {
        articleEntities = Collections.synchronizedList(new ArrayList<>());
    }

    public void addArticleEntity(ArticleEntity articleEntity) {
        articleEntities.add(articleEntity);
    }

    public List<ArticleEntity> getArticleEntities() {
        return articleEntities;
    }
}
