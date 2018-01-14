package com.example.mona.admin;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;


public class MainActivity extends ActionBarActivity {

    ViewPager pager;
    private String titles[] = new String[]{"Customer","Worker"};


    SlidingTabLayout slidingTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        pager = (ViewPager) findViewById(R.id.viewpager);
        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        pager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), titles));

        slidingTabLayout.setViewPager(pager);
        slidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return Color.argb(200, 58, 213, 235);
            }
        });
        //slidingTabLayout.setSelectedIndicatorColors(Color.argb(200, 0, 216, 121));




    }

    @Override
    protected void onPause() {

        super.onPause();

    }
    @Override
    protected void onStop() {

        super.onStop();
        finish();

    }





}
