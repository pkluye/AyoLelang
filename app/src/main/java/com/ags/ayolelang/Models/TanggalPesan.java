package com.ags.ayolelang.Models;

public class TanggalPesan implements InterfacePesan {

    String Tanggal;

    public TanggalPesan(String tanggal) {
        Tanggal = tanggal;
    }

    public void setTanggal(String tanggal) {
        Tanggal = tanggal;
    }

    @Override
    public boolean isHeader() {
        return true;
    }

    @Override
    public String getTanggal() {
        return this.Tanggal;
    }

    @Override
    public String getPesan_isi() {
        return null;
    }

    @Override
    public String getPengirim() {
        return null;
    }
}
