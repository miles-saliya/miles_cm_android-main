package com.milesforce.mwbewb.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UntabbedModel {
    @SerializedName("company_id")
    @Expose
    private int company_id;
    @SerializedName("company_name")
    @Expose
    private String company_name;
    @SerializedName("person_id")
    @Expose
    private int person_id;
    @SerializedName("person_name")
    @Expose
    private String person_name;

    public UntabbedModel(int company_id, String company_name, int person_id, String person_name) {
        this.company_id = company_id;
        this.company_name = company_name;
        this.person_id = person_id;
        this.person_name = person_name;
    }

    public UntabbedModel() {
    }

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
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

    @Override
    public String toString() {
        return "UntabbedModel{" +
                "company_id=" + company_id +
                ", company_name='" + company_name + '\'' +
                ", person_id=" + person_id +
                ", person_name='" + person_name + '\'' +
                '}';
    }
}
