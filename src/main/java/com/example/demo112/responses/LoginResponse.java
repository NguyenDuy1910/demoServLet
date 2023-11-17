package com.example.demo112.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginResponse {
    @JsonProperty("message")
    private String message;

    @JsonProperty("token")
    private String token;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public LoginResponse(String message, String token) {
        this.message = message;
        this.token = token;
    }

    public LoginResponse() {
        // Constructor mặc định
    }

    public void setToken(String token) {
        this.token = token;
    }
}

