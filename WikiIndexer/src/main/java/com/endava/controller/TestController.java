package com.endava.controller;

import com.endava.dto.UserFormDTO;
import com.endava.model.ArticleEntity;
import com.endava.model.WordEntity;
import com.endava.services.MainService;
import com.endava.services.MultiTitlesParser;
import com.endava.services.TextParserService;
import com.endava.services.impl.ArticleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class TestController {

    @Autowired
    MainService mainService;

    @RequestMapping(method = RequestMethod.GET)
    public void print() {
        /*UserFormDTO userFormDTO = new UserFormDTO();
        userFormDTO.setArticleName("Sand");
        if (userFormDTO.getFileName() == null || userFormDTO.getFileName().isEmpty()) {
            System.out.println(mainService.getWordsFromTitle(userFormDTO.getArticleName()));
        }
        else {
            System.out.println(mainService.getWordsFromFile("titles.txt"));
        }*/
        System.out.println(mainService.getWordsFromFile("titles.txt"));
    }
}
