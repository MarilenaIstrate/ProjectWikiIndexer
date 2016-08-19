package com.endava.services;

import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

/**
 * Service that reads the titles file
 */
public interface FileParserService {

    /**
     * Get article titles from file
     * @param fileName file InputStream
     * @return set of article titles
     */
    public Set<String> getTitles(MultipartFile fileName);
}
