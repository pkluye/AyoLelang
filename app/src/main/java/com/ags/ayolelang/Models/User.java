package com.ags.ayolelang.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class User implements Serializable {

    private String user_id,user_nama, user_email, user_telpon, user_alamat, user_imgurl, user_skill,user_tentang;
    private boolean user_status;

    public User() {
    }

    public User(String user_id, String user_nama, String user_email, String user_telpon, String user_alamat, String user_imgurl, boolean user_status) {
        this.user_id = user_id;
        this.user_nama = user_nama;
        this.user_email = user_email;
        this.user_telpon = user_telpon;
        this.user_alamat = user_alamat;
        this.user_imgurl = user_imgurl;
        this.user_status = user_status;
    }

    public User(String user_id, String user_nama, String user_email, String user_telpon, String user_alamat, String user_imgurl, String user_skill, String user_tentang, boolean user_status) {
        this.user_id = user_id;
        this.user_nama = user_nama;
        this.user_email = user_email;
        this.user_telpon = user_telpon;
        this.user_alamat = user_alamat;
        this.user_imgurl = user_imgurl;
        this.user_skill = user_skill;
        this.user_tentang = user_tentang;
        this.user_status = user_status;
    }

    public User(String user_id, String user_nama, String user_email, String user_telpon, String user_alamat, String user_imgurl, String user_skill, String user_tentang) {
        this.user_id = user_id;
        this.user_nama = user_nama;
        this.user_email = user_email;
        this.user_telpon = user_telpon;
        this.user_alamat = user_alamat;
        this.user_imgurl = user_imgurl;
        this.user_skill = user_skill;
        this.user_tentang = user_tentang;
    }

    public String getUser_tentang() {
        return user_tentang;
    }

    public void setUser_tentang(String user_tentang) {
        this.user_tentang = user_tentang;
    }

    public String getUser_skill() {
        return user_skill;
    }

    public void setUser_skill(String user_skill) {
        this.user_skill = user_skill;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_nama() {
        return user_nama;
    }

    public void setUser_nama(String user_nama) {
        this.user_nama = user_nama;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_telpon() {
        return user_telpon;
    }

    public void setUser_telpon(String user_telpon) {
        this.user_telpon = user_telpon;
    }

    public String getUser_alamat() {
        return user_alamat;
    }

    public void setUser_alamat(String user_alamat) {
        this.user_alamat = user_alamat;
    }

    public String getUser_imgurl() {
        return user_imgurl;
    }

    public void setUser_imgurl(String user_imgurl) {
        this.user_imgurl = user_imgurl;
    }

    public boolean isUser_status() {
        return user_status;
    }

    public void setUser_status(boolean user_status) {
        this.user_status = user_status;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id='" + user_id + '\'' +
                ", user_nama='" + user_nama + '\'' +
                ", user_email='" + user_email + '\'' +
                ", user_telpon='" + user_telpon + '\'' +
                ", user_alamat='" + user_alamat + '\'' +
                ", user_imgurl='" + user_imgurl + '\'' +
                ", user_skill='" + user_skill + '\'' +
                ", user_status=" + user_status +
                '}';
    }
}
