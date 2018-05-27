package com.example.jerrychen.barapp;

import java.io.Serializable;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

/**
 * Class resembling Orders
 * Implements {@link Serializable} to enable passing the object in a {@link android.os.Bundle} using {@link android.content.Intent}
 */
public class Order implements Serializable{
    private Date date;
    private Map<String,ArrayList<String>> orderMap;
    private Status status;
    private String color;
    private final String id;
    //4 digit code to verify transaction
    private final String code;
    private int price;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public Order(){
        this.id=generateId();
        this.code=generateCode();
        this.color="#99ff99";
    }

    public String getCode() {
        return code;
    }

    public Order(Date date, Map orderMap, int price){
        this.date=date;
        this.orderMap=orderMap;
        setStatus(Status.unpaid);
        this.id=generateId();
        this.code=generateCode();
        this.price=price;
        this.color="#99ff99";

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

    /**
     * Method to generate a 10 digit ID
     * @return ten digit ID of type String
     */
    public String generateId(){
        char[] temp=new char[10];
        SecureRandom secureRandom=new SecureRandom();
        for (int i=0;i<10;i++){
            temp[i]=(char)(secureRandom.nextInt(10)+48);
        }
        return String.valueOf(temp);
    }

    /**
     * Method to generate a 4 digit Order code
     * @return four digit order code of type String
     */
    public String generateCode(){
        char[] temp=new char[4];
        SecureRandom secureRandom=new SecureRandom();
        for (int i=0;i<4;i++){
            temp[i]=(char)(secureRandom.nextInt(10)+48);
        }
        return String.valueOf(temp);
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

}
