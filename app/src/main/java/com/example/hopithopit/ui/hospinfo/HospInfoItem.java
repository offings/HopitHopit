package com.example.hopithopit.ui.hospinfo;

public class HospInfoItem {
    private String hospName, hospTel, hospAdr, hospDgid, hospSpecialist, hospTime;

    public HospInfoItem(String hospName, String hospTel, String hospAdr, String hospDgid, String hospSpecialist, String hospTime) {
        this.hospName = hospName;
        this.hospTel = hospTel;
        this.hospAdr = hospAdr;
        this.hospDgid = hospDgid;
        this.hospSpecialist = hospSpecialist;
        this.hospTime = hospTime;
    }

    public String getHospName() {
        return hospName;
    }

    public void setHospName(String hospName) {
        this.hospName = hospName;
    }

    public String getHospTel() {
        return hospTel;
    }

    public void setHospTel(String hospTel) {
        this.hospTel = hospTel;
    }

    public String getHospAdr() {
        return hospAdr;
    }

    public void setHospAdr(String hospAdr) {
        this.hospAdr = hospAdr;
    }

    public String getHospDgid() {
        return hospDgid;
    }

    public void setHospDgid(String hospDgid) {
        this.hospDgid = hospDgid;
    }

    public String getHospSpecialist() {
        return hospSpecialist;
    }

    public void setHospSpecialist(String hospSpecialist) {
        this.hospSpecialist = hospSpecialist;
    }

    public String getHospTime() {
        return hospTime;
    }

    public void setHospTime(String hospTime) {
        this.hospTime = hospTime;
    }
}
