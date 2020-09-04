package com.example.android.esiweather;

import android.widget.LinearLayout;

public class ListItem {
    public String action;
    public String state;
    public String key;

    public ListItem(String action, String state, String key){
        this.action = action;
        this.state = state;
        this.key = key;
    }

}
