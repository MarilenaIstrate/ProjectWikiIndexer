package com.endava.controller;

import com.endava.services.TextParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class TestController {

    @Autowired
    TextParserService textParserService;

    @RequestMapping(method = RequestMethod.GET)
    public void print() {
        try {
            textParserService.getTopWords("Beach");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
