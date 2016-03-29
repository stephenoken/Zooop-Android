package com.zooop.zooop_android.ui.activities;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.util.Log;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.zooop.zooop_android.R;
import com.zooop.zooop_android.BuildConfig;
import com.zooop.zooop_android.api.AsyncRequest;
import com.zooop.zooop_android.models.UserDbHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Set;


public class LogInActivity extends Activity {
    private TextView info;
    private CallbackManager callbackManager; //Used to route calls back to fb SDK
    private LoginButton loginButton;//  when someone clicks on the button, the login is initiated with the set permissions.
    private String UserName;
    SharedPreferences preferences;
    //  The button follows the login state,
    //  and displays the correct text based on someone's authentication state
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // Saves State and re-renders it when device is rotated

        /** Initializing facebook sdk **/
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_log_in);
        fbLogin();
        /** check if user is already logged in **/
        //AccessToken fbAccessToken = AccessToken.getCurrentAccessToken();
//        if(fbAccessToken != null || BuildConfig.DEBUG) {
//            loggedIn();
//        }
//        else {
//         Log.d("haha", "fuck you");
//        }
    }

    @Override
    //Tapping the login button creates a new activity, this function handles this activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void fbLogin() {
        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList(
                "public_profile", "user_friends"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
//                String userID = .getUserId();
                final UserDbHelper userDb = new UserDbHelper(getApplicationContext());

                String token = loginResult.getAccessToken().getToken();
//                Set<String> name = loginResult.getAccessToken().getPermissions();
                Log.d("User----------------", token);
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {

                                Log.v("LoginActivity", response.toString());

                                try {
                                    UserName = object.getString("name");
                                    userDb.insert(UserName, null, 1);
//
                                    // String email = object.getString("email");
                                    //String birthday = object.getString("birthday");
                                    Log.d("name-------------->", UserName);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name");
                request.setParameters(parameters);
                request.executeAsync();
                loggedIn();
            }

            @Override
            public void onCancel() {
                info.setText("Facebook login attempt Cancelled");
                Log.d("Canceled", "Facebook login attempt Cancelled");
            }

            @Override
            public void onError(FacebookException exception) {
                info.setText("Login Error");
                Log.d("Facebook exception", exception.getMessage());
            }
        });
    }

    private void loggedIn(){
        Log.i("Success", "-> Logged in");
        Intent i = new Intent(LogInActivity.this, UserIntroActivity.class);
        startActivity(i);
    }



}



