package com.zooop.zooop_android.ui.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zooop.zooop_android.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class UserIntroFragment extends Fragment {

    public UserIntroFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_intro, container, false);
    }
}
