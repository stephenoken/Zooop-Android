package com.zooop.zooop_android.ui.fragments;

import android.graphics.Color;
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
    Screen screen = new Screen();
    LinearLayout adsLayer;

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
        scrollView = (ScrollView) view.findViewById(R.id.adsScrollView);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) scrollView.getLayoutParams();
        scrollView.setLayoutParams(params);

        // get the chat layer
        adsLayer = (LinearLayout) scrollView.findViewById(R.id.adsContainer);
        adsLayer.setBackgroundColor(getResources().getColor(R.color.primary));



        return view;
    }

    public void addAd(String name, String description, String image, String id, double[] location) {

        TextView textView = getAdView();
        textView.setText(name);
        adsLayer.addView(textView);

        TextView textView2 = getAdView();
        textView2.setText(description);
        adsLayer.addView(textView2);
    }

    private TextView getAdView() {
        TextView chatField = new TextView(getActivity());

        int width = screen.getWidth(getActivity());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        params.gravity = Gravity.TOP;
        params.bottomMargin = 20;
        chatField.setLayoutParams(params);
        chatField.setWidth((width / 3) * 2);
        chatField.setMaxWidth((width / 3) * 2);
        chatField.setTextSize((float) 18);
        chatField.setPadding(20, 20, 20, 20);

        chatField.setTextColor(Color.BLACK);
        chatField.setBackgroundColor(getResources().getColor(R.color.primary));
        chatField.setText("<><><><><><><><><><><");

        return chatField;
    }

    public void updateView() {

    }

    private void scrollToBottom(){
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }
}
