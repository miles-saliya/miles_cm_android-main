package com.milesforce.mwbewb.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SuggestModel implements Serializable {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("can_id")
    @Expose
    private int can_id;
    @SerializedName("identity")
    @Expose
    private String identity;
    @SerializedName("person_name")
    @Expose
    private String person_name;
    @SerializedName("person_id")
    @Expose
    private int person_id;
    @SerializedName("level")
    @Expose
    private String level;
    @SerializedName("level_priority")
    @Expose
    private String level_priority;
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

    @SerializedName("source_details")
    @Expose
    private String source_details;

    public SuggestModel(int id, int can_id, String identity, String person_name, int person_id, String level, String level_priority, String courses, String miles_type, String company, String designation, String source_details) {
        this.id = id;
        this.can_id = can_id;
        this.identity = identity;
        this.person_name = person_name;
        this.person_id = person_id;
        this.level = level;
        this.level_priority = level_priority;
        this.courses = courses;
        this.miles_type = miles_type;
        this.company = company;
        this.designation = designation;
        this.source_details = source_details;
    }

    public SuggestModel() {
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

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
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

    public String getLevel_priority() {
        return level_priority;
    }

    public void setLevel_priority(String level_priority) {
        this.level_priority = level_priority;
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

    public String getSource_details() {
        return source_details;
    }

    public void setSource_details(String source_details) {
        this.source_details = source_details;
    }

    @Override
    public String toString() {
        return "SuggestModel{" +
                "id=" + id +
                ", can_id=" + can_id +
                ", identity='" + identity + '\'' +
                ", person_name='" + person_name + '\'' +
                ", person_id=" + person_id +
                ", level='" + level + '\'' +
                ", level_priority='" + level_priority + '\'' +
                ", courses='" + courses + '\'' +
                ", miles_type='" + miles_type + '\'' +
                ", company='" + company + '\'' +
                ", designation='" + designation + '\'' +
                ", source_details='" + source_details + '\'' +
                '}';
    }
    /* {
        "id": 121,
        "can_id": 28086,
        "city_series": 28086,
        "identity": "H-28086",
        "person_name": "Venkat",
        "person_id": 121,
        "profile_pic_link": null,
        "address_proof_link": null,
        "photo_id_proof_link": null,
        "level": "M4",
        "level_priority": 3,
        "courses": "CMA",
        "miles_type": "B2C",
        "company": "Cognizant",
        "designation": "Associate",
        "experience": "1.5",
        "education": "MBA",
        "education_tags": "MBA",
        "city": "Hyderabad",
        "address": null,
        "eligibility": "",
        "submitted_documents": "No",
        "applied_for_loan": 0,
        "loan_status": "",
        "source": "Net Enquiry",
        "source_details": "H19_07_193",
        "engagement_added": 1,
        "last_call": 1565508657,
        "next_call": 1575311400,
        "visited": 1,
        "visit_count": 1,
        "time_to_nfd": "7",
        "enrolled_status": 0,
        "incommunicado_status": 0,
        "spoc_name": "Anita",
        "spoc_id": 105,
        "engagement_details": "He Will Join For Jan 2020 Batch",
        "engagement_added_on": "11-Aug-2019",
        "added_by_id": null,
        "edited_by_id": 38,
        "edited_comment": "Edited Education to MBA",
        "enrollment_added": 0,
        "created_at": "2019-07-11 10:08:23",
        "updated_at": "2019-08-13 16:14:34"
    },*/
}
