package com.example.jerrychen.barapp;

/**
 * Created by jerrychen on 4/25/18.
 */

public class Customer extends User {
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    private String gender;
    private int age;
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }

    public Customer( String name, String email) {
        super(name, email);

        gender="unknown";
    }
    public Customer( String name, String email, int age){
        super(name, email);
        this.age=age;
        gender="unknown";
    }
    public Customer( String name, String email, int age,String gender){
        super(name, email);
        this.age=age;
        this.gender=gender;
    }

}
