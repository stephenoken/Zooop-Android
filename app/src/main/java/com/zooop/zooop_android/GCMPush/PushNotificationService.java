package com.zooop.zooop_android.GCMPush;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;
import com.zooop.zooop_android.ui.fragments.DiggyFragment;

/**
 * Created by michael on 25/03/16.
 */
public class PushNotificationService extends GcmListenerService {

    @Override
    public void onMessageReceived(String from, Bundle data) {
        String message = data.getString("message");
        
        DiggyFragment.postbox.add(message);
    }

}
