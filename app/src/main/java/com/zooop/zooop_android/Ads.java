package com.zooop.zooop_android;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.util.Log;

import com.zooop.zooop_android.ui.ApiCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by michael on 26/02/16.
 */
public class Ads {

    private String name;
    private String description;
    private String image;
    private String id;
    private double[] location;

    /** getters **/
    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public double[] getLocation() {
        return this.location;
    }

    public Ads(String name, String description, String image, String id, String location) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.id = id;
        this.location = doubleFromLocationArray(location);
    }

    protected double[] doubleFromLocationArray(String jsonStr) {
        String cleanStr = jsonStr.replaceAll("[^\\d.,-]", "");
        String[] locationArray = cleanStr.split(",");
        double a = Double.parseDouble(locationArray[0]);
        double b = Double.parseDouble(locationArray[1]);
        double[] dArray = new double []{a,b};
        return dArray;
    }
}
