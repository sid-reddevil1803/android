package com.google.cloud.samples.campusconnect;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
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
import com.appspot.campus_connect_2015.clubs.model.ModelsFollowClubMiniForm;
import com.appspot.campus_connect_2015.clubs.model.ModelsGetCollege;
import com.appspot.campus_connect_2015.clubs.model.ModelsGetInformation;
import com.appspot.campus_connect_2015.clubs.model.ModelsPosts;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.cloud.samples.campusconnect.LoginActivity.CollegeListAdapterActivity;
import com.google.cloud.samples.campusconnect.LoginActivity.CollegeList_infoActivity;
import com.google.common.base.Strings;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


/**
 * Created by RK on 17-09-2015.
 */
public class MainActivity extends AppCompatActivity {

    CollegeFeedAdapterActivity tn;//change to myfeed
    GroupListAdapterActivity gl;
    CollegeFeedAdapterActivity cf;//change to campusfeed
    RecyclerView group_list;
    RecyclerView college_feed;
    RecyclerView topnews;

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

    public void getPersonalFeed() {
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
                        }
                        ;

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
                            ModelsGetInformation modelsGetInformation = new ModelsGetInformation();
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
                        if (cList != null) {
                            try {
                                Log.e(LOG_TAG, cList.toPrettyString());
                                tn = new CollegeFeedAdapterActivity(displayCampusFeed(cList));
                                tn.notifyDataSetChanged();
                                topnews.setAdapter(tn);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                         //   Log.e(LOG_TAG, "No clubs were returned by the API.");
                        }
                    }
                };
        getPersonalFeed.execute((Void) null);
    }

    public void getCampusFeed() {
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
                        }
                        ;

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
                            ModelsGetInformation modelsGetInformation = new ModelsGetInformation();
                            modelsGetInformation.setCollegeId(sharedPreferences.getString(AppConstants.COLLEGE_ID, "null"));
                            Clubs.CollegeFeed getCollegeFeed = apiServiceHandle.collegeFeed(modelsGetInformation);

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
                        if (cList != null) {
                            try {
                                Log.e(LOG_TAG, cList.toPrettyString());
                                cf = new CollegeFeedAdapterActivity(displayCampusFeed(cList));
                                cf.notifyDataSetChanged();
                                college_feed.setAdapter(cf);
                                //displayCampusFeed(cList);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            //Log.e(LOG_TAG, "No clubs were returned by the API.");
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
                        }
                        ;

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
                            ModelsClubRetrievalMiniForm clubRetrievalMiniForm = new ModelsClubRetrievalMiniForm();
                            clubRetrievalMiniForm.setCollegeId(sharedPreferences.getString(AppConstants.COLLEGE_ID, "null"));
                            Clubs.GetClubList gcl = apiServiceHandle.getClubList(clubRetrievalMiniForm);
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
                        if (cList != null) {
                            try {
                                Log.e(LOG_TAG, cList.toPrettyString());
                                gl = new GroupListAdapterActivity(displayClubs(cList));
                                gl.notifyDataSetChanged();
                                group_list.setAdapter(gl);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            //Log.e(LOG_TAG, "No clubs were returned by the API.");
                        }
                    }
                };

        getClubsAndPopulate.execute((Void) null);
    }


    public void followGroup(final ModelsClubMiniForm modelsClubMiniForm){
        if (!isSignedIn()) {
            Toast.makeText(MainActivity.this, "You must sign in for this action.", Toast.LENGTH_LONG).show();
            return;
        }

        AsyncTask<Void, Void, Void> followGroup =
                new AsyncTask<Void, Void, Void>() {

                    @Override
                    protected Void doInBackground(Void... unused) {
                        if (!isSignedIn()) {
                            return null;
                        }
                        ;

                        if (!AppConstants.checkGooglePlayServicesAvailable(MainActivity.this)) {
                            return null;
                        }

                        // Create a Google credential since this is an authenticated request to the API.
                        GoogleAccountCredential credential = GoogleAccountCredential.usingAudience(
                                MainActivity.this, AppConstants.AUDIENCE);
                        credential.setSelectedAccountName(mEmailAccount);

                        // Retrieve service handle using credential since this is an authenticated call.
                        Clubs apiServiceHandle = AppConstants.getApiServiceHandle(credential);
                        //apiServiceHandle.followClub()
                        try {
                            ModelsFollowClubMiniForm modelsFollowClubMiniForm=new ModelsFollowClubMiniForm();
                            modelsFollowClubMiniForm.setClubId(modelsClubMiniForm.getClubId());
                            modelsFollowClubMiniForm.setFromPid(sharedPreferences.getString(AppConstants.PERSON_PID, null));
                            Log.e(LOG_TAG+"as",modelsFollowClubMiniForm.toPrettyString());
                            Clubs.FollowClub followClub = apiServiceHandle.followClub(modelsFollowClubMiniForm);
                            Void followClubResponse = followClub.execute();
                            Log.e(LOG_TAG, "SUCCESS followed");
//                            Log.e(LOG_TAG, followClubResponse.toString());
                            return followClubResponse;
                        } catch (IOException e) {
                            Log.e(LOG_TAG, "Exception during API call", e);
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void cList) {
                        if (cList != null) {
                        } else {
                          //  Log.e(LOG_TAG, "No clubs were returned by the API.");
                        }
                    }
                };

        followGroup.execute((Void) null);
    }

    public void unFollowGroup(final ModelsClubMiniForm modelsClubMiniForm){
        if (!isSignedIn()) {
            Toast.makeText(MainActivity.this, "You must sign in for this action.", Toast.LENGTH_LONG).show();
            return;
        }

        AsyncTask<Void, Void, Void> unFollowGroup =
                new AsyncTask<Void, Void, Void>() {

                    @Override
                    protected Void doInBackground(Void... unused) {
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
                        //apiServiceHandle.followClub()
                        try {
                            ModelsFollowClubMiniForm modelsFollowClubMiniForm=new ModelsFollowClubMiniForm();
                            modelsFollowClubMiniForm.setClubId(modelsClubMiniForm.getClubId());
                            modelsFollowClubMiniForm.setFromPid(sharedPreferences.getString(AppConstants.PERSON_PID,null));

                            Clubs.UnfClub unfollowClub = apiServiceHandle.unfClub(modelsFollowClubMiniForm);
                            Void unfollowClubResponse = unfollowClub.execute();
                            Log.e(LOG_TAG, "SUCCESS unfollowed");
                            //Log.e(LOG_TAG, unfollowClubResponse.toString());
                            return unfollowClubResponse;
                        } catch (IOException e) {
                            Log.e(LOG_TAG, "Exception during API call", e);
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void cList) {
                        if (cList != null) {
                        } else {
                           // Log.e(LOG_TAG, "No clubs were returned by the API.");
                        }
                    }
                };

        unFollowGroup.execute((Void) null);
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

        if (response == null || response.length < 1) {
            return null;
        } else {
            Log.d(LOG_TAG, "Displaying " + response.length + " colleges.");
            List<ModelsClubListResponse> clubList = Arrays.asList(response);
            return clubList.get(0).getList();
        }
    }

    private List<ModelsFeed> displayCampusFeed(ModelsCollegeFeed... response) {
        Log.e(LOG_TAG, "inside");

        if (response == null) {
            Log.e(LOG_TAG, "null");
            return null;
        } else {
            Log.e(LOG_TAG, "Displaying " + response.length + " colleges.");
            List<ModelsCollegeFeed> clubList = Arrays.asList(response);
            //Log.e(LOG_TAG, clubList.toString());
            Log.e(LOG_TAG, clubList.get(0).getItems().toString());
            return clubList.get(0).getItems();
        }
    }

    class FragmentGroups extends Fragment {

        private static final String LOG_TAG = "FragmentGroups";

        Button create_group;

        private String mEmailAccount = "";

        SharedPreferences sharedPreferences;

        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

            //MainActivity.this.getGroups();
            View v = inflater.inflate(R.layout.fragment_groups, container, false);

            group_list = (RecyclerView) v.findViewById(R.id.rv_group_list);
            create_group = (Button) v.findViewById(R.id.b_create_group);

            //group_list.setHasFixedSize(true);
            LinearLayoutManager llm = new LinearLayoutManager(v.getContext());
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            group_list.setLayoutManager(llm);
            group_list.setItemAnimator(new DefaultItemAnimator());
            if (gl == null) {
                try {
                    gl = new GroupListAdapterActivity(createList_gl(1));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
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
            for (int i = 1; i <= size; i++) {
                ModelsClubMiniForm ci = new ModelsClubMiniForm();

                result.add(ci);
            }
            return result;
        }


    }


    public class FragmentCampusFeed extends Fragment {
        private static final String LOG_TAG = "FragmentCampusFeed";
        FloatingActionButton fab;
        String collegeId;
        String mEmailAccount = "";

        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.fragment_events, container, false);

            fab = (FloatingActionButton) v.findViewById(R.id.fab_add);
            college_feed = (RecyclerView) v.findViewById(R.id.rv_college_feed);
            college_feed.setHasFixedSize(false);
            LinearLayoutManager llm = new LinearLayoutManager(v.getContext());
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            college_feed.setLayoutManager(llm);
            college_feed.setItemAnimator(new DefaultItemAnimator());
            if (cf == null) {
                cf = new CollegeFeedAdapterActivity(
                        createList_cf(7));
            }
            college_feed.setAdapter(cf);

            SharedPreferences sharedpreferences = v.getContext().getSharedPreferences(AppConstants.SHARED_PREFS, Context.MODE_PRIVATE);
            collegeId = sharedpreferences.getString(AppConstants.COLLEGE_ID, null);
            mEmailAccount = sharedpreferences.getString(AppConstants.EMAIL_KEY, null);

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent_temp = new Intent(v.getContext(), CreatePostActivity.class);
                    startActivity(intent_temp);

                }
            });
            return v;
        }

        private List<ModelsFeed> createList_cf(int size) {
            List<ModelsFeed> result = new ArrayList<ModelsFeed>();
            for (int i = 1; i <= size; i++) {
                ModelsFeed ci = new ModelsFeed();
                result.add(ci);

            }

            return result;
        }
    }


    public class FragmentMyFeed extends Fragment {

        FloatingActionButton fab;
        public int pos = 0;

        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.fragment_top_news, container, false);

            fab = (FloatingActionButton) v.findViewById(R.id.fab_add);
            topnews = (RecyclerView) v.findViewById(R.id.rv_top_news);
            topnews.setHasFixedSize(true);
            LinearLayoutManager llm = new LinearLayoutManager(v.getContext());
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            topnews.setLayoutManager(llm);
            topnews.setItemAnimator(new DefaultItemAnimator());
            if (tn == null) {
                tn = new CollegeFeedAdapterActivity(
                        createList_cf(4));
            }
            topnews.setAdapter(tn);

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent_temp = new Intent(v.getContext(), CreatePostActivity.class);
                    startActivity(intent_temp);

                }
            });

            return v;
        }


        private List<ModelsFeed> createList_cf(int size) {
            List<ModelsFeed> result = new ArrayList<ModelsFeed>();
            for (int i = 1; i <= size; i++) {
                ModelsFeed ci = new ModelsFeed();
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
                MainActivity.FragmentMyFeed fragtopnews = new MainActivity.FragmentMyFeed();
                MainActivity.this.getPersonalFeed();
                return fragtopnews;
            }
            else if(position == 2)
            {
                MainActivity.FragmentCampusFeed fragevents = new MainActivity.FragmentCampusFeed();
                MainActivity.this.getCampusFeed();
                return fragevents;
            }
            else
            {
                MainActivity.FragmentGroups fraggroups = new MainActivity.FragmentGroups();
                MainActivity.this.getGroups();
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


    public class GroupListAdapterActivity extends
            RecyclerView.Adapter<GroupListAdapterActivity.GroupListViewHolder> {

        private List<ModelsClubMiniForm> GroupList;
        private List<String> itemsName;
        int posi=0;
        private  int[] GroupLogo = new int[] {
                R.mipmap.cell_logo,
                R.mipmap.football_logo,
                R.mipmap.ie_logo,
                R.mipmap.roto_logo
        };
        private  int[] followers_count = new int[] { 2,3,4,2};
        private  int[] members_count = new int[] { 1,2,1,2};

        private HashMap<String,String> followingMap=new HashMap<>();
        private List<Boolean> followingFlag ;

        public GroupListAdapterActivity(List<ModelsClubMiniForm> GroupList) throws IOException {
            this.GroupList = GroupList;

            File f=new File(getFilesDir(),"Follows.txt");
            BufferedReader bfr=new BufferedReader(new InputStreamReader(new FileInputStream(f)));
            String temp;
            while((temp=bfr.readLine())!=null){
                String key=temp.substring(0,temp.indexOf('|'));
                String value=temp.substring(temp.indexOf('|')+1,temp.length());
                followingMap.put(key,value);
            }
            bfr.close();

            followingFlag = new ArrayList<Boolean>();
            for(ModelsClubMiniForm modelsClubMiniForm:GroupList){

                if(followingMap.containsKey(modelsClubMiniForm.getClubId())){
                    followingFlag.add(Boolean.TRUE);
                }else{
                    followingFlag.add(Boolean.FALSE);
                }
            }

        }

        @Override
        public int getItemCount() {
            return GroupList.size();
        }

        public void add(int location, String item){
            itemsName.add(location, item);
            notifyItemInserted(location);
        }

        @Override
        public void onBindViewHolder(GroupListViewHolder group_listViewHolder, int i) {
            //ModelsClubMiniForm ci = GroupList.get(i);
            group_listViewHolder.group_title.setText(GroupList.get(i).getAbbreviation());
            if(followingFlag.get(i)){
                group_listViewHolder.following.setVisibility(View.VISIBLE);
                group_listViewHolder.follow.setVisibility(View.GONE);
            }
        }

        @Override
        public GroupListViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(
                    R.layout.activity_card_layout_group_list, viewGroup, false);

            return new GroupListViewHolder(itemView);
        }

        public void removeLineFromFile(File inFile, String lineToRemove) {

            try {

//                File inFile = new File(file);

                if (!inFile.isFile()) {
                    System.out.println("Parameter is not an existing file");
                    return;
                }

                //Construct the new file that will later be renamed to the original filename.
                File tempFile = new File(inFile.getAbsolutePath() + ".tmp");

                BufferedReader br = new BufferedReader(new FileReader(inFile));
                PrintWriter pw = new PrintWriter(new FileWriter(tempFile));

                String line = null;

                //Read from the original file and write to the new
                //unless content matches data to be removed.
                while ((line = br.readLine()) != null) {

                    if (!line.trim().equals(lineToRemove)) {

                        pw.println(line);
                        pw.flush();
                    }
                }
                pw.close();
                br.close();

                //Delete the original file
                if (!inFile.delete()) {
                    System.out.println("Could not delete file");
                    return;
                }

                //Rename the new file to the filename the original file had.
                if (!tempFile.renameTo(inFile))
                    System.out.println("Could not rename file");

            }
            catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        public void writeLineToFile(File f,String line) {
            Log.e(LOG_TAG+"345","here");
            if(f==null){
                Log.e("NULL","adf");
            }
            try {
                FileOutputStream fos = openFileOutput(f.getName(), MODE_APPEND);
                OutputStreamWriter outputStreamWriter= new OutputStreamWriter(fos);
                outputStreamWriter.write(line);
                outputStreamWriter.flush();
                outputStreamWriter.close();
                Log.e(LOG_TAG + "345", "made it here");
            }catch (Exception e){
                e.printStackTrace();
                Log.e(LOG_TAG + "345", "didnt come here");
                Log.e("EXcpe",e.getMessage());
            }
            Log.e(LOG_TAG+"345","here now");

        }

        public void printFileContents(File f){
            try {
                String line;
                BufferedReader bfr=new BufferedReader(new InputStreamReader(new FileInputStream(f)));
                while ((line = bfr.readLine()) != null) {
                    Log.e(LOG_TAG+"123",line);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        public class GroupListViewHolder extends RecyclerView.ViewHolder {

            CardView group_list;
            TextView follow,following, group_title;

            public GroupListViewHolder(View v) {
                super(v);

                group_list = (CardView) v.findViewById(R.id.group_list_card);
                group_title = (TextView) v.findViewById(R.id.tv_group_name);
                follow = (TextView) v.findViewById(R.id.tv_follow);
                following = (TextView) v.findViewById(R.id.tv_following);

                group_list.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent_temp = new Intent(v.getContext(), GroupPageActivity.class);
                        posi=getPosition();

                        Bundle bundle = new Bundle();
                        bundle.putString("G_NAME", (String) GroupList.get(posi).getName());
                        bundle.putInt("G_ICON", GroupLogo[posi]);//have to change this
                        if(GroupList.get(posi).getFollowers()==null){
                            bundle.putInt("F_COUNT", 0);
                        }else {
                            bundle.putInt("F_COUNT", GroupList.get(posi).getFollowers().length());
                        }
                        if(GroupList.get(posi).getMembers()==null){
                            bundle.putInt("M_COUNT",0);
                        }else{
                            bundle.putInt("M_COUNT",GroupList.get(posi).getMembers().length());
                        }
                        intent_temp.putExtras(bundle);

                        v.getContext().startActivity(intent_temp);

                    }
                });

                follow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        posi = getPosition();
                        follow.setVisibility(View.GONE);
                        following.setVisibility(View.VISIBLE);
                        followingMap.put(GroupList.get(posi).getClubId(), GroupList.get(posi).getName());
                        followingFlag.set(posi, Boolean.TRUE);
                        File f=new File(getFilesDir(),"Follows.txt");
                        writeLineToFile(f,GroupList.get(posi).getClubId()+"|"+GroupList.get(posi).getName()+"\n");
                        printFileContents(f);
                        Log.e("check",GroupList.get(posi).getName()+posi);
                        followGroup(GroupList.get(posi));
                    }
                });
                following.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        posi=getPosition();
                        follow.setVisibility(View.VISIBLE);
                        following.setVisibility(View.GONE);
                        followingFlag.set(posi, Boolean.FALSE);
                        followingMap.remove(GroupList.get(posi).getClubId());
                        File f = new File(getFilesDir(), "Follows.txt");
                        removeLineFromFile(f, GroupList.get(posi).getClubId() + "|" + GroupList.get(posi).getName());
                        printFileContents(f);
                        Log.e("check", GroupList.get(posi).getName() + posi);
                        unFollowGroup(GroupList.get(posi));
                    }
                });

            }

        }
    }

}