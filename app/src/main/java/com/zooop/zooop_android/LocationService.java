package com.zooop.zooop_android;

import android.Manifest;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by michael on 16/02/16.
 */
public class LocationService extends Service implements LocationListener {

    private final Activity activity;
    private final Context context;
    Location location;
    private static final long distForChange = 5;
    private static final long timeout = 1000 * 60;

    protected LocationManager locationManager;

    /******** constructor ********/
    public LocationService(Activity activity) {
        this.context = (Context) activity;
        this.activity = activity;

        //getLocation();
    }

    public double[] getLocation() {
        double locArray[] = {0.0,0.0};

        try {
            locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);

            boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!gps && !network) {
                //Snackbar.make(layout, "Location access is required to show coffee shops nearby.", 10000).show();
            } else {
                if(locationPermissions()) {
                    if (network) {
                        locationManager.requestLocationUpdates(
                                LocationManager.NETWORK_PROVIDER,
                                timeout,
                                distForChange,
                                this);
                    }

                    if(locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    }

                    locArray[0] = location.getLatitude();
                    locArray[1] = location.getLongitude();
                    return locArray;
                }
            }

            if(gps) {
                if(locationPermissions()) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                timeout,
                                distForChange,
                                this);
                    }

                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    }

                    locArray[0] = location.getLatitude();
                    locArray[1] = location.getLongitude();
                    return locArray;
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return locArray;
    }


    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void requestLocationPermissions() {
        if(!fineLocationPermitted()) {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    0);
        }
        if(!coarseLocationPermitted()) {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    0);
        }
    }

    private boolean fineLocationPermitted() {
        return ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private boolean coarseLocationPermitted() {
        return ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    public boolean locationPermissions() {
        if(fineLocationPermitted() &&  coarseLocationPermitted()) {
            return true;
        }
        else {
            return false;
        }
    }
}
