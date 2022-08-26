package com.milesforce.mwbewb.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TokenDataModel {
    @SerializedName("status_code")
    @Expose
    private int status_code;

    @SerializedName("response")
    @Expose
    private TokenModel response;

    public TokenDataModel(int status_code, TokenModel response) {
        this.status_code = status_code;
        this.response = response;
    }

    public TokenDataModel() {
    }

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public TokenModel getResponse() {
        return response;
    }

    public void setResponse(TokenModel response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "TokenDataModel{" +
                "status_code=" + status_code +
                ", response=" + response +
                '}';
    }
}
