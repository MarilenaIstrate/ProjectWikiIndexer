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
    public @ResponseBody ArticleDTO print(UserFormDTO userFormDTO) {

        MultipartFile file = userFormDTO.getFileName();
       /* System.out.println(userFormDTO.getArticleName());
        if (file!= null && !file.isEmpty()) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
                String line = null;
                while((line = reader.readLine()) != null)
                    System.out.println(line);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
            System.out.println(file.getName());
        return "greetings";*/
        System.out.println("HERE");
        ArticleDTO articleDTO = null;
        if (file != null && !file.isEmpty()) {
            articleDTO = mainService.getWordsFromFile(file);
            System.out.println(articleDTO);
        }
        else if (userFormDTO.getArticleName() != null){
            articleDTO = mainService.getWordsFromTitle(userFormDTO.getArticleName());
            System.out.println(articleDTO);
        }
        return articleDTO;
        /*UserFormDTO userFormDTO = new UserFormDTO();
        userFormDTO.setArticleName("Sand");
        if (userFormDTO.getFileName() == null || userFormDTO.getFileName().isEmpty()) {
            System.out.println(mainService.getWordsFromTitle(userFormDTO.getArticleName()));
        }
        else {
            System.out.println(mainService.getWordsFromFile("titles.txt"));
        }*/
        //System.out.println(mainService.getWordsFromFile("titles.txt"));
    }
}
