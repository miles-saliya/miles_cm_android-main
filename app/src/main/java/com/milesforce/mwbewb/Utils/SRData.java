package com.milesforce.mwbewb.Utils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.milesforce.mwbewb.Model.CmaData;
import com.milesforce.mwbewb.Model.CpaData;
import com.milesforce.mwbewb.Model.DelaysModel;
import com.milesforce.mwbewb.Model.StudentReferenceData;

import java.io.Serializable;
import java.util.ArrayList;

public class SRData implements Serializable {
    @SerializedName("mwb")
    @Expose
    private DelaysModel mwbData;

    @SerializedName("cpa")
    @Expose
    private CpaData cpa;

    @SerializedName("cma")
    @Expose
    private CmaData cma;

    @SerializedName("student_reference")
    @Expose
    private ArrayList<StudentReferenceData> student_reference;

    public SRData(DelaysModel mwbData, CpaData cpa, CmaData cma, ArrayList<StudentReferenceData> student_reference) {
        this.mwbData = mwbData;
        this.cpa = cpa;
        this.cma = cma;
        this.student_reference = student_reference;
    }

    public SRData() {
    }

    public DelaysModel getMwbData() {
        return mwbData;
    }

    public void setMwbData(DelaysModel mwbData) {
        this.mwbData = mwbData;
    }

    public CpaData getCpa() {
        return cpa;
    }

    public void setCpa(CpaData cpa) {
        this.cpa = cpa;
    }

    public CmaData getCma() {
        return cma;
    }

    public void setCma(CmaData cma) {
        this.cma = cma;
    }

    public ArrayList<StudentReferenceData> getStudent_reference() {
        return student_reference;
    }

    public void setStudent_reference(ArrayList<StudentReferenceData> student_reference) {
        this.student_reference = student_reference;
    }

    @Override
    public String toString() {
        return "SRData{" +
                "mwbData=" + mwbData +
                ", cpa=" + cpa +
                ", cma=" + cma +
                ", student_reference=" + student_reference +
                '}';
    }
}
