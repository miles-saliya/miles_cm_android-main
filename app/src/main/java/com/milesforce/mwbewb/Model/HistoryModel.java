package com.milesforce.mwbewb.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HistoryModel {
    @SerializedName("added_by_name")
    @Expose
    private String added_by_name;

    @SerializedName("details")
    @Expose
    private String details;

    @SerializedName("last_call")
    @Expose
    private String last_call;
    @SerializedName("created_at")
    @Expose
    private String created_at;

    @SerializedName("id")
    @Expose
    private int mwb_id;

    public HistoryModel(String added_by_name, String details, String last_call, String created_at,
    int mwb_id
    ) {
        this.added_by_name = added_by_name;
        this.details = details;
        this.last_call = last_call;
        this.created_at = created_at;
        this.mwb_id=mwb_id;
    }

    public HistoryModel() {
    }

    public String getAdded_by_name() {
        return added_by_name;
    }

    public void setAdded_by_name(String added_by_name) {
        this.added_by_name = added_by_name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getLast_call() {
        return last_call;
    }

    public void setLast_call(String last_call) {
        this.last_call = last_call;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public int getMWB_ID(){
        return  mwb_id;
    }
    public void  setMwb_id(int mwb_id){
        this.mwb_id=mwb_id;
    }

    @Override
    public String toString() {
        return "HistoryModel{" +
                "added_by_name='" + added_by_name + '\'' +
                ", details='" + details + '\'' +
                ", last_call='" + last_call + '\'' +
                ", created_at='" + created_at + '\'' +
                ", id='" + mwb_id + '\'' +
                '}';
    }
}
