package com.milesforce.mwbewb.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EmailModel {
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("masked_email")
    @Expose
    private String masked_email;
    @SerializedName("id")
    @Expose
    private int id;

    public EmailModel(String email, String masked_email, int id) {
        this.email = email;
        this.masked_email = masked_email;
        this.id = id;
    }

    public EmailModel() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMasked_email() {
        return masked_email;
    }

    public void setMasked_email(String masked_email) {
        this.masked_email = masked_email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "EmailModel{" +
                "email='" + email + '\'' +
                ", masked_email='" + masked_email + '\'' +
                ", id=" + id +
                '}';
    }
}
