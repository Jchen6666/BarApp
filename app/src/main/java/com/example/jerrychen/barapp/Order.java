package com.example.jerrychen.barapp;

import java.lang.reflect.Array;
import java.security.SecureRandom;
import java.util.ArrayList;
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
    private Map<String,ArrayList<String>> orderMap;
    private Status status;

    public String getCode() {
        return code;
    }

    private final String code;
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
        this.code=generateCode();
    }

    public void setOrderMap(Map<String, ArrayList<String>> orderMap) {
        this.orderMap = orderMap;
    }

    public Order(Date date, Map orderMap, Status status, int price){
     this.date=date;
     this.orderMap=orderMap;
     this.status=status;
     this.id=generateId();
     this.code=generateCode();

     this.price=price;
 }


    public Date getDate() {
        return date;
    }
    public Map<String, ArrayList<String>> getOrderMap() {
        return orderMap;
    }
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    public String generateId(){
        char[] temp=new char[10];
        SecureRandom secureRandom=new SecureRandom();
        for (int i=0;i<10;i++){
            temp[i]=(char)(secureRandom.nextInt(10)+48);
        }
        return String.valueOf(temp);
    }
    public String generateCode(){
        char[] temp=new char[4];
        SecureRandom secureRandom=new SecureRandom();
        for (int i=0;i<4;i++){
            temp[i]=(char)(secureRandom.nextInt(10)+48);
        }
        return String.valueOf(temp);
    }
}
