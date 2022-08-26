package com.milesforce.mwbewb.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class MobileSearchModel implements Serializable {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("code")
    @Expose
    private int code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("mwb")
    @Expose
    private List<DelaysModel> delaysModel;

    public MobileSearchModel(String status, int code, String message, List<DelaysModel> delaysModel) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.delaysModel = delaysModel;
    }

    public MobileSearchModel() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DelaysModel> getDelaysModel() {
        return delaysModel;
    }

    public void setDelaysModel(List<DelaysModel> delaysModel) {
        this.delaysModel = delaysModel;
    }

    @Override
    public String toString() {
        return "MobileSearchModel{" +
                "status='" + status + '\'' +
                ", code=" + code +
                ", message='" + message + '\'' +
                ", delaysModel=" + delaysModel +
                '}';
    }

    /*"status": "success",
    "code": 200,
    "message": "Matching mwbs found",
    "mwb"*/
}
