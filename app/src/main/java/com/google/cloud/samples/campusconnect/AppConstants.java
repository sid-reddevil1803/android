package com.google.cloud.samples.campusconnect;

/**
 * Created by Rishab on 08-10-2015.
 */

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;

import com.appspot.campus_connect_2015.clubs.Clubs;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;

import javax.annotation.Nullable;


public class AppConstants {

    public static final String WEB_CLIENT_ID = "245400873255-u13ijemvsqbej9quq5g2i2q3caif3rp2.apps.googleusercontent.com";

    public static final String AUDIENCE = "server:client_id:" + WEB_CLIENT_ID;

    public static final String SHARED_PREFS = "MyPrefs";

    public static final String EMAIL_KEY = "emailKey";

    public static final String COLLEGE_NAME = "collegeName";

    public static final String COLLEGE_LOCATION = "collegeLocation";

    public static final String COLLEGE_ID="collegeId";

    public static final String PERSON_NAME = "personName";

    public static final String PROFILE_CATEGORY = "category";

    public static final String PHONE = "phone";

    public static final String ALUMNI = "alumni";

    public static final String STUDENT = "student";

    public static final String BATCH = "batch";

    public static final String BRANCH = "branch";

    public static final String PERSON_PID = "person_pid";

    public static final String FOLLOW_CLUB_COUNT = "follow_club_count";

    public static final String MEMBER_CLUB_COUNT = "member_club_count";

    public static final JsonFactory JSON_FACTORY = new AndroidJsonFactory();

    public static final HttpTransport HTTP_TRANSPORT = AndroidHttp.newCompatibleTransport();

    public static int countGoogleAccounts(Context context) {
        AccountManager am = AccountManager.get(context);
        Account[] accounts = am.getAccountsByType(GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE);
        if (accounts == null || accounts.length < 1) {
            return 0;
        } else {
            return accounts.length;
        }
    }

    public static Clubs getApiServiceHandle(@Nullable GoogleAccountCredential credential) {
        // Use a builder to help formulate the API request.
        Clubs.Builder helloWorld = new Clubs.Builder(AppConstants.HTTP_TRANSPORT,
                AppConstants.JSON_FACTORY, credential);
        return helloWorld.build();
    }

    public static boolean checkGooglePlayServicesAvailable(Activity activity) {
        final int connectionStatusCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(activity);
        if (GooglePlayServicesUtil.isUserRecoverableError(connectionStatusCode)) {
            showGooglePlayServicesAvailabilityErrorDialog(activity, connectionStatusCode);
            return false;
        }
        return true;
    }

    public static void showGooglePlayServicesAvailabilityErrorDialog(final Activity activity,
                                                                     final int connectionStatusCode) {
        final int REQUEST_GOOGLE_PLAY_SERVICES = 0;
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Dialog dialog = GooglePlayServicesUtil.getErrorDialog(
                        connectionStatusCode, activity, REQUEST_GOOGLE_PLAY_SERVICES);
                dialog.show();
            }
        });
    }
}