package com.google.cloud.samples.campusconnect.LoginActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.appspot.campus_connect_2015.clubs.Clubs;
import com.appspot.campus_connect_2015.clubs.model.ModelsColleges;
import com.appspot.campus_connect_2015.clubs.model.ModelsGetCollege;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.cloud.samples.campusconnect.*;
import com.google.common.base.Strings;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by RK on 23-09-2015.
 */
public class SelectCollegeActivity extends AppCompatActivity {

    private static final String LOG_TAG = "SelectCollegeActivity";

    //private AuthorizationCheckTask mAuthTask;
    private String mEmailAccount = "";


    RecyclerView college_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_college);

        college_list = (RecyclerView) findViewById(R.id.rv_college_list);

        college_list.setHasFixedSize(false);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        college_list.setLayoutManager(llm);
        college_list.setItemAnimator(new DefaultItemAnimator());
        SharedPreferences sharedpreferences = getSharedPreferences(AppConstants.SHARED_PREFS, Context.MODE_PRIVATE);
        mEmailAccount=sharedpreferences.getString(AppConstants.EMAIL_KEY,null);
        getColleges();
    }

    private List<CollegeList_infoActivity> createList_cl(int size) {
        List<CollegeList_infoActivity> result = new ArrayList<CollegeList_infoActivity>();
        for (int i = 1; i <= size; i++) {
            CollegeList_infoActivity ci = new CollegeList_infoActivity();
            result.add(ci);
        }

        return result;
    }


    public void getColleges() {
        if (!isSignedIn()) {
            Toast.makeText(this, "You must sign in for this action.", Toast.LENGTH_LONG).show();
            return;
        }

        AsyncTask<Void, Void, ModelsColleges> getCollegesAndPopulate =
                new AsyncTask<Void, Void, ModelsColleges>() {
                    @Override
                    protected ModelsColleges doInBackground(Void... unused) {
                        if (!isSignedIn()) {
                            return null;
                        };

                        if (!AppConstants.checkGooglePlayServicesAvailable(SelectCollegeActivity.this)) {
                            return null;
                        }

                        // Create a Google credential since this is an authenticated request to the API.
                        GoogleAccountCredential credential = GoogleAccountCredential.usingAudience(
                                SelectCollegeActivity.this, AppConstants.AUDIENCE);
                        credential.setSelectedAccountName(mEmailAccount);

                        // Retrieve service handle using credential since this is an authenticated call.
                        Clubs apiServiceHandle = AppConstants.getApiServiceHandle(credential);

                        try {
                            Clubs.GetColleges gc = apiServiceHandle.getColleges();
                            ModelsColleges collegeList = gc.execute();
                            Log.e(LOG_TAG, "SUCCESS");
                            Log.e(LOG_TAG, collegeList.toPrettyString());
                            return collegeList;
                        } catch (IOException e) {
                            Log.e(LOG_TAG, "Exception during API call", e);
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(ModelsColleges cList) {
                        if (cList!=null) {
                            CollegeListAdapterActivity cl = new CollegeListAdapterActivity(
                                    displayColleges(cList));
                            college_list.setAdapter(cl);

                        } else {
                            Log.e(LOG_TAG, "No colleges were returned by the API.");
                        }
                    }
                };

        getCollegesAndPopulate.execute((Void) null);
    }

    private boolean isSignedIn() {
        if (!Strings.isNullOrEmpty(mEmailAccount)) {
            return true;
        } else {
            return false;
        }
    }

    private List<CollegeList_infoActivity> displayColleges(ModelsColleges... response) {
        String msg="nothing";
        List<CollegeList_infoActivity> cList = new ArrayList<CollegeList_infoActivity>();

        Log.e(LOG_TAG, response.toString());

        if (response==null || response.length < 1) {
            msg = "Colleges were not present";
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
            return null;
        } else {
            {
                Log.d(LOG_TAG, "Displaying " + response.length + " colleges.");
                StringBuilder sb=new StringBuilder();
                List<ModelsColleges> collegeList = Arrays.asList(response);
                Log.d(LOG_TAG,collegeList.toString());

                List<ModelsGetCollege> correctList=(List<ModelsGetCollege>)collegeList.get(0).get("collegeList");

                for(ModelsGetCollege gc: correctList){
                    sb.append(gc.getAbbreviation() + "\n");
                    CollegeList_infoActivity ci = new CollegeList_infoActivity();
                    ci.setName(gc.getName());
                    ci.setLocation(gc.getLocation());
                    ci.setCollegeId(gc.getCollegeId());
                    cList.add(ci);
                    Log.d(LOG_TAG,gc.toString());
                }
                msg=sb.toString();
                Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
            }
            return cList;
        }
    }
}
