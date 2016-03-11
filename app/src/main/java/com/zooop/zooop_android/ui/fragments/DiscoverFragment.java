package com.zooop.zooop_android.ui.fragments;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.zooop.zooop_android.api.APIService;
import com.zooop.zooop_android.Ads;
import com.zooop.zooop_android.R;
import com.zooop.zooop_android.Screen;
import com.zooop.zooop_android.api.ApiCallback;
import com.zooop.zooop_android.api.ImageCallback;

import org.json.JSONException;
import org.json.JSONObject;

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

        getAds();

        // get this current view
        View view = inflater.inflate(R.layout.fragment_discover, container, false);

        // get and setup scrollview
        scrollView = (ScrollView) view.findViewById(R.id.adsScrollView);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) scrollView.getLayoutParams();
        scrollView.setLayoutParams(params);

        // get the chat layer
        adsLayer = (LinearLayout) scrollView.findViewById(R.id.adsContainer);
        adsLayer.setBackgroundColor(Color.WHITE);

        return view;
    }

    private void getAds() {
        APIService api = new APIService(new ApiCallback() {
            @Override
            public void receivedResponse(String responseString) {
                JSONObject jsonObj = null;

                if(!responseString.equals("?")) {
                    try {
                        jsonObj = new JSONObject(responseString);

                        String name = jsonObj.getString("name");
                        String description = jsonObj.getString("description");
                        String image = jsonObj.getString("image");
                        String id = jsonObj.getString("_id");
                        String location = jsonObj.getJSONArray("location").toString();

                        final Ads ad = new Ads(name, description, image, id, location);

                        Handler refresh = new Handler(Looper.getMainLooper());
                        refresh.post(new Runnable() {
                            public void run() {
                                addAd(ad);
                                addAd(ad);
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    Log.i("API", "CAN NOT CALL API");
                }
            }
        });
        api.fetchAds();
    }

    public void addAd(Ads ad) {
        View line = getSeparatorLine();
        adsLayer.addView(line);

        TextView TitleTxtView = getTitleTxtView(ad.getName());
        adsLayer.addView(TitleTxtView);

        TextView textView2 = getDescriptionTxtView(ad.getDescription());
        adsLayer.addView(textView2);

        ImageView imageView = getImgView();
        adsLayer.addView(imageView);
        getImageFromURL(ad.getImage(), imageView);

        scrollToBottom();
    }

    private ImageView getImgView() {
        ImageView imageView = new ImageView(getActivity());

        int width = screen.getWidth(getActivity());

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
                (width, LinearLayout.LayoutParams.WRAP_CONTENT);

        params.gravity = Gravity.LEFT;
        params.bottomMargin = 10;

        imageView.setLayoutParams(params);
        imageView.setBackgroundColor(Color.WHITE);

        return imageView;
    }

    public void getImageFromURL(String url, final ImageView imageView) {
        APIService api = new APIService(new ImageCallback() {
            @Override
            public void receivedImage(Bitmap responseImage) {
                final Bitmap image = responseImage;

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        imageView.setImageBitmap(image);
                    }
                });
            }
        });
        api.downloadImage(url);
    }

    /**** ADSVIEW UI-ELEMENTS ****/
    private View getSeparatorLine() {
        View line = new View(getActivity());
        line.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                5
        ));
        line.setBackgroundColor(getResources().getColor(R.color.primary));

        return line;
    }

    private TextView getTitleTxtView(String title) {
        TextView titleTextView = getTxtView();
        titleTextView.setTextSize(24);
        titleTextView.setText(title);
        return titleTextView;
    }

    private TextView getDescriptionTxtView(String description) {
        TextView descrTextView = getTxtView();
        descrTextView.setTextSize(16);
        descrTextView.setText(description);
        return descrTextView;
    }

    private TextView getTxtView() {
        TextView txtView = new TextView(getActivity());

        int width = screen.getWidth(getActivity());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        params.gravity = Gravity.TOP;
        params.bottomMargin = 20;
        txtView.setLayoutParams(params);
        txtView.setWidth((width / 3) * 2);
        txtView.setMaxWidth((width / 3) * 2);
        txtView.setTextSize((float) 18);
        txtView.setPadding(20, 20, 20, 20);

        txtView.setTextColor(getResources().getColor(R.color.colorSecondary));

        return txtView;
    }

    /**** HELPER METHODS ****/
    private void scrollToBottom(){
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }
}
