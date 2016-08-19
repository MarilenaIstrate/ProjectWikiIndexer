package com.endava.services.impl;

import com.endava.services.FileParserService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

@Service
public class FileParserServiceImpl implements FileParserService {

    public Set<String> getTitles(MultipartFile fileName) {
        Set<String > titles = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(fileName.getInputStream()))) {
            String line;
            while((line = reader.readLine()) != null) {
                titles.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return titles;
    }
}
