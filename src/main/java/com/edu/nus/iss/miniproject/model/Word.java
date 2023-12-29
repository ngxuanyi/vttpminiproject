package com.edu.nus.iss.miniproject.model;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;

public class Word implements Serializable {
    @NotBlank(message = "please enter a word")
    private String wordValue;
    private String validity;
    private String definition;

    public Word(String wordValue, String validity, String definition) {
        this.wordValue = wordValue;
        this.validity = validity;
        this.definition = definition;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

    public String getWordValue() {
        return wordValue;
    }

    public Word() {
    }

    public void setWordValue(String wordValue) {
        this.wordValue = wordValue;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

}
