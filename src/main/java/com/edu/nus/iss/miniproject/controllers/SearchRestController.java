package com.edu.nus.iss.miniproject.controllers;

import jakarta.json.Json;

import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.edu.nus.iss.miniproject.model.Word;
import com.edu.nus.iss.miniproject.repository.SearchRepository;
import com.edu.nus.iss.miniproject.service.SearchService;

import jakarta.json.JsonObject;

@RestController
@RequestMapping(path = "/dictionary/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class SearchRestController {
    @Autowired
    private SearchService dbSvc;

    @Autowired
    private SearchRepository searchRepo;

    @GetMapping("/result")
    public Word getResult(@RequestParam String word) {
        return dbSvc.getWord(word);
    }

//     @GetMapping("/history/{username}")
//     public  ResponseEntity<String> getUser(@PathVariable(name = "username", required = true) String username, Word word) {
//         Map<String,String> map = searchRepo.getAllHistory("username", word);
//         Set<String> keyset = map.keySet();
//         map.get(key)
//         String searchedWord = searchRepo.getAllHistory("username", word.getWordValue()).toString();
//         String searchResult = searchRepo.getAllHistory("username", null).toString();
//         JsonObject resp =  Json.createObjectBuilder()
//         .add("username", username)
//         .add("wordValue", searchedWord)
//         .add("wordDefinition", searchResult)
//         .build();
//     return ResponseEntity.ok(resp.toString());
//     }
}
