package com.endava.services.impl;

import com.endava.services.MultiTitlesParser;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class MultiTitlesParserImpl implements MultiTitlesParser {

    public List<String> getTitles(String fileName) throws FileNotFoundException, IOException {
        List<String > titles = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;
        while((line = reader.readLine()) != null) {
            titles.add(line);
        }
        reader.close();
        return titles;
    }
}
