package com.zooop.zooop_android.ui.fragments;

import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.zooop.zooop_android.API;
import com.zooop.zooop_android.Ads;
import com.zooop.zooop_android.R;
import com.zooop.zooop_android.Screen;

public class DiscoverFragment extends Fragment {

    ScrollView scrollView;

    public DiscoverFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Ads ad1 = new Ads();
        ad1.fetchData();

        // get this current view
        View view = inflater.inflate(R.layout.fragment_discover, container, false);

        // get and setup scrollview
        scrollView = (ScrollView) view.findViewById(R.id.scrollView);





        Log.i("OOOO", "CALL ME BIYCH");

//        API api = new API();
//        api.fetchAds(this);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_discover, container, false);
    }

//    private View getAdView() {
//
//
//
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
//                (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//
//
//
//        return chatField;
//    }

    private void scrollToBottom(){
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }
}
