package com.edu.nus.iss.miniproject.repository;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import com.edu.nus.iss.miniproject.model.NewUser;
import com.edu.nus.iss.miniproject.model.User;

import jakarta.annotation.Resource;

@Repository
public class UserRepository {
    
     @Resource(name = "userRedisTemplate")
    private RedisTemplate <String, String> template;

    @Resource(name = "userRedisTemplate")
     private ValueOperations<String, String> vOps;
    
    //register new user
     public void addUser (NewUser newUser){ 
        //System.out.printf("UserNameRepo:%s",newUser.getUsername());
        vOps.set(newUser.getUsername(), newUser.getPassword());
    }

    // public boolean hasUser(NewUser newUser) {
    //     return vOps.setIfAbsent(newUser.getUsername(), newUser.getPassword());
    // }

    // public boolean hasUser(User user){
    //     return template.hasKey(user.getUsername());
    // }
    public boolean hasUser(String username){
        return template.hasKey(username);
    }


    // retrieve existing user
    public String getUser (User user){
        String existingUser = vOps.get(user.getUsername());
        return existingUser;
    }

    // retrieve existing user
    public boolean checkPassword (String username, User user){
        //System.out.printf("UserNameRepo:%s\n",username);

        return vOps.get(username).equals(user.getPassword());
        //System.out.printf("usernameUser:%s\n",user.getUsername());
        // System.out.println(bool);
        // return bool;
    }
}
