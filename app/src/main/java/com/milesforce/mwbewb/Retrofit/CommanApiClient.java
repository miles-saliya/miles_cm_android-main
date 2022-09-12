package com.milesforce.mwbewb.Retrofit;

import com.milesforce.mwbewb.Model.EmailModel;
import com.milesforce.mwbewb.Model.MobileNumberModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface CommanApiClient {


    /*Getting Client mobile number request Api*/
    @GET("getPersonMobile")
    Call<List<MobileNumberModel>> getCommanClientMobileNumbers(@Query("person_id") int id, @Header("Authorization") String s);
    /*Getting Client Emails request Api*/

    @GET("getPersonEmail")
    Call<List<EmailModel>> getCommanUserEmails(@Query("person_id") int id, @Header("Authorization") String s);


}
