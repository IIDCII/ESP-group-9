package com.example.espg9app;

public class Coordinates {
    private double latitude;
    private double longitude;

    public Coordinates(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void setLatitude(double x) {
        this.latitude = x;
    }

    public void setLongitude(double x) {
        this.longitude = x;
    }

    public double getLatitude() {
         return this.latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }
}
