package com.efixy.efixyfirst;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RnP_Activity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private Button btn_logOut;
    private Button btn_repair;
    private Button btn_purchase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rn_p_);

        firebaseAuth = FirebaseAuth.getInstance();
     //   btn_logOut = (Button)findViewById(R.id.btn_logOut);
        btn_purchase = (Button)findViewById(R.id.btn_purchase);
        btn_repair = (Button)findViewById(R.id.btn_repair);

   /*     btn_logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                startActivity(new Intent(RnP_Activity.this, Login_Activity.class));
            }
        });     */

        btn_repair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RnP_Activity.this,RepairActivity.class));
            }
        });

        btn_purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RnP_Activity.this,Purchase_Activity.class));
            }
        });

        //if the objects getcurrentuser method is not null
        //means user is already logged in
      if(firebaseAuth.getCurrentUser() != null){

        }
        else{
          //close this activity
          finish();
          //opening profile activity
          startActivity(new Intent(getApplicationContext(), Login_Activity.class));
      }
    }
  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.layout.menu, menu);
        return true;
    }       */

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logOut:
                firebaseAuth.signOut();
                startActivity(new Intent(RnP_Activity.this, Login_Activity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
