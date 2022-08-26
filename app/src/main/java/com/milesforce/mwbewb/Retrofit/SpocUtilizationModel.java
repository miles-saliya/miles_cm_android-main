package com.milesforce.mwbewb.Retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SpocUtilizationModel {
    @SerializedName("no_of_calls")
    @Expose
    private int no_of_calls;

    @SerializedName("total_duration")
    @Expose
    private int total_duration;

    @SerializedName("call_utilization")
    @Expose
    private String call_utilization;

    public SpocUtilizationModel(int no_of_calls, int total_duration, String call_utilization) {
        this.no_of_calls = no_of_calls;
        this.total_duration = total_duration;
        this.call_utilization = call_utilization;
    }

    public SpocUtilizationModel() {
    }

    public int getNo_of_calls() {
        return no_of_calls;
    }

    public void setNo_of_calls(int no_of_calls) {
        this.no_of_calls = no_of_calls;
    }

    public int getTotal_duration() {
        return total_duration;
    }

    public void setTotal_duration(int total_duration) {
        this.total_duration = total_duration;
    }

    public String getCall_utilization() {
        return call_utilization;
    }

    public void setCall_utilization(String call_utilization) {
        this.call_utilization = call_utilization;
    }

    @Override
    public String toString() {
        return "SpocUtilizationModel{" +
                "no_of_calls=" + no_of_calls +
                ", total_duration=" + total_duration +
                ", call_utilization='" + call_utilization + '\'' +
                '}';
    }
}
