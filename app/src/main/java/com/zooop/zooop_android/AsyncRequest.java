package com.zooop.zooop_android;

import android.util.Log;

import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public final class AsyncRequest {
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private final static String BASEURL = "http://www.zooop.xyz/api/";
    private final OkHttpClient client = new OkHttpClient();
    API api = new API();

    public void run(String apiCall, String bodyString) throws Exception {
        RequestBody requestBody = RequestBody.create(JSON, bodyString);
        String urlString = BASEURL + apiCall;

        Request request = new Request.Builder()
                .url(urlString)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                Headers responseHeaders = response.headers();
                for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                    Log.d("RESPONSE HEADER:", responseHeaders.name(i) + ": " + responseHeaders.value(i));
                }

                api.receiveResponse(response.body().string());
            }
        });
    }
}
