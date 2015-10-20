package com.google.cloud.samples.campusconnect;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by RK on 07-10-2015.
 */
public class FragmentPostNews extends Fragment {
    RelativeLayout group_name_post;
    TextView group_selected_text_post;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_post_news, container, false);

        group_name_post = (RelativeLayout) v.findViewById(R.id.group_select_when_posting);
        group_selected_text_post = (TextView) v.findViewById(R.id.tv_group_name_selected_when_posting);

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

        return v;
    }
}
