package com.google.cloud.samples.campusconnect;

/**
 * Created by RK on 17-09-2015.
 */
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp1 on 21-01-2015.
 */
public class FragmentGroups extends Fragment {

    RecyclerView group_list;
    Button create_group;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_groups,container,false);

        group_list = (RecyclerView) v.findViewById(R.id.rv_group_list);
        create_group = (Button) v.findViewById(R.id.b_create_group);

        //group_list.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(v.getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        group_list.setLayoutManager(llm);
        group_list.setItemAnimator(new DefaultItemAnimator());
        GroupListAdapterActivity gl = new GroupListAdapterActivity(
                createList_gl(4));
        //GroupListAdapterActivity gl = new  GroupListAdapterActivity(v.getContext());
        group_list.setAdapter(gl);

        create_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent_temp = new Intent(v.getContext(), CreateGroupActivity.class);
                startActivity(intent_temp);

            }
        });

        return v;
    }
    private List<GroupList_infoActivity> createList_gl(int size) {
        List<GroupList_infoActivity> result = new ArrayList<GroupList_infoActivity>();
        for (int i = 1; i <= size; i++) {
            GroupList_infoActivity ci = new GroupList_infoActivity();
            result.add(ci);
        }
        return result;
    }

}
