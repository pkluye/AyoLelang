package com.ags.ayolelang.Models;

public class Kota {
    private int id,provinsi_id;
    String nama;

    public Kota() {
    }

    public Kota(int id, int provinsi_id, String nama) {
        this.id = id;
        this.provinsi_id = provinsi_id;
        this.nama = nama;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProvinsi_id() {
        return provinsi_id;
    }

    public void setProvinsi_id(int provinsi_id) {
        this.provinsi_id = provinsi_id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    @Override
    public String toString() {
        return "Kota{" +
                "id=" + id +
                ", provinsi_id=" + provinsi_id +
                ", nama='" + nama + '\'' +
                '}';
    }
}
