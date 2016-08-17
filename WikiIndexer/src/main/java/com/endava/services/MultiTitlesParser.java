package com.endava.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface MultiTitlesParser {
    public List<String> getTitles(MultipartFile fileName);
}
