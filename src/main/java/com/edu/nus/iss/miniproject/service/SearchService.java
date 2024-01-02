package com.edu.nus.iss.miniproject.service;

import java.io.StringReader;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.edu.nus.iss.miniproject.model.Word;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Service
public class SearchService {

   @Value("${dictionaryapi.key}")
   private String apiKey;


    // returns the word 
    public Word getWord(String word) {
    String url = UriComponentsBuilder
            .fromUriString("https://api.api-ninjas.com/v1/dictionary/?")
            .queryParam("word", word)
            .toUriString();

    RequestEntity<Void> req = RequestEntity.get(url)
        .header("X-Api-Key", apiKey) 
        .build();

    RestTemplate template = new RestTemplate();

    ResponseEntity<String> resp = template.exchange(req, String.class);

    String payload = resp.getBody();
    JsonReader reader = Json.createReader(new StringReader(payload));
    JsonObject outputObject = reader.readObject();
    
    //inputting word into constructor
    String wordValue = outputObject.getString("word");
    String definition = outputObject.getString("definition");
    String validity = String.valueOf(outputObject.getBoolean("valid"));
    Word newWord = new Word(wordValue, validity, definition);
    
    return newWord;
    
}


}


