package com.zooop.zooop_android;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


import android.util.Log;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;


public class LogIn extends Activity {
    private TextView info;
    private CallbackManager callbackManager; //Used to route calls back to fb SDK
    private LoginButton loginButton;//  when someone clicks on the button, the login is initiated with the set permissions.

    //  The button follows the login state,
    //  and displays the correct text based on someone's authentication state
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // Saves State and re-renders it when device is rotated
        FacebookSdk.sdkInitialize(getApplicationContext()); //Initializing facebook sdk
        setContentView(R.layout.activity_log_in);

        callbackManager = CallbackManager.Factory.create();
        info = (TextView) findViewById(R.id.HelloWorld);
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("user_friends");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override            //Customize the properties of login button
            public void onSuccess(LoginResult loginResult) {
                info.setText(
                        "User ID: "
                                + loginResult.getAccessToken().getUserId()
                                + "\n" +
                                "Auth Token: "
                                + loginResult.getAccessToken().getToken()
                );


                        Intent i = new Intent(
                                LogIn.this,
                                UserIntro.class);
                        startActivity(i);



            }

            @Override
            public void onCancel() {
                // App code
                info.setText("Facebook login attempt Cancelled");
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                info.setText("Login Error");
                Log.d("On Cancel", "On Error");
            }
        });


    }


    @Override
    //Tapping the login button creates a new activity, this function handles this activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);


    }
}



