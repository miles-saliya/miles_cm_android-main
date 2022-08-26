package com.milesforce.mwbewb.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class EWBData implements Serializable {
    @SerializedName("cpa")
    @Expose
    private CpaData cpa;

    @SerializedName("cma")
    @Expose
    private CmaData cma;

    /*@SerializedName("attendance")
    @Expose
    private AttendanceData attendance;*/

   /* @SerializedName("fees")
    @Expose
    private FeesData fees;

    @SerializedName("books")
    @Expose
    private BooksData books;*/

    @SerializedName("student_reference")
    @Expose
    private ArrayList<StudentReferenceData> student_reference;

    public EWBData(CpaData cpa, CmaData cma, ArrayList<StudentReferenceData> student_reference) {
        this.cpa = cpa;
        this.cma = cma;
        this.student_reference = student_reference;
    }

    public EWBData() {
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
        return "EWBData{" +
                "cpa=" + cpa +
                ", cma=" + cma +
                ", student_reference=" + student_reference +
                '}';
    }
}
