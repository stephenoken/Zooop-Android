package com.zooop.zooop_android.ui.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.zooop.zooop_android.LocationService;
import com.zooop.zooop_android.R;
import com.zooop.zooop_android.ui.fragments.DiggyFragment;
import com.zooop.zooop_android.ui.fragments.DiscoverFragment;
import com.zooop.zooop_android.ui.fragments.DiscoverMapFragment;

public class HomeActivity extends AppCompatActivity {

    private Drawer mDrawer;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LocationService locationService = new LocationService(HomeActivity.this);
        if(!locationService.locationPermissions()) {
            locationService.requestLocationPermissions();
        }
        else {
            Log.i("PERMISSIONS", "LOCATION");
        }

        // set map as initial fragment
        setDiggyFragment();

        // create menu items
        final PrimaryItem map = new PrimaryItem("Map", 0);
        final Item diggy = new Item("Diggy", 1);
        final Item discover = new Item("Discover", 2);

        // create the menu
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
                        } else if (position == diggy.index) {
                            setDiggyFragment();
                        } else if (position == discover.index) {
                            setDiscoverFragment();
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