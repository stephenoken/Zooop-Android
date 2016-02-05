package com.zooop.zooop_android.ui.activities;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

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

        myPreferences = getSharedPreferences ("userInfo", Context.MODE_PRIVATE);

        String nickStr = myPreferences.getString("nickName", null);
        String favCuisStr = myPreferences.getString("favCuisine", null);

        if(nickStr != null && favCuisStr != null) {
            startActivity();
        }
        else {
            nickname = (EditText) findViewById(R.id.nickInput);
            favCuisine = (EditText) findViewById(R.id.favCousineInput);

            FloatingActionButton submit = (FloatingActionButton) findViewById(R.id.fab);
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //store values permanent
                    storeKeyForValue("nickName", favCuisine.getText().toString());
                    storeKeyForValue("favCuisine", favCuisine.getText().toString());

                    startActivity();
                }
            });
        }
    }

    private void storeKeyForValue(String value, String key) {
        SharedPreferences.Editor editor = myPreferences.edit();
        editor.putString("nickName", nickname.getText().toString());
        editor.apply();
    }

    private void startActivity() {
        Intent i=new Intent(UserIntroActivity.this, HomeActivity.class);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_log_in, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
