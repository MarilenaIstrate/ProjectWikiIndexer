package com.endava.services;

import com.endava.dto.ArticleDTO;
import com.endava.model.ArticleEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MainService {
    /**
     * Return top 10 words from the article given by title
     * @param title = title of the article
     * @return top 10 words
     */
    List<ArticleDTO> getWordsFromTitle(String title);

    /**
     * Return list of top 10 words from the articles given in the file
     * @param fileName = name of the file containing article titles
     * @return list of articles
     */
    List<ArticleDTO> getWordsFromFile(MultipartFile fileName);
}
