package com.milesforce.mwbewb.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ULevelModel {

    @SerializedName("u_level")
    @Expose
    private String uLevel;
    @SerializedName("description")
    @Expose
    private String description;

    public ULevelModel(String uLevel, String description) {
        this.uLevel = uLevel;
        this.description = description;
    }

    public String getuLevel() {
        return uLevel;
    }

    public void setuLevel(String uLevel) {
        this.uLevel = uLevel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}