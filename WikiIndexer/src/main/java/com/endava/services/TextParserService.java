package com.endava.services;

import com.endava.dto.ArticleDTO;
import com.endava.model.ArticleEntity;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public interface TextParserService {

    /**
     * Count the words from the article
     * @param title = title of the article
     * @return article information
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     */
    ArticleDTO getWords(String title) throws IOException, ParserConfigurationException, SAXException;
}
