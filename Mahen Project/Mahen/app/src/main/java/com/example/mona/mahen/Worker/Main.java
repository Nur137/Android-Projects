package com.example.mona.mahen.Worker;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.LocationManager;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.mona.mahen.Account.MapsActivity;
import com.example.mona.mahen.R;
import com.example.mona.mahen.UI.Front;

public class Main extends ActionBarActivity {


    //for pager name .if you want to add more page ,then add more name here in this section
    ViewPager pager;
    private String titles[] = new String[]{"My Location", "Work", "Balance Summary", "Profile"};
    WSlidingTabLayout slidingTabLayout;
    SharedPreferences preferences;
    String gps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.w_activity_main);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        gps=preferences.getString("gps","");
        if(gps.equalsIgnoreCase("1"))
        {
            Intent in=new Intent(Main.this,MapsActivity.class);
            startActivity(in);
        }

        //for page sliding
        pager = (ViewPager) findViewById(R.id.viewpager);
        slidingTabLayout = (WSlidingTabLayout) findViewById(R.id.sliding_tabs);
        pager.setAdapter(new WViewPagerAdapter(getSupportFragmentManager(), titles));
        slidingTabLayout.setViewPager(pager);

        //for give color to the page indicator....given color is blue....if give another then change it argb values
        slidingTabLayout.setCustomTabColorizer(new WSlidingTabLayout.WTabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return Color.argb(235, 40, 179, 75);
        }
        });
    }

    //for enable gps
    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog,  final int id) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        buildAlertMessageNoGps();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {

        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {

            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("sp1", "done");
            editor.commit();

            Intent intent = new Intent(getApplicationContext(),Main.class);
            startActivity(intent);
            finish();
            return false;
        }

        return super.onKeyDown(keyCode, event);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {





        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {

        super.onPause();
        // startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));


    }
    @Override
    protected void onStop() {

        super.onStop();
        finish();

    }

    @Override
    protected void onResume() {
        super.onResume();
        //for checking if the location is enable or not
        final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );

        if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            buildAlertMessageNoGps();

        }
    }

}
