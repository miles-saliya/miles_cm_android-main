package com.milesforce.mwbewb.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DashboardDatum {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("phone_number")
    @Expose
    private String phoneNumber;
    @SerializedName("last_ten_digits")
    @Expose
    private Long lastTenDigits;
    @SerializedName("directory")
    @Expose
    private String directory;
    @SerializedName("call_duration")
    @Expose
    private Integer callDuration;
    @SerializedName("time")
    @Expose
    private Long time;
    @SerializedName("next_call")
    @Expose
    private Integer nextCall;
    @SerializedName("next_call_count")
    @Expose
    private Integer nextCallCount;
    @SerializedName("contact_type")
    @Expose
    private String contactType;
    @SerializedName("info")
    @Expose
    private Object info;
    @SerializedName("updated")
    @Expose
    private Integer updated;
    @SerializedName("tallied")
    @Expose
    private Integer tallied;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("user_city")
    @Expose
    private String userCity;
    @SerializedName("person_id")
    @Expose
    private Object personId;
    @SerializedName("person_name")
    @Expose
    private Object personName;
    @SerializedName("level")
    @Expose
    private Object level;
    @SerializedName("can_id")
    @Expose
    private Object canId;
    @SerializedName("mwb_id")
    @Expose
    private Object mwbId;
    @SerializedName("level_updated")
    @Expose
    private Integer levelUpdated;
    @SerializedName("deleted")
    @Expose
    private Integer deleted;
    @SerializedName("call_recording_found")
    @Expose
    private Integer callRecordingFound;
    @SerializedName("version_number")
    @Expose
    private String versionNumber;
    @SerializedName("processing_comments")
    @Expose
    private Object processingComments;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    /**
     * No args constructor for use in serialization
     */
    public DashboardDatum() {
    }

    /**
     * @param userCity
     * @param callDuration
     * @param mwbId
     * @param tallied
     * @param contactType
     * @param callRecordingFound
     * @param directory
     * @param createdAt
     * @param levelUpdated
     * @param id
     * @param info
     * @param updatedAt
     * @param level
     * @param canId
     * @param userId
     * @param versionNumber
     * @param nextCall
     * @param personName
     * @param phoneNumber
     * @param deleted
     * @param processingComments
     * @param nextCallCount
     * @param lastTenDigits
     * @param personId
     * @param time
     * @param updated
     */
    public DashboardDatum(Integer id, String phoneNumber, Long lastTenDigits, String directory, Integer callDuration, Long time, Integer nextCall, Integer nextCallCount, String contactType, Object info, Integer updated, Integer tallied, Integer userId, String userCity, Object personId, Object personName, Object level, Object canId, Object mwbId, Integer levelUpdated, Integer deleted, Integer callRecordingFound, String versionNumber, Object processingComments, String createdAt, String updatedAt) {
        super();
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.lastTenDigits = lastTenDigits;
        this.directory = directory;
        this.callDuration = callDuration;
        this.time = time;
        this.nextCall = nextCall;
        this.nextCallCount = nextCallCount;
        this.contactType = contactType;
        this.info = info;
        this.updated = updated;
        this.tallied = tallied;
        this.userId = userId;
        this.userCity = userCity;
        this.personId = personId;
        this.personName = personName;
        this.level = level;
        this.canId = canId;
        this.mwbId = mwbId;
        this.levelUpdated = levelUpdated;
        this.deleted = deleted;
        this.callRecordingFound = callRecordingFound;
        this.versionNumber = versionNumber;
        this.processingComments = processingComments;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Long getLastTenDigits() {
        return lastTenDigits;
    }

    public void setLastTenDigits(Long lastTenDigits) {
        this.lastTenDigits = lastTenDigits;
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public Integer getCallDuration() {
        return callDuration;
    }

    public void setCallDuration(Integer callDuration) {
        this.callDuration = callDuration;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Integer getNextCall() {
        return nextCall;
    }

    public void setNextCall(Integer nextCall) {
        this.nextCall = nextCall;
    }

    public Integer getNextCallCount() {
        return nextCallCount;
    }

    public void setNextCallCount(Integer nextCallCount) {
        this.nextCallCount = nextCallCount;
    }

    public String getContactType() {
        return contactType;
    }

    public void setContactType(String contactType) {
        this.contactType = contactType;
    }

    public Object getInfo() {
        return info;
    }

    public void setInfo(Object info) {
        this.info = info;
    }

    public Integer getUpdated() {
        return updated;
    }

    public void setUpdated(Integer updated) {
        this.updated = updated;
    }

    public Integer getTallied() {
        return tallied;
    }

    public void setTallied(Integer tallied) {
        this.tallied = tallied;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserCity() {
        return userCity;
    }

    public void setUserCity(String userCity) {
        this.userCity = userCity;
    }

    public Object getPersonId() {
        return personId;
    }

    public void setPersonId(Object personId) {
        this.personId = personId;
    }

    public Object getPersonName() {
        return personName;
    }

    public void setPersonName(Object personName) {
        this.personName = personName;
    }

    public Object getLevel() {
        return level;
    }

    public void setLevel(Object level) {
        this.level = level;
    }

    public Object getCanId() {
        return canId;
    }

    public void setCanId(Object canId) {
        this.canId = canId;
    }

    public Object getMwbId() {
        return mwbId;
    }

    public void setMwbId(Object mwbId) {
        this.mwbId = mwbId;
    }

    public Integer getLevelUpdated() {
        return levelUpdated;
    }

    public void setLevelUpdated(Integer levelUpdated) {
        this.levelUpdated = levelUpdated;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public Integer getCallRecordingFound() {
        return callRecordingFound;
    }

    public void setCallRecordingFound(Integer callRecordingFound) {
        this.callRecordingFound = callRecordingFound;
    }

    public String getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(String versionNumber) {
        this.versionNumber = versionNumber;
    }

    public Object getProcessingComments() {
        return processingComments;
    }

    public void setProcessingComments(Object processingComments) {
        this.processingComments = processingComments;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}
