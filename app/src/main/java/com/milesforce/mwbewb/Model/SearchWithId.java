package com.milesforce.mwbewb.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class SearchWithId implements Serializable {
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("mwb")
    @Expose
    private DelaysModel delaysModel;
    @SerializedName("message")
    @Expose
    private String message;

    public SearchWithId(String status, DelaysModel delaysModel, String message) {
        this.status = status;
        this.delaysModel = delaysModel;
        this.message = message;
    }

    public SearchWithId() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DelaysModel getDelaysModel() {
        return delaysModel;
    }

    public void setDelaysModel(DelaysModel delaysModel) {
        this.delaysModel = delaysModel;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "SearchWithId{" +
                "status='" + status + '\'' +
                ", delaysModel=" + delaysModel +
                ", message='" + message + '\'' +
                '}';
    }
}
