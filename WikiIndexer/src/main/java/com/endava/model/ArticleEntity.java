package com.endava.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "article")
public class ArticleEntity {

    @Id
    @Column(name = "id_article")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int aid;

    @Column(name = "title")
    private String title;

    @OneToMany(fetch = FetchType.EAGER, targetEntity = WordEntity.class, mappedBy = "articleEntity", cascade = {CascadeType.PERSIST})
    private List<WordEntity> wordList;

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<WordEntity> getWordList() {
        return wordList;
    }

    public void setWordList(List<WordEntity> wordList) {
        this.wordList = wordList;
    }

    @Override
    public String toString() {
        return "ArticleEntity{" +
                "aid=" + aid +
                ", title='" + title + '\'' +
                ", wordList=" + wordList +
                '}';
    }
}
