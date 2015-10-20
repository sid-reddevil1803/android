package com.google.cloud.samples.campusconnect;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.appspot.campus_connect_2015.clubs.Clubs;
import com.appspot.campus_connect_2015.clubs.model.ModelsColleges;
import com.appspot.campus_connect_2015.clubs.model.ModelsGetCollege;
import com.appspot.campus_connect_2015.clubs.model.ModelsPosts;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.cloud.samples.campusconnect.LoginActivity.CollegeListAdapterActivity;
import com.google.cloud.samples.campusconnect.LoginActivity.CollegeList_infoActivity;
import com.google.common.base.Strings;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by RK on 17-09-2015.
 */
public class MainActivity extends AppCompatActivity {

    ImageButton admin;
    ImageButton noti,profile,home,calendar,search;
    public static TextView title,title_two;
    ViewPager pager;
    ViewPagerAdapter_home adapter;
    SlidingTabLayout_home tabs;
    CharSequence Titles[]={"Live","MyFeed","CampusFeed","Groups"};
    int Numboftabs = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("MainActivity", "Entered");
        home = (ImageButton) findViewById(R.id.ib_home);
        noti = (ImageButton) findViewById(R.id.ib_notification);
        profile = (ImageButton) findViewById(R.id.ib_profile);
        calendar = (ImageButton) findViewById(R.id.ib_calendar);
        search = (ImageButton) findViewById(R.id.ib_search);

        admin = (ImageButton) findViewById(R.id.ib_admin);

        title = (TextView) findViewById(R.id.title_home);
                                title_two = (TextView) findViewById(R.id.title_home_two);

        pager = (ViewPager) findViewById(R.id.pager);
        tabs = (SlidingTabLayout_home) findViewById(R.id.tabs);
        adapter =  new ViewPagerAdapter_home(getSupportFragmentManager(),Titles,Numboftabs,MainActivity.this);

        pager.setAdapter(adapter);

        pager.setCurrentItem(1);
        tabs.setDistributeEvenly(true);
        tabs.setViewPager(pager);


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

        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent_temp = new Intent(v.getContext(), CalendarActivity.class);
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

        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent_temp = new Intent(v.getContext(), AdminPageActivity.class);
                startActivity(intent_temp);

            }
        });

    }

    public GoogleAccountCredential getCredential(String emailAccount){
        if (!AppConstants.checkGooglePlayServicesAvailable(MainActivity.this)) {
            return null;
        }

        // Create a Google credential since this is an authenticated request to the API.
        GoogleAccountCredential credential = GoogleAccountCredential.usingAudience(
                MainActivity.this, AppConstants.AUDIENCE);
        credential.setSelectedAccountName(emailAccount);

        return credential;
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
}
