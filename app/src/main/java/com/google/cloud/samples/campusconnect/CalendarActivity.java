package com.google.cloud.samples.campusconnect;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;


public class CalendarActivity extends ActionBarActivity {

    ImageButton noti,profile,home,calendar,search;

    ViewPager pager;
    ViewPagerAdapter_Calendar adapter;
    SlidingTabLayout_Calendar tabs;
    CharSequence Titles[]={"Mon 5","Tue 6","Wed 7","Thu 8","Fri 9","Sat 10","Sun 11"};
    int Numboftabs = 7;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_to_be_deleted);

        home = (ImageButton) findViewById(R.id.ib_home);
        noti = (ImageButton) findViewById(R.id.ib_notification);
        profile = (ImageButton) findViewById(R.id.ib_profile);
        calendar = (ImageButton) findViewById(R.id.ib_calendar);
        search = (ImageButton) findViewById(R.id.ib_search);
/*
        pager = (ViewPager) findViewById(R.id.pager);
        tabs = (SlidingTabLayout_Calendar) findViewById(R.id.tabs_calendar);
        adapter =  new ViewPagerAdapter_Calendar(getSupportFragmentManager(),Titles,Numboftabs,CalendarActivity.this);

        pager.setAdapter(adapter);

        tabs.setDistributeEvenly(true);
        tabs.setViewPager(pager);   */

        noti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent_temp = new Intent(v.getContext(), NotificationActivity.class);
                startActivity(intent_temp);

            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent_temp = new Intent(v.getContext(), ProfilePageActivity.class);
                startActivity(intent_temp);

            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent_temp = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent_temp);

            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent_temp = new Intent(v.getContext(), SearchActivity.class);
                startActivity(intent_temp);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private List<Notification_infoActivity> createList_nl(int size) {
        List<Notification_infoActivity> result = new ArrayList<Notification_infoActivity>();
        for (int i = 1; i <= size; i++) {
            Notification_infoActivity ci = new Notification_infoActivity();
            result.add(ci);
        }

        return result;
    }


}
