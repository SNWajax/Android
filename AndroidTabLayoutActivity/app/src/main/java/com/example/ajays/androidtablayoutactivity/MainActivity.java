package com.example.ajays.androidtablayoutactivity;

import android.app.TabActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TabHost;

public class MainActivity extends TabActivity {

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabHost tabHost = getTabHost();

        // Tab for Photos
        TabHost.TabSpec map = tabHost.newTabSpec("Map");
        // setting Title and Icon for the Tab
        map.setIndicator("Map", getResources().getDrawable(R.drawable.map));
        Intent mapIntent = new Intent(this, Map.class);
        map.setContent(mapIntent);

        // Tab for Songs
        TabHost.TabSpec chat = tabHost.newTabSpec("Chat");
        chat.setIndicator("Chat", getResources().getDrawable(R.drawable.chat));
        Intent chatIntent = new Intent(this, Chat.class);
        chat.setContent(chatIntent);

        // Adding all TabSpec to TabHost
        tabHost.addTab(map); // Adding photos tab
        tabHost.addTab(chat); // Adding songs tab
    }
}
