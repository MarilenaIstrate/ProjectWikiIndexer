package com.endava.services;

import com.endava.dto.ArticleDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Service that calls other services
 * Service used by the controller
 */
public interface MainService {

    /**
     * Return list of one article with top 10 words from the article given by title
     * @param title title of the article
     * @return list of one article with top 10 words
     */
    List<ArticleDTO> getWordsFromTitle(String title);

    /**
     * Return list of top 10 words from the articles given in the file
     * @param fileName name of the file containing article titles
     * @return list of articles
     */
    List<ArticleDTO> getWordsFromFile(MultipartFile fileName);
}
