package com.ags.ayolelang.Models;

public class TWPekerjaan {
    private int twpekerjaan_id, twpekerjaan_tawaranid, twpekerjaan_pekerjaanid;
    private long twpekerjaan_anggaran;

    public TWPekerjaan() {
    }

    public TWPekerjaan(int twpekerjaan_id, int twpekerjaan_tawaranid, int twpekerjaan_pekerjaanid, long twpekerjaan_anggaran) {
        this.twpekerjaan_id = twpekerjaan_id;
        this.twpekerjaan_tawaranid = twpekerjaan_tawaranid;
        this.twpekerjaan_pekerjaanid = twpekerjaan_pekerjaanid;
        this.twpekerjaan_anggaran = twpekerjaan_anggaran;
    }

    public int getTwpekerjaan_id() {
        return twpekerjaan_id;
    }

    public void setTwpekerjaan_id(int twpekerjaan_id) {
        this.twpekerjaan_id = twpekerjaan_id;
    }

    public int getTwpekerjaan_tawaranid() {
        return twpekerjaan_tawaranid;
    }

    public void setTwpekerjaan_tawaranid(int twpekerjaan_tawaranid) {
        this.twpekerjaan_tawaranid = twpekerjaan_tawaranid;
    }

    public int getTwpekerjaan_pekerjaanid() {
        return twpekerjaan_pekerjaanid;
    }

    public void setTwpekerjaan_pekerjaanid(int twpekerjaan_pekerjaanid) {
        this.twpekerjaan_pekerjaanid = twpekerjaan_pekerjaanid;
    }

    public long getTwpekerjaan_anggaran() {
        return twpekerjaan_anggaran;
    }

    public void setTwpekerjaan_anggaran(long twpekerjaan_anggaran) {
        this.twpekerjaan_anggaran = twpekerjaan_anggaran;
    }

    @Override
    public String toString() {
        return "TWPekerjaan{" +
                "twpekerjaan_id=" + twpekerjaan_id +
                ", twpekerjaan_tawaranid=" + twpekerjaan_tawaranid +
                ", twpekerjaan_pekerjaanid=" + twpekerjaan_pekerjaanid +
                ", twpekerjaan_anggaran=" + twpekerjaan_anggaran +
                '}';
    }
}
