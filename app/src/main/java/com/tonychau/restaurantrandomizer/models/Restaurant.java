package com.tonychau.restaurantrandomizer.models;

public class Restaurant {
    private String id;
    private String name;
    private String imageUrl;
    private String yelpStars;
    private String cruisineType;

    public Restaurant(String id, String name, String imageUrl, String yelpStars, String cruisineType) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.yelpStars = yelpStars;
        this.cruisineType = cruisineType;
    }

    public String getYelpStars() {
        return yelpStars;
    }

    public void setYelpStars(String yelpStars) {
        this.yelpStars = yelpStars;
    }

    public String getCruisineType() {
        return cruisineType;
    }

    public void setCruisineType(String cruisineType) {
        this.cruisineType = cruisineType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
