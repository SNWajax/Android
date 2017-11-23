package com.example.ajays.firemap;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Transaction;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Map;

public class Maps extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Firebase mRef;

    private Handler handler;
    private int mInterval = 5000;
    Marker marker;


    Double nLat = 0.0;
    Double nLong = 0.0;

    public static Maps newInstance() {
        Maps fragment = new Maps();
        return fragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        mRef = new Firebase("https://firemap-ca164.firebaseio.com/coordinates/me");

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<Double, Double> data = dataSnapshot.getValue(Map.class);

                nLat = data.get("nlat");
                nLong = data.get("nlong");

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        SupportMapFragment mapFragment = (SupportMapFragment) this.getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        handler = new Handler();

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
        mMap.setMyLocationEnabled(true);


        run.run();
        // Add a marker in Sydney and move the camera

    }

    Runnable run =new Runnable() {
        @Override
        public void run() {
            try {
                if (nLat == 0.0 && nLong == 0.0) {
                    LatLng sydney = new LatLng(-34, 151);
                    mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

                }
                else {
                    if(marker == null)
                    {
                        LatLng myLocation = new LatLng(nLat, nLong);
                        marker = mMap.addMarker(new MarkerOptions().position(myLocation).title("My Current Location"+nLat+", "+nLong));
                        CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(nLat, nLong)).zoom(15).build();
                        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                    }
                    else {
                        marker.remove();
                        LatLng myLocation = new LatLng(nLat, nLong);
                        marker = mMap.addMarker(new MarkerOptions().position(myLocation).title("My Current Location" + nLat + ", " + nLong));
                        marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_cast_dark));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(myLocation));

                    }
                }
                //this function can change value of mInterval.
            } finally {
                handler.postDelayed(run, mInterval);
            }

        }
    };

}


