package com.milesforce.mwbewb.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MissedCallLogModel {
    @SerializedName("dashboard_category")
    @Expose
    private String dashboard_category;

    @SerializedName("dashboard_data")
    @Expose
    private ArrayList<DashBoardModel> dashboard_data;

    public MissedCallLogModel(String dashboard_category, ArrayList<DashBoardModel> dashboard_data) {
        this.dashboard_category = dashboard_category;
        this.dashboard_data = dashboard_data;
    }

    public MissedCallLogModel() {
    }

    public String getDashboard_category() {
        return dashboard_category;
    }

    public void setDashboard_category(String dashboard_category) {
        this.dashboard_category = dashboard_category;
    }

    public ArrayList<DashBoardModel> getDashboard_data() {
        return dashboard_data;
    }

    public void setDashboard_data(ArrayList<DashBoardModel> dashboard_data) {
        this.dashboard_data = dashboard_data;
    }

    @Override
    public String toString() {
        return "MissedCallLogModel{" +
                "dashboard_category='" + dashboard_category + '\'' +
                ", dashboard_data=" + dashboard_data +
                '}';
    }
}
