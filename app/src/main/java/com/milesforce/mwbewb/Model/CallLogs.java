package com.milesforce.mwbewb.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CallLogs implements Serializable {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("phone_number")
    @Expose
    private String phone_number;

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
    private String updated;

    @SerializedName("tallied")
    @Expose
    private int tallied;
    @SerializedName("person_id")
    @Expose
    private int person_id;
    @SerializedName("person_name")
    @Expose
    private String person_name;

    @SerializedName("created_at")
    @Expose
    private String created_at;

    @SerializedName("updated_at")
    @Expose
    private String updated_at;

    @SerializedName("company")
    @Expose
    private String company;

    @SerializedName("designation")
    @Expose
    private String designation;

    public CallLogs(int id, String phone_number, String directory, int call_duration, String time, String contact_type, String updated, int tallied, int person_id, String person_name, String created_at, String updated_at, String company, String designation) {
        this.id = id;
        this.phone_number = phone_number;
        this.directory = directory;
        this.call_duration = call_duration;
        this.time = time;
        this.contact_type = contact_type;
        this.updated = updated;
        this.tallied = tallied;
        this.person_id = person_id;
        this.person_name = person_name;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.company = company;
        this.designation = designation;
    }

    public CallLogs() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
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

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public int getTallied() {
        return tallied;
    }

    public void setTallied(int tallied) {
        this.tallied = tallied;
    }

    public int getPerson_id() {
        return person_id;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }

    public String getPerson_name() {
        return person_name;
    }

    public void setPerson_name(String person_name) {
        this.person_name = person_name;
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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    @Override
    public String toString() {
        return "CallLogs{" +
                "id=" + id +
                ", phone_number='" + phone_number + '\'' +
                ", directory='" + directory + '\'' +
                ", call_duration=" + call_duration +
                ", time='" + time + '\'' +
                ", contact_type='" + contact_type + '\'' +
                ", updated='" + updated + '\'' +
                ", tallied=" + tallied +
                ", person_id=" + person_id +
                ", person_name='" + person_name + '\'' +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                ", company='" + company + '\'' +
                ", designation='" + designation + '\'' +
                '}';
    }
    /*"id": 313,
            "phone_number": "+918712136577",
            "directory": "REJECTED",
            "call_duration": 0,
            "time": "1561198102000",
            "contact_type": "B2C",
            "updated": 0,
            "tallied": 0,
            "user_id": 105,
            "person_id": 15328,
            "person_name": "Dumbu",
            "created_at": "2019-06-22 15:38:47",
            "updated_at": "2019-06-22 15:38:47"*/

}
