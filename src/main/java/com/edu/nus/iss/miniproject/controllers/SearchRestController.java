package com.edu.nus.iss.miniproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.edu.nus.iss.miniproject.model.Word;
import com.edu.nus.iss.miniproject.service.SearchService;

@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class SearchRestController {
    @Autowired
    private SearchService dbSvc;

    @GetMapping("/result")
    public Word getResult(@RequestParam String word) {
        return dbSvc.getWord(word);
    }
}
