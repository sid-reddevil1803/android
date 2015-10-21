package com.google.cloud.samples.campusconnect;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.appspot.campus_connect_2015.clubs.Clubs;
import com.appspot.campus_connect_2015.clubs.model.ModelsClubListResponse;
import com.appspot.campus_connect_2015.clubs.model.ModelsClubMiniForm;
import com.appspot.campus_connect_2015.clubs.model.ModelsClubRetrievalMiniForm;
import com.appspot.campus_connect_2015.clubs.model.ModelsCollegeFeed;
import com.appspot.campus_connect_2015.clubs.model.ModelsColleges;
import com.appspot.campus_connect_2015.clubs.model.ModelsFeed;
import com.appspot.campus_connect_2015.clubs.model.ModelsGetCollege;
import com.appspot.campus_connect_2015.clubs.model.ModelsGetInformation;
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

    GroupListAdapterActivity gl;

    RecyclerView group_list;

    ImageButton admin;
    ImageButton noti, profile, home, calendar, search;
    public static TextView title, title_two;
    ViewPager pager;
    ViewPagerAdapter_home adapter;
    SlidingTabLayout_home tabs;
    CharSequence Titles[] = {"Live", "MyFeed", "CampusFeed", "Groups"};
    int Numboftabs = 4;

    private static String mEmailAccount = "";
    private static final String LOG_TAG = "MainActivity";
    static SharedPreferences sharedPreferences;

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
        adapter = new ViewPagerAdapter_home(getSupportFragmentManager(), Titles, Numboftabs, MainActivity.this);

        pager.setAdapter(adapter);

        pager.setCurrentItem(1);
        tabs.setDistributeEvenly(true);
        tabs.setViewPager(pager);

        sharedPreferences = getSharedPreferences(AppConstants.SHARED_PREFS, Context.MODE_PRIVATE);
        mEmailAccount = sharedPreferences.getString(AppConstants.EMAIL_KEY, null);


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

    public void getPersonalFeed(){
        if (!isSignedIn()) {
            Toast.makeText(MainActivity.this, "You must sign in for this action.", Toast.LENGTH_LONG).show();
            return;
        }

        AsyncTask<Void, Void, ModelsCollegeFeed> getPersonalFeed =
                new AsyncTask<Void, Void, ModelsCollegeFeed>() {
                    @Override
                    protected ModelsCollegeFeed doInBackground(Void... unused) {
                        if (!isSignedIn()) {
                            return null;
                        };

                        if (!AppConstants.checkGooglePlayServicesAvailable(MainActivity.this)) {
                            return null;
                        }

                        // Create a Google credential since this is an authenticated request to the API.
                        GoogleAccountCredential credential = GoogleAccountCredential.usingAudience(
                                MainActivity.this, AppConstants.AUDIENCE);
                        credential.setSelectedAccountName(mEmailAccount);

                        // Retrieve service handle using credential since this is an authenticated call.
                        try {
                            Clubs apiServiceHandle = AppConstants.getApiServiceHandle(credential);
                            ModelsGetInformation modelsGetInformation=new ModelsGetInformation();
                            modelsGetInformation.setCollegeId(sharedPreferences.getString(AppConstants.COLLEGE_ID, "null"));
                            modelsGetInformation.setPid(sharedPreferences.getString(AppConstants.PERSON_PID, "null"));
                            Clubs.PersonalFeed getFeed = apiServiceHandle.personalFeed(modelsGetInformation);

                            ModelsCollegeFeed pf = getFeed.execute();
                            Log.e(LOG_TAG, "SUCCESS");
                            Log.e(LOG_TAG, pf.toPrettyString());
                            return pf;
                        } catch (IOException e) {
                            Log.e(LOG_TAG, "Exception during API call", e);
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(ModelsCollegeFeed cList) {
                        if (cList!=null) {
                            try {
                                Log.e(LOG_TAG,cList.toPrettyString());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.e(LOG_TAG, "No clubs were returned by the API.");
                        }
                    }
                };
        getPersonalFeed.execute((Void) null);
    }

    public void getCampusFeed(){
        if (!isSignedIn()) {
            Toast.makeText(MainActivity.this, "You must sign in for this action.", Toast.LENGTH_LONG).show();
            return;
        }

        AsyncTask<Void, Void, ModelsCollegeFeed> getCampusFeed =
                new AsyncTask<Void, Void, ModelsCollegeFeed>() {
                    @Override
                    protected ModelsCollegeFeed doInBackground(Void... unused) {
                        if (!isSignedIn()) {
                            return null;
                        };

                        if (!AppConstants.checkGooglePlayServicesAvailable(MainActivity.this)) {
                            return null;
                        }

                        // Create a Google credential since this is an authenticated request to the API.
                        GoogleAccountCredential credential = GoogleAccountCredential.usingAudience(
                                MainActivity.this, AppConstants.AUDIENCE);
                        credential.setSelectedAccountName(mEmailAccount);

                        // Retrieve service handle using credential since this is an authenticated call.
                        try {
                            Clubs apiServiceHandle = AppConstants.getApiServiceHandle(credential);
                            ModelsGetInformation modelsGetInformation=new ModelsGetInformation();
                            modelsGetInformation.setCollegeId(sharedPreferences.getString(AppConstants.COLLEGE_ID, "null"));
                            Clubs.CollegeFeed getCollegeFeed=apiServiceHandle.collegeFeed(modelsGetInformation);

                            ModelsCollegeFeed cf = getCollegeFeed.execute();
                            Log.e(LOG_TAG, "SUCCESS");
                            Log.e(LOG_TAG, cf.toPrettyString());
                            return cf;
                        } catch (IOException e) {
                            Log.e(LOG_TAG, "Exception during API call", e);
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(ModelsCollegeFeed cList) {
                        if (cList!=null) {
                            try {
                                Log.e(LOG_TAG,cList.toPrettyString());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.e(LOG_TAG, "No clubs were returned by the API.");
                        }
                    }
                };
        getCampusFeed.execute((Void) null);
    }


    public void getGroups() {
        if (!isSignedIn()) {
            Toast.makeText(MainActivity.this, "You must sign in for this action.", Toast.LENGTH_LONG).show();
            return;
        }

        AsyncTask<Void, Void, ModelsClubListResponse> getClubsAndPopulate =
                new AsyncTask<Void, Void, ModelsClubListResponse>() {
                    @Override
                    protected ModelsClubListResponse doInBackground(Void... unused) {
                        if (!isSignedIn()) {
                            return null;
                        };

                        if (!AppConstants.checkGooglePlayServicesAvailable(MainActivity.this)) {
                            return null;
                        }

                        // Create a Google credential since this is an authenticated request to the API.
                        GoogleAccountCredential credential = GoogleAccountCredential.usingAudience(
                                MainActivity.this, AppConstants.AUDIENCE);
                        credential.setSelectedAccountName(mEmailAccount);

                        // Retrieve service handle using credential since this is an authenticated call.
                        Clubs apiServiceHandle = AppConstants.getApiServiceHandle(credential);

                        try {
                            ModelsClubRetrievalMiniForm clubRetrievalMiniForm=new ModelsClubRetrievalMiniForm();
                            clubRetrievalMiniForm.setCollegeId(sharedPreferences.getString(AppConstants.COLLEGE_ID,"null"));
                            Clubs.GetClubList gcl= apiServiceHandle.getClubList(clubRetrievalMiniForm);
                            ModelsClubListResponse clubListResponse = gcl.execute();
                            Log.e(LOG_TAG, "SUCCESS");
                            Log.e(LOG_TAG, clubListResponse.toPrettyString());
                            return clubListResponse;
                        } catch (IOException e) {
                            Log.e(LOG_TAG, "Exception during API call", e);
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(ModelsClubListResponse cList) {
                        if (cList!=null) {
                            try {
                                Log.e(LOG_TAG, cList.toPrettyString());
                                gl=new GroupListAdapterActivity(displayClubs(cList));
                                gl.notifyDataSetChanged();
                                group_list.setAdapter(gl);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.e(LOG_TAG, "No clubs were returned by the API.");
                        }
                    }
                };

        getClubsAndPopulate.execute((Void) null);
    }

    private boolean isSignedIn() {
        if (!Strings.isNullOrEmpty(mEmailAccount)) {
            return true;
        } else {
            return false;
        }
    }

    private List<ModelsClubMiniForm> displayClubs(ModelsClubListResponse... response) {
        Log.e(LOG_TAG, response.toString());

        if (response==null || response.length < 1) {
            return null;
        } else
        {
            Log.d(LOG_TAG, "Displaying " + response.length + " colleges.");
            List<ModelsClubListResponse> clubList = Arrays.asList(response);
            return clubList.get(0).getList();
        }
    }


    public class FragmentGroups extends Fragment {

        private static final String LOG_TAG = "FragmentGroups";

        Button create_group;

        private String mEmailAccount = "";

        SharedPreferences sharedPreferences;

        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

            MainActivity.this.getGroups();
            View v = inflater.inflate(R.layout.fragment_groups, container, false);

            group_list = (RecyclerView) v.findViewById(R.id.rv_group_list);
            create_group = (Button) v.findViewById(R.id.b_create_group);

            //group_list.setHasFixedSize(true);
            LinearLayoutManager llm = new LinearLayoutManager(v.getContext());
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            group_list.setLayoutManager(llm);
            group_list.setItemAnimator(new DefaultItemAnimator());
            gl=new GroupListAdapterActivity(createList_gl(1));
            group_list.setAdapter(gl);
            gl.notifyDataSetChanged();

            create_group.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent_temp = new Intent(v.getContext(), CreateGroupActivity.class);
                    startActivity(intent_temp);

                }
            });
            return v;
        }

        public List<ModelsClubMiniForm> createList_gl(int size) {
            List<ModelsClubMiniForm> result = new ArrayList<ModelsClubMiniForm>();
            for (int i = 1; i <=size; i++) {
                ModelsClubMiniForm ci = new ModelsClubMiniForm();

                result.add(ci);
            }
            return result;
        }


    }


    public class FragmentEvents extends Fragment {
        private static final String LOG_TAG="FragmentEvents";
        RecyclerView college_feed;
        FloatingActionButton fab;
        String collegeId;
        String mEmailAccount = "";
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View v =inflater.inflate(R.layout.fragment_events,container,false);

            fab=(FloatingActionButton)v.findViewById(R.id.fab_add);
            college_feed = (RecyclerView) v.findViewById(R.id.rv_college_feed);
            college_feed.setHasFixedSize(false);
            LinearLayoutManager llm = new LinearLayoutManager(v.getContext());
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            college_feed.setLayoutManager(llm);
            college_feed.setItemAnimator(new DefaultItemAnimator());
            CollegeFeedAdapterActivity cf = new CollegeFeedAdapterActivity(
                    createList_cf(7));
            college_feed.setAdapter(cf);

            SharedPreferences sharedpreferences = v.getContext().getSharedPreferences(AppConstants.SHARED_PREFS, Context.MODE_PRIVATE);
            collegeId=sharedpreferences.getString(AppConstants.COLLEGE_ID,null);
            mEmailAccount=sharedpreferences.getString(AppConstants.EMAIL_KEY,null);

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent_temp = new Intent(v.getContext(), CreatePostActivity.class);
                    startActivity(intent_temp);

                }
            });
            return v;
        }
        private List<CollegeFeed_infoActivity> createList_cf(int size) {
            List<CollegeFeed_infoActivity> result = new ArrayList<CollegeFeed_infoActivity>();
            for (int i = 1; i <= size; i++) {
                CollegeFeed_infoActivity ci = new CollegeFeed_infoActivity();
                result.add(ci);

            }

            return result;
        }
    }


    public class FragmentTopNews extends Fragment {

        RecyclerView topnews;
        FloatingActionButton fab;
        public int pos=0;

        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View v =inflater.inflate(R.layout.fragment_top_news,container,false);

            fab=(FloatingActionButton)v.findViewById(R.id.fab_add);
            topnews = (RecyclerView) v.findViewById(R.id.rv_top_news);
            topnews.setHasFixedSize(true);
            LinearLayoutManager llm = new LinearLayoutManager(v.getContext());
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            topnews.setLayoutManager(llm);
            topnews.setItemAnimator(new DefaultItemAnimator());
            TopNewsAdapterActivity tn = new TopNewsAdapterActivity(
                    createList_tn(4));
            topnews.setAdapter(tn);
            tn.SetOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(View v , int position) {
                    pos=position;
                }
            });

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent_temp = new Intent(v.getContext(), CreatePostActivity.class);
                    startActivity(intent_temp);

                }
            });

            return v;
        }



        private List<TopNews_infoActivity> createList_tn(int size) {
            List<TopNews_infoActivity> result = new ArrayList<TopNews_infoActivity>();
            for (int i = 1; i <= size; i++) {
                TopNews_infoActivity ci = new TopNews_infoActivity();

                result.add(ci);
            }
            return result;
        }
    }


    public class FragmentLive extends Fragment {

        RecyclerView live;
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View v =inflater.inflate(R.layout.fragment_live_to_be_deleted,container,false);

            return v;
        }

        private List<Live_infoActivity> createList_l(int size) {
            List<Live_infoActivity> result = new ArrayList<Live_infoActivity>();
            for (int i = 1; i <= size; i++) {
                Live_infoActivity ci = new Live_infoActivity();

                result.add(ci);
            }
            return result;
        }

    }


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
                MainActivity.FragmentLive fraglive = new MainActivity.FragmentLive();
                return fraglive;
            }
            else if(position == 1)
            {
                MainActivity.FragmentTopNews fragtopnews = new MainActivity.FragmentTopNews();
                MainActivity.this.getPersonalFeed();
                return fragtopnews;
            }
            else if(position == 2)
            {
                MainActivity.FragmentEvents fragevents = new MainActivity.FragmentEvents();
                MainActivity.this.getCampusFeed();
                return fragevents;
            }
            else
            {
                MainActivity.FragmentGroups fraggroups = new MainActivity.FragmentGroups();
                //MainActivity.this.getGroups();
                return fraggroups;
            }
        }



        private int[] ICONS = new int[] {
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

}