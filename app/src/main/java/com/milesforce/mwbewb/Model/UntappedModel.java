package com.milesforce.mwbewb.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UntappedModel {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("can_id")
    @Expose
    private int can_id;
    @SerializedName("person_id")
    @Expose
    private int person_id;

    @SerializedName("person_name")
    @Expose
    private String person_name;
    @SerializedName("level")
    @Expose
    private String level;
    @SerializedName("courses")
    @Expose
    private String courses;
    @SerializedName("last_call")
    @Expose
    private String last_call;
    @SerializedName("next_call")
    @Expose
    private String next_call;
    @SerializedName("created_at")
    @Expose
    private String created_at;
    @SerializedName("updated_at")
    @Expose
    private String updated_at;
    @SerializedName("time_to_nfd")
    @Expose
    private int time_to_nfd;
    @SerializedName("spoc_name")
    @Expose
    private String spoc_name;
    @SerializedName("spoc_id")
    @Expose
    private int spoc_id;
    @SerializedName("miles_type")
    @Expose
    private String miles_type;
    @SerializedName("company")
    @Expose
    private String company;
    @SerializedName("designation")
    @Expose
    private String designation;
    @SerializedName("experience")
    @Expose
    private String experience;
    @SerializedName("education")
    @Expose
    private String education;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("eligibility")
    @Expose
    private String eligibility;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("untapped")
    @Expose
    private String untapped;


    public UntappedModel() {
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

    public String getLast_call() {
        return last_call;
    }

    public void setLast_call(String last_call) {
        this.last_call = last_call;
    }

    public String getNext_call() {
        return next_call;
    }

    public void setNext_call(String next_call) {
        this.next_call = next_call;
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

    public int getTime_to_nfd() {
        return time_to_nfd;
    }

    public void setTime_to_nfd(int time_to_nfd) {
        this.time_to_nfd = time_to_nfd;
    }

    public String getSpoc_name() {
        return spoc_name;
    }

    public void setSpoc_name(String spoc_name) {
        this.spoc_name = spoc_name;
    }

    public int getSpoc_id() {
        return spoc_id;
    }

    public void setSpoc_id(int spoc_id) {
        this.spoc_id = spoc_id;
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

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEligibility() {
        return eligibility;
    }

    public void setEligibility(String eligibility) {
        this.eligibility = eligibility;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUntapped() {
        return untapped;
    }

    public void setUntapped(String untapped) {
        this.untapped = untapped;
    }

    public UntappedModel(int id, int can_id, int person_id, String person_name, String level, String courses, String last_call, String next_call, String created_at, String updated_at, int time_to_nfd, String spoc_name, int spoc_id, String miles_type, String company, String designation, String experience, String education, String city, String eligibility, String source, String untapped) {
        this.id = id;
        this.can_id = can_id;
        this.person_id = person_id;
        this.person_name = person_name;
        this.level = level;
        this.courses = courses;
        this.last_call = last_call;
        this.next_call = next_call;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.time_to_nfd = time_to_nfd;
        this.spoc_name = spoc_name;
        this.spoc_id = spoc_id;
        this.miles_type = miles_type;
        this.company = company;
        this.designation = designation;
        this.experience = experience;
        this.education = education;
        this.city = city;
        this.eligibility = eligibility;
        this.source = source;
        this.untapped = untapped;
    }

    @Override
    public String toString() {
        return "UntappedModel{" +
                "id=" + id +
                ", can_id=" + can_id +
                ", person_id=" + person_id +
                ", person_name='" + person_name + '\'' +
                ", level='" + level + '\'' +
                ", courses='" + courses + '\'' +
                ", last_call='" + last_call + '\'' +
                ", next_call='" + next_call + '\'' +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                ", time_to_nfd=" + time_to_nfd +
                ", spoc_name='" + spoc_name + '\'' +
                ", spoc_id=" + spoc_id +
                ", miles_type='" + miles_type + '\'' +
                ", company='" + company + '\'' +
                ", designation='" + designation + '\'' +
                ", experience='" + experience + '\'' +
                ", education='" + education + '\'' +
                ", city='" + city + '\'' +
                ", eligibility='" + eligibility + '\'' +
                ", source='" + source + '\'' +
                ", untapped='" + untapped + '\'' +
                '}';
    }
    /* "id": 5310,
            "can_id": 9008,
            "person_name": "Mohammed Abdul Kareem",
            "person_id": 5310,
            "level": "L2-",
            "courses": "CMA",
            "miles_type": "B2C",
            "company": "",
            "designation": "",
            "experience": "",
            "education": "b.com",
            "city": "Hyderabad",
            "eligibility": "",
            "source": "",
            "untapped": 0,
            "last_call": 1553715000,
            "next_call": 1564601400,
            "time_to_nfd": null,
            "spoc_name": "Anita",
            "spoc_id": 105,
            "created_at": "2019-06-18 11:38:46",
            "updated_at": "2019-06-20 10:52:31"*/
}
