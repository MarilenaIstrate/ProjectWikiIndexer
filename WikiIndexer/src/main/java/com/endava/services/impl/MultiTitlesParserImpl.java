package com.endava.services.impl;

import com.endava.services.MultiTitlesParser;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class MultiTitlesParserImpl implements MultiTitlesParser {

    public List<String> getTitles(MultipartFile fileName) {
        List<String > titles = new ArrayList<>();
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
