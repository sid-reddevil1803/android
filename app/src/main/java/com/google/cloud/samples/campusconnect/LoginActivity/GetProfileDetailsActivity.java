package com.google.cloud.samples.campusconnect.LoginActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.appspot.campus_connect_2015.clubs.Clubs;
import com.appspot.campus_connect_2015.clubs.model.ModelsProfileMiniForm;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.cloud.samples.campusconnect.AppConstants;
import com.google.cloud.samples.campusconnect.MainActivity;
import com.google.cloud.samples.campusconnect.R;
import com.google.common.base.Strings;

import java.io.IOException;


/**
 * Created by RK on 26-09-2015.
 */
public class GetProfileDetailsActivity extends AppCompatActivity implements View.OnClickListener{

    Button cont;
    TextView skip;
    EditText batch,branch,phone;

    static ModelsProfileMiniForm pmf;
    private  static final  String LOG_TAG="GetProfileDetails";

    private String mEmailAccount = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_profile_details);

        batch = (EditText) findViewById(R.id.et_batch);
        branch = (EditText) findViewById(R.id.et_branch);
        skip = (TextView) findViewById(R.id.tv_skip);
        cont = (Button)findViewById(R.id.b_continue);
        phone = (EditText)findViewById(R.id.et_phone);
        SharedPreferences sharedpreferences = getSharedPreferences(AppConstants.SHARED_PREFS, Context.MODE_PRIVATE);
        mEmailAccount=sharedpreferences.getString(AppConstants.EMAIL_KEY,null);
        cont.setOnClickListener(this);
        skip.setOnClickListener(this);
    }


    private void createProfile(View v){
        Log.d(LOG_TAG,"entered cp");
        pmf=new ModelsProfileMiniForm();
        SharedPreferences
                sharedPreferences=v.getContext().getSharedPreferences(AppConstants.SHARED_PREFS, Context.MODE_PRIVATE);
        pmf.setName(sharedPreferences.getString(AppConstants.PERSON_NAME, null));
        pmf.setEmail(sharedPreferences.getString(AppConstants.EMAIL_KEY, null));
        if(sharedPreferences.getString(AppConstants.PROFILE_CATEGORY,null).equals(AppConstants.ALUMNI)){
            pmf.setIsAlumni("Y");
        }else{
            pmf.setIsAlumni("N");
        }
        String phoneNo=phone.getText().toString();
        if(phoneNo==null){
            phoneNo="";
        }
        String str_batch=batch.getText().toString();
        if(str_batch==null){
            str_batch="";
        }
        String str_branch=branch.getText().toString();
        if(str_branch==null){
            str_branch="";
        }
        pmf.setPhone(phoneNo);
        pmf.setBatch(str_batch);
        pmf.setBranch(str_branch);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(AppConstants.BATCH, pmf.getBatch());
        edit.putString(AppConstants.BRANCH, pmf.getBranch());
        Log.e("Follows", String.valueOf(pmf.getFollows()));
        //edit.putString(AppConstants.BRANCH, pmf.getFollows();
        edit.commit();
        pmf.setCollegeId(sharedPreferences.getString(AppConstants.COLLEGE_ID,null));
        saveProfile();
    }


    public void saveProfile() {
        Log.d(LOG_TAG,"starting save profile");
        if (!isSignedIn()) {
            Toast.makeText(this, "You must sign in for this action.", Toast.LENGTH_LONG).show();
            return;
        }

        AsyncTask<Void, Void, ModelsProfileMiniForm> saveProfile =
                new AsyncTask<Void, Void, ModelsProfileMiniForm>() {
                    @Override
                    protected ModelsProfileMiniForm doInBackground(Void... unused) {
                        if (!isSignedIn()) {
                            return null;
                        };

                        if (!AppConstants.checkGooglePlayServicesAvailable(GetProfileDetailsActivity.this)) {
                            return null;
                        }

                        // Create a Google credential since this is an authenticated request to the API.
                        GoogleAccountCredential credential = GoogleAccountCredential.usingAudience(
                                GetProfileDetailsActivity.this, AppConstants.AUDIENCE);
                        credential.setSelectedAccountName(mEmailAccount);

                        // Retrieve service handle using credential since this is an authenticated call.
                        Clubs apiServiceHandle = AppConstants.getApiServiceHandle(credential);

                        try {
                            Clubs.SaveProfile sp = apiServiceHandle.saveProfile(pmf);

                            ModelsProfileMiniForm mpf=sp.execute();
                            Log.e(LOG_TAG, "SUCCESS");
                            Log.e(LOG_TAG, mpf.toPrettyString());
                            return mpf;
                        } catch (IOException e) {
                            Log.e(LOG_TAG, "Exception during API call", e);
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(ModelsProfileMiniForm prof) {
                        if (prof!=null) {
                            Log.d(LOG_TAG,"saved successfully");
                        } else {
                            Log.e(LOG_TAG, "couldnt save");
                        }
                    }
                };

        saveProfile.execute((Void) null);
    }

    private boolean isSignedIn() {
        if (!Strings.isNullOrEmpty(mEmailAccount)) {
            return true;
        } else {
            return false;
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.b_continue:
                createProfile(v);
                Intent intent_temp = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent_temp);
                break;
            case R.id.tv_skip:
                createProfile(v);
                Intent intent_temp1 = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent_temp1);
                break;
        }

    }
}