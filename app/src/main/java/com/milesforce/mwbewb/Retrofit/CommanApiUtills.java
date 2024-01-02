package com.milesforce.mwbewb.Retrofit;

public class CommanApiUtills {

    public static final String BASEURL = "https://mfcomms.2x2.ninja/api/";

    public static CommanApiClient getAPIService() {
        return CommanRetrofitClient.getCommanClient(BASEURL).create(CommanApiClient.class);


    }
}




