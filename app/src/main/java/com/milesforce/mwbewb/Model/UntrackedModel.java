package com.milesforce.mwbewb.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UntrackedModel {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("user_id")
    @Expose
    private int user_id;
    @SerializedName("call_duration")
    @Expose
    private int call_duration;

    @SerializedName("mobile_number")
    @Expose
    private String mobile_number;
    @SerializedName("directory")
    @Expose
    private String directory;
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

    public UntrackedModel(int id, int user_id, int call_duration, String mobile_number, String directory, String time, String contact_type, int updated, String created_at, String updated_at) {
        this.id = id;
        this.user_id = user_id;
        this.call_duration = call_duration;
        this.mobile_number = mobile_number;
        this.directory = directory;
        this.time = time;
        this.contact_type = contact_type;
        this.updated = updated;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public UntrackedModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getCall_duration() {
        return call_duration;
    }

    public void setCall_duration(int call_duration) {
        this.call_duration = call_duration;
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

    @Override
    public String toString() {
        return "UntrackedModel{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", call_duration=" + call_duration +
                ", mobile_number='" + mobile_number + '\'' +
                ", directory='" + directory + '\'' +
                ", time='" + time + '\'' +
                ", contact_type='" + contact_type + '\'' +
                ", updated=" + updated +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                '}';
    }
}
