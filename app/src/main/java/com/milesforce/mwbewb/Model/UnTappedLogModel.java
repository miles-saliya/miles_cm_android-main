package com.milesforce.mwbewb.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class UnTappedLogModel {
    @SerializedName("current_page")
    @Expose
    private int current_page;
    @SerializedName("data")
    @Expose
    private ArrayList<UntappedModel> getUntappedModel;

    @SerializedName("first_page_url")
    @Expose
    private String first_page_url;

    @SerializedName("from")
    @Expose
    private int from;
    @SerializedName("last_page")
    @Expose
    private int last_page;

    @SerializedName("last_page_url")
    @Expose
    private String last_page_url;
    @SerializedName("next_page_url")
    @Expose
    private String next_page_url;
    @SerializedName("path")
    @Expose
    private String path;
    @SerializedName("per_page")
    @Expose
    private int per_page;
    @SerializedName("prev_page_url")
    @Expose
    private String prev_page_url;
    @SerializedName("to")
    @Expose
    private int to;
    @SerializedName("total")
    @Expose
    private int total;

    public UnTappedLogModel(int current_page, ArrayList<UntappedModel> getUntappedModel, String first_page_url, int from, int last_page, String last_page_url, String next_page_url, String path, int per_page, String prev_page_url, int to, int total) {
        this.current_page = current_page;
        this.getUntappedModel = getUntappedModel;
        this.first_page_url = first_page_url;
        this.from = from;
        this.last_page = last_page;
        this.last_page_url = last_page_url;
        this.next_page_url = next_page_url;
        this.path = path;
        this.per_page = per_page;
        this.prev_page_url = prev_page_url;
        this.to = to;
        this.total = total;
    }

    public UnTappedLogModel() {
    }

    public int getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(int current_page) {
        this.current_page = current_page;
    }

    public ArrayList<UntappedModel> getGetUntappedModel() {
        return getUntappedModel;
    }

    public void setGetUntappedModel(ArrayList<UntappedModel> getUntappedModel) {
        this.getUntappedModel = getUntappedModel;
    }

    public String getFirst_page_url() {
        return first_page_url;
    }

    public void setFirst_page_url(String first_page_url) {
        this.first_page_url = first_page_url;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getLast_page() {
        return last_page;
    }

    public void setLast_page(int last_page) {
        this.last_page = last_page;
    }

    public String getLast_page_url() {
        return last_page_url;
    }

    public void setLast_page_url(String last_page_url) {
        this.last_page_url = last_page_url;
    }

    public String getNext_page_url() {
        return next_page_url;
    }

    public void setNext_page_url(String next_page_url) {
        this.next_page_url = next_page_url;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getPer_page() {
        return per_page;
    }

    public void setPer_page(int per_page) {
        this.per_page = per_page;
    }

    public String getPrev_page_url() {
        return prev_page_url;
    }

    public void setPrev_page_url(String prev_page_url) {
        this.prev_page_url = prev_page_url;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "UnTappedLogModel{" +
                "current_page=" + current_page +
                ", getUntappedModel=" + getUntappedModel +
                ", first_page_url='" + first_page_url + '\'' +
                ", from=" + from +
                ", last_page=" + last_page +
                ", last_page_url='" + last_page_url + '\'' +
                ", next_page_url='" + next_page_url + '\'' +
                ", path='" + path + '\'' +
                ", per_page=" + per_page +
                ", prev_page_url='" + prev_page_url + '\'' +
                ", to=" + to +
                ", total=" + total +
                '}';
    }
}
