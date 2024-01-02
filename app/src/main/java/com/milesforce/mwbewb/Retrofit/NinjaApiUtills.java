package com.milesforce.mwbewb.Retrofit;

public class NinjaApiUtills {

//    public static final String BASEURL = "https://mfcomms.2x2.ninja/api/";
    public static final String BASEURL = "https://milescrmdev.2x2.ninja/api/";

    public static NinjaApiClient getAPIService() {
        return NinjaRetrofitClient.getNinjaClient(BASEURL).create(NinjaApiClient.class);


    }
}
