package com.zooop.zooop_android.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.zooop.zooop_android.DiscoverAds;
import com.zooop.zooop_android.LocationService;
import com.zooop.zooop_android.R;

public class DiscoverMapFragment extends Fragment implements OnMapReadyCallback {

    private static int ZOOMLEVEL = 13;
    private GoogleMap mMap;
    LocationService locationService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        locationService = new LocationService(getActivity());

        View view = inflater.inflate(R.layout.fragment_discover_map, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return view;
    }

    private void addPin(LatLng location, String title) {
        mMap.addMarker(new MarkerOptions().position(location).title(title));
    }

    public void onMapReady(GoogleMap googleMap) throws SecurityException{
        mMap = googleMap;

        double[] currentLocation = locationService.getLocation();
        double latit = currentLocation[0];
        double longi = currentLocation[1];


        for(DiscoverAds ad : DiscoverAds.ads) {
            LatLng location = new LatLng(ad.getShopLocation().getLatitude(), ad.getShopLocation().getLongitude());
            addPin(location, ad.getShopName());
        }

        LatLng userLocation = new LatLng(latit, longi);

        mMap.setMyLocationEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, ZOOMLEVEL));
    }
}
