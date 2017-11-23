package orchid.fireorchid;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;

import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Tab2_Map extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    public GoogleMap mMap;
    //    public String title, snippet;

    //GPS Location
    GPSTracker gps;
    List<Address> addresses;

    EditText loc;
    Button explore;
    Marker marker1;

    ConnectionDetector cd;
    Boolean isInternetPresent = false;

    //    AlertDialogManager alert = new AlertDialogManager();
    final int[] count = {0};

    DatabaseReference databaseReference;


    SharedPreferences sharedpreferences;


    ArrayList<LatLng> markerPoints;

    public Tab2_Map() {

    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        gps = new GPSTracker(getContext());
        databaseReference = FirebaseDatabase.getInstance().getReference();

        LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new MyLocationListener();
        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            new AlertDialog.Builder(getActivity())
                    .setTitle("Location Permission Needed")
                    .setMessage("This app needs the Location permission, please accept to use location functionality")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //Prompt the user once explanation has been shown
                            ActivityCompat.requestPermissions(getActivity(),
                                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                                    99 );
                        }
                    })
                    .create()
                    .show();
            return;
        }
       lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
    }
/*
    explore.{
        EditText loc = (EditText)findViewById(R.id.editText);
        String location = loc.getText().toString();
        List<Address> addressList = null;

        if(location!=null || !location.equals("")){
            Geocoder geocoder = new Geocoder(this);
            try {
                addressList = geocoder.getFromLocationName(location, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Address address = addressList.get(0);
            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
            mMap.addMarker(new MarkerOptions().position(latLng).title("Marker"));
            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        }
    }
*/

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //   return super.onCreateView(inflater, container, savedInstanceState);

   //     Firebase reference = new Firebase("https://fireorchid-1e1ab.firebaseio.com/users");

        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.tab2_map, container, false);

        // creating GPS Class object
        gps = new GPSTracker(getContext());
        databaseReference = FirebaseDatabase.getInstance().getReference();

        cd = new ConnectionDetector(getContext());
        //     sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        // Check if Internet present
        isInternetPresent = cd.isConnectingToInternet();
        if (!isInternetPresent) {
            // Internet Connection is not present
     /*       alert.showAlertDialog(MapsActivity.this, "Internet Connection Error",
                    "Please connect to working Internet connection", false);
       */     // stop executing code by return
            //
            Toast.makeText(getActivity(), "Please Switch on Data pack", Toast.LENGTH_LONG).show();
            // return;
        }


        // check if GPS location can get
        if (gps.canGetLocation()) {
            Log.d("Your Location", "latitude:" + gps.getLatitude() + ", longitude: " + gps.getLongitude());
            //Test ME!!!!
        //    reference.child("users").child("location").setValue(gps);
        } else {
            // Can't get user's current location
            // stop executing code by return

            Toast.makeText(getActivity(), "gps disabled", Toast.LENGTH_LONG).show();
            //return;
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.activity_map_fragment);
        mapFragment.getMapAsync(this);


        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference().child("users");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
              saveLocationInformation();

            }
        }, 20000); // 10 seconds

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                retriveLocation();
            }
        }, 20000);
     //   retriveLocation();
        return view;
    }

    private void retriveLocation() {


        final DatabaseReference myRef = databaseReference.child("users");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //  collectionStatusInfo((Map<String, Object>) dataSnapshot.getValue());
              //  locationData.clear

                collectLocation((Map<String, Object>) dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

    }
    public void collectLocation(Map<String, Object> users) {

        mMap.clear();
    /*    if(marker1.getPosition() != null) {
            LatLng lat = marker1.getPosition();
            mMap.addMarker(new MarkerOptions().position(lat).title(""));

          }
*/
        final ArrayList<String> locationData = new ArrayList<>();
        final ArrayList<String> userInfo = new ArrayList<>();
        final ArrayList<String> usernumber = new ArrayList<>();




        for (Map.Entry<String, Object> entry : users.entrySet()) {
            //Get user map
            Map singleUser = (Map) entry.getValue();
            //Get phone field and append to list
            userInfo.add((String) singleUser.get("name"));
        }


        for (Map.Entry<String, Object> entry : users.entrySet()) {
            //Get user map
            Map singleUser = (Map) entry.getValue();
            //Get phone field and append to list
            locationData.add((String) singleUser.get("location"));
            count[0]++;

        }


        System.out.println("Location: " + locationData.toString());
        System.out.println("My count is: " + count[0]);

        final double[] latitude = new double[count[0]];
        final double[] longitude = new double[count[0]];


        int j = 0;
        for (String loc : locationData) {
            System.out.println("LocationMMdm" + loc);
            String[] Location = loc.split(",");
            latitude[j] = Double.parseDouble(Location[0]);
            longitude[j] = Double.parseDouble(Location[1]);
            LatLng latlng = new LatLng(latitude[j], longitude[j]);
            System.out.println("lat: " + latitude[j] + " " + "log: " + longitude[j]);

            mMap.addMarker(new MarkerOptions().position(latlng).
                    title(userInfo.get(j)).
                    snippet("").
                            flat(true).
                            anchor(0.5f, 0.5f).
                            rotation((0.0f)).
                            icon(BitmapDescriptorFactory.fromResource(R.drawable.markeramb)));
            j++;
        }
    }


    @Override
    public void onCreateOptionsMenu (Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.main, menu);

        // SearchManager searchManager = (SearchManager)getContext().getSystemService(Context.SEARCH_SERVICE);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView sv = new SearchView(((MainActivity) getActivity()).getSupportActionBar().getThemedContext());
        //  sv.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        sv.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default


        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW |
                MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
        MenuItemCompat.setActionView(item, sv);
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                Toast.makeText(getActivity(),query,Toast.LENGTH_LONG).show();
                List<Address> addressList = null;
                if(query!=null || !query.equals("")){
                  //  mMap.clear();
                    Geocoder geocoder = new Geocoder(getContext());
                    try {
                        addressList = geocoder.getFromLocationName(query, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Address address = addressList.get(0);
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());

                    mMap.addMarker(new MarkerOptions().position(latLng).title(query));
                    mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
            //        markerPoints.add(latLng);
      //              marker1.setPosition(latLng);

                //    Toast.makeText(getActivity(),marker1.getPosition(),Toast.LENGTH_LONG).show();

                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                System.out.println("tap");
                return false;
            }
        });
    }



    @Override
    public boolean onMarkerClick(Marker marker) {


     if( !marker.isFlat()) {
         LatLng position = marker.getPosition();
         double latitude = position.latitude;
         double longitude = position.longitude;

         Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());

         try {
             addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
         } catch (IOException e) {
             e.printStackTrace();
         }

         String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
         String city = addresses.get(0).getLocality();
         String state = addresses.get(0).getAdminArea();
         String country = addresses.get(0).getCountryName();

         Log.d("Address -", " " + address + ", " + city + ", " + state + ", " + country);

         Intent i = new Intent(getContext(), EventCreator.class);
         i.putExtra("Latitude", latitude);
         i.putExtra("Longitude", longitude);
         i.putExtra("address", address);
         i.putExtra("City", city);
         i.putExtra("state", state);
         i.putExtra("country", country);
         startActivity(i);




     }
        return false;
    }

    class MyLocationListener implements LocationListener {

        double sLat = 0.0;
        double sLong = 0.0;
        private int interval = 50000;
        String loca;
        Handler handler1;

        @Override
        public void onLocationChanged(Location location) {
            handler1 = new Handler();

            if (location != null) {
                sLat = location.getLatitude();
                sLong = location.getLongitude();
                loca = sLat + "," + sLong;
//                Toast.makeText(getActivity(), loca, Toast.LENGTH_LONG).show();

            }
            run2.run();

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }

        Runnable run2 = new Runnable() {

            @Override
            public void run() {
                try {
                    databaseReference.child("users").child(UserDetails.username).child("location").setValue(loca);

                } finally {
                    handler1.postDelayed(run2, interval);
                }
            }
        };
    }


    private void saveLocationInformation() {
        //   String userid = firebaseUser.getUid();
        //   String contact = editTextContact.getText().toString().trim();
       // String staus = "A";
        double lat;
        double log;
        GPSTracker gpsTracker = new GPSTracker(getActivity());
        lat = gpsTracker.getLatitude();
        log = gpsTracker.getLongitude();

        String location = lat + "," + log;
   //     Toast.makeText(getActivity(), "2:" + location, Toast.LENGTH_LONG).show();

        databaseReference.child("users").child(UserDetails.username).child("location").setValue(location);
        //   UserInformation userInformation = new UserInformation(driverInfo,location, userid, staus);

        //  databaseReference.child("users").child(firebaseUser.getUid()).setValue(userInformation);
        //    Toast.makeText(this,"Information Saved",Toast.LENGTH_SHORT).show();

    }



    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
        double myLatitude = gps.getLatitude();
        double myLongitude = gps.getLongitude();
        LatLng latLng = new LatLng(myLatitude, myLongitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
        mMap.setOnMarkerClickListener(this);
    }

}