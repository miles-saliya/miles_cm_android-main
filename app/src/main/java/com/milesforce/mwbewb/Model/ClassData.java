package com.milesforce.mwbewb.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClassData {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("class_name")
    @Expose
    private String class_name;
    @SerializedName("batch")
    @Expose
    private String batch;
    @SerializedName("venue")
    @Expose
    private String venue;
    @SerializedName("course")
    @Expose
    private String course;
    @SerializedName("trainer")
    @Expose
    private String trainer;
    @SerializedName("subject")
    @Expose
    private String subject;
    @SerializedName("class_start_date")
    @Expose
    private String class_start_date;

    public ClassData(int id, String class_name, String batch, String venue, String course, String trainer, String subject, String class_start_date) {
        this.id = id;
        this.class_name = class_name;
        this.batch = batch;
        this.venue = venue;
        this.course = course;
        this.trainer = trainer;
        this.subject = subject;
        this.class_start_date = class_start_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getTrainer() {
        return trainer;
    }

    public void setTrainer(String trainer) {
        this.trainer = trainer;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getClass_start_date() {
        return class_start_date;
    }

    public void setClass_start_date(String class_start_date) {
        this.class_start_date = class_start_date;
    }

    public ClassData() {
    }

    @Override
    public String toString() {
        return "ClassData{" +
                "id=" + id +
                ", class_name='" + class_name + '\'' +
                ", batch='" + batch + '\'' +
                ", venue='" + venue + '\'' +
                ", course='" + course + '\'' +
                ", trainer='" + trainer + '\'' +
                ", subject='" + subject + '\'' +
                ", class_start_date='" + class_start_date + '\'' +
                '}';
    }
}
