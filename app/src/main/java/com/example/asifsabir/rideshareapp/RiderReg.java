package com.example.asifsabir.rideshareapp;

/**
 * Created by asifsabir on 1/22/18.
 */

public class RiderReg {
    public String fullName;
    public String password;
    public String mobile;
    public String nid;
    public String lat;
    public String lon;

    public RiderReg() {
        // Default constructor required for calls to DataSnapshot.getValue(Article.class)
    }

    public RiderReg(String fullName,String password,
                     String mobile, String nid,
                     String lat, String lon) {
        this.fullName = fullName;
        this.password = password;
        this.mobile = mobile;
        this.nid = nid;
        this.lat = lat;
        this.lon = lon;
    }
}

