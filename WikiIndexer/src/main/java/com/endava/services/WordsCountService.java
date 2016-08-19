package com.endava.services;

import com.endava.dto.ArticleDTO;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * Service that counts words in an article
 */
public interface WordsCountService {

    /**
     * Count the words from the article
     * @param title title of the article
     * @return article information
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     */
    ArticleDTO countWords(String title) throws IOException, ParserConfigurationException, SAXException;
}
