package com.google.cloud.samples.campusconnect;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appspot.campus_connect_2015.clubs.Clubs;
import com.appspot.campus_connect_2015.clubs.model.ModelsClubListResponse;
import com.appspot.campus_connect_2015.clubs.model.ModelsClubMiniForm;
import com.appspot.campus_connect_2015.clubs.model.ModelsClubRetrievalMiniForm;
import com.appspot.campus_connect_2015.clubs.model.ModelsCollegeFeed;
import com.appspot.campus_connect_2015.clubs.model.ModelsGetInformation;
import com.appspot.campus_connect_2015.clubs.model.ModelsMessageResponse;
import com.appspot.campus_connect_2015.clubs.model.ModelsPostMiniForm;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.common.base.Strings;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by RK on 07-10-2015.
 */
public class CreatePostActivity extends AppCompatActivity {

    private static final String LOG_TAG="CreatePostActivity";
    ViewPager pager;
    ViewPagerAdapter_CreatePost adapter;
    SlidingTabLayout_CreatePost tabs;
    public static Button post;
    CharSequence Titles[]={"Event","News"};

    List<ModelsClubMiniForm> modelsClubMiniForms;

    static SharedPreferences sharedPreferences;
    private String mEmailAccount="";

    int Numboftabs = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        post = (Button) findViewById(R.id.b_post);

        pager = (ViewPager) findViewById(R.id.pager);
        tabs = (SlidingTabLayout_CreatePost) findViewById(R.id.tabs_createpost);
        adapter =  new ViewPagerAdapter_CreatePost(getSupportFragmentManager(),Titles,Numboftabs,CreatePostActivity.this);

        pager.setAdapter(adapter);

        tabs.setDistributeEvenly(true);
        tabs.setViewPager(pager);
        sharedPreferences = getSharedPreferences(AppConstants.SHARED_PREFS, Context.MODE_PRIVATE);
        mEmailAccount = sharedPreferences.getString(AppConstants.EMAIL_KEY, null);

    }

    public void getGroups() {
        if (!isSignedIn()) {
            Toast.makeText(CreatePostActivity.this, "You must sign in for this action.", Toast.LENGTH_LONG).show();
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

                        if (!AppConstants.checkGooglePlayServicesAvailable(CreatePostActivity.this)) {
                            return null;
                        }

                        // Create a Google credential since this is an authenticated request to the API.
                        GoogleAccountCredential credential = GoogleAccountCredential.usingAudience(
                                CreatePostActivity.this, AppConstants.AUDIENCE);
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
                                modelsClubMiniForms=displayClubs(cList);
                                Log.e(LOG_TAG,modelsClubMiniForms.toString());
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

    private boolean isSignedIn() {
        if (!Strings.isNullOrEmpty(mEmailAccount)) {
            return true;
        } else {
            return false;
        }
    }

    public void createPost(ModelsPostMiniForm postMiniForm){
        if (!isSignedIn()) {
            Toast.makeText(CreatePostActivity.this, "You must sign in for this action.", Toast.LENGTH_LONG).show();
            return;
        }

        AsyncTask<ModelsPostMiniForm, Void, Void> createFeed =
                new AsyncTask<ModelsPostMiniForm, Void, Void>() {


                    @Override
                    protected Void doInBackground(ModelsPostMiniForm... params) {
                        if (!isSignedIn()) {
                            return null;
                        }
                        ;

                        if (!AppConstants.checkGooglePlayServicesAvailable(CreatePostActivity.this)) {
                            return null;
                        }

                        // Create a Google credential since this is an authenticated request to the API.
                        GoogleAccountCredential credential = GoogleAccountCredential.usingAudience(
                                CreatePostActivity.this, AppConstants.AUDIENCE);
                        credential.setSelectedAccountName(mEmailAccount);

                        // Retrieve service handle using credential since this is an authenticated call.
                        try {
                            Clubs apiServiceHandle = AppConstants.getApiServiceHandle(credential);
                            Clubs.PostEntry postEntry = apiServiceHandle.postEntry(params[0]);
                            ModelsMessageResponse res = postEntry.execute();
                            Log.e(LOG_TAG, "SUCCESS");
                            //Log.e(LOG_TAG,res.toString());
                        } catch (IOException e) {
                            Log.e(LOG_TAG, "Exception during API call", e);
                        }
                        return null;
                    }


                };
        createFeed.execute((ModelsPostMiniForm) postMiniForm);
    }

    public class ViewPagerAdapter_CreatePost extends FragmentPagerAdapter {

        CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter_home is created
        int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter_home is created

        private Context mContext;
        // Build a Constructor and assign the passed Values to appropriate values in the class
        public ViewPagerAdapter_CreatePost(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb, Context context) {
            super(fm);
            this.Titles = mTitles;
            this.NumbOfTabs = mNumbOfTabsumb;
            this.mContext = context;
        }

        //This method return the fragment for the every position in the View Pager
        @Override
        public Fragment getItem(int position) {

            CreatePostActivity.this.getGroups();
            if(position == 0)
            {
                CreatePostActivity.FragmentPostEvent frag_post_event = new CreatePostActivity.FragmentPostEvent();
                return frag_post_event;
            }
            else
            {
                CreatePostActivity.FragmentPostNews frag_post_news = new CreatePostActivity.FragmentPostNews();
                return frag_post_news;
            }


        }



        @Override
        public CharSequence getPageTitle(int position) {
            return Titles[position];
        }


        // This method return the Number of tabs for the tabs Strip

        @Override
        public int getCount() {
            return NumbOfTabs;
        }
    }


    public class FragmentPostNews extends Fragment {
        RelativeLayout group_name_post;
        TextView group_selected_text_post;
        EditText et_title,et_description,et_date,et_time,et_venue,et_tags;
        ModelsPostMiniForm pmf;
        int position;
        final String[] items = {
                "E-Cell", "IE NITK", "Football Team","Rotaract Club"
        };
        private  static final  String LOG_TAG="GetProfileDetails";


        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.fragment_post_news, container, false);

            group_name_post = (RelativeLayout) v.findViewById(R.id.group_select_when_posting);
            group_selected_text_post = (TextView) v.findViewById(R.id.tv_group_name_selected_when_posting);

            et_title = (EditText) v.findViewById(R.id.et_post_title);
            et_description = (EditText) v.findViewById(R.id.et_post_description);
            et_date = (EditText) v.findViewById(R.id.et_date);
            et_time = (EditText) v.findViewById(R.id.et_time);
            et_tags = (EditText) v.findViewById(R.id.et_tags);

            group_name_post.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setTitle("Group:");
                    if(CreatePostActivity.this.modelsClubMiniForms==null){
                        builder.setItems(items, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int item) {
                                // Do something with the selection
                                position = item;
                                group_selected_text_post.setText(items[item]);

                            }
                        });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                    else
                    {
                        String[] groupList=new String[CreatePostActivity.this.modelsClubMiniForms.size()];
                        for(int i=0;i<modelsClubMiniForms.size();i++){
                            groupList[i]=modelsClubMiniForms.get(i).getAbbreviation();
                        }
                        builder.setItems(groupList, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int item) {
                                // Do something with the selection
                                position = item;
                                group_selected_text_post.setText(CreatePostActivity.this.modelsClubMiniForms.get(position).getAbbreviation());
                                pmf.setClubId(CreatePostActivity.this.modelsClubMiniForms.get(position).getClubId());
                            }
                        });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }



                }
            });

            CreatePostActivity.post.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pmf = new ModelsPostMiniForm();

                    String test = et_title.getText().toString();
                    //CreatePostActivity.post.setText(test);
                    SharedPreferences
                            sharedPreferences=v.getContext().getSharedPreferences(AppConstants.SHARED_PREFS, Context.MODE_PRIVATE);

                    Log.e(LOG_TAG, et_title.getText().toString());
                    Log.e(LOG_TAG, et_description.getText().toString());
                    Log.e(LOG_TAG, et_date.getText().toString());
                    Log.e(LOG_TAG, et_time.getText().toString());
                    Log.e(LOG_TAG, et_tags.getText().toString());
                    Log.e(LOG_TAG, items[position]);
                    //Log.e(LOG_TAG, sharedPreferences.getString(AppConstants.));
                    Log.e(LOG_TAG, sharedPreferences.getString(AppConstants.COLLEGE_ID, null));
                    Log.e(LOG_TAG, sharedPreferences.getString(AppConstants.PERSON_PID, null));

                    //Log.e(LOG_TAG, et_tags.getText().toString());


                    pmf.setDate(et_date.getText().toString());
                    pmf.setTime(et_time.getText().toString());
                    pmf.setTitle(et_title.getText().toString());
                    pmf.setDescription(et_description.getText().toString());
                    pmf.setClubId(sharedPreferences.getString(AppConstants.COLLEGE_ID, null));
                    pmf.setFromPid(sharedPreferences.getString(AppConstants.PERSON_PID, null));
                    String clubID = "5109799364591616"; // Remove this
                    pmf.setClubId(clubID);

                    CreatePostActivity.this.createPost(pmf);


                }
            });




            return v;
        }


    }

    public class FragmentPostEvent extends Fragment {

        RelativeLayout group_name_post;
        TextView group_selected_text_post;
        EditText et_title;
        String test;
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.fragment_post_event, container, false);
            group_name_post = (RelativeLayout) v.findViewById(R.id.group_select_when_posting);
            group_selected_text_post = (TextView) v.findViewById(R.id.tv_group_name_selected_when_posting);
            et_title = (EditText) v.findViewById(R.id.et_post_title);



            group_name_post.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final CharSequence[] items = {
                            "E-Cell", "IE NITK", "Football Team","Rotaract Club"
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setTitle("Group:");
                    builder.setItems(items, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int item) {
                            // Do something with the selection
                            group_selected_text_post.setText(items[item]);
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();


                }
            });

            CreatePostActivity.post.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    test = et_title.getText().toString();
                    CreatePostActivity.post.setText(test);


                }
            });
            return v;
        }
    }



}
