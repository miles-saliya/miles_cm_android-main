package com.milesforce.mwbewb.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MobileNumberModel {
    @SerializedName("phone_number")
    @Expose
    private String phone_number;

    @SerializedName("masked_number")
    @Expose
    private String masked_number;

    @SerializedName("id")
    @Expose
    private int id;

    public MobileNumberModel(String phone_number, String masked_number, int id) {
        this.phone_number = phone_number;
        this.masked_number = masked_number;
        this.id = id;
    }

    public MobileNumberModel() {
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getMasked_number() {
        return masked_number;
    }

    public void setMasked_number(String masked_number) {
        this.masked_number = masked_number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "MobileNumberModel{" +
                "phone_number='" + phone_number + '\'' +
                ", masked_number='" + masked_number + '\'' +
                ", id=" + id +
                '}';
    }
    /*"id": 14480,
        "phone_number": "9000611056",
        "masked_number": "xxxxxxx056"*/

}
