package com.endava.services.impl;

import com.endava.dto.ArticleDTO;
import com.endava.dto.WordDTO;
import com.endava.model.ArticleEntity;
import com.endava.model.WordEntity;
import com.endava.repository.ArticleRepository;
import com.endava.services.ArticleDatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleDatabaseServiceImpl implements ArticleDatabaseService {

    @Autowired
    ArticleRepository articleRepository;

    @Transactional(readOnly = true)
    public ArticleDTO checkTitle(String title){

        /* Query database */
        ArticleEntity articleEntity = articleRepository.findByTitle(title);
        if (articleEntity == null){
            return null;
        }

        /* Create DTO */
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setTitle(articleEntity.getTitle());
        List<WordDTO> wordDTOs = articleEntity.getWordList().stream()
                .map(wordEntity -> {
                    WordDTO wordDTO = new WordDTO();
                    wordDTO.setWord(wordEntity.getWord());
                    wordDTO.setNrAppar(wordEntity.getNrAppar());
                    return wordDTO;
                })
                .collect(Collectors.toList());

        articleDTO.setWordList(wordDTOs);
        articleDTO.setFromDatabase(true);
        return articleDTO;
    }

    @Transactional
    public void insertArticle(ArticleDTO articleDTO){

        /* Create article entity */
        ArticleEntity articleEntity = new ArticleEntity();
        articleEntity.setTitle(articleDTO.getTitle());
        List<WordEntity> wordEntities = articleDTO.getWordList().stream()
                .map(wordDTO -> {
                    WordEntity wordEntity = new WordEntity();
                    wordEntity.setWord(wordDTO.getWord());
                    wordEntity.setNrAppar(wordDTO.getNrAppar());
                    wordEntity.setArticleEntity(articleEntity);
                    return wordEntity;
                })
                .collect(Collectors.toList());
        articleEntity.setWordList(wordEntities);

        /* Save in the database */
    	articleRepository.save(articleEntity);
    }
}
