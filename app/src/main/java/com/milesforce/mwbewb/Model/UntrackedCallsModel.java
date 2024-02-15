package com.milesforce.mwbewb.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UntrackedCallsModel {

    @SerializedName("dashboard_category")
    @Expose
    private String dashboardCategory;
    @SerializedName("dashboard_data")
    @Expose
    private List<DashboardDatum> dashboardData;

    /**
     * No args constructor for use in serialization
     */
    public UntrackedCallsModel() {
    }

    /**
     * @param dashboardData
     * @param dashboardCategory
     */
    public UntrackedCallsModel(String dashboardCategory, List<DashboardDatum> dashboardData) {
        super();
        this.dashboardCategory = dashboardCategory;
        this.dashboardData = dashboardData;
    }

    public String getDashboardCategory() {
        return dashboardCategory;
    }

    public void setDashboardCategory(String dashboardCategory) {
        this.dashboardCategory = dashboardCategory;
    }

    public List<DashboardDatum> getDashboardData() {
        return dashboardData;
    }

    public void setDashboardData(List<DashboardDatum> dashboardData) {
        this.dashboardData = dashboardData;
    }

}

