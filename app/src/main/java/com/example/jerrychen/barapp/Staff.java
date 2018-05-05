package com.example.jerrychen.barapp;

/**
 * Created by jerrychen on 4/25/18.
 */

public class Staff extends User {
    private boolean isStaff;
    public Staff(String name, String email, boolean isStaff) {
        super(name, email);
        this.isStaff=isStaff;
    }


}
