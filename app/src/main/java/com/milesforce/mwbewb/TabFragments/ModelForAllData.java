package com.milesforce.mwbewb.TabFragments;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.milesforce.mwbewb.Model.WorkLogModel;

import java.io.Serializable;

public class ModelForAllData implements Serializable {

    @SerializedName("dashboard_category")
    @Expose
    private String dashboard_category;

    @SerializedName("dashboard_data")
    @Expose
    private WorkLogModel dashboard_data;

    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("message")
    @Expose
    private String message;

    public ModelForAllData(String dashboard_category, WorkLogModel dashboard_data, int status, String message) {
        this.dashboard_category = dashboard_category;
        this.dashboard_data = dashboard_data;
        this.status = status;
        this.message = message;
    }

    public ModelForAllData() {
    }

    public String getDashboard_category() {
        return dashboard_category;
    }

    public void setDashboard_category(String dashboard_category) {
        this.dashboard_category = dashboard_category;
    }

    public WorkLogModel getDashboard_data() {
        return dashboard_data;
    }

    public void setDashboard_data(WorkLogModel dashboard_data) {
        this.dashboard_data = dashboard_data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ModelForAllData{" +
                "dashboard_category='" + dashboard_category + '\'' +
                ", dashboard_data=" + dashboard_data +
                ", status=" + status +
                ", message='" + message + '\'' +
                '}';
    }
}
