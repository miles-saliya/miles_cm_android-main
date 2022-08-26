package com.milesforce.mwbewb.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelForId {
    @SerializedName("user_id")
    @Expose
    private int user_id;

    public ModelForId(int user_id) {
        this.user_id = user_id;
    }

    public ModelForId() {
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "ModelForId{" +
                "user_id=" + user_id +
                '}';
    }
}
