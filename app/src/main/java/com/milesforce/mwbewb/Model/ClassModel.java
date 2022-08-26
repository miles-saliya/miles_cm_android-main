package com.milesforce.mwbewb.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

import retrofit2.http.POST;

public class ClassModel {
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("data")
    @Expose
    private ArrayList<ClassData> data;

    public ClassModel() {
    }

    public ClassModel(String status, ArrayList<ClassData> data) {
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<ClassData> getData() {
        return data;
    }

    public void setData(ArrayList<ClassData> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ClassModel{" +
                "status='" + status + '\'' +
                ", data=" + data +
                '}';
    }
}
