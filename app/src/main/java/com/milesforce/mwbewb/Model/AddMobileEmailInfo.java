package com.milesforce.mwbewb.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddMobileEmailInfo {
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("message")
    @Expose
    private String message;



    @SerializedName("new_masked_mobile")
    @Expose
    private String new_masked_mobile;

    @SerializedName("new_mobile_country_code")
    @Expose
    private String new_mobile_country_code;



    @SerializedName("new_masked_email")
    @Expose
    private String new_masked_email;

    public AddMobileEmailInfo(String status, String message, String new_masked_mobile, String new_mobile_country_code, String new_masked_email) {
        this.status = status;
        this.message = message;
        this.new_masked_mobile = new_masked_mobile;
        this.new_mobile_country_code = new_mobile_country_code;
        this.new_masked_email = new_masked_email;
    }

    public AddMobileEmailInfo() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNew_masked_mobile() {
        return new_masked_mobile;
    }

    public void setNew_masked_mobile(String new_masked_mobile) {
        this.new_masked_mobile = new_masked_mobile;
    }

    public String getNew_mobile_country_code() {
        return new_mobile_country_code;
    }

    public void setNew_mobile_country_code(String new_mobile_country_code) {
        this.new_mobile_country_code = new_mobile_country_code;
    }

    public String getNew_masked_email() {
        return new_masked_email;
    }

    public void setNew_masked_email(String new_masked_email) {
        this.new_masked_email = new_masked_email;
    }

    @Override
    public String toString() {
        return "AddMobileEmailInfo{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", new_masked_mobile='" + new_masked_mobile + '\'' +
                ", new_mobile_country_code='" + new_mobile_country_code + '\'' +
                ", new_masked_email='" + new_masked_email + '\'' +
                '}';
    }
    /* "status": "success",
    "message": "Created new email testqwerty@gmail.com.",
    "new_email_id": 13043,
    "new_masked_email": "testq*****@gmail.com"*/

    /*{
    "status": "success",
    "message": "Created new phone number 123456543211.",
    "new_mobile_id": 14473,
    "new_masked_mobile": "xxxxxxxxx211",
    "new_mobile_country_code": "91"
}*/
}
