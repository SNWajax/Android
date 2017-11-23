package com.example.ajays.firemap;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    private Firebase mRootRef;
    private TextView Lat, Long;
    double mlat = 0.0;
    double mlong = 0.0;
    private Handler mhandler;
    private int mInterval = 5000;
    private Button get_Map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRootRef = new Firebase("https://firemap-ca164.firebaseio.com/");

        Lat = (TextView) findViewById(R.id.Lattitude);
        Long = (TextView) findViewById(R.id.Longitude);

        get_Map = (Button) findViewById(R.id.maps1);

        mhandler = new Handler();

        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationListener ll = new myLocationListener();

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, ll);

        run.run();


    }

    Runnable run =new Runnable() {
        @Override
        public void run() {
            try {
                if (mlat == 0.0 && mlong == 0.0) {
                    //Toast.makeText(this, "coordinates not yet concluded...", Toast.LENGTH_SHORT).show();
                } else {
                    userLocation(mlat, mlong, "me");
                }
                //this function can change value of mInterval.
            } finally {
                mhandler.postDelayed(run, mInterval);
            }

        }
    };

    public void get(View view){
        startActivity(new Intent(MainActivity.this,Maps.class));
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void userLocation(double mlat, double mlong , String userId){

        Coordinates coordinates = new Coordinates(mlat, mlong);

        mRootRef.child("coordinates").child(userId).setValue(coordinates);
    }

    @IgnoreExtraProperties
    public class Coordinates {

        public double nlat;
        public double nlong;

        public Coordinates() {
            // Default constructor required for calls to DataSnapshot.getValue(User.class)
        }

        public Coordinates(double nlat , double nlong) {
            this.nlat = nlat;
            this.nlong = nlong;
        }

    }

    class myLocationListener implements LocationListener{


        @Override
        public void onLocationChanged(Location location) {

            if(location != null)
            {
                mlat = location.getLatitude();
                mlong = location.getLongitude();

                Lat.setText(Double.toString(mlat));
                Long.setText(Double.toString(mlong));
            }
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    }
}

