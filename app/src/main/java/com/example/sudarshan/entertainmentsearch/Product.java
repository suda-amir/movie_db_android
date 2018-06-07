package com.example.sudarshan.entertainmentsearch;

/**
 * Created by Belal on 10/18/2017.
 */


public class Product {
    private String id;
    private String title;
    private String year;
    private String type;
    private String poster;

    public Product(String id, String title, String year, String type, String poster) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.type = type;
        this.poster = poster;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }
    public String getYear() {
        return year;
    }
    public String getPoster() {
        return poster;
    }
}