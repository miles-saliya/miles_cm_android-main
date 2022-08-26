package com.milesforce.mwbewb.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CpaData implements Serializable {

    @SerializedName("id")
    @Expose
    private  int id;
    @SerializedName("cpa_can_id")
    @Expose
    private  int cpa_can_id;
    @SerializedName("mwb_can_id")
    @Expose
    private  int mwb_can_id;
    @SerializedName("person_id")
    @Expose
    private  int person_id;
    @SerializedName("person_name")
    @Expose
    private  String person_name;
    @SerializedName("batch")
    @Expose
    private  String batch;
    @SerializedName("Name")
    @Expose
    private  String Name;
    @SerializedName("enrollment_date")
    @Expose
    private  String enrollment_date;
    @SerializedName("level")
    @Expose
    private  String level;
    @SerializedName("mobile_no")
    @Expose
    private  String mobile_no;
    @SerializedName("email")
    @Expose
    private  String email;
    @SerializedName("company")
    @Expose
    private  String company;
    @SerializedName("designation")
    @Expose
    private  String designation;
    @SerializedName("education")
    @Expose
    private  String education;
    @SerializedName("experience")
    @Expose
    private  String experience;
    @SerializedName("connected_by")
    @Expose
    private  String connected_by;
    @SerializedName("last_call")
    @Expose
    private  String last_call;
    @SerializedName("next_call")
    @Expose
    private  String next_call;
    @SerializedName("details")
    @Expose
    private  String details;
    @SerializedName("corporate_referral")
    @Expose
    private  String corporate_referral;
    @SerializedName("cr_comments")
    @Expose
    private  String cr_comments;
    @SerializedName("lead_details")
    @Expose
    private  String lead_details;
    @SerializedName("evaluation")
    @Expose
    private  String evaluation;
    @SerializedName("nts")
    @Expose
    private  String nts;

    @SerializedName("aud")
    @Expose
    private  String aud;
    @SerializedName("far")
    @Expose
    private  String far;
    @SerializedName("bec")
    @Expose
    private  String bec;
    @SerializedName("reg")
    @Expose
    private  String reg;

    public CpaData(int id, int cpa_can_id, int mwb_can_id, int person_id, String person_name, String batch, String name, String enrollment_date, String level, String mobile_no, String email, String company, String designation, String education, String experience, String connected_by, String last_call, String next_call, String details, String corporate_referral, String cr_comments, String lead_details, String evaluation, String nts, String aud, String far, String bec, String reg) {
        this.id = id;
        this.cpa_can_id = cpa_can_id;
        this.mwb_can_id = mwb_can_id;
        this.person_id = person_id;
        this.person_name = person_name;
        this.batch = batch;
        Name = name;
        this.enrollment_date = enrollment_date;
        this.level = level;
        this.mobile_no = mobile_no;
        this.email = email;
        this.company = company;
        this.designation = designation;
        this.education = education;
        this.experience = experience;
        this.connected_by = connected_by;
        this.last_call = last_call;
        this.next_call = next_call;
        this.details = details;
        this.corporate_referral = corporate_referral;
        this.cr_comments = cr_comments;
        this.lead_details = lead_details;
        this.evaluation = evaluation;
        this.nts = nts;
        this.aud = aud;
        this.far = far;
        this.bec = bec;
        this.reg = reg;
    }

    public CpaData() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCpa_can_id() {
        return cpa_can_id;
    }

    public void setCpa_can_id(int cpa_can_id) {
        this.cpa_can_id = cpa_can_id;
    }

    public int getMwb_can_id() {
        return mwb_can_id;
    }

    public void setMwb_can_id(int mwb_can_id) {
        this.mwb_can_id = mwb_can_id;
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

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEnrollment_date() {
        return enrollment_date;
    }

    public void setEnrollment_date(String enrollment_date) {
        this.enrollment_date = enrollment_date;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getConnected_by() {
        return connected_by;
    }

    public void setConnected_by(String connected_by) {
        this.connected_by = connected_by;
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

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getCorporate_referral() {
        return corporate_referral;
    }

    public void setCorporate_referral(String corporate_referral) {
        this.corporate_referral = corporate_referral;
    }

    public String getCr_comments() {
        return cr_comments;
    }

    public void setCr_comments(String cr_comments) {
        this.cr_comments = cr_comments;
    }

    public String getLead_details() {
        return lead_details;
    }

    public void setLead_details(String lead_details) {
        this.lead_details = lead_details;
    }

    public String getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }

    public String getNts() {
        return nts;
    }

    public void setNts(String nts) {
        this.nts = nts;
    }

    public String getAud() {
        return aud;
    }

    public void setAud(String aud) {
        this.aud = aud;
    }

    public String getFar() {
        return far;
    }

    public void setFar(String far) {
        this.far = far;
    }

    public String getBec() {
        return bec;
    }

    public void setBec(String bec) {
        this.bec = bec;
    }

    public String getReg() {
        return reg;
    }

    public void setReg(String reg) {
        this.reg = reg;
    }

    @Override
    public String toString() {
        return "CpaData{" +
                "id=" + id +
                ", cpa_can_id=" + cpa_can_id +
                ", mwb_can_id=" + mwb_can_id +
                ", person_id=" + person_id +
                ", person_name='" + person_name + '\'' +
                ", batch='" + batch + '\'' +
                ", Name='" + Name + '\'' +
                ", enrollment_date='" + enrollment_date + '\'' +
                ", level='" + level + '\'' +
                ", mobile_no='" + mobile_no + '\'' +
                ", email='" + email + '\'' +
                ", company='" + company + '\'' +
                ", designation='" + designation + '\'' +
                ", education='" + education + '\'' +
                ", experience='" + experience + '\'' +
                ", connected_by='" + connected_by + '\'' +
                ", last_call='" + last_call + '\'' +
                ", next_call='" + next_call + '\'' +
                ", details='" + details + '\'' +
                ", corporate_referral='" + corporate_referral + '\'' +
                ", cr_comments='" + cr_comments + '\'' +
                ", lead_details='" + lead_details + '\'' +
                ", evaluation='" + evaluation + '\'' +
                ", nts='" + nts + '\'' +
                ", aud='" + aud + '\'' +
                ", far='" + far + '\'' +
                ", bec='" + bec + '\'' +
                ", reg='" + reg + '\'' +
                '}';
    }
    /* "aud": "Drop-Out",
                "far": "Drop-Out",
                "bec": "Drop-Out",
                "reg": "Drop-Out",
                "miles_feedback": "Happy",
                "testimonial": "",
                "acads_escalation": "",
                "candidate_status": "Drop-out",
                "next_exam": "N/A",
                "evaluation_agency_state": "",
                "acads_evaluation_advice_date": "",
                "acads_evaluation_advice_by": "",
                "acads_nts_advice_date": "",
                "acads_nts_advice_by": "",
                "acads_last_call": "",
                "acads_last_call_by": "",
                "acads_comments": "",
                "fees_status": "Cleared",
                "spoc_name": "Imran",
                "spoc_id": 75,
                "enrolled_by": "",
                "new_can_id": "HYD-CPA-000099",
                "address": "",
                "tt": "",
                "Email ID official/Alternate": "",
                "alternate_number_1": "",
                "Alternate Number 2": "",
                "Whatsapp": "Yes",
                "mwb_id": 16112,
                "mwb_found": 1,
                "email_found": 0,
                "can_id_found": 0,
                "mobile_found": 1,
                "comments": null,
                "going_to_process": 1,
                "created_at": "2019-09-05 06:33:27",
                "updated_at": "2019-10-14 13:09:29"
    },*/
}
