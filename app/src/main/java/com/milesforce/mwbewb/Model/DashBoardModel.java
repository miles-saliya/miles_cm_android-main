package com.milesforce.mwbewb.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DashBoardModel {
    @SerializedName("call_data")
    @Expose
    private CallData call_data;
    @SerializedName("mwb")
    @Expose
    private DelaysModel mwb;

    public DashBoardModel() {
    }

    public DashBoardModel(CallData call_data, DelaysModel mwb) {
        this.call_data = call_data;
        this.mwb = mwb;
    }

    public CallData getCall_data() {
        return call_data;
    }

    public void setCall_data(CallData call_data) {
        this.call_data = call_data;
    }

    public DelaysModel getMwb() {
        return mwb;
    }

    public void setMwb(DelaysModel mwb) {
        this.mwb = mwb;
    }

    @Override
    public String toString() {
        return "DashBoardModel{" +
                "call_data=" + call_data +
                ", mwb=" + mwb +
                '}';
    }
}
