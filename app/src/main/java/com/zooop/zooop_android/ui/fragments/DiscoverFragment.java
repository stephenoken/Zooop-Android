package com.zooop.zooop_android.ui.fragments;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
                            String[] adTags = splitToStringArray(adInfo.getJSONArray("tags").toString());

                            String adImageUrl;
                            try {
                                adImageUrl = adInfo.getString("imageUrl");
                            }
                            catch(Exception e) {
                                adImageUrl = "";
                            }

                            JSONObject shopInfo = jsonObj.getJSONObject("shopInfo");

                            String shopName = shopInfo.getString("name");
                            String[] shopCoordinates = splitToStringArray(shopInfo.getString("location"));

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

    public void addAd(DiscoverAds ad) {
        RelativeLayout adLayout = new RelativeLayout(getActivity());
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
        adLayout.addView(progressBar, parameters);


        if(ad.getImage() != "") {
            getImageFromURL(ad.getImage(), imageView, progressBar);
        }
        else {
            adLayout.removeView(progressBar);
        }

        View headerLine = getheaderLine();
        adLayout.addView(headerLine);

        TextView TitleTxtView = getTitleTxtView(ad.getName());
        adLayout.addView(TitleTxtView);

        TextView textView2 = getDescriptionTxtView(ad.getDescription());
        adLayout.addView(textView2);

        scrollToBottom();
    }

    private String[] splitToStringArray(String arrayStr) {
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
    private View getheaderLine() {
        View line = new View(getActivity());

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
                (RelativeLayout.LayoutParams.MATCH_PARENT, 150);

        line.setLayoutParams(params);

        line.setY(30);
        line.setBackgroundColor(Color.WHITE);
        line.getBackground().setAlpha(128);

        return line;
    }

    private TextView getTitleTxtView(String title) {
        TextView titleTextView = getTxtView();
        titleTextView.setTextSize(24);
        titleTextView.setText(title);
        titleTextView.setY(0);
        return titleTextView;
    }

    private TextView getDescriptionTxtView(String description) {
        TextView descrTextView = getTxtView();
        descrTextView.setTextSize(16);
        descrTextView.setText(description);
        descrTextView.setY(60);
        return descrTextView;
    }

    private TextView getTxtView() {
        TextView txtView = new TextView(getActivity());

        int width = screen.getWidth(getActivity());
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams
                (RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);

        //params.gravity = Gravity.TOP;
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
