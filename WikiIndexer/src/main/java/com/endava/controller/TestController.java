package com.endava.controller;

import com.endava.dto.ArticleDTO;
import com.endava.dto.UserFormDTO;
import com.endava.services.MainService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import java.util.*;

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
        List<ArticleDTO> articlesDTO = null;
        if (file != null && !file.isEmpty()) {
            articlesDTO = mainService.getWordsFromFile(file);
            System.out.println(articlesDTO);
        }
        else if (userFormDTO.getArticleName() != null){
            articlesDTO = mainService.getWordsFromTitle(userFormDTO.getArticleName());
            System.out.println(articlesDTO);
        }
        if (articlesDTO == null)
            articlesDTO = new ArrayList<>();
        /*ObjectMapper mapper = new ObjectMapper();
        String json = null;
        try {
            json = mapper.writeValueAsString(articlesDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        model.addAttribute("articles", articlesDTO);
        return "chart";
    }
}
