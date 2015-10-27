package com.google.cloud.samples.campusconnect;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.IOException;
import com.appspot.campus_connect_2015.clubs.Clubs;
import com.appspot.campus_connect_2015.clubs.ClubsRequest;
import com.appspot.campus_connect_2015.clubs.model.ModelsClubRequestMiniForm;
import com.appspot.campus_connect_2015.clubs.model.ModelsPostMiniForm;

/**
 * Created by RK on 23-09-2015.
 */
public class CreateGroupActivity extends AppCompatActivity {

    ImageView upload;
    Button createGroup;
    EditText groupAbbreviation,groupName,groupDescription;
    ModelsClubRequestMiniForm cbr;
    private int PICK_IMAGE_REQUEST = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);
        createGroup =(Button)findViewById(R.id.et_createGroup);
        groupName = (EditText)findViewById(R.id.et_group_name);
        groupAbbreviation = (EditText)findViewById(R.id.et_group_abbreviation);
        groupDescription = (EditText)findViewById(R.id.et_group_description);
        upload=(ImageView)findViewById(R.id.group_icon_group);

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
                Log.e("TYPE",groupName.getText().toString());
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
// Show only images, no videos or anything else
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
// Always show the chooser (if there are multiple options available)
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);


            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));
                upload.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
