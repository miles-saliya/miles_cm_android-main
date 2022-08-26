package com.milesforce.mwbewb.Utils;

public class BatteryModel {
    private String battey_percentage;
    private String charging_status;

    public BatteryModel(String battey_percentage, String charging_status) {
        this.battey_percentage = battey_percentage;
        this.charging_status = charging_status;
    }

    public BatteryModel() {
    }

    public String getBattey_percentage() {
        return battey_percentage;
    }

    public void setBattey_percentage(String battey_percentage) {
        this.battey_percentage = battey_percentage;
    }

    public String getCharging_status() {
        return charging_status;
    }

    public void setCharging_status(String charging_status) {
        this.charging_status = charging_status;
    }

    @Override
    public String toString() {
        return "BatteryModel{" +
                "battey_percentage='" + battey_percentage + '\'' +
                ", charging_status='" + charging_status + '\'' +
                '}';
    }
}
