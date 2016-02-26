package com.zooop.zooop_android.ui.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import com.zooop.zooop_android.R;
import com.zooop.zooop_android.Screen;

public class DiggyFragment extends Fragment {
    enum SIDE {
        USER, DIGGY;
    }

    ScrollView scrollView;
    Screen screen = new Screen();
    LinearLayout chatLayer;

    public DiggyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // get this current view
        View view = inflater.inflate(R.layout.fragment_diggy, container, false);

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
        // get input text
        String text = inputField.getText().toString();

        // empty the input field
        inputField.setText("");

        // get a chat item and add it
        TextView textView = getMsgUser(text);
        chatLayer.addView(textView);

        // default respond from diggy
        TextView one = getMsgDiggy("Hi, what's up??");
        chatLayer.addView(one);

        // scroll to end of scrollview
        scrollToBottom();

        inputField.requestFocus();
    }
}
