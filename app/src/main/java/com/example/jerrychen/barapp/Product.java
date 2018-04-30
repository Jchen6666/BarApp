package com.example.jerrychen.barapp;

import android.media.Image;

/**
 * Created by Kangur on 09.04.2018.
 */

public class Product {

    private String pictureUrl;
    private String name;
    private int price;
    private double volume;
    private String description;
    private ProductCategory category;
    private Boolean availability;

    public Product() {
    }

    public Product(String pictureUrl, String name, int price, double volume, String description, ProductCategory category, Boolean availability) {
        this.pictureUrl = pictureUrl;
        this.name = name;
        this.price = price;
        this.volume = volume;
        this.description = description;
        this.category = category;
        this.availability = availability;
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
