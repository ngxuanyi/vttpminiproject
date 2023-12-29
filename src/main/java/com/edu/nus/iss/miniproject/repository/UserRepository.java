package com.edu.nus.iss.miniproject.repository;

import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import com.edu.nus.iss.miniproject.model.NewUser;
import com.edu.nus.iss.miniproject.model.User;

import jakarta.annotation.Resource;

@Repository
public class UserRepository {
    
     @Resource(name = "userRedisTemplate")

     private ValueOperations<String, String> vOps;
    
    // register new user
     public void addUser (NewUser newUser){ 
        vOps.set(newUser.getUsername(), newUser.getPassword());
    }

    // retrieve existing user
    public String getUser (User user){
        String u = vOps.get(user.getUsername());
        return u;
    }
}
