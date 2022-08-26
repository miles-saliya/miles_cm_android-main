package com.milesforce.mwbewb.Model;

public class CallLogModel {
    String mobileNumber;
    String callType;
    String callDate;
    String callDuration;
    String timeStamp;
    String directory_mode;
    String event;
    String tallied_id;

    public CallLogModel(String mobileNumber, String callType, String callDate, String callDuration, String timeStamp, String directory_mode, String event, String tallied_id) {
        this.mobileNumber = mobileNumber;
        this.callType = callType;
        this.callDate = callDate;
        this.callDuration = callDuration;
        this.timeStamp = timeStamp;
        this.directory_mode = directory_mode;
        this.event = event;
        this.tallied_id = tallied_id;
    }

    public CallLogModel() {
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getCallType() {
        return callType;
    }

    public void setCallType(String callType) {
        this.callType = callType;
    }

    public String getCallDate() {
        return callDate;
    }

    public void setCallDate(String callDate) {
        this.callDate = callDate;
    }

    public String getCallDuration() {
        return callDuration;
    }

    public void setCallDuration(String callDuration) {
        this.callDuration = callDuration;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getDirectory_mode() {
        return directory_mode;
    }

    public void setDirectory_mode(String directory_mode) {
        this.directory_mode = directory_mode;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getTallied_id() {
        return tallied_id;
    }

    public void setTallied_id(String tallied_id) {
        this.tallied_id = tallied_id;
    }

    @Override
    public String toString() {
        return "CallLogModel{" +
                "mobileNumber='" + mobileNumber + '\'' +
                ", callType='" + callType + '\'' +
                ", callDate='" + callDate + '\'' +
                ", callDuration='" + callDuration + '\'' +
                ", timeStamp='" + timeStamp + '\'' +
                ", directory_mode='" + directory_mode + '\'' +
                ", event='" + event + '\'' +
                ", tallied_id='" + tallied_id + '\'' +
                '}';
    }
}
