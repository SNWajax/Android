package com.example.ajays.localdatabase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    TextView textView;
    dbHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText)findViewById(R.id.editText);
        textView = (TextView)findViewById(R.id.textView);
        dbHandler = new dbHandler(this, null, null, 1);
    }

    public void addOnClickButton(View view){
        Product product = new Product(editText.getText().toString());
        dbHandler.addProduct(product);
        printDatabase();
    }

    public void deleteOnClickButton(View view){
        String inputText = editText.getText().toString();
        dbHandler.deleteProduct(inputText);
        printDatabase();
    }

    public void printDatabase(){
        String dbString = dbHandler.databaseToString();
        textView.setText(dbString);
        editText.setText("");
    }
}
