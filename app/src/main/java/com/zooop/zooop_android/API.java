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

public class API extends AppCompatActivity {

    public void testAPI() {

        // defining sets of paramters
        String[] keys = {"firstName"};
        String[] values = {"batman"};

        JSONObject paramsJson = getParamsJSON(keys, values);
        Log.i("JSON paramters----> ", paramsJson.toString());

        AsyncRequest asynchronousGet = new AsyncRequest();
        String bodySTTT = paramsJson.toString();

        try {
            asynchronousGet.run("getInfo", bodySTTT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void receiveResponse(String responseString) {
        Log.i("RESPONSE", responseString);
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
}


