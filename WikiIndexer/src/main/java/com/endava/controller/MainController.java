package com.endava.controller;

import com.endava.dto.ArticleDTO;
import com.endava.dto.UserFormDTO;
import com.endava.services.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import java.util.*;

/**
 * Application controller
 */
@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    MainService mainService;

    /**
     * Starting page
     * @return path to the main page
     */
    @RequestMapping(method = RequestMethod.GET)
    public String homePage() {
        return "mainPage";
    }

    /**
     * Solves POST method
     * @param userFormDTO form values
     * @param model model attributes to hold the response
     * @return path to the chart page
     */
    @RequestMapping(method = RequestMethod.POST, value = "/")
    public String print(UserFormDTO userFormDTO, Model model) {

        MultipartFile file = userFormDTO.getFileName();
        List<ArticleDTO> articlesDTO = null;
        if (file != null && !file.isEmpty()) {
            articlesDTO = mainService.getWordsFromFile(file);
        }
        else if (userFormDTO.getArticleName() != null){
            articlesDTO = mainService.getWordsFromTitle(userFormDTO.getArticleName());
        }
        if (articlesDTO == null)
            articlesDTO = new ArrayList<>();
        model.addAttribute("articles", articlesDTO);
        return "chart";
    }
}
