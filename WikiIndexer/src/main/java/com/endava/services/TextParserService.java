package com.endava.services;

import com.endava.dto.ArticleDTO;
import com.endava.model.ArticleEntity;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public interface TextParserService {
    /**
     * Prints the top 10 words
     * @param title = title of the article
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     */
    ArticleDTO getTopWords(String title) throws IOException, ParserConfigurationException, SAXException;
}
