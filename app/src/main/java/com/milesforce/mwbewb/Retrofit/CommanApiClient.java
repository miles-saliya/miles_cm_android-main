package com.milesforce.mwbewb.Retrofit;

import com.milesforce.mwbewb.Model.CandidatePersonaDetailsModel;
import com.milesforce.mwbewb.Model.EmailModel;
import com.milesforce.mwbewb.Model.EngagemmentHistoryModel;
import com.milesforce.mwbewb.Model.MobileNumberModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface CommanApiClient {

    @GET("getPersonMobile")
    Call<List<MobileNumberModel>> getCommanClientMobileNumbers(@Query("person_id") int id, @Header("Authorization") String s, @Header("Accept") String s1);

    @GET("getPersonEmail")
    Call<List<EmailModel>> getCommanUserEmails(@Query("person_id") int id, @Header("Authorization") String s, @Header("Accept") String s1);

    @GET("checkCallRecordingExistence")
    Call<SuccessModel> checkCallRecordingExistance(@Query("timestamp") String timestamp, @Query("extension") String strextension, @Header("Authorization") String s, @Header("Accept") String s1);

    @GET("generatePresignedUrl")
    Call<SuccessModel> getGeneratedPresignedUrl(@Query("timestamp") String url, @Query("extension") String strextension, @Header("Authorization") String s, @Header("Accept") String s1);


    ////    https://mfcomms.2x2.ninja/api/getCandidatePersonaDetails?mwb_id=16324
    @GET("getCandidatePersonaDetails")
    Call<CandidatePersonaDetailsModel> getCandidatePersonaDetails(@Query("mwb_id") int mwb_id, @Header("Authorization") String s, @Header("Accept") String s1);

}
