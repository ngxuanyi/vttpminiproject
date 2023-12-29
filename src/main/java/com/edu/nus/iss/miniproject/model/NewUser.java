package com.edu.nus.iss.miniproject.model;

import jakarta.validation.constraints.NotBlank;

public class NewUser {
    @NotBlank (message = "please input your full name")
    private String fullName;
    
    @NotBlank (message = "please set a username")
    private String username;

    @NotBlank (message = "please set a password")
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
