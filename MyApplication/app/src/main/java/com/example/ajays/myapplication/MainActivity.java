package com.example.ajays.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import static android.R.id.content;

public class MainActivity extends AppCompatActivity {

    private EditText mEmail;
    private EditText mPassword;
    String  Email, Login, Password,Name;
    private Button mLogin;

    TextView content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEmail = (EditText) findViewById(R.id.Email);
        mPassword = (EditText) findViewById(R.id.Password);
        mLogin = (Button) findViewById(R.id.Login);

        content    =   (TextView)findViewById( R.id.content );
        mLogin.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {

                try {

                    // CALL GetText method to make post method call
                    GetText();
                } catch (Exception ex) {
                    content.setText(" url exeption! ");
                }
            }
        });
    }
        public  void  GetText()  throws UnsupportedEncodingException
        {
            // Get user defined values

            Email   = mEmail.getText().toString();
            Password   = mPassword.getText().toString();

            // Create data variable for sent values to server

            String data = "&" + URLEncoder.encode("email", "UTF-8") + "="
                    + URLEncoder.encode(Email, "UTF-8");

            data += "&" + URLEncoder.encode("user", "UTF-8")
                    + "=" + URLEncoder.encode(Login, "UTF-8");

            data += "&" + URLEncoder.encode("pass", "UTF-8")
                    + "=" + URLEncoder.encode(Password, "UTF-8");

            String text = "";
            BufferedReader reader=null;

            // Send data
            try
            {

                // Defined URL  where to send data
                URL url = new URL("http://aditya12345.ddns.net");

                // Send POST data request

                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write( data );
                wr.flush();

                // Get the server response

                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = null;

                // Read Server Response
                while((line = reader.readLine()) != null)
                {
                    // Append server response in string
                    sb.append(line + "\n");
                }


                text = sb.toString();
            }
            catch(Exception ex)
            {

            }
            finally
            {
                try
                {

                    reader.close();
                }

                catch(Exception ex) {}
            }

            // Show response on activity
            content.setText( text  );

        }

}



