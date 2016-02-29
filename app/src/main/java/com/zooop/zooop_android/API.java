package com.zooop.zooop_android;

/**
 * Created by michael on 17/02/16.
 */
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class API extends AppCompatActivity {

    private String adResponse;

    private final OkHttpClient client = new OkHttpClient();

    public void fetchAds(Ads ads) {
        AsyncRequest asynchronousGet = new AsyncRequest();

        try {
            Request request =  asynchronousGet.run("getAd");
            test(request, ads);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testAPI() {

        // defining sets of paramters
        String[] keys = {"firstName"};
        String[] values = {"batman"};

        JSONObject paramsJson = getParamsJSON(keys, values);
        Log.i("JSON paramters----> ", paramsJson.toString());

        AsyncRequest asynchronousGet = new AsyncRequest();
        String bodySTTT = paramsJson.toString();

        try {
            Request request = asynchronousGet.run("getInfo", bodySTTT);
            //test(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void test(Request request ,final Ads ads) {
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

                ads.gotResponse(response.body().string());

                Log.i("CALL", call.toString());

                //api.receiveResponse(response.body().string());
            }
        });
    }

    public void receiveResponse(String responseString) {
        Log.i("API", responseString);
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

    public boolean connectable() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }

    public String getAdResponse () {
        return this.adResponse;
    }
}


