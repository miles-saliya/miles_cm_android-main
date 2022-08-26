package com.milesforce.mwbewb.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EventModel {
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("contact_type")
    @Expose
    private String contact_type;

    @SerializedName("contact_details")
    private ContactDetails contact_details;

    public EventModel(String status, String contact_type, ContactDetails contact_details) {
        this.status = status;
        this.contact_type = contact_type;
        this.contact_details = contact_details;
    }

    public EventModel() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getContact_type() {
        return contact_type;
    }

    public void setContact_type(String contact_type) {
        this.contact_type = contact_type;
    }

    public ContactDetails getContact_details() {
        return contact_details;
    }

    public void setContact_details(ContactDetails contact_details) {
        this.contact_details = contact_details;
    }

    @Override
    public String toString() {
        return "EventModel{" +
                "status='" + status + '\'' +
                ", contact_type='" + contact_type + '\'' +
                ", contact_details=" + contact_details +
                '}';
    }
    /* "status": "success",
    "contact_type": "B2C",
    "contact_details": {
        "user_id": 105,
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
