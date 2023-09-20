package com.example.demo112.models;

import java.io.Serializable;
public class User implements Serializable {
    private String lastName;
    private String firstName;
    private String emailStr;

    public User(String lastName, String firstName, String email){
        this.lastName = lastName;
        this.firstName = firstName;
        this.emailStr = email;
    }
    public void setEmail(String email){
        this.emailStr = email;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public String getEmail(){
        return this.emailStr;
    }

    public String getLastName(){
        return this.lastName;
    }

    public String getFirstName(){
        return this.firstName;
    }
}