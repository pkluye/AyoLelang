package com.ags.ayolelang.Models;

import java.util.ArrayList;

public class FetchDB {
    private ArrayList<Kota> kotas;
    private ArrayList<Provinsi> provinsis;
    private ArrayList<Kategori> kategoris;
    private String token_kategori;
    private String token_provinsi;
    private String token_kota;

    public FetchDB() {
    }

    public FetchDB(ArrayList<Kota> kotas, ArrayList<Provinsi> provinsis, ArrayList<Kategori> kategoris, String token_kategori, String token_provinsi, String token_kota) {
        this.kotas = kotas;
        this.provinsis = provinsis;
        this.kategoris = kategoris;
        this.token_kategori = token_kategori;
        this.token_provinsi = token_provinsi;
        this.token_kota = token_kota;
    }

    public String getToken_kategori() {
        return token_kategori;
    }

    public void setToken_kategori(String token_kategori) {
        this.token_kategori = token_kategori;
    }

    public String getToken_provinsi() {
        return token_provinsi;
    }

    public void setToken_provinsi(String token_provinsi) {
        this.token_provinsi = token_provinsi;
    }

    public String getToken_kota() {
        return token_kota;
    }

    public void setToken_kota(String token_kota) {
        this.token_kota = token_kota;
    }

    public ArrayList<Kategori> getKategoris() {
        return kategoris;
    }

    public void setKategoris(ArrayList<Kategori> kategoris) {
        this.kategoris = kategoris;
    }

    public ArrayList<Kota> getKotas() {
        return kotas;
    }

    public void setKotas(ArrayList<Kota> kotas) {
        this.kotas = kotas;
    }

    public ArrayList<Provinsi> getProvinsis() {
        return provinsis;
    }

    public void setProvinsis(ArrayList<Provinsi> provinsis) {
        this.provinsis = provinsis;
    }

    @Override
    public String toString() {
        return "FetchDB{" +
                "kotas=" + kotas.size() +
                ", provinsis=" + provinsis.size() +
                ", kategoris=" + kategoris.size() +
                ", token_kategori='" + token_kategori + '\'' +
                ", token_provinsi='" + token_provinsi + '\'' +
                ", token_kota='" + token_kota + '\'' +
                '}';
    }
}
