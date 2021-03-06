package com.ags.ayolelang.Models;

import java.io.Serializable;

public class Tawaran implements Serializable {
    private int tawaran_id,tawaran_historiid;
    private int tawaran_lelangid;
    private String tawaran_userid;
    private String tawaran_cdate;
    private long tawaran_anggaran;

    public Tawaran() {
    }

    public Tawaran(int tawaran_id, int tawaran_historiid, int tawaran_lelangid, String tawaran_userid, String tawaran_cdate, long tawaran_anggaran) {
        this.tawaran_id = tawaran_id;
        this.tawaran_historiid = tawaran_historiid;
        this.tawaran_lelangid = tawaran_lelangid;
        this.tawaran_userid = tawaran_userid;
        this.tawaran_cdate = tawaran_cdate;
        this.tawaran_anggaran = tawaran_anggaran;
    }

    public int getTawaran_id() {
        return tawaran_id;
    }

    public void setTawaran_id(int tawaran_id) {
        this.tawaran_id = tawaran_id;
    }

    public int getTawaran_historiid() {
        return tawaran_historiid;
    }

    public void setTawaran_historiid(int tawaran_historiid) {
        this.tawaran_historiid = tawaran_historiid;
    }

    public int getTawaran_lelangid() {
        return tawaran_lelangid;
    }

    public void setTawaran_lelangid(int tawaran_lelangid) {
        this.tawaran_lelangid = tawaran_lelangid;
    }

    public String getTawaran_userid() {
        return tawaran_userid;
    }

    public void setTawaran_userid(String tawaran_userid) {
        this.tawaran_userid = tawaran_userid;
    }

    public long getTawaran_anggaran() {
        return tawaran_anggaran;
    }

    public void setTawaran_anggaran(long tawaran_anggaran) {
        this.tawaran_anggaran = tawaran_anggaran;
    }

    public String getTawaran_cdate() {
        return tawaran_cdate;
    }

    public void setTawaran_cdate(String tawaran_cdate) {
        this.tawaran_cdate = tawaran_cdate;
    }

    @Override
    public String toString() {
        return "Tawaran{" +
                "tawaran_id=" + tawaran_id +
                ", tawaran_historiid=" + tawaran_historiid +
                ", tawaran_lelangid=" + tawaran_lelangid +
                ", tawaran_userid='" + tawaran_userid + '\'' +
                ", tawaran_cdate='" + tawaran_cdate + '\'' +
                ", tawaran_anggaran=" + tawaran_anggaran +
                '}';
    }
}
