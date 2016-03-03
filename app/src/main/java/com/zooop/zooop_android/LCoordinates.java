package com.zooop.zooop_android;

/**
 * Created by michael on 26/02/16.
 */
public class LCoordinates {

    private double latitude;
    private double longitude;

    public LCoordinates(double latitude,double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }
}
