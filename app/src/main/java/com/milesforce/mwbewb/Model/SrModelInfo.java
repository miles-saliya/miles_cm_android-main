package com.milesforce.mwbewb.Model;

public class SrModelInfo {
    private String name;
    private String companyName;
    private String category;
    private String batch;
    private String level;
    private String last_follow_up_date;

    public SrModelInfo(String name, String companyName, String category, String batch, String level, String last_follow_up_date) {
        this.name = name;
        this.companyName = companyName;
        this.category = category;
        this.batch = batch;
        this.level = level;
        this.last_follow_up_date = last_follow_up_date;
    }

    public SrModelInfo() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getLast_follow_up_date() {
        return last_follow_up_date;
    }

    public void setLast_follow_up_date(String last_follow_up_date) {
        this.last_follow_up_date = last_follow_up_date;
    }
}
