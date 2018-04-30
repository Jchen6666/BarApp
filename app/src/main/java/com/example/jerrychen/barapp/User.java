package com.example.jerrychen.barapp;

/**
 * Created by jerrychen on 4/11/18.
 */

public abstract class User {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String name;
    private String email;


    private String gender;

    public User(String name, String email){
        this.name=name;
        this.email=email;
    }

}
