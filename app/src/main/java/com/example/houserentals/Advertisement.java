package com.example.houserentals;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Advertisement {

    private String title;
    private String shortdesc;
    private String city;
    private String price;
    private String url;

    public Advertisement() {

    }

    public Advertisement(String title, String shortdesc, String city, String price, String url) {

        this.title = title;
        this.shortdesc = shortdesc;
        this.city = city;
        this.price = price;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getShortdesc() {
        return shortdesc;
    }

    public String getCity() {
        return city;
    }

    public String getPrice() {
        return price;
    }

    public String getUrl() {
        return url;
    }
}
