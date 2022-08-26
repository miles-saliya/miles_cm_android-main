package com.milesforce.mwbewb.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MissedCallModel {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("mobile_number")
    @Expose
    private String mobile_number;
    @SerializedName("directory")
    @Expose
    private String directory;
    @SerializedName("call_duration")
    @Expose
    private int call_duration;
    @SerializedName("time")
    @Expose
    private String time;

    @SerializedName("contact_type")
    @Expose
    private String contact_type;

    @SerializedName("updated")
    @Expose
    private int updated;
    @SerializedName("created_at")
    @Expose
    private String created_at;
    @SerializedName("updated_at")
    @Expose
    private String updated_at;

    @SerializedName("user_id")
    @Expose
    private int user_id;

    @SerializedName("company_name")
    @Expose
    private String company_name;

    @SerializedName("person_name")
    @Expose
    private String person_name;

    @SerializedName("locations")
    @Expose
    private String locations;

    @SerializedName("job_titles")
    @Expose
    private String job_titles;

    @SerializedName("person_id")
    @Expose
    private int person_id;

    public MissedCallModel() {
    }

    public MissedCallModel(int id, String mobile_number, String directory, int call_duration, String time, String contact_type, int updated, String created_at, String updated_at, int user_id, String company_name, String person_name, String locations, String job_titles, int person_id) {
        this.id = id;
        this.mobile_number = mobile_number;
        this.directory = directory;
        this.call_duration = call_duration;
        this.time = time;
        this.contact_type = contact_type;
        this.updated = updated;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.user_id = user_id;
        this.company_name = company_name;
        this.person_name = person_name;
        this.locations = locations;
        this.job_titles = job_titles;
        this.person_id = person_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public int getCall_duration() {
        return call_duration;
    }

    public void setCall_duration(int call_duration) {
        this.call_duration = call_duration;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContact_type() {
        return contact_type;
    }

    public void setContact_type(String contact_type) {
        this.contact_type = contact_type;
    }

    public int getUpdated() {
        return updated;
    }

    public void setUpdated(int updated) {
        this.updated = updated;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getPerson_name() {
        return person_name;
    }

    public void setPerson_name(String person_name) {
        this.person_name = person_name;
    }

    public String getLocations() {
        return locations;
    }

    public void setLocations(String locations) {
        this.locations = locations;
    }

    public String getJob_titles() {
        return job_titles;
    }

    public void setJob_titles(String job_titles) {
        this.job_titles = job_titles;
    }

    public int getPerson_id() {
        return person_id;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }
}
