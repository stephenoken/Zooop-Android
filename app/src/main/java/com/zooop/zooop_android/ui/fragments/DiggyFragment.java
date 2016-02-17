package com.zooop.zooop_android.ui.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zooop.zooop_android.R;
import com.google.android.gms.plus.PlusOneButton;

public class DiggyFragment extends Fragment {

    public DiggyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_diggy, container, false);

        return view;
    }
}
