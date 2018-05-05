package com.example.jerrychen.barapp;

import java.io.Serializable;

/**
 * Created by jerrychen on 4/25/18.
 */

public class Staff extends User implements Serializable {
    public Staff(){};
    public Staff(String name, String email) {
        super(name, email,true);
    }
}
