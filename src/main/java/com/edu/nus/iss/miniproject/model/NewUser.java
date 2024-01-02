package com.edu.nus.iss.miniproject.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class NewUser {
    @NotBlank (message = "please input your full name")
    private String fullName;
    
    @NotBlank (message = "please set a username")
    @Size (min = 3, max = 50, message = "minimum of 3 characters and maximum of 50 characters allowed")
    private String username;

    @NotBlank (message = "please set a password")
    @Size (min = 3, max = 50, message = "minimum of 3 characters and maximum of 50 characters allowed")
    private String password;

    @NotBlank (message = "please confirm your password")
    private String confirmedPassword;

    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
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
    public String getConfirmedPassword() {
        return confirmedPassword;
    }
    public void setConfirmedPassword(String confirmedPassword) {
        this.confirmedPassword = confirmedPassword;
    }
    
}
