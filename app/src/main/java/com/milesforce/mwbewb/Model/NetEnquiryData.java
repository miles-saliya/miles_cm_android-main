package com.milesforce.mwbewb.Model;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NetEnquiryData {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("enquiry_date")
    @Expose
    private String enquiry_date;

    @SerializedName("enquiry_date_unix")
    @Expose
    private String enquiry_date_unix;

    @SerializedName("details")
    @Expose
    private String details;

    @SerializedName("course")
    @Expose
    private String course;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("suggested_city")
    @Expose
    private String suggested_city;
    @SerializedName("selected_spoc")
    @Expose
    private int selected_spoc;
    @SerializedName("selected_iiml_spoc")
    @Expose
    private int selected_iiml_spoc;
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("person_id")
    @Expose
    private int person_id;
    @SerializedName("person_name")
    @Expose
    private String person_name;
    @SerializedName("identity")
    @Expose
    private String identity;
    @SerializedName("mwb_id")
    @Expose
    private String mwb_id;
    @SerializedName("level")
    @Expose
    private String level;
    @SerializedName("person_type")
    @Expose
    private String person_type;
    @SerializedName("net_enquiry_type")
    @Expose
    private String net_enquiry_type;

    private String dialling_number;

    private int duplicateCount;

    public NetEnquiryData(int id, String enquiry_date, String enquiry_date_unix, String details, String course, String name, String mobile, String email, String city, String suggested_city, int selected_spoc, int selected_iiml_spoc, String status, int person_id, String person_name, String identity, String mwb_id, String level, String person_type, String net_enquiry_type, String dialling_number, int duplicateCount) {
        this.id = id;
        this.enquiry_date = enquiry_date;
        this.enquiry_date_unix = enquiry_date_unix;
        this.details = details;
        this.course = course;
        this.name = name;
        this.mobile = mobile;
        this.email = email;
        this.city = city;
        this.suggested_city = suggested_city;
        this.selected_spoc = selected_spoc;
        this.selected_iiml_spoc = selected_iiml_spoc;
        this.status = status;
        this.person_id = person_id;
        this.person_name = person_name;
        this.identity = identity;
        this.mwb_id = mwb_id;
        this.level = level;
        this.person_type = person_type;
        this.net_enquiry_type = net_enquiry_type;
        this.dialling_number = dialling_number;
        this.duplicateCount = duplicateCount;
    }

    public NetEnquiryData() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEnquiry_date() {
        return enquiry_date;
    }

    public void setEnquiry_date(String enquiry_date) {
        this.enquiry_date = enquiry_date;
    }

    public String getEnquiry_date_unix() {
        return enquiry_date_unix;
    }

    public void setEnquiry_date_unix(String enquiry_date_unix) {
        this.enquiry_date_unix = enquiry_date_unix;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSuggested_city() {
        return suggested_city;
    }

    public void setSuggested_city(String suggested_city) {
        this.suggested_city = suggested_city;
    }

    public int getSelected_spoc() {
        return selected_spoc;
    }

    public void setSelected_spoc(int selected_spoc) {
        this.selected_spoc = selected_spoc;
    }

    public int getSelected_iiml_spoc() {
        return selected_iiml_spoc;
    }

    public void setSelected_iiml_spoc(int selected_iiml_spoc) {
        this.selected_iiml_spoc = selected_iiml_spoc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getMwb_id() {
        return mwb_id;
    }

    public void setMwb_id(String mwb_id) {
        this.mwb_id = mwb_id;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getPerson_type() {
        return person_type;
    }

    public void setPerson_type(String person_type) {
        this.person_type = person_type;
    }

    public String getNet_enquiry_type() {
        return net_enquiry_type;
    }

    public void setNet_enquiry_type(String net_enquiry_type) {
        this.net_enquiry_type = net_enquiry_type;
    }

    public String getDialling_number() {
        return dialling_number;
    }

    public void setDialling_number(String dialling_number) {
        this.dialling_number = dialling_number;
    }

    public int getDuplicateCount() {
        return duplicateCount;
    }

    public void setDuplicateCount(int duplicateCount) {
        this.duplicateCount = duplicateCount;
    }


    @Override
    public boolean equals(@Nullable Object obj) {
        if( obj instanceof NetEnquiryData ){
            return ((NetEnquiryData) obj).dialling_number == dialling_number;
        }
        return false;
    }
    @Override
    public int hashCode() {
        return this.id;
    }

    @Override
    public String toString() {
        return "NetEnquiryData{" +
                "id=" + id +
                ", enquiry_date='" + enquiry_date + '\'' +
                ", enquiry_date_unix='" + enquiry_date_unix + '\'' +
                ", details='" + details + '\'' +
                ", course='" + course + '\'' +
                ", name='" + name + '\'' +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", city='" + city + '\'' +
                ", suggested_city='" + suggested_city + '\'' +
                ", selected_spoc=" + selected_spoc +
                ", selected_iiml_spoc=" + selected_iiml_spoc +
                ", status='" + status + '\'' +
                ", person_id=" + person_id +
                ", person_name='" + person_name + '\'' +
                ", identity='" + identity + '\'' +
                ", mwb_id='" + mwb_id + '\'' +
                ", level='" + level + '\'' +
                ", person_type='" + person_type + '\'' +
                ", net_enquiry_type='" + net_enquiry_type + '\'' +
                ", dialling_number='" + dialling_number + '\'' +
                ", duplicateCount=" + duplicateCount +
                '}';
    }
}
