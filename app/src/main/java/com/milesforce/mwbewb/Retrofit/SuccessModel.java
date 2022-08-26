package com.milesforce.mwbewb.Retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.milesforce.mwbewb.Model.CmaData;
import com.milesforce.mwbewb.Model.CpaData;

public class SuccessModel {
    @SerializedName("result")
    @Expose
    private String result;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("exists")
    @Expose
    private String exists;

    @SerializedName("cpa")
    @Expose
    private CpaData cpa;

    @SerializedName("cma")
    @Expose
    private CmaData cma;

    @SerializedName("city")
    @Expose
    private String city;

    public SuccessModel(String result, String status, String url, String message, String exists, CpaData cpa, CmaData cma, String city) {
        this.result = result;
        this.status = status;
        this.url = url;
        this.message = message;
        this.exists = exists;
        this.cpa = cpa;
        this.cma = cma;
        this.city = city;
    }

    public SuccessModel() {
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getExists() {
        return exists;
    }

    public void setExists(String exists) {
        this.exists = exists;
    }

    public CpaData getCpa() {
        return cpa;
    }

    public void setCpa(CpaData cpa) {
        this.cpa = cpa;
    }

    public CmaData getCma() {
        return cma;
    }

    public void setCma(CmaData cma) {
        this.cma = cma;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "SuccessModel{" +
                "result='" + result + '\'' +
                ", status='" + status + '\'' +
                ", url='" + url + '\'' +
                ", message='" + message + '\'' +
                ", exists='" + exists + '\'' +
                ", cpa=" + cpa +
                ", cma=" + cma +
                ", city='" + city + '\'' +
                '}';
    }
}
