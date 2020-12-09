package com.example.hopithopit.ui.hospinfo;

public class HospInfoItem {
    private String hospName, hospAdr, hospTel1, hospTel3, hospTime;
    private float distance;

    public HospInfoItem(String hospName, String hospAdr, String hospTel1, String hospTel3, String hospTime, float distance) {
        this.hospName = hospName;
        this.hospAdr = hospAdr;
        this.hospTel1 = hospTel1;
        this.hospTel3 = hospTel3;
        this.hospTime = hospTime;
        this.distance = distance;
    }

    public String getHospName() {
        return hospName;
    }

    public void setHospName(String hospName) {
        this.hospName = hospName;
    }

    public String getHospAdr() {
        return hospAdr;
    }

    public void setHospAdr(String hospAdr) {
        this.hospAdr = hospAdr;
    }

    public String getHospTel1() {
        return hospTel1;
    }

    public void setHospTel1(String hospTel1) {
        this.hospTel1 = hospTel1;
    }

    public String getHospTel3() {
        return hospTel3;
    }

    public void setHospTel3(String hospTel3) {
        this.hospTel3 = hospTel3;
    }

    public String getHospTime() {
        return hospTime;
    }

    public void setHospTime(String hospTime) {
        this.hospTime = hospTime;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }
}
