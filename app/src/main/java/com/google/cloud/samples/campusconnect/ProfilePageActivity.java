package com.google.cloud.samples.campusconnect;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;


public class ProfilePageActivity extends ActionBarActivity {

    RecyclerView groups_joined;
    int top=0;
    ImageButton noti,profile,home,calendar,search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        home = (ImageButton) findViewById(R.id.ib_home);
        noti = (ImageButton) findViewById(R.id.ib_notification);
        profile = (ImageButton) findViewById(R.id.ib_profile);
        calendar = (ImageButton) findViewById(R.id.ib_calendar);
        search = (ImageButton) findViewById(R.id.ib_search);

        groups_joined = (RecyclerView) findViewById(R.id.recycler_groups);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        groups_joined.setLayoutManager(llm);
        groups_joined.setHasFixedSize(true);
        groups_joined.setItemAnimator(new DefaultItemAnimator());

        ProfilePageAdapterActivity gj = new ProfilePageAdapterActivity(
                createList_groups_joined(3));

        groups_joined.setAdapter(gj);

        groups_joined.setOnScrollListener(new MyScrollListenerProfilePage(this) {

            @Override
            public void onMoved(int distance) {
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                top += dy;
            }

        });

        noti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent_temp = new Intent(v.getContext(), NotificationActivity.class);
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

    private List<ProfilePage_infoActivity> createList_groups_joined(int size) {

        List<ProfilePage_infoActivity> result = new ArrayList<ProfilePage_infoActivity>();
        for (int i = 1; i <= size; i++) {
            ProfilePage_infoActivity ci = new ProfilePage_infoActivity();

            result.add(ci);

        }

        return result;
    }
}
