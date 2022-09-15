package com.milesforce.mwbewb.Retrofit;

import com.milesforce.mwbewb.Activities.BussinessCallRecorderModel;
import com.milesforce.mwbewb.Model.AddMobileEmailInfo;
import com.milesforce.mwbewb.Model.AutoSuggestModel;
import com.milesforce.mwbewb.Model.CallDataLogModel;
import com.milesforce.mwbewb.Model.CallLogDataModel;
import com.milesforce.mwbewb.Model.ClassModel;
import com.milesforce.mwbewb.Model.DelaysModel;
import com.milesforce.mwbewb.Model.EWBData;
import com.milesforce.mwbewb.Model.EmailModel;
import com.milesforce.mwbewb.Model.EngagemmentHistoryModel;
import com.milesforce.mwbewb.Model.EventModel;
import com.milesforce.mwbewb.Model.EwbDataModel;
import com.milesforce.mwbewb.Model.MissedCallLogModel;
import com.milesforce.mwbewb.Model.MissedCallModel;
import com.milesforce.mwbewb.Model.MobileNumberModel;
import com.milesforce.mwbewb.Model.MobileSearchModel;
import com.milesforce.mwbewb.Model.ModelForId;
import com.milesforce.mwbewb.Model.NetEnquiry;
import com.milesforce.mwbewb.Model.SearchWithId;
import com.milesforce.mwbewb.Model.SrModelInfo;
import com.milesforce.mwbewb.Model.SuggestModel;
import com.milesforce.mwbewb.Model.TeamModel;
import com.milesforce.mwbewb.Model.TokenDataModel;
import com.milesforce.mwbewb.Model.TokenModel;
import com.milesforce.mwbewb.Model.UntrackedMainModel;
import com.milesforce.mwbewb.Model.UntrackedModel;
import com.milesforce.mwbewb.Model.WorkLogModel;
import com.milesforce.mwbewb.TabFragments.ModelForAllData;
import com.milesforce.mwbewb.Utils.SRDataModel;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ApiClient {

    /*Login Api For getting AccessToken */
    @FormUrlEncoded
    @POST("generateAccesstoken")
    Call<TokenDataModel> getAccessTokenFromLogin(@Field("username") String username, @Field("password") String password);

    /*Get Latest Call Log*/
    @FormUrlEncoded
    @POST("sendCallLog")
    Call<EventModel> sendLatestCallLog(@Field("call_log") JSONArray json, @Header("Authorization") String s, @Header("Accept") String s1);

    /*Post Call Log */
    @FormUrlEncoded
    @POST("saveCallLogs")
    Call<SuccessModel> SendAllCallLogData(@Field("call_logs") JSONArray json, @Header("Authorization") String s, @Header("Accept") String s1);

    /*Getting Delays WorkLog*/
    @GET()
    Call<ModelForAllData> getDelaysModel(@Url String url,@Query("page") int page, @Header("Authorization") String s, @Header("Accept") String s1);

    /*Getting Missed Call Data*/
    @GET("getMissedCallData")
    Call<List<MissedCallModel>> getMissedCallData(@Header("Authorization") String s, @Header("Accept") String s1);

    /*Getting Untracked Call Data*/
    @GET("getUntrackedCallData")
    Call<List<UntrackedModel>> getUntrackedCallData(@Header("Authorization") String s, @Header("Accept") String s1);

    /*Getting UserId for Pusher*/
    @GET("giveMeMyId")
    Call<ModelForId> getUserIdForPusherSubscription(@Header("Authorization") String s, @Header("Accept") String s1);

    /*Getting Waiting data*/
    @GET()
    Call<ModelForAllData> getImWaitingData(@Url String url,@Query("page") int page, @Header("Authorization") String s, @Header("Accept") String s1);

    /*Getting getTodays Data*/
    @GET()
    Call<ModelForAllData> getJobsForToday(@Url String Url,@Query("page") int page, @Header("Authorization") String s, @Header("Accept") String s1);

    /*Getting Bussiness call Data for Call Recording sorting */
    @GET("getAllBusinessCalls")
    Call<List<BussinessCallRecorderModel>> getBussinessCallRecorder(@Header("Authorization") String s, @Header("Accept") String s1);

    /*Getting Url for Posting Call Records */
    @GET("generatePresignedUrl")
    Call<SuccessModel> getGeneratedPresignedUrl(@Query("timestamp") String url, @Header("Authorization") String s, @Header("Accept") String s1);

    /*Put Request for upload Audio file to server*/
    @PUT
    Call<ResponseBody> UploadBinaryFile(@Url String Url, @Body RequestBody file);

    /*Getting Client mobile number request Api*/
    @GET("getMobileNumbers")
    Call<List<MobileNumberModel>> getClientMobileNumbers(@Query("person_id") int id, @Header("Authorization") String s, @Header("Accept") String s1);
    /*Getting Client Emails request Api*/

    @GET("getEmails")
    Call<List<EmailModel>> getUserEmails(@Query("person_id") int id, @Header("Authorization") String s, @Header("Accept") String s1);

    /*Getting Untapped Data*/
    @GET()
    Call<ModelForAllData> getUntappedData(@Url String url,@Query("page") int page, @Header("Authorization") String s, @Header("Accept") String s1);

    @GET("getEngagementHistory")
    Call<EngagemmentHistoryModel> getPersonengagement(@Query("page") int page, @Query("person_id") int person_id, @Header("Authorization") String s, @Header("Accept") String s1);

    @POST("addEngagement")
    @FormUrlEncoded
    Call<SuccessModel> AddEngagement(@Field("connection_status") String connection_status,@Field("phone_number") String phone_number, @Field("can_id") int can_id, @Field("person_id") int person_id, @Field("person_name") String person_name, @Field("level") String level, @Field("courses") String courses, @Field("details") String details, @Field("type") String type, @Field("acads") int acads, @Field("next_call") long next_call, @Field("update_call_log") String update_call_log, @Field("mobile_id") int mobile_id,@Field("enrollment")String enrollment, @Field("battery_percentage") String battery_percentage, @Field("battery_status") String battery_status, @Field("version_number") String version_number, @Header("Authorization") String s, @Header("Accept") String s1);


    @GET("searchMwbNameByType")
    Call<List<DelaysModel>> getSearchedLeads(@Query("name") String personname, @Query("type") String type, @Header("Authorization") String s, @Header("Accept") String s1);


    @GET("searchMwbName")
    Call<List<DelaysModel>> getSearchedLeadsWithName(@Query("name") String personname, @Header("Authorization") String s, @Header("Accept") String s1);


    @GET("getToUpdate")
    Call<CallLogDataModel> getToUpdateData(@Query("page") int page, @Header("Authorization") String s, @Header("Accept") String s1);

    @GET("getMissedCalls")
    Call<MissedCallLogModel> getMissedCallsData(@Query("page") int page, @Header("Authorization") String s, @Header("Accept") String s1);

    @GET("getUntrackedCalls")
    Call<UntrackedMainModel> getUntrackedCallLogs(@Query("page") int page, @Header("Authorization") String s, @Header("Accept") String s1);

    @POST("addMobile")
    @FormUrlEncoded
    Call<AddMobileEmailInfo> AddUserMobilenumber(@Field("can_id") int can_id, @Field("person_id") int person_id, @Field("person_name") String person_name, @Field("mobiles") String mobiles, @Field("country_code") String country_code, @Field("battery_percentage") String battery_percentage, @Field("battery_status") String battery_status, @Field("Version_number") String Version_number, @Header("Authorization") String s, @Header("Accept") String s1);

    @POST("addEmail")
    @FormUrlEncoded
    Call<AddMobileEmailInfo> AddUserEmail(@Field("can_id") int can_id, @Field("person_id") int person_id, @Field("person_name") String person_name, @Field("emails") String emails, @Field("battery_percentage") String battery_percentage, @Field("battery_status") String battery_status, @Field("Version_number") String Version_number, @Header("Authorization") String s, @Header("Accept") String s1);

    @POST("deleteMobile")
    @FormUrlEncoded
    Call<AddMobileEmailInfo> DeleteUserMobile(@Field("mobile_id") int mobile_id, @Field("battery_percentage") String battery_percentage, @Field("battery_status") String battery_status, @Field("Version_number") String Version_number, @Header("Authorization") String s, @Header("Accept") String s1);

    @POST("deleteEmail")
    @FormUrlEncoded
    Call<AddMobileEmailInfo> DeleteEmail(@Field("email_id") int email_id, @Field("battery_percentage") String battery_percentage, @Field("battery_status") String battery_status, @Field("Version_number") String Version_number, @Header("Authorization") String s, @Header("Accept") String s1);

    @POST("editMobile")
    @FormUrlEncoded
    Call<AddMobileEmailInfo> EditUserMobileNumber(@Field("mobile_id") int mobile_id, @Field("edited_number") String edited_number, @Field("country_code") String country_code, @Field("battery_percentage") String battery_percentage, @Field("battery_status") String battery_status, @Field("Version_number") String Version_number, @Header("Authorization") String s, @Header("Accept") String s1);

    @POST("editEmail")
    @FormUrlEncoded
    Call<AddMobileEmailInfo> EditUserEmailAddress(@Field("email_id") int email_id, @Field("edited_email") String edited_email, @Field("battery_percentage") String battery_percentage, @Field("battery_status") String battery_status, @Field("Version_number") String Version_number, @Header("Authorization") String s, @Header("Accept") String s1);

    @POST("addMwb")
    @FormUrlEncoded
    Call<SuccessModel> AddMwbLead(@Field("name") String name, @Field("level") String level, @Field("courses") String courses, @Field("type") String type, @Field("company") String company, @Field("designation") String designation, @Field("experience") String experience, @Field("education") String education, @Field("city") String city, @Field("eligibility") String eligibility, @Field("source") String source, @Field("source_details") String source_details, @Field("emails") String emails, @Field("mobiles") String mobiles, @Field("details") String details, @Field("next_call") long next_call, @Field("acads") int acads, @Field("battery_percentage") String battery_percentage, @Field("battery_status") String battery_status, @Field("Version_number") String Version_number, @Field("international_city") String international_city,@Field("country") String country,@Field("connection_status") String connection_status,@Field("net_enquiry_type") String net_enquiry_type,@Header("Authorization") String s, @Header("Accept") String s1);

    @POST("saveAsPersonalCall")
    @FormUrlEncoded
    Call<SuccessModel> AddUntrackedAsPersonal(@Field("name") String name, @Field("relationship") String relationship, @Field("phone_number") String phone_number, @Field("battery_percentage") String battery_percentage, @Field("battery_status") String battery_status, @Field("Version_number") String Version_number, @Header("Authorization") String s, @Header("Accept") String s1);

    @POST("saveAsSpamCall")
    @FormUrlEncoded
    Call<SuccessModel> AddUntrackedAsSpam(@Field("phone_number") String phone_number, @Field("info") String info, @Field("battery_percentage") String battery_percentage, @Field("battery_status") String battery_status, @Field("Version_number") String Version_number, @Header("Authorization") String s, @Header("Accept") String s1);

    @POST("saveAsMilesEmployee")
    @FormUrlEncoded
    Call<SuccessModel> AddUntrackedAsMilesemployee(@Field("name") String name, @Field("phone_number") String phone_number, @Field("battery_percentage") String battery_percentage, @Field("battery_status") String battery_status, @Field("Version_number") String Version_number, @Header("Authorization") String s, @Header("Accept") String s1);

    @POST("saveUntrackedToExistingPerson")
    @FormUrlEncoded
    Call<SuccessModel> AddUntrackedToExistingPerson(@Field("person_id") int person_id, @Field("person_name") String person_name, @Field("can_id") int can_id, @Field("type") String type, @Field("phone_number") String phone_number, @Field("battery_percentage") String battery_percentage, @Field("battery_status") String battery_status, @Field("Version_number") String Version_number, @Header("Authorization") String s, @Header("Accept") String s1);


    @POST("addEligibility")
    @FormUrlEncoded
    Call<SuccessModel> AddEligibility(@Field("mwb_id") int mwb_id, @Field("person_id") int person_id, @Field("eligibility") String eligibility, @Field("submitted_documents") String submitted_documents, @Field("battery_percentage") String battery_percentage, @Field("battery_status") String battery_status, @Field("Version_number") String Version_number, @Header("Authorization") String s, @Header("Accept") String s1);

    @GET("getCorporates")
    Call<List<AutoSuggestModel>> getCorporates(@Header("Authorization") String s, @Header("Accept") String s1);

    @GET("getUniversities")
    Call<List<AutoSuggestModel>> getUniversities(@Header("Authorization") String s, @Header("Accept") String s1);

    @POST("addAppliedForLoan")
    @FormUrlEncoded
    Call<SuccessModel> AddApplyForLoan(@Field("mwb_id") int mwb_id, @Field("person_id") int person_id, @Field("loan_status") String loan_status, @Field("applied_for_loan") String applied_for_loan, @Field("battery_percentage") String battery_percentage, @Field("battery_status") String battery_status, @Field("Version_number") String Version_number, @Header("Authorization") String s, @Header("Accept") String s1);

    @POST("saveAsVendor")
    @FormUrlEncoded
    Call<SuccessModel> AddUntrackedAsVendor(@Field("name") String name, @Field("phone_number") String phone_number, @Field("battery_percentage") String battery_percentage, @Field("battery_status") String battery_status, @Field("Version_number") String Version_number, @Header("Authorization") String s, @Header("Accept") String s1);

    @POST("editEducation")
    @FormUrlEncoded
    Call<SuccessModel> UpdateEducation(@Field("mwb_id") int mwb_id, @Field("person_id") int person_id, @Field("education_tags") String eligibility, @Field("battery_percentage") String battery_percentage, @Field("battery_status") String battery_status, @Field("Version_number") String Version_number, @Header("Authorization") String s, @Header("Accept") String s1);

    @GET("getCallLogs")
    Call<CallLogDataModel> getCallLogs(@Header("Authorization") String s, @Header("Accept") String s1);

    @GET("getSpocUtilization")
    Call<SpocUtilizationModel> getSpocUtilization(@Header("Authorization") String s, @Header("Accept") String s1);

    @GET("searchMwbPhone")
    Call<MobileSearchModel> getSearchedLeadsWithMobileNumber(@Query("phone_number") String phone_number, @Header("Authorization") String s, @Header("Accept") String s1);

    @GET
    Call<SRDataModel>getEwbDataWithSpocId(@Url String url, @Header("Authorization") String s, @Header("Accept") String s1);

    @POST("addCPAPlanOfAction")
    @FormUrlEncoded
    Call<SuccessModel>updatePlanOffAction(@Field("cpa_id") int cpa_id, @Field("evaluation") String evaluation, @Field("nts") String nts, @Field("aud") String aud, @Field("bec") String bec, @Field("far") String far, @Field("reg") String reg,@Header("Authorization") String s, @Header("Accept") String s1);

    @POST("addCMAPlanOfAction")
    @FormUrlEncoded
    Call<SuccessModel>updateCMAPlanOffAction(@Field("cma_id") int cpa_id, @Field("target1") String evaluation, @Field("target2") String nts,@Header("Authorization") String s, @Header("Accept") String s1);

    @GET("searchMwbNameForReference")
    Call<ArrayList<SuggestModel>> getSuggestNamesForMWB(@Query("name") String name, @Header("Authorization") String s, @Header("Accept") String s1);

    @POST("addStudentReference")
    @FormUrlEncoded
    Call<SuccessModel>AddReferrelPersonTocandidate(@Field("person_id") int person_id, @Field("mwb_id") int mwb_id, @Field("bank_details") String back_details, @Field("reference_given_date") String reference_given_date, @Field("referred_person_id") int referred_person_id, @Field("referred_person_name") String referred_person_name, @Field("referred_mwb_id") int referred_mwb_id,@Header("Authorization") String s, @Header("Accept") String s1);

    @GET("checkCallRecordingExistence")
    Call<SuccessModel>checkCallRecordingExistance(@Query("timestamp") String timestamp,@Header("Authorization") String s, @Header("Accept") String s1);

    @GET("searchWithCanId")
    Call<SearchWithId>searchWithCanId(@Query("identity") String identity, @Header("Authorization") String s, @Header("Accept") String s1);

    @POST("AddClass")
    @FormUrlEncoded
    Call<SuccessModel>addClass(@Field("class_date") String class_date,@Field("venue") String venue,@Field("course") String course,@Field("class") String studen_class,@Field("trainer") String trainer,@Header("Authorization") String s, @Header("Accept") String s1);

    @POST("indexFace")
    Call<SuccessModel> registerWithIndentity(@Body RequestBody file, @Header("Authorization") String accessToken, @Header("Accept") String accptance);

    @POST("getStudentInfoFromImage")
    Call<SuccessModel> searchWithStudentPic(@Body RequestBody file, @Header("Authorization") String accessToken, @Header("Accept") String accptance);

    @GET("getClassrooms")
    Call<ClassModel>getClassesData(@Header("Authorization") String accessToken, @Header("Accept") String accptance);

    @GET("searchWithPhone")
    Call<SearchWithId>searchWithPhone(@Query("phone_number") String identity, @Header("Authorization") String s, @Header("Accept") String s1);

    @GET("getClassroomAttendance")
    Call<MobileSearchModel>getAttandance(@Query("class_id") String identity, @Header("Authorization") String s, @Header("Accept") String s1);

    //Get AcadesEscalation
    @GET()
    Call<ModelForAllData> getAccadesData(@Url String url,@Query("page") int page, @Header("Authorization") String s, @Header("Accept") String s1);

   @GET()
   Call<NetEnquiry>getNetEnquiresData(@Url String url,@Query("page") int page, @Header("Authorization") String s, @Header("Accept") String s1);

    @POST("invalidNetEnquiry")
    @FormUrlEncoded
    Call<SuccessModel>markNetEnquiryInValid(@Field("net_enquiry_id") int net_enquiry_id, @Field("comments") String comments,@Header("Authorization") String s, @Header("Accept") String s1);

    @POST("matchNetEnquiryWithMwb")
    @FormUrlEncoded
    Call<SuccessModel>markNetEnquiryWIthMwbID(@Field("identity") String identity, @Field("mobile") String mobile,@Field("mwb_id") int mwb_id,@Field("person_id") int person_id,@Field("person_name") String person_name,@Header("Authorization") String s, @Header("Accept") String s1);

    @POST("addEngagement")
    @FormUrlEncoded
    Call<SuccessModel> AddEngagementForIIML(@Field("level") String level_,@Field("connection_status") String connection_status,@Field("phone_number") String phone_number, @Field("can_id") int can_id, @Field("person_id") int person_id, @Field("person_name") String person_name, @Field("iiml_level") String level, @Field("courses") String courses, @Field("details") String details, @Field("type") String type, @Field("acads") int acads, @Field("next_call") long next_call, @Field("update_call_log") String update_call_log, @Field("mobile_id") int mobile_id,@Field("enrollment")String enrollment, @Field("battery_percentage") String battery_percentage, @Field("battery_status") String battery_status, @Field("version_number") String version_number, @Header("Authorization") String s, @Header("Accept") String s1);

    @GET("getCmFlagPaginationMobile")
    Call<ModelForAllData> getFlaggedData(@Query("page") int page, @Header("Authorization") String s, @Header("Accept") String s1);

    @GET()
    Call<ModelForAllData> getMHPData(@Url String Url,@Query("page") int page, @Header("Authorization") String s, @Header("Accept") String s1);

    @GET("getSpocCity")
    Call<SuccessModel> getSpocCity( @Header("Authorization") String s);

    @GET("getMHPMobileWithoutPagination")
    Call<ModelForAllData> getMHPDataWithOutPagination(@Query("page") int page, @Header("Authorization") String s, @Header("Accept") String s1);


    @POST("editCompany")
    @FormUrlEncoded
    Call<SuccessModel> editCompany(@Field("edited_company") String edited_company,@Field("mwb_id") int mwb_id,@Field("person_id") int person_id,@Header("Authorization") String s);

    @POST("editExperience")
    @FormUrlEncoded
    Call<SuccessModel> editExperiance(@Field("edited_experience") String editExperience,@Field("mwb_id") int mwb_id,@Field("person_id") int person_id,@Header("Authorization") String s);

    @POST("editDesignation")
    @FormUrlEncoded
    Call<SuccessModel> editDesignation(@Field("edited_designation") String editDesignation,@Field("mwb_id") int mwb_id,@Field("person_id") int person_id,@Header("Authorization") String s);

    @POST()
    @FormUrlEncoded
    Call<SuccessModel> inviteToWebinar(@Url String Url,@Field("mwb_id") int mwb_id,@Header("Authorization") String s);

    @POST("removeFromSpamVendor")
    @FormUrlEncoded
    Call<SuccessModel> removeFromSpam(@Field("phone_number") String phone_number,@Header("Authorization") String s);

    @GET
    Call<TokenModel> getAccessTokenFromGoogle(@Url String Url);

    @POST("updateWorkingStatus")
    @FormUrlEncoded
    Call<SuccessModel>updateWorkingStatus(@Field("status") String status,@Header("Authorization") String s);

    @GET("getTeam")
    Call<TeamModel> getTeam(@Header("Authorization") String s);

}
