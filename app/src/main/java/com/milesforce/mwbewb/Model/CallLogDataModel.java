package com.milesforce.mwbewb.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CallLogDataModel {
    @SerializedName("dashboard_category")
    @Expose
    private String dashboard_category;
    @SerializedName("dashboard_data")
    @Expose
    private ArrayList<DelaysModel> dashboard_data;

    public CallLogDataModel(String dashboard_category, ArrayList<DelaysModel> dashboard_data) {
        this.dashboard_category = dashboard_category;
        this.dashboard_data = dashboard_data;
    }

    public CallLogDataModel() {
    }

    public String getDashboard_category() {
        return dashboard_category;
    }

    public void setDashboard_category(String dashboard_category) {
        this.dashboard_category = dashboard_category;
    }

    public ArrayList<DelaysModel> getDashboard_data() {
        return dashboard_data;
    }

    public void setDashboard_data(ArrayList<DelaysModel> dashboard_data) {
        this.dashboard_data = dashboard_data;
    }

    @Override
    public String toString() {
        return "CallLogDataModel{" +
                "dashboard_category='" + dashboard_category + '\'' +
                ", dashboard_data=" + dashboard_data +
                '}';
    }
}
