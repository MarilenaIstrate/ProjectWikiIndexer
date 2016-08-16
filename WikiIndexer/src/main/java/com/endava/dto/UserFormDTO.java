package com.endava.dto;

import org.springframework.web.multipart.MultipartFile;

public class UserFormDTO {

    private String articleName;
    private MultipartFile fileName;

    public String getArticleName() {
        return articleName;
    }

    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }

    public MultipartFile getFileName() {
        return fileName;
    }

    public void setFileName(MultipartFile fileName) {
        this.fileName = fileName;
    }
}
