package com.milesforce.mwbewb.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class UntrackedMainModel {
    @SerializedName("dashboard_category")
    @Expose
    private String dashboard_category;
    @SerializedName("dashboard_data")
    @Expose
    private ArrayList<CallLogs> dashboard_data;
    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("message")
    @Expose
    private String message;

    public UntrackedMainModel(String dashboard_category, ArrayList<CallLogs> dashboard_data, int status, String message) {
        this.dashboard_category = dashboard_category;
        this.dashboard_data = dashboard_data;
        this.status = status;
        this.message = message;
    }

    public UntrackedMainModel() {
    }

    public String getDashboard_category() {
        return dashboard_category;
    }

    public void setDashboard_category(String dashboard_category) {
        this.dashboard_category = dashboard_category;
    }

    public ArrayList<CallLogs> getDashboard_data() {
        return dashboard_data;
    }

    public void setDashboard_data(ArrayList<CallLogs> dashboard_data) {
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
        return "UntrackedMainModel{" +
                "dashboard_category='" + dashboard_category + '\'' +
                ", dashboard_data=" + dashboard_data +
                ", status=" + status +
                ", message='" + message + '\'' +
                '}';
    }
}
