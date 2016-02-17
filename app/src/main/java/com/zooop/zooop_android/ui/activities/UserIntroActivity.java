package com.zooop.zooop_android.ui.activities;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.zooop.zooop_android.LocationService;
import com.zooop.zooop_android.R;

/**
 * Created by anuj on 2/2/2016.
 */
public class UserIntroActivity extends AppCompatActivity {
    EditText nickname;
    EditText favCuisine;
    SharedPreferences myPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_intro);

        /** retrieve the nickname and the favourite cuisine from the user **/
        myPreferences = getSharedPreferences ("userInfo", Context.MODE_PRIVATE);
        String nickStr = myPreferences.getString("nickName", null);
        String favCuisStr = myPreferences.getString("favCuisine", null);

        if(nickStr != null && favCuisStr != null) {
            startActivity();
        }
        else {
            nickname = (EditText) findViewById(R.id.nickInput);
            favCuisine = (EditText) findViewById(R.id.favCousineInput);

            favCuisine.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View view, int i, KeyEvent keyEvent) {
                    if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (i == KeyEvent.KEYCODE_ENTER)) {
                        storeValsAndContinue();
                        return true;
                    }
                    return false;
                }
            });

            View view = this.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
            }

            FloatingActionButton submit = (FloatingActionButton) findViewById(R.id.fab);
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    storeValsAndContinue();
                }
            });
        }
    }

    private void storeValsAndContinue() {
        //store values permanent
        storeKeyForValue("nickName", favCuisine.getText().toString());
        storeKeyForValue("favCuisine", favCuisine.getText().toString());

        startActivity();
    }

    private void storeKeyForValue(String value, String key) {
        SharedPreferences.Editor editor = myPreferences.edit();
        editor.putString(value, key);
        editor.apply();
    }

    private void startActivity() {
        Intent i=new Intent(UserIntroActivity.this, HomeActivity.class);
        startActivity(i);
    }
}
