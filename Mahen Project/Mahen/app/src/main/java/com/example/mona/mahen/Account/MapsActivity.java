package com.example.mona.mahen.Account;

import android.Manifest;
import android.content.Intent;
import android.content.IntentSender;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;

import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import android.view.KeyEvent;
import android.widget.Toast;

import com.example.mona.mahen.Class.Config;
import com.example.mona.mahen.R;
import com.example.mona.mahen.Worker.Main;
import com.firebase.client.Firebase;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    public static final String TAG = MapsActivity.class.getSimpleName();
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private LocationRequest mLocationRequest;
    SharedPreferences preferences;
    String c_active,w_active;
    Firebase firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        preferences=PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        c_active=preferences.getString("cust_id","");
        w_active=preferences.getString("wrkr_id","");
        editor.putString("gps", "2");
        editor.commit();


        Firebase.setAndroidContext(this);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        setUpMapIfNeeded();
        firebase=new Firebase(Config.FIREBASE_URL);

        if (mGoogleApiClient == null) {

            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .addApi(AppIndex.API).build();

        }
        // Create the LocationRequest object
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10000)        // 10 seconds, in milliseconds
                .setFastestInterval(5000); // 1 second, in milliseconds


        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);
        final PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient,
                        builder.build());


    }

    @Override
    protected void onPause() {

        super.onPause();

        finish();

    }
    @Override
    protected void onResume() {
        mGoogleApiClient.connect();
        super.onResume();
        setUpMapIfNeeded();


    }

    @Override
      protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();


    }

    private void setUpMapIfNeeded() {

        try {
            if (mMap == null) {

                mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                        .getMap();

                if (mMap != null) {
                    setUpMap();
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void setUpMap() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

    }

    @Override
    public void onConnected(Bundle bundle) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (location == null) {
//            Toast.makeText(MapsActivity.this, "null", Toast.LENGTH_SHORT).show();
        }
        else {
  //          Toast.makeText(MapsActivity.this, "Not null", Toast.LENGTH_SHORT).show();
            handleNewLocation(location);
        }


        if(w_active.equalsIgnoreCase(""))
        {
            firebase.child("Reg_users").child("customer").child(c_active).child("Latitude").setValue(getLat(location));
            firebase.child("Reg_users").child("customer").child(c_active).child("Longitude").setValue(getLong(location));
        }
        else if(c_active.equalsIgnoreCase(""))
        {
            firebase.child("Reg_users").child("worker").child(w_active).child("Latitude").setValue(getLat(location));
            firebase.child("Reg_users").child("worker").child(w_active).child("Longitude").setValue(getLong(location));
        }

    }



    private void handleNewLocation(Location location) {
        Log.d(TAG, location.toString());

        double currentLatitude = location.getLatitude();
        double currentLongitude = location.getLongitude();

        String lat=String.valueOf(currentLatitude);

        Toast.makeText(getApplicationContext(), "Latitude"+lat, Toast.LENGTH_SHORT).show();

        String lng=String.valueOf(currentLongitude);

        Toast.makeText(getApplicationContext(), "Longitude"+lng, Toast.LENGTH_SHORT).show();

        //multiple point
        //int j=0;
        //float j = (float) 0.001;
        //for(int i = 0; i< 4 ;i++){

            //LatLng sydney1 = new LatLng(currentLatitude+j, currentLongitude+j);
           // mMap.addMarker(new MarkerOptions().position(sydney1).title("Marker in Sydney1"));
          //  mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney1,14));
          //  j = (float) (j+0.0011);
        //}

        final LatLng latLng = new LatLng(currentLatitude, currentLongitude);

        //my location

        MarkerOptions options = new MarkerOptions()
                .position(latLng)
                .title("I am here!");
        mMap.addMarker(options);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,14));

    }
    private String getLat(Location location)
    {

        double currentLatitude = location.getLatitude();

        String lat=String.valueOf(currentLatitude);
        return lat;

    }

    private String getLong(Location location)
    {

        double currentLongitude = location.getLongitude();


        String lng=String.valueOf(currentLongitude);
        return lng;


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {

        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            finish();
            if(w_active.equalsIgnoreCase(""))
            {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                intent.putExtra("gps","3");
                startActivity(intent);
            }
            else if(c_active.equalsIgnoreCase(""))
            {
                Intent intent = new Intent(getApplicationContext(),Main.class);
                intent.putExtra("gps","3");
                startActivity(intent);
            }
            return false;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Location services suspended. Please reconnect.");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        } else {
            Log.i(TAG, "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        handleNewLocation(location);
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

    @Override
    public void onStart() {
        super.onStart();

        mGoogleApiClient.connect();

    }
}
