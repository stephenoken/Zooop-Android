package com.zooop.zooop_android;

import junit.framework.TestCase;

/**
 * Created by michael on 07/03/16.
 */
public class DiscoverAdsTest extends TestCase {
    public void testConstructor() {
        //init
        String name = "aloha";
        String description = "this is a test";
        String imageUrl = "https://pbs.twimg.com/profile_images/616076655547682816/6gMRtQyY.jpg";
        String id = "ab123";
        String[] tagsArray = {"hot", "spicy"};
        String created = "2016-03-22T15:16:34.171Z";
        String shop = "McDonalds";
        String[] shopLoc = {"53.350140", "-6.266155"};

        DiscoverAds testAd = new DiscoverAds(id, name, description, imageUrl, tagsArray, created, shop, shopLoc);

        // tests
        assertEquals(testAd.getName(), name);
        assertEquals(testAd.getDescription(), description);
        assertEquals(testAd.getImage(), imageUrl);
        assertEquals(testAd.getId(), id);
        assertEquals(testAd.getTags(), tagsArray);
        assertEquals(testAd.getCreatedAt().toString(), "Tue Mar 22 15:16:34 EDT 2016");
        assertEquals(testAd.getShopName(), shop);
        assertEquals(testAd.getShopLocation().getLatitude(), 53.350140);
        assertEquals(testAd.getShopLocation().getLongitude(), -6.266155);
    }

    public void testRemoveTimeZomeFromTimeString() {
        String name = "";
        String description = "";
        String imageUrl = "";
        String id = "";
        String[] tagsArray = {""};
        String created = "2016-03-22T15:16:34.171Z";
        String shop = "";
        String[] shopLoc = {"53.350140", "-6.266155"};

        DiscoverAds testAd = new DiscoverAds(id, name, description, imageUrl, tagsArray, created, shop, shopLoc);

        String result = testAd.removeTimeZomeFromTimeString(created);
        assertEquals(result, "2016-03-22T15:16:34");
    }

    public void testDoubleFromLocationArray() {
        String name = "";
        String description = "";
        String imageUrl = "";
        String id = "";
        String[] tagsArray = {""};
        String created = "2016-03-22T15:16:34.171Z";
        String shop = "";
        String[] shopLoc = {"53.350140", "-6.266155"};

        DiscoverAds testAd = new DiscoverAds(id, name, description, imageUrl, tagsArray, created, shop, shopLoc);

        String[] test = {"53.3437651", "-6.2505505999999995"};
        double[] result = testAd.doubleFromLocationArray(test);

        assertEquals(result[0], 53.3437651);
        assertEquals(result[1], -6.2505505999999995);
    }
}