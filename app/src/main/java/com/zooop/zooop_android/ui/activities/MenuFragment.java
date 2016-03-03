package com.zooop.zooop_android.ui.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.zooop.zooop_android.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment {

    View view;

    public MenuFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home, container, false);

        createActionBarElements();
        return null;
    }

    public Toolbar createActionBarElements() {
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);

        return toolbar;
    }
}
