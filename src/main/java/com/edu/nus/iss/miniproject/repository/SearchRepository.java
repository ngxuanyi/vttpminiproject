package com.edu.nus.iss.miniproject.repository;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.stereotype.Repository;

import com.edu.nus.iss.miniproject.model.User;
import com.edu.nus.iss.miniproject.model.Word;

import jakarta.annotation.Resource;

@Repository
public class SearchRepository {
    
//     @Resource(name = "wordRedisTemplate")
//     private HashOperations <String, String, String> hOps;
    
//     public void saveSearchInput (String username, Word word) {
//         hOps.put(username, word.getWordValue(), word.getDefinition());
//     }


//     public Map<String, String> getAllHistory (String username, Word word) {
//         Map<String, String> historyList = hOps.entries(username);
        
//         return historyList;

//     }    
// }

@Resource (name = "wordRedisTemplate")
private ListOperations <String, String> lOps;

public void saveSearchInput (String username, Word word){
       // System.out.printf("word definition:%s",word.getDefinition());
        String record = "%s!%s!%s".formatted(word.getWordValue(),word.getDefinition(),word.getValidity());
        //System.out.printf("\nWord Record:%s\n", record);
        //System.out.printf("\nUsername:%s\n", username);
        lOps.leftPush(username, record);
    
}

    public List<Word> getAllHistory(String username){
        long historySize = lOps.size(username);
        List<String> historyList = lOps.range(username, 0, historySize);
        List<Word> wordList = new LinkedList<>();
        for(String i : historyList) {
            String[] terms = i.split("!");
            Word word =  new Word();
            word.setWordValue(terms[0]);
            word.setDefinition(terms[1]);
            word.setValidity(terms[2]);
            wordList.add(word);
        }
        
        return wordList;
    }

}


    
    
    

