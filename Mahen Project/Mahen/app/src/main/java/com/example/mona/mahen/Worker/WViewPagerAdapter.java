package com.example.mona.mahen.Worker;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Nur on 9/12/2016.
 */
public class WViewPagerAdapter  extends FragmentPagerAdapter {

    final int PAGE_COUNT =4;// this is important for number of the pages
    private String titles[] ;

    public WViewPagerAdapter(FragmentManager fm, String[] titles2) {
        super(fm);
        titles=titles2;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            // Open FragmentTab1.java
            case 0:


                return WSampleFragment.newInstance(position);
            case 1:


                return WSampleFragment.newInstance(position);
            case 2:


                return WSampleFragment.newInstance(position);
            case 3:


                return WSampleFragment.newInstance(position);

        }
        return null;
    }

    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

}