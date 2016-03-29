package com.zooop.zooop_android.ui.fragments;

import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.zooop.zooop_android.DiscoverAds;
import com.zooop.zooop_android.api.APIService;
import com.zooop.zooop_android.R;
import com.zooop.zooop_android.Screen;
import com.zooop.zooop_android.api.ApiCallback;
import com.zooop.zooop_android.api.ImageCallback;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DiscoverFragment extends Fragment {

    ScrollView scrollView;
    Screen screen = new Screen();
    LinearLayout adsLayer;
    View view;

    public DiscoverFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getAds();

        // get this current view
        view = inflater.inflate(R.layout.fragment_discover, container, false);

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
                JSONArray jsonArray = null;

                if(!responseString.equals("?")) {
                    try {
                        jsonArray = new JSONArray(responseString);

                        for(int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObj = jsonArray.getJSONObject(i);
                            JSONObject adInfo = jsonObj.getJSONObject("adInfo");

                            String adId = adInfo.getString("_id");
                            String adCreatedAt = adInfo.getString("createdAt");
                            String adName = adInfo.getString("name");
                            String adDescription = adInfo.getString("description");
                            String[] adTags = splitToStringArray(adInfo.getString("tags").toString());

                            String adImageUrl;
                            try {
                                adImageUrl = adInfo.getString("imgUrl");
                            }
                            catch(Exception e) {
                                adImageUrl = "";
                            }

                            JSONObject shopInfo = jsonObj.getJSONObject("shopInfo");

                            String shopName = shopInfo.getString("name");
                           // String[] shopCoordinates = splitToStringArray(shopInfo.getString("location"));

                            String[] shopCoordinates = {"45.0", "20.56"};

                            final DiscoverAds ad = new DiscoverAds(adId, adName, adDescription, adImageUrl, adTags, adCreatedAt , shopName, shopCoordinates);

                            Handler refresh = new Handler(Looper.getMainLooper());
                            refresh.post(new Runnable() {
                                public void run() {
                                    addAd(ad);
                                    DiscoverAds.ads.add(ad);
                                }
                            });
                        }

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

    public void addAd(final DiscoverAds ad) {
        final RelativeLayout adLayout = new RelativeLayout(getActivity());
        adLayout.setBackgroundColor(Color.WHITE);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams
                (RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        adLayout.setLayoutParams(params);
        adsLayer.addView(adLayout);

        ImageView imageView = getImgView();
        adLayout.addView(imageView);

        ProgressBar progressBar = new ProgressBar(getActivity(), null, android.R.attr.progressBarStyleSmall);
        RelativeLayout.LayoutParams parameters = new RelativeLayout.LayoutParams(100,100);
        parameters.addRule(RelativeLayout.CENTER_IN_PARENT);
        parameters.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        progressBar.setY(250);
        adLayout.addView(progressBar, parameters);


        if(ad.getImage() != "") {
            getImageFromURL(ad.getImage(), imageView, progressBar);
        }
        else {
            adLayout.removeView(progressBar);
        }

        final TextView descriptonTxt = getDescripLayout();
        descriptonTxt.setVisibility(View.INVISIBLE);
        adLayout.addView(descriptonTxt);

        descriptonTxt.setText(ad.getDescription());

        final LinearLayout headerLine = getHeaderLayout();
        adLayout.addView(headerLine);

        TextView TitleTxtView = getTitleTxtView(ad.getName());
        headerLine.addView(TitleTxtView);

        final Button btn1 = new Button(getActivity());
        params.width = 100;
        params.height = 100;
        btn1.setLayoutParams(params);
        btn1.setBackgroundResource(R.drawable.show);
        btn1.setAlpha(0.5f);
        headerLine.addView(btn1);

        btn1.setOnClickListener(new View.OnClickListener() {
            boolean detailsShown = false;

            @Override
            public void onClick(View v) {
                if(!detailsShown) {
                    btn1.setBackgroundResource(R.drawable.hide);
                    descriptonTxt.setVisibility(View.VISIBLE);
                    detailsShown = true;
                }
                else {
                    btn1.setBackgroundResource(R.drawable.show);
                    descriptonTxt.setVisibility(View.INVISIBLE);
                    detailsShown = false;
                }
            }
        });
    }

    public String[] splitToStringArray(String arrayStr) {
        String cleanStr = arrayStr.replaceAll("[^\\d.,-]", "");
        String[] strArray = cleanStr.split(",");

        return strArray;
    }

    private ImageView getImgView() {
        ImageView imageView = new ImageView(getActivity());

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams
                (RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        params.bottomMargin = 40;

        imageView.setAdjustViewBounds(true);
        imageView.setLayoutParams(params);
        imageView.setBackgroundColor(Color.WHITE);
        imageView.setImageResource(R.drawable.fallback_img);
        
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        return imageView;
    }

    public void getImageFromURL(String url, final ImageView imageView, final ProgressBar progessbar) {
        APIService api = new APIService(new ImageCallback() {
            @Override
            public void receivedImage(Bitmap responseImage) {
                final Bitmap image = responseImage;

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        imageView.setImageBitmap(image);
                        ((ViewGroup) progessbar.getParent()).removeView(progessbar);
                    }
                });
            }
        });
        api.downloadImage(url);
    }

    /**** ADSVIEW UI-ELEMENTS ****/
    private LinearLayout getHeaderLayout() {
        final LinearLayout headerLine = new LinearLayout(getActivity());

        LinearLayout.LayoutParams hlParams = new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.MATCH_PARENT, 120);

        headerLine.setLayoutParams(hlParams);
        headerLine.setBackgroundColor(Color.WHITE);
        headerLine.setY(30);
        headerLine.getBackground().setAlpha(200);
        headerLine.setOrientation(LinearLayout.HORIZONTAL);

        return headerLine;
    }

    private TextView getDescripLayout() {
        TextView txtView = new TextView(getActivity());

        int width = 2*(screen.getWidth(getActivity()))/3;
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams
                (RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);

        txtView.setLayoutParams(params);
        txtView.setWidth(width);
        txtView.setMaxWidth(width);
        txtView.setTextSize((float) 18);
        txtView.setY(30);
        txtView.setPadding(20, 140, 20, 20);
        txtView.setBackgroundColor(Color.WHITE);
        txtView.getBackground().setAlpha(180);

        txtView.setTextColor(getResources().getColor(R.color.colorSecondary));

        return txtView;
    }

    private TextView getTitleTxtView(String title) {
        TextView titleTextView = getTxtView();
        titleTextView.setTextSize(24);
        titleTextView.setText(title);
        return titleTextView;
    }

    private TextView getTxtView() {
        TextView txtView = new TextView(getActivity());

        int width = 2*(screen.getWidth(getActivity()))/3;
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams
                (RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);

        txtView.setLayoutParams(params);
        txtView.setWidth(width);
        txtView.setMaxWidth(width);
        txtView.setTextSize((float) 18);
        txtView.setPadding(50, 10, 50, 10);

        txtView.setTextColor(getResources().getColor(R.color.colorSecondary));

        return txtView;
    }
}
