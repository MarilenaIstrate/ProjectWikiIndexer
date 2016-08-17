package com.endava.controller;

import com.endava.dto.ArticleDTO;
import com.endava.dto.UserFormDTO;
import com.endava.model.ArticleEntity;
import com.endava.model.WordEntity;
import com.endava.services.MainService;
import com.endava.services.MultiTitlesParser;
import com.endava.services.TextParserService;
import com.endava.services.impl.ArticleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class TestController {

    @Autowired
    MainService mainService;

    @RequestMapping(method = RequestMethod.GET)
    public String homePage() {
        return "greetings";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/")
    public String print(UserFormDTO userFormDTO, Model model) {

        MultipartFile file = userFormDTO.getFileName();
        ArticleDTO articleDTO = null;
        if (file != null && !file.isEmpty()) {
            articleDTO = mainService.getWordsFromFile(file);
            System.out.println(articleDTO);
        }
        else if (userFormDTO.getArticleName() != null){
            articleDTO = mainService.getWordsFromTitle(userFormDTO.getArticleName());
            System.out.println(articleDTO);
        }
        model.addAttribute("article", articleDTO);
        return "greetings";
    }
}
