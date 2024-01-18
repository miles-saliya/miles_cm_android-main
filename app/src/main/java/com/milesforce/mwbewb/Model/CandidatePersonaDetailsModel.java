package com.milesforce.mwbewb.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CandidatePersonaDetailsModel {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("mwb_id")
    @Expose
    private Integer mwbId;
    @SerializedName("identity")
    @Expose
    private String identity;
    @SerializedName("pathway")
    @Expose
    private Object pathway;
    @SerializedName("pathway_value")
    @Expose
    private String pathwayValue;
    @SerializedName("persona")
    @Expose
    private String persona;
    @SerializedName("placement_assistance")
    @Expose
    private Object placementAssistance;
    @SerializedName("placement_assistance_created_date")
    @Expose
    private Object placementAssistanceCreatedDate;
    @SerializedName("company")
    @Expose
    private String company;
    @SerializedName("college")
    @Expose
    private Object college;
    @SerializedName("years_of_experience")
    @Expose
    private Integer yearsOfExperience;
    @SerializedName("graduation_year")
    @Expose
    private String graduationYear;
    @SerializedName("indian_professional_qualification")
    @Expose
    private String indianProfessionalQualification;
    @SerializedName("global_professional_qualification")
    @Expose
    private String globalProfessionalQualification;
    @SerializedName("ug_qualification")
    @Expose
    private String ugQualification;
    @SerializedName("pg_qualification")
    @Expose
    private String pgQualification;
    @SerializedName("updated_by_id")
    @Expose
    private Object updatedById;
    @SerializedName("pathway_updated_date")
    @Expose
    private Object pathwayUpdatedDate;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("location")
    @Expose
    private String location;

    /**
     * No args constructor for use in serialization
     */
    public CandidatePersonaDetailsModel() {
    }

    /**
     * @param college
     * @param pgQualification
     * @param persona
     * @param mwbId
     * @param pathwayValue
     * @param placementAssistanceCreatedDate
     * @param updatedById
     * @param indianProfessionalQualification
     * @param createdAt
     * @param pathwayUpdatedDate
     * @param globalProfessionalQualification
     * @param yearsOfExperience
     * @param identity
     * @param placementAssistance
     * @param pathway
     * @param company
     * @param graduationYear
     * @param location
     * @param id
     * @param ugQualification
     * @param updatedAt
     */
    public CandidatePersonaDetailsModel(Integer id, Integer mwbId, String identity, Object pathway, String pathwayValue, String persona, Object placementAssistance, Object placementAssistanceCreatedDate, String company, Object college, Integer yearsOfExperience, String graduationYear, String indianProfessionalQualification, String globalProfessionalQualification, String ugQualification, String pgQualification, Object updatedById, Object pathwayUpdatedDate, String createdAt, String updatedAt, String location) {
        super();
        this.id = id;
        this.mwbId = mwbId;
        this.identity = identity;
        this.pathway = pathway;
        this.pathwayValue = pathwayValue;
        this.persona = persona;
        this.placementAssistance = placementAssistance;
        this.placementAssistanceCreatedDate = placementAssistanceCreatedDate;
        this.company = company;
        this.college = college;
        this.yearsOfExperience = yearsOfExperience;
        this.graduationYear = graduationYear;
        this.indianProfessionalQualification = indianProfessionalQualification;
        this.globalProfessionalQualification = globalProfessionalQualification;
        this.ugQualification = ugQualification;
        this.pgQualification = pgQualification;
        this.updatedById = updatedById;
        this.pathwayUpdatedDate = pathwayUpdatedDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.location = location;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMwbId() {
        return mwbId;
    }

    public void setMwbId(Integer mwbId) {
        this.mwbId = mwbId;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public Object getPathway() {
        return pathway;
    }

    public void setPathway(Object pathway) {
        this.pathway = pathway;
    }

    public String getPathwayValue() {
        return pathwayValue;
    }

    public void setPathwayValue(String pathwayValue) {
        this.pathwayValue = pathwayValue;
    }

    public String getPersona() {
        return persona;
    }

    public void setPersona(String persona) {
        this.persona = persona;
    }

    public Object getPlacementAssistance() {
        return placementAssistance;
    }

    public void setPlacementAssistance(Object placementAssistance) {
        this.placementAssistance = placementAssistance;
    }

    public Object getPlacementAssistanceCreatedDate() {
        return placementAssistanceCreatedDate;
    }

    public void setPlacementAssistanceCreatedDate(Object placementAssistanceCreatedDate) {
        this.placementAssistanceCreatedDate = placementAssistanceCreatedDate;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Object getCollege() {
        return college;
    }

    public void setCollege(Object college) {
        this.college = college;
    }

    public Integer getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(Integer yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    public String getGraduationYear() {
        return graduationYear;
    }

    public void setGraduationYear(String graduationYear) {
        this.graduationYear = graduationYear;
    }

    public String getIndianProfessionalQualification() {
        return indianProfessionalQualification;
    }

    public void setIndianProfessionalQualification(String indianProfessionalQualification) {
        this.indianProfessionalQualification = indianProfessionalQualification;
    }

    public String getGlobalProfessionalQualification() {
        return globalProfessionalQualification;
    }

    public void setGlobalProfessionalQualification(String globalProfessionalQualification) {
        this.globalProfessionalQualification = globalProfessionalQualification;
    }

    public String getUgQualification() {
        return ugQualification;
    }

    public void setUgQualification(String ugQualification) {
        this.ugQualification = ugQualification;
    }

    public String getPgQualification() {
        return pgQualification;
    }

    public void setPgQualification(String pgQualification) {
        this.pgQualification = pgQualification;
    }

    public Object getUpdatedById() {
        return updatedById;
    }

    public void setUpdatedById(Object updatedById) {
        this.updatedById = updatedById;
    }

    public Object getPathwayUpdatedDate() {
        return pathwayUpdatedDate;
    }

    public void setPathwayUpdatedDate(Object pathwayUpdatedDate) {
        this.pathwayUpdatedDate = pathwayUpdatedDate;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}