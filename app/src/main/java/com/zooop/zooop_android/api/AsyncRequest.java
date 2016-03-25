package com.zooop.zooop_android.api;

import android.util.Log;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

public final class AsyncRequest {
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private final static String BASEURL = "http://10.6.36.151:5001";

    public Request apiRequest(String apiCall, String bodyString) throws Exception {
        RequestBody requestBody = RequestBody.create(JSON, bodyString);
        Log.d("boooty", bodyString);
        String urlString = BASEURL + apiCall;

        Request request = new Request.Builder()
                .url(urlString)
                .post(requestBody)
                .build();

        return request;
    }

    public Request imageRequest(String url) throws Exception {
        Request request = new Request.Builder()
                .url(url)
                .build();

        return request;
    }

}
