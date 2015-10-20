package com.google.cloud.samples.campusconnect;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by hp1 on 21-01-2015.
 */
public class ViewPagerAdapter_home extends FragmentPagerAdapter {

    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter_home is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter_home is created

    private Context mContext;
    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ViewPagerAdapter_home(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb, Context context) {
        super(fm);
        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;
        this.mContext = context;
    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {

        if(position == 0)
        {
            FragmentLive fraglive = new FragmentLive();
            return fraglive;
        }
        else if(position == 1)
        {
            FragmentTopNews fragtopnews = new FragmentTopNews();
            return fragtopnews;
        }
        else if(position == 2)
        {
            FragmentEvents fragevents = new FragmentEvents();
            return fragevents;
        }
        else
        {
            FragmentGroups fraggroups = new FragmentGroups();
            return fraggroups;
        }


    }



    private static int[] ICONS = new int[] {
            R.drawable.selector_live,
            R.drawable.selector_news,
            R.drawable.selector_events,
            R.drawable.selector_group
    };



    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }


    // This method return the Number of tabs for the tabs Strip

    @Override
    public int getCount() {
        return NumbOfTabs;
    }

    public int getDrawableId(int position) {
        return ICONS[position];
    }
}
