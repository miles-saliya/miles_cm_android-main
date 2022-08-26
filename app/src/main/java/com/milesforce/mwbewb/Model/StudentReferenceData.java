package com.milesforce.mwbewb.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class StudentReferenceData implements Serializable {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("person_id")
    @Expose
    private int person_id;

    @SerializedName("mwb_id")
    @Expose
    private int mwb_id;

    @SerializedName("bank_details")
    @Expose
    private String bank_details;
    @SerializedName("bonus_status")
    @Expose
    private String bonus_status;
    @SerializedName("bonus_comments")
    @Expose
    private String bonus_comments;
    @SerializedName("reference_given_date")
    @Expose
    private String reference_given_date;
    @SerializedName("referred_person_id")
    @Expose
    private String referred_person_id;
    @SerializedName("referred_person_name")
    @Expose
    private String referred_person_name;
    @SerializedName("referred_mwb_id")
    @Expose
    private int referred_mwb_id;
    @SerializedName("added_by_id")
    @Expose
    private int added_by_id;
    @SerializedName("edited_by_id")
    @Expose
    private int edited_by_id;

    public StudentReferenceData(int id, int person_id, int mwb_id, String bank_details, String bonus_status, String bonus_comments, String reference_given_date, String referred_person_id, String referred_person_name, int referred_mwb_id, int added_by_id, int edited_by_id) {
        this.id = id;
        this.person_id = person_id;
        this.mwb_id = mwb_id;
        this.bank_details = bank_details;
        this.bonus_status = bonus_status;
        this.bonus_comments = bonus_comments;
        this.reference_given_date = reference_given_date;
        this.referred_person_id = referred_person_id;
        this.referred_person_name = referred_person_name;
        this.referred_mwb_id = referred_mwb_id;
        this.added_by_id = added_by_id;
        this.edited_by_id = edited_by_id;
    }

    public StudentReferenceData() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPerson_id() {
        return person_id;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }

    public int getMwb_id() {
        return mwb_id;
    }

    public void setMwb_id(int mwb_id) {
        this.mwb_id = mwb_id;
    }

    public String getBank_details() {
        return bank_details;
    }

    public void setBank_details(String bank_details) {
        this.bank_details = bank_details;
    }

    public String getBonus_status() {
        return bonus_status;
    }

    public void setBonus_status(String bonus_status) {
        this.bonus_status = bonus_status;
    }

    public String getBonus_comments() {
        return bonus_comments;
    }

    public void setBonus_comments(String bonus_comments) {
        this.bonus_comments = bonus_comments;
    }

    public String getReference_given_date() {
        return reference_given_date;
    }

    public void setReference_given_date(String reference_given_date) {
        this.reference_given_date = reference_given_date;
    }

    public String getReferred_person_id() {
        return referred_person_id;
    }

    public void setReferred_person_id(String referred_person_id) {
        this.referred_person_id = referred_person_id;
    }

    public String getReferred_person_name() {
        return referred_person_name;
    }

    public void setReferred_person_name(String referred_person_name) {
        this.referred_person_name = referred_person_name;
    }

    public int getReferred_mwb_id() {
        return referred_mwb_id;
    }

    public void setReferred_mwb_id(int referred_mwb_id) {
        this.referred_mwb_id = referred_mwb_id;
    }

    public int getAdded_by_id() {
        return added_by_id;
    }

    public void setAdded_by_id(int added_by_id) {
        this.added_by_id = added_by_id;
    }

    public int getEdited_by_id() {
        return edited_by_id;
    }

    public void setEdited_by_id(int edited_by_id) {
        this.edited_by_id = edited_by_id;
    }

    @Override
    public String toString() {
        return "StudentReferenceData{" +
                "id=" + id +
                ", person_id=" + person_id +
                ", mwb_id=" + mwb_id +
                ", bank_details='" + bank_details + '\'' +
                ", bonus_status='" + bonus_status + '\'' +
                ", bonus_comments='" + bonus_comments + '\'' +
                ", reference_given_date='" + reference_given_date + '\'' +
                ", referred_person_id='" + referred_person_id + '\'' +
                ", referred_person_name='" + referred_person_name + '\'' +
                ", referred_mwb_id=" + referred_mwb_id +
                ", added_by_id=" + added_by_id +
                ", edited_by_id=" + edited_by_id +
                '}';
    }
    /* "id": 1,
                    "person_id": 16112,
                    "mwb_id": 16112,
                    "bank_details": "Indian Bank 52552525",
                    "bonus_status": "initiated",
                    "bonus_comments": null,
                    "reference_given_date": 1571033889,
                    "referred_person_id": 16235,
                    "referred_person_name": "SEET-Cintu",
                    "referred_mwb_id": 16212,
                    "added_by_id": 76,
                    "edited_by_id": null,
                    "created_at": "2019-10-14 12:26:48",
                    "updated_at": "2019-10-14 12:26:48"*/
}
