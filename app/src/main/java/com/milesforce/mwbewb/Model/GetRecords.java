package com.milesforce.mwbewb.Model;

public class GetRecords {
    private String start_time;
    private String end_time;
    private String file_name;

    public GetRecords() {
    }

    public GetRecords(String start_time, String end_time, String file_name) {
        this.start_time = start_time;
        this.end_time = end_time;
        this.file_name = file_name;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }
}
