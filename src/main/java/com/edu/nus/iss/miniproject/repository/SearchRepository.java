package com.edu.nus.iss.miniproject.repository;

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
    
    @Resource(name = "wordRedisTemplate")
    private HashOperations <String, String, String> hOps;
    
    public void saveSearchInput (String username, Word word) {

        hOps.put(username, word.getWordValue(), word.getDefinition());
    }



    public Map<String, String> getAllHistory (String username, Word word) {
        Map<String, String> historyList = hOps.entries(username);
        
        return historyList;

    }    
}
    
    
    

