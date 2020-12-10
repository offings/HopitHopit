package com.example.hopithopit.ui.search;

public class HospItem {

    private String hospName, hospAdr, hospTel1;
    private double lat, lon;

    public HospItem(String hospName, String hospAdr, String hospTel1, double lat, double lon) {
        this.hospName = hospName;
        this.hospAdr = hospAdr;
        this.hospTel1 = hospTel1;
        this.lat = lat;
        this.lon = lon;
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

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}
