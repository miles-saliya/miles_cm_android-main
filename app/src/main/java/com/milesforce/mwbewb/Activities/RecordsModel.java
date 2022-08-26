package com.milesforce.mwbewb.Activities;

public class RecordsModel {
    private String fileName;
    private int position;
    private boolean is_Checked;
    private String fileCreatedAt;

    public RecordsModel(String fileName, int position, boolean is_Checked, String fileCreatedAt) {
        this.fileName = fileName;
        this.position = position;
        this.is_Checked = is_Checked;
        this.fileCreatedAt = fileCreatedAt;
    }

    public RecordsModel() {
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isIs_Checked() {
        return is_Checked;
    }

    public void setIs_Checked(boolean is_Checked) {
        this.is_Checked = is_Checked;
    }

    public String getFileCreatedAt() {
        return fileCreatedAt;
    }

    public void setFileCreatedAt(String fileCreatedAt) {
        this.fileCreatedAt = fileCreatedAt;
    }
}
