package com.milesforce.mwbewb.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ContactDetails {
    @SerializedName("user_id")
    @Expose
    private int user_id;
    @SerializedName("data")
    @Expose
    private UserInfoModel data;
    @SerializedName("mobile_id")
    @Expose
    private int mobile_id;

    public ContactDetails(int user_id, UserInfoModel data, int mobile_id) {
        this.user_id = user_id;
        this.data = data;
        this.mobile_id = mobile_id;
    }

    public ContactDetails() {
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public UserInfoModel getData() {
        return data;
    }

    public void setData(UserInfoModel data) {
        this.data = data;
    }

    public int getMobile_id() {
        return mobile_id;
    }

    public void setMobile_id(int mobile_id) {
        this.mobile_id = mobile_id;
    }

    @Override
    public String toString() {
        return "ContactDetails{" +
                "user_id=" + user_id +
                ", data=" + data +
                ", mobile_id=" + mobile_id +
                '}';
    }
    /* "user_id": 105,
        "data": {
            "id": 15326,
            "can_id": 123456789,
            "person_name": "Dumbu",
            "person_id": 15328,
            "level": "L1",
            "courses": "CPA",
            "miles_type": "B2C",
            "company": "Scatter 221",
            "designation": "HR",
            "experience": "5",
            "education": "B.Com,M.Com",
            "city": "Hyderabad",
            "eligibility": "Eligibility",
            "source": "Facebook Ad",
            "engagement_added": 1,
            "last_call": 1561192921,
            "next_call": 1561192800,
            "visited": 0,
            "time_to_nfd": null,
            "spoc_name": "Imran",
            "spoc_id": 75,
            "engagement_details": "Connected / Never call back",
            "engagement_added_on": "22-Jun-2019",
            "added_by_id": null,
            "edited_by_id": 75,
            "edited_comment": "Edited Company to Scatter 221",
            "created_at": "2019-06-21 13:15:16",
            "updated_at": "2019-06-24 15:04:20"*/
}
