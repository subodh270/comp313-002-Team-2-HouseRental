package com.example.houserentals;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Advertisement {

    private String advertisementId;
    private String title;
    private String shortdesc;
    private String city;
    private String price;
    private String userId;
    private String url;



    public Advertisement() {

    }

    public Advertisement(String advertisementId, String title, String shortdesc, String city, String price, String userId, String url) {

        this.advertisementId = advertisementId;
        this.title = title;
        this.shortdesc = shortdesc;
        this.city = city;
        this.price = price;
        this.userId = userId;
        this.url = url;
    }

    public String getAdvertisementId() {
        return advertisementId;
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

    public String getUserId() {
        return userId;
    }

    public String getUrl() {
        return url;
    }
}
