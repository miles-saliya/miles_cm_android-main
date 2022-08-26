package com.milesforce.mwbewb.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TeamModel {
    @SerializedName("team")
    @Expose
    private String team;
    @SerializedName("sub_team")
    @Expose
    private String sub_team;

    public TeamModel(String team, String sub_team) {
        this.team = team;
        this.sub_team = sub_team;
    }

    public TeamModel() {
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getSub_team() {
        return sub_team;
    }

    public void setSub_team(String sub_team) {
        this.sub_team = sub_team;
    }

    @Override
    public String toString() {
        return "TeamModel{" +
                "team='" + team + '\'' +
                ", sub_team='" + sub_team + '\'' +
                '}';
    }
}
