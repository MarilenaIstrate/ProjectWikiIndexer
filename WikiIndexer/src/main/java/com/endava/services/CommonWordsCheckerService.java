package com.endava.services;

public interface CommonWordsCheckerService {
    /**
     * Check if a word is in the top 100 common English words
     * @param word = word to check
     * @return true if the word is in the top 100 common English words
     */
    boolean isCommonWord(String word);
}
