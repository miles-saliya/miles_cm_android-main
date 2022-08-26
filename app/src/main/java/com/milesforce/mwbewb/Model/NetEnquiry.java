package com.milesforce.mwbewb.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class NetEnquiry {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("data")
    @Expose
    private ArrayList<NetEnquiryData> netEnquiryData;
    @SerializedName("path")
    @Expose
    private String path;
    @SerializedName("total")
    @Expose
    private int total;

    public NetEnquiry(int id, ArrayList<NetEnquiryData> netEnquiryData, String path, int total) {
        this.id = id;
        this.netEnquiryData = netEnquiryData;
        this.path = path;
        this.total = total;
    }

    public NetEnquiry() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<NetEnquiryData> getNetEnquiryData() {
        return netEnquiryData;
    }

    public void setNetEnquiryData(ArrayList<NetEnquiryData> netEnquiryData) {
        this.netEnquiryData = netEnquiryData;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "NetEnquiry{" +
                "id=" + id +
                ", netEnquiryData=" + netEnquiryData +
                ", path='" + path + '\'' +
                ", total=" + total +
                '}';
    }
}
