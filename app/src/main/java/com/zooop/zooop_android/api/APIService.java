package com.zooop.zooop_android.api;

/**
 * Created by michael on 17/02/16.
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class APIService extends AppCompatActivity {
    private final OkHttpClient client = new OkHttpClient();
    private ApiCallback callback;
    private ImageCallback imageCallback;

    public APIService(ApiCallback callback){
        this.callback = callback;
    }

    public APIService(ImageCallback callback){
        this.imageCallback = callback;
    }

    public void ApiRequest(Request request) {
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                callback.receivedResponse("?");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                Headers responseHeaders = response.headers();
                for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                    Log.d("RESPONSE HEADER:", responseHeaders.name(i) + ": " + responseHeaders.value(i));
                }

                String responseBody = response.body().string();
                System.out.println(responseBody);

                callback.receivedResponse(responseBody);
            }
        });
    }

    public void ImageRequest(Request request) {
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                callback.receivedResponse("?");
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                Headers responseHeaders = response.headers();
                for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                    Log.d("RESPONSE HEADER:", responseHeaders.name(i) + ": " + responseHeaders.value(i));
                }

                InputStream inputStream = response.body().byteStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                imageCallback.receivedImage(bitmap);
            }
        });
    }

    public void fetchAds() {
        AsyncRequest asynchronousGet = new AsyncRequest();

        try {
            Request request =  asynchronousGet.apiRequest("getAd", "");
            ApiRequest(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void textDiggy(String text) {
        AsyncRequest asynchronousGet = new AsyncRequest();

        String[] keys = {"message"};
        String[] values = {text};

        String requestBody = getParamsJSON(keys,values).toString();

        try {
            Request request =  asynchronousGet.apiRequest("messageDiggy", requestBody);
            ApiRequest(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void downloadImage(String url) {
        System.out.print("ALRIGHTY!!!");

        AsyncRequest asynchronousGet = new AsyncRequest();

        try {
            Request request =  asynchronousGet.imageRequest(url);
            ImageRequest(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private JSONObject getParamsJSON(String[] keys, String[] values) {
        int numberOfParameters = keys.length;

        if(numberOfParameters > 0) {
            try{
                JSONObject paramsJson = new JSONObject();

                // add key value pairs to JSONObject
                for(int i = 0; i < numberOfParameters; i++) {
                    if(i <= values.length) {
                        paramsJson.put(keys[i], values[i]);
                    }
                    else {
                        paramsJson.put(keys[i], "");
                    }
                }

                return paramsJson;
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}


