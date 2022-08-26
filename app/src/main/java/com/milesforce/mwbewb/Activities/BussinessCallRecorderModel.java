package com.milesforce.mwbewb.Activities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BussinessCallRecorderModel {
    @SerializedName("person_name")
    @Expose
    private String person_name;
    @SerializedName("company_name")
    @Expose
    private String company_name;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("filename")
    @Expose
    private String filename;

    @SerializedName("contact_type")
    @Expose
    private String contact_type;
    @SerializedName("created_at")
    @Expose
    private String created_at;
    @SerializedName("call_duration")
    @Expose
    private int call_duration;

    private String start_time;
    private String end_time;
    private String file_name;

    public BussinessCallRecorderModel(String person_name, String company_name, String time, String filename, String contact_type, String created_at, int call_duration, String start_time, String end_time, String file_name) {
        this.person_name = person_name;
        this.company_name = company_name;
        this.time = time;
        this.filename = filename;
        this.contact_type = contact_type;
        this.created_at = created_at;
        this.call_duration = call_duration;
        this.start_time = start_time;
        this.end_time = end_time;
        this.file_name = file_name;
    }

    public BussinessCallRecorderModel() {
    }

    public String getPerson_name() {
        return person_name;
    }

    public void setPerson_name(String person_name) {
        this.person_name = person_name;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getContact_type() {
        return contact_type;
    }

    public void setContact_type(String contact_type) {
        this.contact_type = contact_type;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public int getCall_duration() {
        return call_duration;
    }

    public void setCall_duration(int call_duration) {
        this.call_duration = call_duration;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    @Override
    public String toString() {
        return "BussinessCallRecorderModel{" +
                "person_name='" + person_name + '\'' +
                ", company_name='" + company_name + '\'' +
                ", time='" + time + '\'' +
                ", filename='" + filename + '\'' +
                ", contact_type='" + contact_type + '\'' +
                ", created_at='" + created_at + '\'' +
                ", call_duration=" + call_duration +
                ", start_time='" + start_time + '\'' +
                ", end_time='" + end_time + '\'' +
                ", file_name='" + file_name + '\'' +
                '}';
    }
}
