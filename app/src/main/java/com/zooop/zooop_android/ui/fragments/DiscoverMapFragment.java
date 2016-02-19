package com.zooop.zooop_android.ui.fragments;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.zooop.zooop_android.LocationService;
import com.zooop.zooop_android.R;
import com.zooop.zooop_android.ui.activities.HomeActivity;

public class DiscoverMapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    LocationService locationService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        locationService = new LocationService(getActivity());

        View view = inflater.inflate(R.layout.fragment_discover_map, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        createActionBarElements();

        return view;
    }

    private void createActionBarElements() {
        //Creates a search bar
        EditText searchEditText = new EditText(getContext());
        searchEditText.setHint(R.string.search);
        searchEditText.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        ImageButton searchImageButton = new ImageButton(getContext());
        searchImageButton.setBackground(getResources().getDrawable(R.drawable.ic_done_white));
        searchImageButton.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.addView(searchEditText);
        toolbar.addView(searchImageButton);
    }

    public void onMapReady(GoogleMap googleMap) throws SecurityException{
        mMap = googleMap;

        double[] currentLocation = locationService.getLocation();
        double latit = currentLocation[0];
        double longi = currentLocation[1];

        // Add a marker in Sydney and move the camera
        LatLng userLocation = new LatLng(latit, longi);
        mMap.addMarker(new MarkerOptions().position(userLocation).title("That's you, dude \uD83D\uDE0E"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(userLocation));

        //Zooop map settings
        mMap.setMyLocationEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 13));
    }
}
