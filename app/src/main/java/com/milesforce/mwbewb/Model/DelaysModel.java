package com.milesforce.mwbewb.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DelaysModel implements Serializable {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("can_id")
    @Expose
    private int can_id;
    @SerializedName("person_id")
    @Expose
    private int person_id;

    @SerializedName("person_name")
    @Expose
    private String person_name;

    @SerializedName("added_by_id")
    @Expose
    private int added_by_id;

    @SerializedName("added_by_name")
    @Expose
    private String added_by_name;
    @SerializedName("assigned_spoc_id")
    @Expose
    private int assigned_spoc_id;

    @SerializedName("assigned_spoc_name")
    @Expose
    private String assigned_spoc_name;

    @SerializedName("level")
    @Expose
    private String level;
    @SerializedName("courses")
    @Expose
    private String courses;
    @SerializedName("details")
    @Expose
    private String details;
    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("acads")
    @Expose
    private int acads;
    @SerializedName("last_call")
    @Expose
    private String last_call;
    @SerializedName("next_call")
    @Expose
    private String next_call;
    @SerializedName("addressed")
    @Expose
    private String addressed;
    @SerializedName("created_at")
    @Expose
    private String created_at;
    @SerializedName("updated_at")
    @Expose
    private String updated_at;

    @SerializedName("time_to_nfd")
    @Expose
    private String time_to_nfd;
    @SerializedName("spoc_name")
    @Expose
    private String spoc_name;
    @SerializedName("spoc_id")
    @Expose
    private int spoc_id;
    @SerializedName("miles_type")
    @Expose
    private String miles_type;
    @SerializedName("company")
    @Expose
    private String company;

    @SerializedName("designation")
    @Expose
    private String designation;
    @SerializedName("experience")
    @Expose
    private String experience;
    @SerializedName("education")
    @Expose
    private String education;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("eligibility")
    @Expose
    private String eligibility;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("untapped")
    @Expose
    private String untapped;

    @SerializedName("engagement_details")
    @Expose
    private String engagement_details;

    @SerializedName("engagement_added_on")
    @Expose
    private String engagement_added_on;
    /*For Here */


    @SerializedName("phone_number")
    @Expose
    private String phone_number;

    @SerializedName("directory")
    @Expose
    private String directory;

    @SerializedName("call_duration")
    @Expose
    private int call_duration;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("contact_type")
    @Expose
    private String contact_type;
    @SerializedName("updated")
    @Expose
    private String updated;

    @SerializedName("tallied")
    @Expose
    private int tallied;

    @SerializedName("visited")
    @Expose
    private int visited;

    @SerializedName("engagement_added")
    @Expose
    private int engagement_added;

    @SerializedName("submitted_documents")
    @Expose
    private String submitted_documents;

    @SerializedName("level_priority")
    @Expose
    private float level_priority;
    @SerializedName("applied_for_loan")
    @Expose
    private int applied_for_loan;
    @SerializedName("loan_status")
    @Expose
    private String loan_status;
    @SerializedName("education_tags")
    @Expose
    private String education_tags;
    @SerializedName("identity")
    @Expose
    private String identity;
    @SerializedName("batch")
    @Expose
    private String batch;
    @SerializedName("iiml_level")
    @Expose
    private String iiml_level;

    public DelaysModel(int id, int can_id, int person_id, String person_name, int added_by_id, String added_by_name, int assigned_spoc_id, String assigned_spoc_name, String level, String courses, String details, String type, int acads, String last_call, String next_call, String addressed, String created_at, String updated_at, String time_to_nfd, String spoc_name, int spoc_id, String miles_type, String company, String designation, String experience, String education, String city, String eligibility, String source, String untapped, String engagement_details, String engagement_added_on, String phone_number, String directory, int call_duration, String time, String contact_type, String updated, int tallied, int visited, int engagement_added, String submitted_documents, float level_priority, int applied_for_loan, String loan_status, String education_tags, String identity, String batch, String iiml_level) {
        this.id = id;
        this.can_id = can_id;
        this.person_id = person_id;
        this.person_name = person_name;
        this.added_by_id = added_by_id;
        this.added_by_name = added_by_name;
        this.assigned_spoc_id = assigned_spoc_id;
        this.assigned_spoc_name = assigned_spoc_name;
        this.level = level;
        this.courses = courses;
        this.details = details;
        this.type = type;
        this.acads = acads;
        this.last_call = last_call;
        this.next_call = next_call;
        this.addressed = addressed;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.time_to_nfd = time_to_nfd;
        this.spoc_name = spoc_name;
        this.spoc_id = spoc_id;
        this.miles_type = miles_type;
        this.company = company;
        this.designation = designation;
        this.experience = experience;
        this.education = education;
        this.city = city;
        this.eligibility = eligibility;
        this.source = source;
        this.untapped = untapped;
        this.engagement_details = engagement_details;
        this.engagement_added_on = engagement_added_on;
        this.phone_number = phone_number;
        this.directory = directory;
        this.call_duration = call_duration;
        this.time = time;
        this.contact_type = contact_type;
        this.updated = updated;
        this.tallied = tallied;
        this.visited = visited;
        this.engagement_added = engagement_added;
        this.submitted_documents = submitted_documents;
        this.level_priority = level_priority;
        this.applied_for_loan = applied_for_loan;
        this.loan_status = loan_status;
        this.education_tags = education_tags;
        this.identity = identity;
        this.batch = batch;
        this.iiml_level = iiml_level;
    }

    public DelaysModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCan_id() {
        return can_id;
    }

    public void setCan_id(int can_id) {
        this.can_id = can_id;
    }

    public int getPerson_id() {
        return person_id;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }

    public String getPerson_name() {
        return person_name;
    }

    public void setPerson_name(String person_name) {
        this.person_name = person_name;
    }

    public int getAdded_by_id() {
        return added_by_id;
    }

    public void setAdded_by_id(int added_by_id) {
        this.added_by_id = added_by_id;
    }

    public String getAdded_by_name() {
        return added_by_name;
    }

    public void setAdded_by_name(String added_by_name) {
        this.added_by_name = added_by_name;
    }

    public int getAssigned_spoc_id() {
        return assigned_spoc_id;
    }

    public void setAssigned_spoc_id(int assigned_spoc_id) {
        this.assigned_spoc_id = assigned_spoc_id;
    }

    public String getAssigned_spoc_name() {
        return assigned_spoc_name;
    }

    public void setAssigned_spoc_name(String assigned_spoc_name) {
        this.assigned_spoc_name = assigned_spoc_name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getCourses() {
        return courses;
    }

    public void setCourses(String courses) {
        this.courses = courses;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAcads() {
        return acads;
    }

    public void setAcads(int acads) {
        this.acads = acads;
    }

    public String getLast_call() {
        return last_call;
    }

    public void setLast_call(String last_call) {
        this.last_call = last_call;
    }

    public String getNext_call() {
        return next_call;
    }

    public void setNext_call(String next_call) {
        this.next_call = next_call;
    }

    public String getAddressed() {
        return addressed;
    }

    public void setAddressed(String addressed) {
        this.addressed = addressed;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getTime_to_nfd() {
        return time_to_nfd;
    }

    public void setTime_to_nfd(String time_to_nfd) {
        this.time_to_nfd = time_to_nfd;
    }

    public String getSpoc_name() {
        return spoc_name;
    }

    public void setSpoc_name(String spoc_name) {
        this.spoc_name = spoc_name;
    }

    public int getSpoc_id() {
        return spoc_id;
    }

    public void setSpoc_id(int spoc_id) {
        this.spoc_id = spoc_id;
    }

    public String getMiles_type() {
        return miles_type;
    }

    public void setMiles_type(String miles_type) {
        this.miles_type = miles_type;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEligibility() {
        return eligibility;
    }

    public void setEligibility(String eligibility) {
        this.eligibility = eligibility;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUntapped() {
        return untapped;
    }

    public void setUntapped(String untapped) {
        this.untapped = untapped;
    }

    public String getEngagement_details() {
        return engagement_details;
    }

    public void setEngagement_details(String engagement_details) {
        this.engagement_details = engagement_details;
    }

    public String getEngagement_added_on() {
        return engagement_added_on;
    }

    public void setEngagement_added_on(String engagement_added_on) {
        this.engagement_added_on = engagement_added_on;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public int getCall_duration() {
        return call_duration;
    }

    public void setCall_duration(int call_duration) {
        this.call_duration = call_duration;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContact_type() {
        return contact_type;
    }

    public void setContact_type(String contact_type) {
        this.contact_type = contact_type;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public int getTallied() {
        return tallied;
    }

    public void setTallied(int tallied) {
        this.tallied = tallied;
    }

    public int getVisited() {
        return visited;
    }

    public void setVisited(int visited) {
        this.visited = visited;
    }

    public int getEngagement_added() {
        return engagement_added;
    }

    public void setEngagement_added(int engagement_added) {
        this.engagement_added = engagement_added;
    }

    public String getSubmitted_documents() {
        return submitted_documents;
    }

    public void setSubmitted_documents(String submitted_documents) {
        this.submitted_documents = submitted_documents;
    }

    public float getLevel_priority() {
        return level_priority;
    }

    public void setLevel_priority(float level_priority) {
        this.level_priority = level_priority;
    }

    public int getApplied_for_loan() {
        return applied_for_loan;
    }

    public void setApplied_for_loan(int applied_for_loan) {
        this.applied_for_loan = applied_for_loan;
    }

    public String getLoan_status() {
        return loan_status;
    }

    public void setLoan_status(String loan_status) {
        this.loan_status = loan_status;
    }

    public String getEducation_tags() {
        return education_tags;
    }

    public void setEducation_tags(String education_tags) {
        this.education_tags = education_tags;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getIiml_level() {
        return iiml_level;
    }

    public void setIiml_level(String iiml_level) {
        this.iiml_level = iiml_level;
    }

    @Override
    public String toString() {
        return "DelaysModel{" +
                "id=" + id +
                ", can_id=" + can_id +
                ", person_id=" + person_id +
                ", person_name='" + person_name + '\'' +
                ", added_by_id=" + added_by_id +
                ", added_by_name='" + added_by_name + '\'' +
                ", assigned_spoc_id=" + assigned_spoc_id +
                ", assigned_spoc_name='" + assigned_spoc_name + '\'' +
                ", level='" + level + '\'' +
                ", courses='" + courses + '\'' +
                ", details='" + details + '\'' +
                ", type='" + type + '\'' +
                ", acads=" + acads +
                ", last_call='" + last_call + '\'' +
                ", next_call='" + next_call + '\'' +
                ", addressed='" + addressed + '\'' +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                ", time_to_nfd='" + time_to_nfd + '\'' +
                ", spoc_name='" + spoc_name + '\'' +
                ", spoc_id=" + spoc_id +
                ", miles_type='" + miles_type + '\'' +
                ", company='" + company + '\'' +
                ", designation='" + designation + '\'' +
                ", experience='" + experience + '\'' +
                ", education='" + education + '\'' +
                ", city='" + city + '\'' +
                ", eligibility='" + eligibility + '\'' +
                ", source='" + source + '\'' +
                ", untapped='" + untapped + '\'' +
                ", engagement_details='" + engagement_details + '\'' +
                ", engagement_added_on='" + engagement_added_on + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", directory='" + directory + '\'' +
                ", call_duration=" + call_duration +
                ", time='" + time + '\'' +
                ", contact_type='" + contact_type + '\'' +
                ", updated='" + updated + '\'' +
                ", tallied=" + tallied +
                ", visited=" + visited +
                ", engagement_added=" + engagement_added +
                ", submitted_documents='" + submitted_documents + '\'' +
                ", level_priority=" + level_priority +
                ", applied_for_loan=" + applied_for_loan +
                ", loan_status='" + loan_status + '\'' +
                ", education_tags='" + education_tags + '\'' +
                ", identity='" + identity + '\'' +
                ", batch='" + batch + '\'' +
                ", iiml_level='" + iiml_level + '\'' +
                '}';
    }
}
    /*education_tags*/

    /* public DelaysModel(int id, int can_id, int person_id, String person_name, int added_by_id, String added_by_name, int assigned_spoc_id, String assigned_spoc_name, String level, String courses, String details, String type, int acads, String last_call, String next_call, String addressed, String created_at, String updated_at, int time_to_nfd, String spoc_name, int spoc_id, String miles_type, String company, String designation, String experience, String education, String city, String eligibility, String source, String untapped, String engagement_details, String engagement_added_on, String phone_number, String directory, int call_duration, String time, String contact_type, String updated, int tallied, int visited, int engagement_added, String submitted_documents) {
        this.id = id;
        this.can_id = can_id;
        this.person_id = person_id;
        this.person_name = person_name;
        this.added_by_id = added_by_id;
        this.added_by_name = added_by_name;
        this.assigned_spoc_id = assigned_spoc_id;
        this.assigned_spoc_name = assigned_spoc_name;
        this.level = level;
        this.courses = courses;
        this.details = details;
        this.type = type;
        this.acads = acads;
        this.last_call = last_call;
        this.next_call = next_call;
        this.addressed = addressed;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.time_to_nfd = time_to_nfd;
        this.spoc_name = spoc_name;
        this.spoc_id = spoc_id;
        this.miles_type = miles_type;
        this.company = company;
        this.designation = designation;
        this.experience = experience;
        this.education = education;
        this.city = city;
        this.eligibility = eligibility;
        this.source = source;
        this.untapped = untapped;
        this.engagement_details = engagement_details;
        this.engagement_added_on = engagement_added_on;
        this.phone_number = phone_number;
        this.directory = directory;
        this.call_duration = call_duration;
        this.time = time;
        this.contact_type = contact_type;
        this.updated = updated;
        this.tallied = tallied;
        this.visited = visited;
        this.engagement_added = engagement_added;
        this.submitted_documents = submitted_documents;
    }

    public DelaysModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCan_id() {
        return can_id;
    }

    public void setCan_id(int can_id) {
        this.can_id = can_id;
    }

    public int getPerson_id() {
        return person_id;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }

    public String getPerson_name() {
        return person_name;
    }

    public void setPerson_name(String person_name) {
        this.person_name = person_name;
    }

    public int getAdded_by_id() {
        return added_by_id;
    }

    public void setAdded_by_id(int added_by_id) {
        this.added_by_id = added_by_id;
    }

    public String getAdded_by_name() {
        return added_by_name;
    }

    public void setAdded_by_name(String added_by_name) {
        this.added_by_name = added_by_name;
    }

    public int getAssigned_spoc_id() {
        return assigned_spoc_id;
    }

    public void setAssigned_spoc_id(int assigned_spoc_id) {
        this.assigned_spoc_id = assigned_spoc_id;
    }

    public String getAssigned_spoc_name() {
        return assigned_spoc_name;
    }

    public void setAssigned_spoc_name(String assigned_spoc_name) {
        this.assigned_spoc_name = assigned_spoc_name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getCourses() {
        return courses;
    }

    public void setCourses(String courses) {
        this.courses = courses;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAcads() {
        return acads;
    }

    public void setAcads(int acads) {
        this.acads = acads;
    }

    public String getLast_call() {
        return last_call;
    }

    public void setLast_call(String last_call) {
        this.last_call = last_call;
    }

    public String getNext_call() {
        return next_call;
    }

    public void setNext_call(String next_call) {
        this.next_call = next_call;
    }

    public String getAddressed() {
        return addressed;
    }

    public void setAddressed(String addressed) {
        this.addressed = addressed;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public int getTime_to_nfd() {
        return time_to_nfd;
    }

    public void setTime_to_nfd(int time_to_nfd) {
        this.time_to_nfd = time_to_nfd;
    }

    public String getSpoc_name() {
        return spoc_name;
    }

    public void setSpoc_name(String spoc_name) {
        this.spoc_name = spoc_name;
    }

    public int getSpoc_id() {
        return spoc_id;
    }

    public void setSpoc_id(int spoc_id) {
        this.spoc_id = spoc_id;
    }

    public String getMiles_type() {
        return miles_type;
    }

    public void setMiles_type(String miles_type) {
        this.miles_type = miles_type;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEligibility() {
        return eligibility;
    }

    public void setEligibility(String eligibility) {
        this.eligibility = eligibility;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUntapped() {
        return untapped;
    }

    public void setUntapped(String untapped) {
        this.untapped = untapped;
    }

    public String getEngagement_details() {
        return engagement_details;
    }

    public void setEngagement_details(String engagement_details) {
        this.engagement_details = engagement_details;
    }

    public String getEngagement_added_on() {
        return engagement_added_on;
    }

    public void setEngagement_added_on(String engagement_added_on) {
        this.engagement_added_on = engagement_added_on;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public int getCall_duration() {
        return call_duration;
    }

    public void setCall_duration(int call_duration) {
        this.call_duration = call_duration;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContact_type() {
        return contact_type;
    }

    public void setContact_type(String contact_type) {
        this.contact_type = contact_type;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public int getTallied() {
        return tallied;
    }

    public void setTallied(int tallied) {
        this.tallied = tallied;
    }

    public int getVisited() {
        return visited;
    }

    public void setVisited(int visited) {
        this.visited = visited;
    }

    public int getEngagement_added() {
        return engagement_added;
    }

    public void setEngagement_added(int engagement_added) {
        this.engagement_added = engagement_added;
    }

    public String getSubmitted_documents() {
        return submitted_documents;
    }

    public void setSubmitted_documents(String submitted_documents) {
        this.submitted_documents = submitted_documents;
    }

    @Override
    public String toString() {
        return "DelaysModel{" +
                "id=" + id +
                ", can_id=" + can_id +
                ", person_id=" + person_id +
                ", person_name='" + person_name + '\'' +
                ", added_by_id=" + added_by_id +
                ", added_by_name='" + added_by_name + '\'' +
                ", assigned_spoc_id=" + assigned_spoc_id +
                ", assigned_spoc_name='" + assigned_spoc_name + '\'' +
                ", level='" + level + '\'' +
                ", courses='" + courses + '\'' +
                ", details='" + details + '\'' +
                ", type='" + type + '\'' +
                ", acads=" + acads +
                ", last_call='" + last_call + '\'' +
                ", next_call='" + next_call + '\'' +
                ", addressed='" + addressed + '\'' +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                ", time_to_nfd=" + time_to_nfd +
                ", spoc_name='" + spoc_name + '\'' +
                ", spoc_id=" + spoc_id +
                ", miles_type='" + miles_type + '\'' +
                ", company='" + company + '\'' +
                ", designation='" + designation + '\'' +
                ", experience='" + experience + '\'' +
                ", education='" + education + '\'' +
                ", city='" + city + '\'' +
                ", eligibility='" + eligibility + '\'' +
                ", source='" + source + '\'' +
                ", untapped='" + untapped + '\'' +
                ", engagement_details='" + engagement_details + '\'' +
                ", engagement_added_on='" + engagement_added_on + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", directory='" + directory + '\'' +
                ", call_duration=" + call_duration +
                ", time='" + time + '\'' +
                ", contact_type='" + contact_type + '\'' +
                ", updated='" + updated + '\'' +
                ", tallied=" + tallied +
                ", visited=" + visited +
                ", engagement_added=" + engagement_added +
                ", submitted_documents='" + submitted_documents + '\'' +
                '}';
    }*/
    /* "can_id": 71,
             "person_name": "Imran Ahmed",
             "person_id": 62,
             "spoc_name": "Anita",
             "spoc_id": 105,
             "courses": "CPA",
             "level": "L2-",
             "source": "",
             "last_call": 1554406200,
             "next_call": 1564601400,
             "education": "B.COM+cwa PUR",
             "experience": "",
             "designation": "",
             "company": "Deloitte Tax",
             "eligibility": "",
             "city": "Hyderabad",
             "engagement_details": "5/4 does not exist.",
             "engagement_added_on": "18-Jun-2019"
    */




    /*"id": 1706,
            "can_id": 2661,
            "person_id": 1706,
            "person_name": "Kiran",
            "added_by_id": 75,
            "added_by_name": "Imran",
            "assigned_spoc_id": 75,
            "assigned_spoc_name": "Imran",
            "level": "L4-",
            "courses": "CMA",
            "details": "LIfted the call but no respond and disconected\n17/1 Wrong Number",
            "type": "Call",
            "acads": 0,
            "last_call": 1547667000,
            "next_call": 1561923000,
            "addressed": 0,
            "created_at": "2019-06-18 11:54:36",
            "updated_at": "2019-06-19 04:48:06"*/



    /*  @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("can_id")
    @Expose
    private int can_id;
    @SerializedName("person_id")
    @Expose
    private int person_id;

    @SerializedName("person_name")
    @Expose
    private String person_name;
    @SerializedName("level")
    @Expose
    private String level;
    @SerializedName("courses")
    @Expose
    private String courses;
    @SerializedName("last_call")
    @Expose
    private String last_call;
    @SerializedName("next_call")
    @Expose
    private String next_call;
    @SerializedName("created_at")
    @Expose
    private String created_at;
    @SerializedName("updated_at")
    @Expose
    private String updated_at;
    @SerializedName("time_to_nfd")
    @Expose
    private int time_to_nfd;
    @SerializedName("spoc_name")
    @Expose
    private String spoc_name;
    @SerializedName("spoc_id")
    @Expose
    private int spoc_id;
    @SerializedName("miles_type")
    @Expose
    private String miles_type;
    @SerializedName("company")
    @Expose
    private String company;
    @SerializedName("designation")
    @Expose
    private String designation;
    @SerializedName("experience")
    @Expose
    private String experience;
    @SerializedName("education")
    @Expose
    private String education;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("eligibility")
    @Expose
    private String eligibility;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("untapped")
    @Expose
    private String untapped;*/

