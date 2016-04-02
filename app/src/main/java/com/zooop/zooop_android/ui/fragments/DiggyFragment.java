package com.zooop.zooop_android.ui.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.zooop.zooop_android.api.APIService;
import com.zooop.zooop_android.R;
import com.zooop.zooop_android.Screen;
import com.zooop.zooop_android.api.ApiCallback;
import com.zooop.zooop_android.models.DbHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

interface MyHandlerInterface {
    void update();
}
public class DiggyFragment extends Fragment {
    enum SIDE {
        USER, DIGGY;
    }

    private static DiggyFragment singleton = new DiggyFragment();

    /* Static 'instance' method */
    public static DiggyFragment getInstance( ) {
        return singleton;
    }

    ScrollView scrollView;
    Screen screen = new Screen();
    LinearLayout chatLayer;

    public DiggyFragment() {/*empty constr requiered*/}

    @Override
    public void onResume() {
        super.onResume();
        loadMessagesFromDB();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // get this current view
        View view = inflater.inflate(R.layout.fragment_diggy, container, false);

        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mMessageReceiver,
                new IntentFilter("newMessageEvent"));

        // get and setup scrollview
        scrollView = (ScrollView) view.findViewById(R.id.scrollView);
        setupScrollView();

        // get the chat layer
        chatLayer = (LinearLayout) scrollView.findViewById(R.id.container);

        // get the input field
        final EditText inputField = (EditText) view.findViewById(R.id.inputField);
        inputField.isFocusable();
        inputField.requestFocus();

        inputField.setOnEditorActionListener(
                new EditText.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_SEND) {
                            submitInput(inputField);
                            return true;
                        }
                        return false;
                    }
                });

        return view;
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("message");
            TextView chatView = getMsgDiggy(message);

            addChatField(chatView);
        }
    };

    @Override
    public void onDestroy() {
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }

    private void loadMessagesFromDB() {
        final DbHelper dbHelper = new DbHelper(getContext());
        try {
            String messagePacket[] = dbHelper.readReturnChat();

            if(!messagePacket[0].equals("") && !messagePacket[1].equals("") && !messagePacket[2].equals("")) {
                Log.i("CHAT", messagePacket[0]);
                Log.i("CH2", messagePacket[1]);
                Log.i("CH3", messagePacket[2]);

                TextView chatView;

                if(messagePacket[1].equals("DIGGY")) {
                    chatView = getMsgDiggy(messagePacket[2]);
                }
                else {
                    chatView = getMsgUser(messagePacket[2]);
                }
                addChatField(chatView);
            }
        }
        catch(Exception e) {
            Log.e("NO CUISINE FOUND", ":", e);
        }


    }

    private TextView getMsgDiggy(String message) {
        TextView chatField = getChatView(SIDE.DIGGY);
        chatField.setTextColor(getResources().getColor(R.color.primary));
        chatField.setBackgroundColor(Color.WHITE);
        chatField.setText(message);
        return chatField;
    }

    private TextView getMsgUser(String message) {
        TextView chatField = getChatView(SIDE.USER);
        chatField.setTextColor(Color.WHITE);
        chatField.setBackgroundColor(getResources().getColor(R.color.primary));
        chatField.setText(message);
        return chatField;
    }

    private TextView getChatView(SIDE side) {
        TextView chatField = new TextView(getActivity());

        int width = screen.getWidth(getActivity());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        params.gravity = Gravity.TOP;
        if(side == SIDE.DIGGY) {
            params.gravity = Gravity.LEFT;
        }
        else {
            params.gravity = Gravity.RIGHT;
        }

        params.bottomMargin = 20;
        chatField.setLayoutParams(params);
        chatField.setMaxWidth((width / 3) * 2);
        chatField.setTextSize((float) 18);
        chatField.setPadding(20, 20, 20, 20);
        chatField.isFocusable();
        chatField.isFocusableInTouchMode();

        return chatField;
    }

    private void scrollToBottom(){
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }

    private void setupScrollView() {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) scrollView.getLayoutParams();
        Screen screen = new Screen();

        params.height = (screen.getHeight(getActivity())/2)-100;
        scrollView.setLayoutParams(params);
    }

    private void submitInput(EditText inputField) {
        String text = inputField.getText().toString();
        inputField.setText("");

        // get a chat item and add it
        TextView textView = getMsgUser(text);
        addChatField(textView);

        //send iput to diggyApi and add respond to view
        retrieveDiggyAnswer(text);



        inputField.requestFocus();
    }

    private void addChatField(TextView textView) {
        chatLayer.addView(textView);
        scrollToBottom();
    }

    private void retrieveDiggyAnswer(String text) {
        APIService api = new APIService(new ApiCallback() {
            @Override
            public void receivedResponse(String responseString) {
                if(!responseString.equals("?")) {
                    JSONObject jsonObj = null;
                    try {
                        jsonObj = new JSONObject(responseString);
                        final TextView textView = getMsgDiggy(jsonObj.getString("diggyResponse"));

                        Handler refresh = new Handler(Looper.getMainLooper());
                        refresh.post(new Runnable() {
                            public void run() {
                                addChatField(textView);
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
        api.textDiggy(text);
    }
}
