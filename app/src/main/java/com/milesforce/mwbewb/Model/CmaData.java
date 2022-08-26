package com.milesforce.mwbewb.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CmaData implements Serializable {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("identity")
    @Expose
    private String identity;
    @SerializedName("person_name")
    @Expose
    private String person_name;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("cma_can_id")
    @Expose
    private String cma_can_id;

    @SerializedName("enrollment_date")
    @Expose
    private String enrollment_date;

    @SerializedName("batch")
    @Expose
    private String batch;
    @SerializedName("mobile_no")
    @Expose
    private String mobile_no;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("company")
    @Expose
    private String company;

    @SerializedName("part_1_exam_target")
    @Expose
    private String part_1_exam_target;

    @SerializedName("part_2_exam_target")
    @Expose
    private String part_2_exam_target;

    @SerializedName("mwb_id")
    @Expose
    private String mwb_id;

    @SerializedName("IMA membership Number")
    @Expose
    private String IMA_membership_Number;

    public CmaData(int id, String identity, String person_name, String city, String cma_can_id, String enrollment_date, String batch, String mobile_no, String email, String company, String part_1_exam_target, String part_2_exam_target, String mwb_id, String IMA_membership_Number) {
        this.id = id;
        this.identity = identity;
        this.person_name = person_name;
        this.city = city;
        this.cma_can_id = cma_can_id;
        this.enrollment_date = enrollment_date;
        this.batch = batch;
        this.mobile_no = mobile_no;
        this.email = email;
        this.company = company;
        this.part_1_exam_target = part_1_exam_target;
        this.part_2_exam_target = part_2_exam_target;
        this.mwb_id = mwb_id;
        this.IMA_membership_Number = IMA_membership_Number;
    }

    public CmaData() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCma_can_id() {
        return cma_can_id;
    }

    public void setCma_can_id(String cma_can_id) {
        this.cma_can_id = cma_can_id;
    }

    public String getEnrollment_date() {
        return enrollment_date;
    }

    public void setEnrollment_date(String enrollment_date) {
        this.enrollment_date = enrollment_date;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
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

    public String getPart_1_exam_target() {
        return part_1_exam_target;
    }

    public void setPart_1_exam_target(String part_1_exam_target) {
        this.part_1_exam_target = part_1_exam_target;
    }

    public String getPart_2_exam_target() {
        return part_2_exam_target;
    }

    public void setPart_2_exam_target(String part_2_exam_target) {
        this.part_2_exam_target = part_2_exam_target;
    }

    public String getMwb_id() {
        return mwb_id;
    }

    public void setMwb_id(String mwb_id) {
        this.mwb_id = mwb_id;
    }

    public String getIMA_membership_Number() {
        return IMA_membership_Number;
    }

    public void setIMA_membership_Number(String IMA_membership_Number) {
        this.IMA_membership_Number = IMA_membership_Number;
    }

    @Override
    public String toString() {
        return "CmaData{" +
                "id=" + id +
                ", identity='" + identity + '\'' +
                ", person_name='" + person_name + '\'' +
                ", city='" + city + '\'' +
                ", cma_can_id='" + cma_can_id + '\'' +
                ", enrollment_date='" + enrollment_date + '\'' +
                ", batch='" + batch + '\'' +
                ", mobile_no='" + mobile_no + '\'' +
                ", email='" + email + '\'' +
                ", company='" + company + '\'' +
                ", part_1_exam_target='" + part_1_exam_target + '\'' +
                ", part_2_exam_target='" + part_2_exam_target + '\'' +
                ", mwb_id='" + mwb_id + '\'' +
                ", IMA_membership_Number='" + IMA_membership_Number + '\'' +
                '}';
    }
    /*"id": 717,
            "identity": null,
            "person_id": 266,
            "person_name": "Venkat",
            "city": "Hyderabad",
            "cma_can_id": "27940",
            "Can Name": "Venkat",
            "enrollment_date": "17-Jun-2019",
            "batch": "CMA-M7-EE",
            "mobile_no": "8978100880",
            "email": "chalamgv@gmail.com",
            "company": "UHG",
            "Designation": "Finance and accounting",
            "Education": "Bcom",
            "Yr Exp": "",
            "Connect by (Initials only)": "Anita",
            "Last Connect Date": "6-Jul-2019",
            "Next Call date": "1-Oct-2019",
            "Details": "facing issue with OTB access for part 2 , suggested to sent mail to support@miles educaton.com",
            "Corporate Referral": "",
            "CR Connect comments": "",
            "Lead Details (Name/Company/Designation/Contact No./Email ID)": "",
            "part_1_exam_target": "Undecided",
            "part_2_exam_target": "Undecided",
            "Overall Feedback with Miles": "",
            "candidate_status": "Undecided for exams",
            "Candidate Next Exams [Formula-driven; do NOT hard-code]": "N/A",
            "Critical Comments & Escalation to Acads team": "",
            "Acads Team - Last Call Date": "",
            "Acads Team - Call by (Initials)": "",
            "Comments / Connect by Acads Team": "",
            "fees_status": "Cleared",
            "IMA membership Number": "",
            "Name of College": "",
            "Scholarship Status": "",
            "Comments": "",
            "LinkedIn ID": "",
            "Facebook ID": "",
            "Lead Allocation": "Anita",
            "spoc_id": 105,
            "spoc_name": "Anita",
            "Enrolled By": "",
            "Address": "",
            "TT": "",
            "Email ID official/Alternate": "",
            "Alternate No 1": "",
            "Alternate No 2": "",
            "New Can No": "HYD-CMA-027940",
            "Whatsapp": "",
            "mobile_found": 1,
            "email_found": 0,
            "mwb_found": 1,
            "mwb_id": 266,
            "going_to_process": 1,
            "comments_gautami": null,
            "created_at": "2019-10-11 11:51:19",
            "updated_at": "2019-10-12 17:10:09"*/
}
