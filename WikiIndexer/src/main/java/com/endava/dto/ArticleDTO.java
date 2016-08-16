package com.endava.dto;

import com.endava.model.WordEntity;

import java.util.List;

/**
 * Created by mistrate on 8/16/2016.
 */
public class ArticleDTO {
    private long time;
    private List<WordDTO> wordList;

    public List<WordDTO> getWordList() {
        return wordList;
    }

    public void setWordList(List<WordDTO> wordList) {
        this.wordList = wordList;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "ArticleDTO{" +
                "time=" + time +
                ", wordList=" + wordList +
                '}';
    }
}
