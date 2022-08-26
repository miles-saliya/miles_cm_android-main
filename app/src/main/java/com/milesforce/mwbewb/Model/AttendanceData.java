package com.milesforce.mwbewb.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AttendanceData implements Serializable {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("cpa_ewb_id")
    @Expose
    private int cpa_ewb_id;
    @SerializedName("can_id")
    @Expose
    private String can_id;
    @SerializedName("Can Name")
    @Expose
    private String Can_Name;
    @SerializedName("Batch")
    @Expose
    private String Batch;
    @SerializedName("aud")
    @Expose
    private String aud;
    @SerializedName("far")
    @Expose
    private String far;
    @SerializedName("bec")
    @Expose
    private String bec;
    @SerializedName("reg")
    @Expose
    private String reg;

    public AttendanceData(int id, int cpa_ewb_id, String can_id, String can_Name, String batch, String aud, String far, String bec, String reg) {
        this.id = id;
        this.cpa_ewb_id = cpa_ewb_id;
        this.can_id = can_id;
        Can_Name = can_Name;
        Batch = batch;
        this.aud = aud;
        this.far = far;
        this.bec = bec;
        this.reg = reg;
    }

    public AttendanceData() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCpa_ewb_id() {
        return cpa_ewb_id;
    }

    public void setCpa_ewb_id(int cpa_ewb_id) {
        this.cpa_ewb_id = cpa_ewb_id;
    }

    public String getCan_id() {
        return can_id;
    }

    public void setCan_id(String can_id) {
        this.can_id = can_id;
    }

    public String getCan_Name() {
        return Can_Name;
    }

    public void setCan_Name(String can_Name) {
        Can_Name = can_Name;
    }

    public String getBatch() {
        return Batch;
    }

    public void setBatch(String batch) {
        Batch = batch;
    }

    public String getAud() {
        return aud;
    }

    public void setAud(String aud) {
        this.aud = aud;
    }

    public String getFar() {
        return far;
    }

    public void setFar(String far) {
        this.far = far;
    }

    public String getBec() {
        return bec;
    }

    public void setBec(String bec) {
        this.bec = bec;
    }

    public String getReg() {
        return reg;
    }

    public void setReg(String reg) {
        this.reg = reg;
    }

    @Override
    public String toString() {
        return "AttendanceData{" +
                "id=" + id +
                ", cpa_ewb_id=" + cpa_ewb_id +
                ", can_id='" + can_id + '\'' +
                ", Can_Name='" + Can_Name + '\'' +
                ", Batch='" + Batch + '\'' +
                ", aud='" + aud + '\'' +
                ", far='" + far + '\'' +
                ", bec='" + bec + '\'' +
                ", reg='" + reg + '\'' +
                '}';
    }
    /*"attendance": {
                "id": 14,
                "cpa_ewb_id": 14,
                "can_id": "215",
                "Can Name": "Karthik",
                "Batch": "CPA-M7-01",
                "mobile_no": "9985814258",
                "email": "mailto:vangkartik@gmail.com",
                "Fees Due": "Default",
                "Duplicate": "OK",
                "aud": "0",
                "far": "0",
                "bec": "0",
                "reg": "0",
                "aud_1": "",
                "aud_2": "",
                "aud_3": "",
                "aud_4": "",
                "aud_5": "",
                "aud_6": "",
                "aud_7": "",
                "far_1": "",
                "far_2": "",
                "far_3": "",
                "far_4": "",
                "far_5": "",
                "far_6": "",
                "far_7": "",
                "far_8": "",
                "far_9": "",
                "bec_1": "",
                "bec_2": "",
                "bec_3": "",
                "bec_4": "",
                "bec_5": "",
                "reg_1": "",
                "reg_2": "",
                "reg_3": "",
                "reg_4": "",
                "reg_5": "",
                "reg_6": "",
                "aud_1_repeat": "",
                "aud_2_repeat": "",
                "aud_3_repeat": "",
                "aud_4_repeat": "",
                "aud_5_repeat": "",
                "aud_6_repeat": "",
                "aud_7_repeat": "",
                "far_1_repeat": "",
                "far_2_repeat": "",
                "far_3_repeat": "",
                "far_4_repeat": "",
                "far_5_repeat": "",
                "far_6_repeat": "",
                "far_7_repeat": "",
                "far_8_repeat": "",
                "far_9_extra_class": "",
                "reg_1_repeat": "",
                "reg_2_repeat": "",
                "reg_3_repeat": "",
                "reg_4_repeat": "",
                "reg_5_repeat": "",
                "reg_6_repeat": "",
                "reg_7_extra_class": "",
                "reg_8_extra_class": "",
                "BEC Class - 1 Repeat": "",
                "BEC Class -2 Repeat": "",
                "BEC Class -3 Repeat": "",
                "BEC Class - 4 Repeat": "",
                "REG - Class 1 Repeat": "",
                "REG - Class 2 Repeat": "",
                "REG - Class 3 Repeat": "",
                "REG - Class 4 Repeat": "",
                "REG - Class 5 Repeat": "",
                "REG - Class 6 Repeat": "",
                "mobile_found": 1,
                "email_found": 0,
                "mwb_id": 16037,
                "going_to_process": 1,
                "comments": "",
                "created_at": "-0001-11-30 00:00:00",
                "updated_at": "2019-10-10 10:10:34"
            },*/
}
