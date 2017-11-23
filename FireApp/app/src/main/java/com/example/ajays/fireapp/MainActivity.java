package com.example.ajays.fireapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.Firebase;

public class MainActivity extends AppCompatActivity {

    private Button mSendData;
    private Firebase mRef;
    private EditText mValue;
    private EditText mKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Firebase.setAndroidContext(this);

        mValue = (EditText)findViewById(R.id.value);
        mRef = new Firebase("https://fireapp-841b6.firebaseio.com/Users");
        mSendData = (Button)findViewById(R.id.sendData);
        mKey = (EditText)findViewById(R.id.Key);
        mSendData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = mValue.getText().toString();
                String key = mKey.getText().toString();
                Firebase childRef = mRef.child(key);
                childRef.setValue(value);
            }
        });
    }
}
