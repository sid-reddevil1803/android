package com.google.cloud.samples.campusconnect;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import com.appspot.campus_connect_2015.clubs.Clubs;
import com.appspot.campus_connect_2015.clubs.ClubsRequest;
import com.appspot.campus_connect_2015.clubs.model.ModelsClubListResponse;
import com.appspot.campus_connect_2015.clubs.model.ModelsClubRequestMiniForm;
import com.appspot.campus_connect_2015.clubs.model.ModelsClubRetrievalMiniForm;
import com.appspot.campus_connect_2015.clubs.model.ModelsPostMiniForm;
import com.appspot.campus_connect_2015.clubs.model.ModelsRequestMiniForm;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.common.base.Strings;

/**
 * Created by RK on 23-09-2015.
 */
public class CreateGroupActivity extends AppCompatActivity {

    ImageView upload;
    Button createGroup;
    EditText groupAbbreviation,groupName,groupDescription;
    ModelsClubRequestMiniForm cbr;
    private int PICK_IMAGE_REQUEST = 1;
    static SharedPreferences sharedPreferences;
    private String mEmailAccount="";
    private static final String LOG_TAG="CreateGroupActivity";


    private final int GALLERY_ACTIVITY_CODE=200;
    private final int RESULT_CROP = 400;

    String encodedImageStr;
    private boolean isSignedIn() {
        if (!Strings.isNullOrEmpty(mEmailAccount)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

        createGroup =(Button)findViewById(R.id.et_createGroup);
        groupName = (EditText)findViewById(R.id.et_group_name);
        groupAbbreviation = (EditText)findViewById(R.id.et_group_abbreviation);
        groupDescription = (EditText)findViewById(R.id.et_group_description);
        upload=(ImageView)findViewById(R.id.group_icon_group);


        sharedPreferences = getSharedPreferences(AppConstants.SHARED_PREFS, Context.MODE_PRIVATE);
        mEmailAccount = sharedPreferences.getString(AppConstants.EMAIL_KEY, null);

        createGroup.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences=v.getContext().getSharedPreferences(AppConstants.SHARED_PREFS, Context.MODE_PRIVATE);
                cbr = new ModelsClubRequestMiniForm();
                cbr.setClubName(groupName.getText().toString());
                cbr.setDescription(groupDescription.getText().toString());
                cbr.setAbbreviation(groupAbbreviation.getText().toString());
                cbr.setFromPid(sharedPreferences.getString(AppConstants.PERSON_PID, null));
                cbr.setCollegeId(sharedPreferences.getString(AppConstants.COLLEGE_ID, null));
                cbr.setPicture(encodedImageStr);
                Log.e("TYPE", groupName.getText().toString());
                createGroup(cbr);
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Start Activity To Select Image From Gallery
                Intent gallery_Intent = new Intent(getApplicationContext(), GalleryUtil.class);
                startActivityForResult(gallery_Intent, GALLERY_ACTIVITY_CODE);
                //break;
            }
        });
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_ACTIVITY_CODE) {
            if(resultCode == Activity.RESULT_OK){
                String picturePath = data.getStringExtra("picturePath");
                //perform Crop on the Image Selected from Gallery
                performCrop(picturePath);
            }
        }

        if (requestCode == RESULT_CROP ) {
            if(resultCode == Activity.RESULT_OK){
                Bundle extras = data.getExtras();
                Bitmap selectedBitmap = extras.getParcelable("data");
                // Set The Bitmap Data To ImageView
                upload.setImageBitmap(selectedBitmap);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                selectedBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                //Bitmap decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(out.toByteArray()));
                byte[] b = baos.toByteArray();
                encodedImageStr = Base64.encodeToString(b, Base64.DEFAULT);
                upload.setScaleType(ImageView.ScaleType.CENTER);
            }
        }
    }

    public void createGroup(final ModelsClubRequestMiniForm modelsClubRequestMiniForm){
        if (!isSignedIn()) {
            Toast.makeText(CreateGroupActivity.this, "You must sign in for this action.", Toast.LENGTH_LONG).show();
            return;
        }

        AsyncTask<Void, Void, Void> createGroup =
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... unused) {
                        if (!isSignedIn()) {
                            return null;
                        }
                        ;

                        if (!AppConstants.checkGooglePlayServicesAvailable(CreateGroupActivity.this)) {
                            return null;
                        }

                        // Create a Google credential since this is an authenticated request to the API.
                        GoogleAccountCredential credential = GoogleAccountCredential.usingAudience(
                                CreateGroupActivity.this, AppConstants.AUDIENCE);
                        credential.setSelectedAccountName(mEmailAccount);

                        // Retrieve service handle using credential since this is an authenticated call.
                        Clubs apiServiceHandle = AppConstants.getApiServiceHandle(credential);

                        try {
                            //ModelsRequestMiniForm modelsRequestMiniForm=new ModelsRequestMiniForm();
                            //modelsClubRequestMiniForm.setAbbreviation(modelsClubRequestMiniForm.getAbbreviation());
                            //modelsClubRequestMiniForm.setFromPid(modelsClubRequestMiniForm.getFromPid());
                            //modelsClubRequestMiniForm.setDescription(modelsClubRequestMiniForm.getDescription());
                            //modelsClubRequestMiniForm.setClubName(modelsClubRequestMiniForm.getClubName());
                            //modelsClubRequestMiniForm.setCollegeId(modelsClubRequestMiniForm.getCollegeId());

                            Clubs.CreateClubRequest res = apiServiceHandle.createClubRequest(modelsClubRequestMiniForm);


                            Void createGroupRes = res.execute();
                            Log.e(LOG_TAG, "SUCCESS");
                            //Log.e(LOG_TAG, createGroupRes.toPrettyString());
                            return createGroupRes;
                        } catch (IOException e) {
                            Log.e(LOG_TAG, "Exception during API call", e);
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void cList) {
                        if (cList != null) {
                        } else {
                            //Log.e(LOG_TAG, "No clubs were returned by the API.");
                        }
                    }
                };

        createGroup.execute((Void) null);
    }

    private void performCrop(String picUri) {
        try {
            //Start Crop Activity

            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            // indicate image type and Uri
            File f = new File(picUri);
            Uri contentUri = Uri.fromFile(f);

            cropIntent.setDataAndType(contentUri, "image/*");
            // set crop properties
            cropIntent.putExtra("crop", "true");
            // indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            // indicate output X and Y
            cropIntent.putExtra("outputX", 280);
            cropIntent.putExtra("outputY", 280);

            // retrieve data on return
            cropIntent.putExtra("return-data", true);
            // start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent, RESULT_CROP);
        }
        // respond to users whose devices do not support the crop action
        catch (ActivityNotFoundException anfe) {
            // display an error message
            String errorMessage = "your device doesn't support the crop action!";
            Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

}
