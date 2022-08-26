package com.milesforce.mwbewb.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TokenModel {
    @SerializedName("token_type")
    @Expose
    String token_type;

    @SerializedName("expires_in")
    @Expose
    String expires_in;

    @SerializedName("access_token")
    @Expose
    String access_token;
    @SerializedName("refresh_token")
    @Expose
    String refresh_token;

    public TokenModel(String token_type, String expires_in, String access_token, String refresh_token) {
        this.token_type = token_type;
        this.expires_in = expires_in;
        this.access_token = access_token;
        this.refresh_token = refresh_token;
    }

    public TokenModel() {
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(String expires_in) {
        this.expires_in = expires_in;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    @Override
    public String toString() {
        return "TokenModel{" +
                "token_type='" + token_type + '\'' +
                ", expires_in='" + expires_in + '\'' +
                ", access_token='" + access_token + '\'' +
                ", refresh_token='" + refresh_token + '\'' +
                '}';
    }
}
