package com.zooop.zooop_android.ui.fragments;

import junit.framework.TestCase;

/**
 * Created by michael on 22/03/16.
 */
public class DiscoverFragmentTests extends TestCase {
    public void testSplitToStringArray() {
        DiscoverFragment df = new DiscoverFragment();

        String[] testArray = {"hot", "spicy"};
        String[] stringArray = df.splitToStringArray(testArray.toString());

        assertEquals("hot", stringArray[0]);
        assertEquals("spicy", stringArray[1]);
    }
}