package com.example.jerrychen.barapp;

import java.io.Serializable;
import java.security.SecureRandom;

/**
 * Class resembling Products
 * Implements {@link Serializable} to enable passing the object in a {@link android.os.Bundle} using {@link android.content.Intent}
 */
public class Product implements Serializable{

    private String pictureUrl;
    private String ID;
    private String name;
    private int price;
    private double volume;
    private String description;
    private ProductCategory category;
    private Boolean availability;

    public Product() {
        this.ID=generateID();
    }

    public String getID() {
        return ID;
    }

    public Product(String pictureUrl, String name, int price, double volume, String description, ProductCategory category, Boolean availability) {
        this.ID=generateID();
        this.pictureUrl = pictureUrl;
        this.name = name;
        this.price = price;
        this.volume = volume;
        this.description = description;
        this.category = category;
        this.availability = availability;
    }

    private String generateID(){
        char[] myID=new char[10];
        SecureRandom secureRandom=new SecureRandom();
        for(int i=0;i<myID.length;i++){
                myID[i] = (char)(secureRandom.nextInt(25)+65);
        }
        return String.valueOf(myID);
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public Boolean getAvailability() {
        return availability;
    }

    public void setAvailability(Boolean availability) {
        this.availability = availability;
    }
}
