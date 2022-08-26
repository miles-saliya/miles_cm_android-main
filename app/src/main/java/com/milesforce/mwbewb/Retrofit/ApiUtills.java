package com.milesforce.mwbewb.Retrofit;

public class ApiUtills {
    //public static final String BASEURL = "https://milescmback.2x2.ninja/api/";
    // public static final String BASEURL="http://milesforcedev.2x2.ninja/api/";
   // public static final String BASEURL = "https://mfbackend.mileseducation.com/api/";
    public static final String BASEURL = "https://crm.milesforce.com/api/";

    public static ApiClient getAPIService() {
        return RetrofitClient.getClient(BASEURL).create(ApiClient.class);
    }
}
