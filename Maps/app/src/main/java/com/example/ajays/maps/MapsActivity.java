package com.example.ajays.maps;

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

import android.support.v4.app.FragmentActivity;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Transaction;
import com.firebase.client.ValueEventListener;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Firebase mRef;

    private Handler handler;
    private int mInterval = 5000;
    Marker marker;


    Double nLat = 0.0;
    Double nLong = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        mRef = new Firebase("https://fir-test1-697f5.firebaseio.com/coordinates/me");

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<Double, Double> data = (Map<Double, Double>) dataSnapshot.getValue(MapsActivity.class);

                nLat = data.get("nlat");
                nLong = data.get("nlong");

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
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


