package com.example.jerrychen.barapp;

import java.lang.reflect.Array;
import java.security.SecureRandom;
import java.util.Date;
import java.util.Map;

/**
 * Created by jerrychen on 5/2/18.
 */

public class Order {
    private Date date;
    private Map<String,Integer> orderMap;
    private Status status;
    private final SecureRandom secureRandom;
    private final String id;
    private int price;
 public Order(Date date, Map orderMap, Status status,int price){
     this.date=date;
     this.orderMap=orderMap;
     this.status=status;
     this.secureRandom=new SecureRandom();
     this.id=generateId();
     this.price=price;
 }

    public Date getDate() {
        return date;
    }
    public Map<String, Integer> getOrderMap() {
        return orderMap;
    }
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    public String generateId(){
        char[] temp=new char[4];
        for (int i=0;i<4;i++){
            temp[i]=(char)(secureRandom.nextInt(10)+48);
        }
        return String.valueOf(temp);
    }
}
