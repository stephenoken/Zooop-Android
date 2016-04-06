package com.zooop.zooop_android.api;

import android.util.Log;

import junit.framework.TestCase;

import org.json.JSONObject;

/**
 * Created by michael on 06/04/16.
 */
public class APIServiceTest extends TestCase {

    public void testGetParamsJSON() throws Exception {

        APIService apiService = new APIService();

        String[] keys = {"test","name","hi"};
        String[] values = {"42","no lo se", "ack"};

        String jsonParamsStr = apiService.getParamsJSON(keys, values).toString();

        assertEquals(jsonParamsStr, "{\"test\":\"42\",\"name\":\"no lo se\",\"hi\":\"ack\"}");
    }
}