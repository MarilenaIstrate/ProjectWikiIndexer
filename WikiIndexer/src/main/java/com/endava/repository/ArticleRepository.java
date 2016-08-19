package com.endava.repository;

import com.endava.model.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<ArticleEntity, Integer> {

    /**
     * Get article by title
     * @param title = article title
     * @return ArticleEntity from database
     */
    ArticleEntity findByTitle(String title);
}
