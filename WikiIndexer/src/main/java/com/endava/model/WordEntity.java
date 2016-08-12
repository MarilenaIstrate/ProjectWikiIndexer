package com.endava.model;
import javax.persistence.*;

/**
 * Created by mistrate on 8/11/2016.
 */
@Entity
@Table(name = "word")
public class WordEntity {

    @Id
    @Column(name = "id_word")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int wid;

    @Column(name = "word")
    private String word;

    @Column(name = "apparitions_nr")
    private int nrAppar;

    @ManyToOne
    @JoinColumn(name = "id_article")
    private ArticleEntity articleEntity;

    public ArticleEntity getArticleEntity() {
        return articleEntity;
    }

    public void setArticleEntity(ArticleEntity articleEntity) {
        this.articleEntity = articleEntity;
    }

    public int getWid() {
        return wid;
    }

    public void setWid(int wid) {
        this.wid = wid;
    }

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
        return "WordEntity{" +
                "wid=" + wid +
                ", word='" + word + '\'' +
                ", nrAppar=" + nrAppar +
                '}';
    }
}
