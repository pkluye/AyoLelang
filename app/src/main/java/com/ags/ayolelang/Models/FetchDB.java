package com.ags.ayolelang.Models;

import java.util.ArrayList;

public class FetchDB {
    private ArrayList<Kota> kotas;
    private ArrayList<Provinsi> provinsis;
    private ArrayList<Kategori> kategoris;
    private ArrayList<Lelang> lelangs;
    private ArrayList<Pekerjaan> pekerjaans;
    private ArrayList<User> users;
    private ArrayList<Tawaran> tawarans;
    private ArrayList<Tawaran> historitawarans;
    private String token_kategori;
    private String token_provinsi;
    private String token_kota;
    private String token_lelang;
    private String token_pekerjaan;
    private String token_user;
    private String token_tawaran;

    public FetchDB() {
    }

    public FetchDB(ArrayList<Kota> kotas, ArrayList<Provinsi> provinsis, ArrayList<Kategori> kategoris, ArrayList<Lelang> lelangs, ArrayList<Pekerjaan> pekerjaans, ArrayList<User> users, ArrayList<Tawaran> tawarans, ArrayList<Tawaran> historitawarans, String token_kategori, String token_provinsi, String token_kota, String token_lelang, String token_pekerjaan, String token_user, String token_tawaran) {
        this.kotas = kotas;
        this.provinsis = provinsis;
        this.kategoris = kategoris;
        this.lelangs = lelangs;
        this.pekerjaans = pekerjaans;
        this.users = users;
        this.tawarans = tawarans;
        this.historitawarans = historitawarans;
        this.token_kategori = token_kategori;
        this.token_provinsi = token_provinsi;
        this.token_kota = token_kota;
        this.token_lelang = token_lelang;
        this.token_pekerjaan = token_pekerjaan;
        this.token_user = token_user;
        this.token_tawaran = token_tawaran;
    }

    public String getToken_tawaran() {
        return token_tawaran;
    }

    public void setToken_tawaran(String token_tawaran) {
        this.token_tawaran = token_tawaran;
    }

    public ArrayList<Tawaran> getTawarans() {
        return tawarans;
    }

    public void setTawarans(ArrayList<Tawaran> tawarans) {
        this.tawarans = tawarans;
    }

    public ArrayList<Tawaran> getHistoritawarans() {
        return historitawarans;
    }

    public void setHistoritawarans(ArrayList<Tawaran> historitawarans) {
        this.historitawarans = historitawarans;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public String getToken_user() {
        return token_user;
    }

    public void setToken_user(String token_user) {
        this.token_user = token_user;
    }

    public ArrayList<Pekerjaan> getPekerjaans() {
        return pekerjaans;
    }

    public void setPekerjaans(ArrayList<Pekerjaan> pekerjaans) {
        this.pekerjaans = pekerjaans;
    }

    public String getToken_pekerjaan() {
        return token_pekerjaan;
    }

    public void setToken_pekerjaan(String token_pekerjaan) {
        this.token_pekerjaan = token_pekerjaan;
    }

    public ArrayList<Lelang> getLelangs() {
        return lelangs;
    }

    public void setLelangs(ArrayList<Lelang> lelangs) {
        this.lelangs = lelangs;
    }

    public String getToken_lelang() {
        return token_lelang;
    }

    public void setToken_lelang(String token_lelang) {
        this.token_lelang = token_lelang;
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

}
