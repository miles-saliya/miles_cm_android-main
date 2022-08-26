package com.milesforce.mwbewb.Model;

public class LevelsModel {
    private String DefinationLevel;
    private String LevelCode;

    public LevelsModel( String levelCode,String definationLevel) {
        DefinationLevel = definationLevel;
        LevelCode = levelCode;
    }

    public LevelsModel() {
    }

    public String getDefinationLevel() {
        return DefinationLevel;
    }

    public void setDefinationLevel(String definationLevel) {
        DefinationLevel = definationLevel;
    }

    public String getLevelCode() {
        return LevelCode;
    }

    public void setLevelCode(String levelCode) {
        LevelCode = levelCode;
    }
}
