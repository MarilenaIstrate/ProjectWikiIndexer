package com.endava.repository;

import com.endava.model.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by mistrate on 8/11/2016.
 */
public interface ArticleRepository extends JpaRepository<ArticleEntity, Integer> {
    ArticleEntity findByTitle(String title);

}
