package com.zooop.zooop_android;

/**
 * Created by michael on 17/02/16.
 */

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.zooop.zooop_android.ui.ApiCallback;
import com.zooop.zooop_android.ui.fragments.DiggyFragment;
import org.json.JSONObject;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class APIService extends AppCompatActivity {
    private final OkHttpClient client = new OkHttpClient();

    public void fetchAds() {
        AsyncRequest asynchronousGet = new AsyncRequest();

        try {
            Request request =  asynchronousGet.run("getAd", "");
            ApiRequest(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ApiCallback callback;
    public APIService(ApiCallback callback){
        this.callback = callback;
    }

    public void textDiggy(String text) {

        AsyncRequest asynchronousGet = new AsyncRequest();

        try {
            //Request request =  asynchronousGet.run("messageDiggy", text);
            Request request =  asynchronousGet.run("getAd", "");
            DiggyRequest(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ApiRequest(Request request) {
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                Headers responseHeaders = response.headers();
                for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                    Log.d("RESPONSE HEADER:", responseHeaders.name(i) + ": " + responseHeaders.value(i));
                }

                String responseBody = response.body().string();
                callback.receivedResponse(responseBody);
            }
        });
    }

    public void DiggyRequest(Request request) {

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                Headers responseHeaders = response.headers();
                for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                    Log.d("RESPONSE HEADER:", responseHeaders.name(i) + ": " + responseHeaders.value(i));
                }

                String responseBody = response.body().string();
                Log.i("RESPONSE", responseBody );
                callback.receivedResponse(responseBody);
            }
        });
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


