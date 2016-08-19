package com.endava.dto;

public class WordDTO {
    private String word;
    private int nrAppar;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getNrAppar() {
        return nrAppar;
    }

    public void setNrAppar(int nrAppar) {
        this.nrAppar = nrAppar;
    }

    @Override
    public String toString() {
        return "WordDTO{" +
                "word='" + word + '\'' +
                ", nrAppar=" + nrAppar +
                '}';
    }
}
