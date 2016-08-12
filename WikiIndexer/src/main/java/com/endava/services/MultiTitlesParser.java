package com.endava.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface MultiTitlesParser {
    public List<String> getTitles(String fileName) throws FileNotFoundException, IOException;
}
