package com.endava.services.impl;

import com.endava.dto.ArticleDTO;
import com.endava.dto.WordDTO;
import com.endava.model.ArticleEntity;
import com.endava.model.WordEntity;
import com.endava.repository.ArticleRepository;
import com.endava.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mistrate on 8/11/2016.
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    ArticleRepository articleRepository;

    @Transactional(readOnly = true)
    public ArticleDTO checkTitle(String title){
       ArticleEntity articleEntity = articleRepository.findByTitle(title);
        if (articleEntity == null){
            return null;
        }

        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setTime(articleEntity.getTime());
        articleDTO.setTitle(articleEntity.getTitle());
        List<WordDTO> wordDTOs = new ArrayList<WordDTO>();


        for (WordEntity wordEntity : articleEntity.getWordList()){
            WordDTO wordDTO = new WordDTO();
            wordDTO.setWord(wordEntity.getWord());
            wordDTO.setNrAppar(wordEntity.getNrAppar());

            wordDTOs.add(wordDTO);
        }
        articleDTO.setWordList(wordDTOs);
        articleDTO.setFromDatabase(true);
        return articleDTO;
    }

    @Transactional
    public void insertArticle(ArticleDTO articleDTO){
        ArticleEntity articleEntity = new ArticleEntity();

        articleEntity.setTime(articleDTO.getTime());
        articleEntity.setTitle(articleDTO.getTitle());
        List<WordEntity> wordEntities = new ArrayList<WordEntity>();

        for(WordDTO wordDTO : articleDTO.getWordList()){
            WordEntity wordEntity = new WordEntity();
            wordEntity.setWord(wordDTO.getWord());
            wordEntity.setNrAppar(wordDTO.getNrAppar());

            wordEntity.setArticleEntity(articleEntity);

            wordEntities.add(wordEntity);

        }
        articleEntity.setWordList(wordEntities);

    	articleRepository.save(articleEntity);
    }
}
