package com.ags.ayolelang.Models;

public class Pesan implements InterfacePesan {

    int pesan_id;
    int pesan_roomid;
    String pesan_isi;
    String pesan_userid;
    String pesan_cdate;

    public Pesan(String pesan_isi, String pesan_userid) {
        this.pesan_isi = pesan_isi;
        this.pesan_userid = pesan_userid;
    }

    public Pesan(int pesan_id, int pesan_roomid, String pesan_isi, String pesan_userid, String pesan_cdate) {
        this.pesan_id = pesan_id;
        this.pesan_roomid = pesan_roomid;
        this.pesan_isi = pesan_isi;
        this.pesan_userid = pesan_userid;
        this.pesan_cdate = pesan_cdate;
    }

    public void setPesan_id(int pesan_id) {
        this.pesan_id = pesan_id;
    }

    public void setPesan_roomid(int pesan_roomid) {
        this.pesan_roomid = pesan_roomid;
    }

    public void setPesan_isi(String pesan_isi) {
        this.pesan_isi = pesan_isi;
    }

    public void setPesan_userid(String pesan_userid) {
        this.pesan_userid = pesan_userid;
    }

    public void setPesan_cdate(String pesan_cdate) {
        this.pesan_cdate = pesan_cdate;
    }

    public String getPesan_userid() {
        return pesan_userid;
    }

    public String getPesan_cdate() {
        return pesan_cdate;
    }

    public int getPesan_id() {
        return pesan_id;
    }

    public int getPesan_roomid() {
        return pesan_roomid;
    }

    @Override
    public boolean isHeader() {
        return false;
    }

    @Override
    public String getTanggal() {
        return this.pesan_cdate;
    }

    @Override
    public String getPesan_isi() {
        return this.pesan_isi;
    }

    @Override
    public String getPengirim() {
        return this.pesan_userid;
    }

    @Override
    public String toString() {
        return "Pesan{" +
                "pesan_id=" + pesan_id +
                ", pesan_roomid=" + pesan_roomid +
                ", pesan_isi='" + pesan_isi + '\'' +
                ", pesan_userid='" + pesan_userid + '\'' +
                ", pesan_cdate='" + pesan_cdate + '\'' +
                '}';
    }
}
