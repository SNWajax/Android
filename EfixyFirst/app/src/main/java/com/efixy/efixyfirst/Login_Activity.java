package com.efixy.efixyfirst;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login_Activity extends AppCompatActivity implements View.OnClickListener {

    private TextView text_signIn;
    private Button btn_signIn;
    private EditText edit_mail, edit_pass;

    //firebase auth object
    private FirebaseAuth firebaseAuth;

    //progress dialog
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);


        //getting firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        //if the objects getcurrentuser method is not null
        //means user is already logged in
    /*    if(firebaseAuth.getCurrentUser() != null){
            //close this activity
            finish();
            //opening profile activity
            startActivity(new Intent(getApplicationContext(), RnP_Activity.class));
        }       */

        text_signIn = (TextView)findViewById(R.id.text_signIn);
        btn_signIn = (Button)findViewById(R.id.btn_signIn);
        edit_mail = (EditText)findViewById(R.id.edit_mail);
        edit_pass = (EditText)findViewById(R.id.edit_pass);

   /*     text_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login_Activity.this, MainActivity.class));
            }
        });     */
        progressDialog = new ProgressDialog(this);

        //attaching click listener
        btn_signIn.setOnClickListener(this);
        text_signIn.setOnClickListener(this);
    }
    //method for user login
    private void userLogin(){
        String email = edit_mail.getText().toString().trim();
        String password  = edit_pass.getText().toString().trim();


        //checking if email and passwords are empty
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }

        //if the email and password are not empty
        //displaying a progress dialog

        progressDialog.setMessage("Logging In Please Wait...");
        progressDialog.show();

        //logging in the user
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        //if the task is successfull
                        if(task.isSuccessful()){
                            //start the profile activity
                            finish();
                            startActivity(new Intent(Login_Activity.this, RnP_Activity.class));
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        if(view == btn_signIn){
            userLogin();
        }
        if(view == text_signIn){
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
    }
}
