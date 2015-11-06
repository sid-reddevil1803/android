package com.google.cloud.samples.campusconnect;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by RK on 23-09-2015. 
 */
public class AdminPageActivity extends AppCompatActivity {

    ImageButton close;
    Button request,members;
    RelativeLayout admin_group;
    TextView admin_group_selected_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);

        request = (Button) findViewById(R.id.b_request);
        members = (Button) findViewById(R.id.b_members);
        close = (ImageButton) findViewById(R.id.ib_cancel);
        admin_group = (RelativeLayout) findViewById(R.id.group_select);
        admin_group_selected_text = (TextView) findViewById(R.id.tv_group_name_selected);

        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent_temp = new Intent(v.getContext(),RequestsPage_InAdminActivity.class);
                startActivity(intent_temp);

            }
        });

        members.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent_temp = new Intent(v.getContext(),GroupMembersPage_InAdminActivity.class);
                startActivity(intent_temp);

            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });

        admin_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final CharSequence[] items = {
                        "Football Team","Rotaract Club"
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(AdminPageActivity.this);
                builder.setTitle("Group:");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        // Do something with the selection
                        admin_group_selected_text.setText(items[item]);
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();


            }
        });

    }
}
