package com.google.cloud.samples.campusconnect;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


/**
 * Created by RK on 11-09-2015.
 */
public class ProfilePageAdapterActivity extends
        RecyclerView.Adapter<ProfilePageAdapterActivity.GroupsViewHolder> {

    private List<ProfilePage_infoActivity> GroupsJoinedList;
    int posi=0;
    SharedPreferences sharedPreferences;
    ProfileInfoViewHolder holder1;
    TextView profile_name,profile_tags,tv_branch,tv_batch_of;
    GroupsJoinedListHolder holder2;
    CharSequence GroupsJoined[]={"Rotaract Club","Football Team"};
    private static int[] GroupLogo = new int[] {
            R.mipmap.roto_logo,
            R.mipmap.football_logo
    };
    private static int[] followers_count = new int[] {2,3};
    private static int[] members_count = new int[] { 2,2};


    public ProfilePageAdapterActivity(List<ProfilePage_infoActivity> GroupsJoinedList) {
        this.GroupsJoinedList = GroupsJoinedList;
    }

    @Override
    public int getItemCount() {
        return GroupsJoinedList.size();
    }

    @Override
    public void onBindViewHolder(GroupsViewHolder groupViewHolder, int i) {
        if(getItemViewType(i)==0) {
            holder1 = (ProfileInfoViewHolder) groupViewHolder;

            profile_name.setText(sharedPreferences.getString(AppConstants.PERSON_NAME,"null"));
            tv_branch.setText(sharedPreferences.getString(AppConstants.BRANCH,"null"));
            tv_batch_of.setText(sharedPreferences.getString(AppConstants.BATCH,"null"));
        }
        else {
            holder2 = (GroupsJoinedListHolder) groupViewHolder;
            holder2.group_joined.setText(GroupsJoined[i-1]);
        }
    }

    @Override
    public int getItemViewType(int position) {
        int viewType;
        if(position==0)
            viewType = 0;
        else
            viewType=1;

        return viewType;
    }

    @Override
    public GroupsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        GroupsViewHolder holder;

        if(i==0)
        {
            View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(
                    R.layout.activity_card_layout_profile_info, viewGroup, false);

            sharedPreferences = itemView.getContext().getSharedPreferences(AppConstants.SHARED_PREFS, Context.MODE_PRIVATE);
            holder = new ProfileInfoViewHolder(itemView);
        }
        else
        {
            View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(
                    R.layout.activity_card_layout_groups_joined, viewGroup, false);
            sharedPreferences = itemView.getContext().getSharedPreferences(AppConstants.SHARED_PREFS, Context.MODE_PRIVATE);
            holder = new GroupsJoinedListHolder(itemView);
        }

        return holder;
    }

    public static class GroupsViewHolder extends RecyclerView.ViewHolder {
        TextView group_joined;
        public GroupsViewHolder(View v) {
            super(v);
            group_joined = (TextView)v.findViewById(R.id.tv_group_joined_name);
        }

    }
    public class GroupsJoinedListHolder extends ProfilePageAdapterActivity.GroupsViewHolder {
        CardView card_g_joined;

        public GroupsJoinedListHolder(View itemView) {
            super(itemView);
            card_g_joined = (CardView) itemView.findViewById(R.id.cv_groups_joined);

            card_g_joined.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent_temp = new Intent(v.getContext(), GroupPageActivity.class);
                    posi=getPosition()-1;

                    Bundle bundle = new Bundle();
                    bundle.putString("G_NAME", (String) GroupsJoined[posi]);
                    bundle.putInt("G_ICON", GroupLogo[posi]);
                    bundle.putInt("F_COUNT", followers_count[posi]);
                    bundle.putInt("M_COUNT",members_count[posi]);
                    intent_temp.putExtras(bundle);

                    v.getContext().startActivity(intent_temp);

                }
            });

        }
    }
    public class ProfileInfoViewHolder extends ProfilePageAdapterActivity.GroupsViewHolder{

        public ProfileInfoViewHolder(View itemView) {
            super(itemView);
            profile_name=(TextView)itemView.findViewById(R.id.profile_name);
            profile_tags=(TextView)itemView.findViewById(R.id.profile_tag);
            tv_branch=(TextView)itemView.findViewById(R.id.tv_branch);
            tv_batch_of=(TextView)itemView.findViewById(R.id.tv_batch_of);
        }
    }

}