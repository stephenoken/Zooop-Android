package com.zooop.zooop_android;

import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by michael on 21/02/16.
 */
public class Screen extends Application {

    public int getWidth(Context context) {
        return getDisplay(context).getWidth();
    }

    public int getHeight(Context context) {
        return getDisplay(context).getHeight();
    }

    private Display getDisplay(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay();
    }
}
