package com.example.jerrychen.barapp;

import java.lang.reflect.Array;
import java.security.SecureRandom;
import java.util.Date;
import java.util.Map;

/**
 * Created by jerrychen on 5/2/18.
 */

public class Order {
    public void setDate(Date date) {
        this.date = date;
    }

    private Date date;
    private Map<String,Integer> orderMap;
    private Status status;

    public String getId() {
        return id;
    }

    private final String id;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    private int price;
    public Order(){
        this.id=generateId();
    }
 public Order(Date date, Map orderMap,Status status,int price){
     this.date=date;
     this.orderMap=orderMap;
     this.status=status;
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
     SecureRandom secureRandom=new SecureRandom();
        char[] temp=new char[4];
        for (int i=0;i<4;i++){
            temp[i]=(char)(secureRandom.nextInt(10)+48);
        }
        return String.valueOf(temp);
    }
}
