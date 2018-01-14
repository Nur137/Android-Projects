package com.example.mona.mahen.UI;


import android.Manifest;
import android.content.Intent;
import android.content.IntentSender;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;

import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import android.view.KeyEvent;
import android.widget.Toast;


import com.example.mona.mahen.Account.Work_Status;
import com.example.mona.mahen.Class.Config;
import com.example.mona.mahen.R;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.ResultCallbacks;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.Map;

public class Workermap extends FragmentActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

private GoogleMap mMap;
private GoogleApiClient mGoogleApiClient;
public static final String TAG = Workermap.class.getSimpleName();
private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
private LocationRequest mLocationRequest;
        SharedPreferences preferences;
        String c_active,w_active,work,wrkr;
        Firebase firebase;
        ArrayList<String> lats;
        ArrayList<String> longs;


@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workermap);
        Firebase.setAndroidContext(this);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        c_active=preferences.getString("cust_id","");
        Bundle bundle = getIntent().getExtras();
        work = bundle.getString("work");
        lats=new ArrayList<String>();
        longs=new ArrayList<String>();


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

        if (mMap == null) {

        mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
        .getMap();

        if (mMap != null) {
        setUpMap();
        }
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



        }



private void handleNewLocation(Location location) {
        Log.d(TAG, location.toString());

        double currentLatitude = location.getLatitude();
        double currentLongitude = location.getLongitude();

final String lat=String.valueOf(currentLatitude);
        String lng=String.valueOf(currentLongitude);


        firebase.child("notify").child("customer").addChildEventListener(new ChildEventListener() {
@Override
public void onChildAdded(final DataSnapshot snapshot, String previousChildKey) {

        Map<String,Object> newPost=(Map<String,Object>)snapshot.getValue();
        String c_id = String.valueOf(newPost.get("cust_id"));
        String w_id = String.valueOf(newPost.get("work_id"));
        if(c_id.equalsIgnoreCase(c_active) && work.equalsIgnoreCase(w_id))
        {
        wrkr = String.valueOf(newPost.get("worker_id"));
        firebase.child("Reg_users").child("worker").child(wrkr).child("Latitude").addListenerForSingleValueEvent(new ValueEventListener() {
public void onDataChange(DataSnapshot dataSnapshot) {
        lats.add((String)dataSnapshot.getValue());

        firebase.child("Reg_users").child("worker").child(wrkr).child("Longitude").addListenerForSingleValueEvent(new ValueEventListener() {
public void onDataChange(DataSnapshot dataSnapshot) {
        longs.add((String)dataSnapshot.getValue());
        }
public void onCancelled(FirebaseError firebaseError) { }
        });
        }
public void onCancelled(FirebaseError firebaseError) { }
        });


        }
        }

@Override
public void onChildChanged(DataSnapshot dataSnapshot, String s) {


        }

@Override
public void onChildRemoved(DataSnapshot dataSnapshot) {

        }

@Override
public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

@Override
public void onCancelled(FirebaseError firebaseError) {

        }
        });

        //Checking authorization when loading is done
        firebase.child("notify").child("customer").addListenerForSingleValueEvent(new ValueEventListener() {
public void onDataChange(DataSnapshot dataSnapshot) {

        for(int i = 0; i< lats.size() ;i++){

        LatLng sydney1 = new LatLng(Double.parseDouble(lats.get(i)), Double.parseDouble(longs.get(i)));
        mMap.addMarker(new MarkerOptions().position(sydney1).title("Applied workers for the job"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney1, 14));
        }

        }
public void onCancelled(FirebaseError firebaseError) { }
        });








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
        Intent intent = new Intent(getApplicationContext(),Work_Status.class);
        startActivity(intent);
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