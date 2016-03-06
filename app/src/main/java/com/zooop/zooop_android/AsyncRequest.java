package com.zooop.zooop_android;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public final class AsyncRequest {
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private final static String BASEURL = "http://www.zooop.xyz/api/";
    private final OkHttpClient client = new OkHttpClient();

    public Request run(String apiCall, String bodyString) throws Exception {
        RequestBody requestBody = RequestBody.create(JSON, bodyString);
        String urlString = BASEURL + apiCall;

        Request request = new Request.Builder()
                .url(urlString)
                .post(requestBody)
                .build();

        return request;
    }
}
