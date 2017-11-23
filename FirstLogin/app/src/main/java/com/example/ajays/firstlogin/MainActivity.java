package com.example.ajays.firstlogin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static EditText user_name;
    private static EditText Password;
    private static EditText Attempts;
    private static Button Log_in;
    int attempt_counter = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LoginButton();
    }
    public void LoginButton(){
        user_name = (EditText)findViewById(R.id.user_name);
        Password = (EditText)findViewById(R.id.Password);
        Attempts = (EditText)findViewById(R.id.Attempts);
        Log_in = (Button)findViewById(R.id.Log_in);
        Attempts.setText(Integer.toString(attempt_counter));
        Log_in.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (user_name.getText().toString().equals("Shubhya") &&
                                Password.getText().toString().equals("7040119280")) {
                            Toast.makeText(MainActivity.this, "Password is Correct!",
                                    Toast.LENGTH_LONG).show();
                            Intent i = new Intent("com.example.ajays.firstlogin.LoginAccess");
                            startActivity(i);

                        }else if (user_name.getText().toString().equals("Ajya") &&
                                Password.getText().toString().equals("7768989919")) {
                            Toast.makeText(MainActivity.this, "Password is Correct!",
                                    Toast.LENGTH_LONG).show();
                            Intent i = new Intent("com.example.ajays.firstlogin.LoginAccess");
                            startActivity(i);
                        }else if (user_name.getText().toString().equals("Shirish") &&
                                Password.getText().toString().equals("8806254176")) {
                            Toast.makeText(MainActivity.this, "Password is Correct!",
                                    Toast.LENGTH_LONG).show();
                            Intent i = new Intent("com.example.ajays.firstlogin.LoginAccess");
                            startActivity(i);
                        }else if (user_name.getText().toString().equals("Nikhil") &&
                                Password.getText().toString().equals("7875089729")) {
                            Toast.makeText(MainActivity.this, "Password is Correct!",
                                    Toast.LENGTH_LONG).show();
                            Intent i = new Intent("com.example.ajays.firstlogin.LoginAccess");
                            startActivity(i);
                        }else if (user_name.getText().toString().equals("Radhe") &&
                                Password.getText().toString().equals("9604448139")) {
                            Toast.makeText(MainActivity.this, "Password is Correct!",
                                    Toast.LENGTH_LONG).show();
                            Intent i = new Intent("com.example.ajays.firstlogin.LoginAccess");
                            startActivity(i);
                        }else if (user_name.getText().toString().equals("Chiu") &&
                                Password.getText().toString().equals("9011900447")) {
                            Toast.makeText(MainActivity.this, "Password is Correct!",
                                    Toast.LENGTH_LONG).show();
                            Intent i = new Intent("com.example.ajays.firstlogin.LoginAccess");
                            startActivity(i);
                        }else if (user_name.getText().toString().equals("Babysitter") &&
                                Password.getText().toString().equals("8888374515")) {
                            Toast.makeText(MainActivity.this, "Password is Correct!",
                                    Toast.LENGTH_LONG).show();
                            Intent i = new Intent("com.example.ajays.firstlogin.LoginAccess");
                            startActivity(i);
                        }else{
                            Toast.makeText(MainActivity.this, "Incorrect Username or Password",
                                    Toast.LENGTH_LONG).show();
                            attempt_counter--;
                            Attempts.setText(Integer.toString(attempt_counter));
                            if (attempt_counter==0){
                                Log_in.setEnabled(false);
                            }
                        }
                    }
                }
        );
    }
}
