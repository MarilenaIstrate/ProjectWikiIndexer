package com.endava.model;

/**
 * Created by mistrate on 8/11/2016.
 */
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


    @Column(name = "search_time")
    private long time;

    @Column(name = "category")
    private String category;

    @OneToMany(targetEntity = WordEntity.class, mappedBy = "articleEntity", cascade = {CascadeType.PERSIST})
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

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
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
                ", time=" + time +
                ", category='" + category + '\'' +
                ", wordList=" + wordList +
                '}';
    }
}
