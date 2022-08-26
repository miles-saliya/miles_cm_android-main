package com.milesforce.mwbewb.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserInfoModel {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("can_id")
    @Expose
    private int can_id;
    @SerializedName("person_name")
    @Expose
    private String person_name;
    @SerializedName("person_id")
    @Expose
    private int person_id;
    @SerializedName("level")
    @Expose
    private String level;
    @SerializedName("courses")
    @Expose
    private String courses;
    @SerializedName("miles_type")
    @Expose
    private String miles_type;
    @SerializedName("company")
    @Expose
    private String company;
    @SerializedName("designation")
    @Expose
    private String designation;
    @SerializedName("engagement_added")
    @Expose
    private int engagement_added;

    @SerializedName("engagement_details")
    @Expose
    private String engagement_details;

    public UserInfoModel(int id, int can_id, String person_name, int person_id, String level, String courses, String miles_type, String company, String designation, int engagement_added, String engagement_details) {
        this.id = id;
        this.can_id = can_id;
        this.person_name = person_name;
        this.person_id = person_id;
        this.level = level;
        this.courses = courses;
        this.miles_type = miles_type;
        this.company = company;
        this.designation = designation;
        this.engagement_added = engagement_added;
        this.engagement_details = engagement_details;
    }

    public UserInfoModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCan_id() {
        return can_id;
    }

    public void setCan_id(int can_id) {
        this.can_id = can_id;
    }

    public String getPerson_name() {
        return person_name;
    }

    public void setPerson_name(String person_name) {
        this.person_name = person_name;
    }

    public int getPerson_id() {
        return person_id;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getCourses() {
        return courses;
    }

    public void setCourses(String courses) {
        this.courses = courses;
    }

    public String getMiles_type() {
        return miles_type;
    }

    public void setMiles_type(String miles_type) {
        this.miles_type = miles_type;
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

    public int getEngagement_added() {
        return engagement_added;
    }

    public void setEngagement_added(int engagement_added) {
        this.engagement_added = engagement_added;
    }

    public String getEngagement_details() {
        return engagement_details;
    }

    public void setEngagement_details(String engagement_details) {
        this.engagement_details = engagement_details;
    }

    @Override
    public String toString() {
        return "UserInfoModel{" +
                "id=" + id +
                ", can_id=" + can_id +
                ", person_name='" + person_name + '\'' +
                ", person_id=" + person_id +
                ", level='" + level + '\'' +
                ", courses='" + courses + '\'' +
                ", miles_type='" + miles_type + '\'' +
                ", company='" + company + '\'' +
                ", designation='" + designation + '\'' +
                ", engagement_added=" + engagement_added +
                ", engagement_details='" + engagement_details + '\'' +
                '}';
    }
    /*"id": 15326,
            "can_id": 123456789,
            "person_name": "Dumbu",
            "person_id": 15328,
            "level": "L1",
            "courses": "CPA",
            "miles_type": "B2C",
            "company": "Scatter 221",
            "designation": "HR",
            "experience": "5",
            "education": "B.Com,M.Com",
            "city": "Hyderabad",
            "eligibility": "Eligibility",
            "source": "Facebook Ad",
            "engagement_added": 1,
            "last_call": 1561192921,
            "next_call": 1561192800,
            "visited": 0,
            "time_to_nfd": null,
            "spoc_name": "Imran",
            "spoc_id": 75,
            "engagement_details": "Connected / Never call back",
            "engagement_added_on": "22-Jun-2019",
            "added_by_id": null,
            "edited_by_id": 75,
            "edited_comment": "Edited Company to Scatter 221",
            "created_at": "2019-06-21 13:15:16",
            "updated_at": "2019-06-24 15:04:20"*/
}
