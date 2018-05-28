package com.example.jerrychen.barapp;

import java.io.Serializable;

/**
 * A simple, more specified User subclass
 * @see User
 */

public class Staff extends User implements Serializable {
    public Staff(){};
    public Staff(String name, String email) {
        super(name, email,true);
    }
}
