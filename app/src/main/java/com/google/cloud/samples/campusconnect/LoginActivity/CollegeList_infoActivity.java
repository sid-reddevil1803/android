package com.google.cloud.samples.campusconnect.LoginActivity;

/**
 * Created by RK on 26-09-2015.
 */
public class CollegeList_infoActivity {
    private String name;

    private String collegeId;

    private String location;

    public String getLocation() {return location;}

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name=name;
    }

    public String getCollegeId(){return collegeId;}

    public void setCollegeId(String collegeId){
        this.collegeId=collegeId;
    }
}
