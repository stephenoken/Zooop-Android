package com.zooop.zooop_android;


/**
 * Created by michael on 26/02/16.
 */
public class Ads {

    private String name;
    private String description;
    private String image;
    private String id;
    private double[] loc;
    private LCoordinates location;

    /** getters **/
    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public String getImage() {
        return this.image;
    }

    public LCoordinates getLocation() {
        return this.location;
    }

    public Ads(String name, String description, String image, String id, String location) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.id = id;

        double[] loc = doubleFromLocationArray(location);
        this.location = new LCoordinates(loc[0], loc[1]);
    }

    private double[] doubleFromLocationArray(String jsonStr) {
        String cleanStr = jsonStr.replaceAll("[^\\d.,-]", "");
        String[] locationArray = cleanStr.split(",");
        double a = Double.parseDouble(locationArray[0]);
        double b = Double.parseDouble(locationArray[1]);
        double[] dArray = new double []{a,b};
        return dArray;
    }
}
