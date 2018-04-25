package com.example.jerrychen.barapp;

/**
 * Created by jerrychen on 4/25/18.
 */

public class Customer extends User {
    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    private String age,gender;
    public Customer( String name, String email) {
        super(name, email);
        gender="unknown";
    }
    public Customer( String name, String email, String age){
        super(name, email);
        this.age=age;
        gender="unknown";
    }
    public Customer( String name, String email, String age,String gender){
        super(name, email);
        this.age=age;
        this.gender=gender;
    }

}
