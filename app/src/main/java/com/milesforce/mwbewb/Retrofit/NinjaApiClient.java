package com.milesforce.mwbewb.Retrofit;

import com.milesforce.mwbewb.Model.ULevelModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface NinjaApiClient {

    @GET("getMLevels")
     Call<List<String>> getMLevels(@Header("Authorization") String s, @Header("Accept") String s1);

//    @GET("getULevels")
//    Call<List<String>> getULevels( @Header("Authorization") String s, @Header("Accept") String s1);

//    ULevelModel
    @GET("getULevels")
    Call<List<ULevelModel>> getULevels(@Header("Authorization") String s, @Header("Accept") String s1);

}
