package com.zooop.zooop_android.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.zooop.zooop_android.R;


public class HomeActivity extends AppCompatActivity {

    private PrimaryDrawerItem item1 = new PrimaryDrawerItem().withName(R.string.app_name);
    private SecondaryDrawerItem item2 = new SecondaryDrawerItem().withName("Test");

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .addDrawerItems(item1, new DividerDrawerItem(),
                        item2, new SecondaryDrawerItem().withName("Settings"))
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        Toast.makeText(getApplicationContext(), String.valueOf(position), Toast.LENGTH_LONG).show();
                        return true;
                    }
                })
                .build();
    }
}
