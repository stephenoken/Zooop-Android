package com.zooop.zooop_android;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by michael on 26/02/16.
 */
public class DiscoverAds {
    public static final ArrayList<DiscoverAds> ads = new ArrayList<DiscoverAds>();
    private String id;
    private String name;
    private String description;
    private String image;
    private String[] tags;
    private String shopName;
    private Date createdAt;
    private double[] loc;
    private LCoordinates shopLocation;


    /** getters **/
    public String getId() {
        return this.id;
    }
    public String getName() {
        return this.name;
    }
    public String getDescription() {
        return this.description;
    }
    public String getImage() {
        return this.image;
    }
    public Date getCreatedAt() {
        return this.createdAt;
    }
    public String[] getTags() {
        return this.tags;
    }
    public String getShopName() {
        return this.shopName;
    }
    public double[] getLoc() {
        return loc;
    }
    public LCoordinates getShopLocation() {
        return this.shopLocation;
    }

    /** constructor **/
    public DiscoverAds(String adId, String adName, String adDescription, String adImage, String[] adTags, String adCreatedAt, String shopName, String[] shopLocation) {
        this.id = adId;
        this.name = adName;
        this.description = adDescription;
        this.image = adImage;
        this.tags = adTags;
        this.shopName = shopName;
        this.createdAt = dateFromDateString(adCreatedAt);

        double[] loc = doubleFromLocationArray(shopLocation);
        this.shopLocation = new LCoordinates(loc[0], loc[1]);
    }

    private Date dateFromDateString(String dateString) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String newDateString = removeTimeZomeFromTimeString(dateString);

        try {
            Date date = format.parse(newDateString);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            e.printStackTrace();
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, 2016);
            cal.set(Calendar.MONTH, Calendar.JANUARY);
            cal.set(Calendar.DAY_OF_MONTH, 1);
            return cal.getTime();
        }
    }

    protected String removeTimeZomeFromTimeString(String dateString) {
        String resultDateString = "";

        Pattern pattern = Pattern.compile("((.*?)T(.*?))\\.");
        Matcher matcher = pattern.matcher(dateString);
        if (matcher.find()) {
            resultDateString = matcher.group(1);
        }
        else {
            System.out.println("NO MATCH");
        }

        return resultDateString;
    }

    protected double[] doubleFromLocationArray(String[] locStr) {
        double a = Double.parseDouble(locStr[0]);
        double b = Double.parseDouble(locStr[1]);
        double[] dArray = new double []{a,b};

        return dArray;
    }
}
