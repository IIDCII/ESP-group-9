package com.example.espg9app;

public class Business {

    private int id;
    private String name;
    private String iconPath;
    private String description;
    private String tags;
    private float susRating;
    private float userRating;
    private Coordinates coordinates;
    private boolean voucherActive;
    private String discountTiers;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public float getSusRating() {
        return susRating;
    }

    public void setSusRating(float susRating) {
        this.susRating = susRating;
    }

    public float getUserRating() {
        return userRating;
    }

    public void setUserRating(float userRating) {
        this.userRating = userRating;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public boolean isVoucherActive() {
        return voucherActive;
    }

    public void setVoucherActive(boolean voucherActive) {
        this.voucherActive = voucherActive;
    }

    public String getDiscountTiers() {
        return discountTiers;
    }

    public void setDiscountTiers(String discountTiers) {
        this.discountTiers = discountTiers;
    }

}
