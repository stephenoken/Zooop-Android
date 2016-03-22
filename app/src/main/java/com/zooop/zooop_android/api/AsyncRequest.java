package com.zooop.zooop_android.api;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

public final class AsyncRequest {
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private final static String BASEURL = "http://www.zooop.xyz/api/";

    public Request apiRequest(String apiCall, String bodyString) throws Exception {
        RequestBody requestBody = RequestBody.create(JSON, bodyString);
        //String urlString = BASEURL + apiCall;

        String urlString = "http://10.6.48.207:3000/adverts-api/get-android-ads";

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
