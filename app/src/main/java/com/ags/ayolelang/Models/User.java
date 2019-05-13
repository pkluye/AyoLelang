package com.ags.ayolelang.Models;

public class User {

    String user_id,user_nama, user_email, user_telpon, user_alamat, user_imgurl;
    boolean user_verif;

    public User() {
    }

    public User(String user_id, String user_nama, String user_email, String user_telpon, String user_alamat, String user_imgurl, boolean user_verif) {
        this.user_id = user_id;
        this.user_nama = user_nama;
        this.user_email = user_email;
        this.user_telpon = user_telpon;
        this.user_alamat = user_alamat;
        this.user_imgurl = user_imgurl;
        this.user_verif = user_verif;
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

    public boolean isUser_verif() {
        return user_verif;
    }

    public void setUser_verif(boolean user_verif) {
        this.user_verif = user_verif;
    }
}
