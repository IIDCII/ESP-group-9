package com.example.espg9app;

public class Coordinates {
    private float latitude;
    private float longitude;

    public Coordinates(float latitude, float longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void setLatitude(float x) {
        this.latitude = x;
    }

    public void setLongitude(float x) {
        this.longitude = x;
    }

    public double getLatitude() {
         return this.latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }
}
