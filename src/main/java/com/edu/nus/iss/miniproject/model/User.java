package com.edu.nus.iss.miniproject.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class User {
    
    @NotBlank (message = "username cannot be blank")
    @Size (min = 3, max = 50, message = "minimum of 3 characters and maximum of 50 characters allowed")
    private String username;


    @NotBlank (message = "password cannot be blank")
    @Size (min = 3, max = 50, message = "minimum of 3 characters and maximum of 50 characters allowed")
    private String password;

    public User() {
    }


    public User(@NotBlank(message = "username cannot be blank") String username,
            @NotBlank(message = "password cannot be blank") String password) {
        this.username = username;
        this.password = password;
    }


    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }

}
