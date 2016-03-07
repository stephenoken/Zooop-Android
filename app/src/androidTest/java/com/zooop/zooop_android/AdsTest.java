package com.zooop.zooop_android;

import junit.framework.TestCase;

/**
 * Created by michael on 07/03/16.
 */
public class AdsTest extends TestCase {
    public void testConstructor() {
        //init
        String name = "aloha";
        String description = "this is a test";
        String image = "https://pbs.twimg.com/profile_images/616076655547682816/6gMRtQyY.jpg";
        String id = "ab123";

        double lat = 53.350140;
        double lon = -6.266155;
        String location = "[\"" + lat +"\", \"" + lon + "\"]";


        Ads testAd = new Ads(name, description, image, id, location);

        // tests
        assertEquals(testAd.getName(), name);
        assertEquals(testAd.getDescription(), description);
        assertEquals(testAd.getLocation()[0], lat);
        assertEquals(testAd.getLocation()[1], lon);
    }
}