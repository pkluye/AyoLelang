package com.ags.ayolelang.Models;

public class KotaProvinsi {
    private String kota;
    private String provinsi;

    public KotaProvinsi() {
    }

    public KotaProvinsi(String kota, String provinsi) {
        this.kota = kota;
        this.provinsi = provinsi;
    }

    public String getKota() {
        return kota;
    }

    public void setKota(String kota) {
        this.kota = kota;
    }

    public String getProvinsi() {
        return provinsi;
    }

    public void setProvinsi(String provinsi) {
        this.provinsi = provinsi;
    }

    @Override
    public String toString() {
        return "KotaProvinsi{" +
                "kota='" + kota + '\'' +
                ", provinsi='" + provinsi + '\'' +
                '}';
    }
}
