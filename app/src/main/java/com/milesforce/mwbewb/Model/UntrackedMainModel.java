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
    private DashboardData dashboard_data;

    @SerializedName("status")
    @Expose
    private int status;

    @SerializedName("message")
    @Expose
    private String message;

    public UntrackedMainModel(String dashboard_category, DashboardData dashboard_data, int status, String message) {
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
        return dashboard_data.getData();
    }

    public void setDashboard_data(DashboardData dashboard_data) {
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

class DashboardData {
    @SerializedName("current_page")
    @Expose
    private int current_page;

    @SerializedName("data")
    @Expose
    private ArrayList<CallLogs> data;

    // Add other fields as needed

    public int getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(int current_page) {
        this.current_page = current_page;
    }

    public ArrayList<CallLogs> getData() {
        return data;
    }

    public void setData(ArrayList<CallLogs> data) {
        this.data = data;
    }

    // Add getters and setters for other fields as needed
}