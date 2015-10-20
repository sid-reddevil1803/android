package com.google.cloud.samples.campusconnect;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by RK on 07-10-2015.
 */
public class CreatePostActivity extends AppCompatActivity {

    ViewPager pager;
    ViewPagerAdapter_CreatePost adapter;
    SlidingTabLayout_CreatePost tabs;
    CharSequence Titles[]={"Event","News"};
    int Numboftabs = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        pager = (ViewPager) findViewById(R.id.pager);
        tabs = (SlidingTabLayout_CreatePost) findViewById(R.id.tabs_createpost);
        adapter =  new ViewPagerAdapter_CreatePost(getSupportFragmentManager(),Titles,Numboftabs,CreatePostActivity.this);

        pager.setAdapter(adapter);

        tabs.setDistributeEvenly(true);
        tabs.setViewPager(pager);

    }
}
