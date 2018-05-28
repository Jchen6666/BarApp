package com.example.jerrychen.barapp;

import java.io.Serializable;

/**
 * A simple, more specified User subclass
 * @see User
 */

public class Customer extends User implements Serializable {
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
    private Gender gender;
    private int age;
    public Gender getGender() {
        return gender;
    }
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Customer(){
    }

    public Customer( String name, String email) {
        super(name, email,false);
        gender=Gender.unknown;

    }
    public Customer( String name, String email, int age){
        super(name, email,false);
        this.age=age;
        gender=Gender.unknown;
    }
    public Customer( String name, String email, int age,String gender){
        super(name, email,false);
        this.age=age;
        this.gender=Gender.unknown;
    }

}
