package com.milesforce.mwbewb.Model;

import io.realm.RealmObject;

public class UserToken extends RealmObject {
    private String accessToken;

    public UserToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public UserToken() {
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
