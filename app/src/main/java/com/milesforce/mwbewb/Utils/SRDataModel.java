package com.milesforce.mwbewb.Utils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SRDataModel implements Serializable {
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("data")
    @Expose
    private SRData data;


    public SRDataModel(String status, SRData data) {
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public SRData getData() {
        return data;
    }

    public void setData(SRData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "SRDataModel{" +
                "status='" + status + '\'' +
                ", data=" + data +
                '}';
    }
}
