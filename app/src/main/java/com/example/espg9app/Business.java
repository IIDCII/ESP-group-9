package com.example.espg9app;

public class Business {

    private int id;
    private String name;
    private String iconPath;
    private String description;
    private String tags;
    private float susRating;
    private float userRating;
    private int numReviews;
    private Coordinates coordinates;
    private boolean voucherActive;
    private String discountTiers;
    private String voucherDescription;

    private String email;

    private int[] numRatingArr;

    public String setEmail(String email){ return this.email = email;}

    public String returnEmail(){return email;}

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

    public int[] getNumRatingArr() {
        for(int i = 0; i < numRatingArr.length / 2; i++)
        {
            int temp = numRatingArr[i];
            numRatingArr[i] = numRatingArr[numRatingArr.length - i - 1];
            numRatingArr[numRatingArr.length - i - 1] = temp;
        }
        return numRatingArr;
    }

    public void setNumRatingArr(int[] numRatingArr) {this.numRatingArr = numRatingArr;}

    public int getNumReviews() {
        return numReviews;
    }

    public void setNumReviews(int numReviews) {
        this.numReviews = numReviews;
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

    public String getVoucherDescription() {
        return voucherDescription;
    }

    public void setVoucherDescription(String voucherDescription) {
        this.voucherDescription = voucherDescription;
    }

    public void soutBusiness() {
        System.out.println(this.getName());
        System.out.println(this.getTags());
        System.out.println(this.getDescription());
        System.out.println(this.getIconPath());
        System.out.println(this.getId());
        System.out.println(this.getSusRating());
        System.out.println(this.getUserRating());
        System.out.println(this.getNumReviews());
        System.out.println(this.getCoordinates().getLatitude());
        System.out.println(this.getCoordinates().getLongitude());
        System.out.println(this.getDiscountTiers());
        System.out.println(this.getVoucherDescription());
        System.out.println(this.isVoucherActive());
        System.out.println();
        System.out.println();
    }

}

