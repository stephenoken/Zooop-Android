package com.zooop.zooop_android.ui.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.zooop.zooop_android.GCMPush.GCMClientManager;
import com.zooop.zooop_android.LocationService;
import com.zooop.zooop_android.R;
import com.zooop.zooop_android.ui.fragments.DiggyFragment;
import com.zooop.zooop_android.ui.fragments.DiscoverFragment;
import com.zooop.zooop_android.ui.fragments.DiscoverMapFragment;

import java.io.IOException;

public class HomeActivity extends AppCompatActivity {
    private Drawer mDrawer;

    String PROJECT_NUMBER = "665662071179";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        SharedPreferences sharedpreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String nickStr = sharedpreferences.getString("nickName", null);
        System.out.println("--------------------------------------");
        System.out.println(nickStr);

        GCMClientManager pushClientManager = new GCMClientManager(this, PROJECT_NUMBER);
        pushClientManager.registerIfNeeded(new GCMClientManager.RegistrationCompletedHandler() {
            @Override
            public void onSuccess(String registrationId, boolean isNewRegistration) {

                System.out.print("--------------------------");
                System.out.print(registrationId);
                //send this registrationId to your server
            }

            @Override
            public void onFailure(String ex) {
                System.out.print("--------------FAILURE ");
                super.onFailure(ex);
            }
        });

        Fragment menu = new MenuFragment();
        changeFragment(menu);

        LocationService locationService = new LocationService(HomeActivity.this);
        if(!locationService.locationPermissions()) {
            locationService.requestLocationPermissions();
        }
        else {
            Log.i("PERMISSIONS", "LOCATION");
        }

        // set map as initial fragment
        //setDiscoverMapsFragment();
        setDiscoverFragment();

        // create menu items
        final PrimaryItem map = new PrimaryItem("Map", 0);
        final Item diggy = new Item("Diggy", 1);
        final Item discover = new Item("Discover", 2);

        // create the menu
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);

        mDrawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .addDrawerItems(
                        map.drawerItem,
                        diggy.drawerItem,
                        discover.drawerItem)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {

                        if (position == map.index) {
                            setDiscoverMapsFragment();
                            showKeyboard(false, view);
                        } else if (position == diggy.index) {
                            setDiggyFragment();
                            showKeyboard(true, view);
                        } else if (position == discover.index) {
                            setDiscoverFragment();
                            showKeyboard(false, view);
                        }

                        mDrawer.getActionBarDrawerToggle().setDrawerIndicatorEnabled(false);
                        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

                        mDrawer.closeDrawer();

                        return true;
                    }
                })
                .build();
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        mDrawer.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);
    }


    /******* functions: change fragment *******/
    private void setDiscoverMapsFragment() {
        Fragment fragment = new DiscoverMapFragment();
        changeFragment(fragment);
    }

    private void setDiggyFragment() {
        Fragment fragment = new DiggyFragment();
        changeFragment(fragment);
    }

    private void setDiscoverFragment() {
        Fragment fragment = new DiscoverFragment();
        changeFragment(fragment);
    }

    private void changeFragment( Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame,fragment).commit();
    }

    private void showKeyboard(boolean show, View view) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        if(show) {
            imm.toggleSoftInputFromWindow(view.getWindowToken(), InputMethodManager.SHOW_FORCED, 0);
        } else {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /******* classes: wrapper for menu items *******/
    private class PrimaryItem {
        String name;
        final int index;
        PrimaryDrawerItem drawerItem;

        PrimaryItem(String name, int index) {
            this.name = name;
            this.index = index;
            this.drawerItem = new PrimaryDrawerItem().withName(name);
        }
    }

    private class Item {
        String name;
        final int index;
        SecondaryDrawerItem drawerItem;

        Item(String name, int index) {
            this.name = name;
            this.index = index;
            this.drawerItem = new SecondaryDrawerItem().withName(name);
        }
    }
}