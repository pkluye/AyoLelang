package com.ags.ayolelang.Models;

import java.util.ArrayList;

public class Lelang {
    private String lelang_deskripsi,lelang_tglmulai,lelang_tglselesai,lelang_judul,lelang_userid,lelang_pembayaran,lelang_alamat,lelang_kotanama,lelang_provnama;
    private int lelang_id,lelang_kota,lelang_status;
    private long lelang_anggaran;
    private ArrayList<Pekerjaan> pekerjaan;

    public Lelang() {
    }

    public Lelang(String lelang_deskripsi, String lelang_tglmulai, String lelang_tglselesai, String lelang_judul, String lelang_userid, String lelang_pembayaran, String lelang_alamat, String lelang_kotanama, String lelang_provnama, int lelang_id, int lelang_kota, int lelang_status, long lelang_anggaran, ArrayList<Pekerjaan> pekerjaan) {
        this.lelang_deskripsi = lelang_deskripsi;
        this.lelang_tglmulai = lelang_tglmulai;
        this.lelang_tglselesai = lelang_tglselesai;
        this.lelang_judul = lelang_judul;
        this.lelang_userid = lelang_userid;
        this.lelang_pembayaran = lelang_pembayaran;
        this.lelang_alamat = lelang_alamat;
        this.lelang_kotanama = lelang_kotanama;
        this.lelang_provnama = lelang_provnama;
        this.lelang_id = lelang_id;
        this.lelang_kota = lelang_kota;
        this.lelang_status = lelang_status;
        this.lelang_anggaran = lelang_anggaran;
        this.pekerjaan = pekerjaan;
    }

    public ArrayList<Pekerjaan> getPekerjaan() {
        return pekerjaan;
    }

    public void setPekerjaan(ArrayList<Pekerjaan> pekerjaan) {
        this.pekerjaan = pekerjaan;
    }

    public int getLelang_status() {
        return lelang_status;
    }

    public void setLelang_status(int lelang_status) {
        this.lelang_status = lelang_status;
    }

    public String getLelang_deskripsi() {
        return lelang_deskripsi;
    }

    public void setLelang_deskripsi(String lelang_deskripsi) {
        this.lelang_deskripsi = lelang_deskripsi;
    }

    public String getLelang_tglmulai() {
        return lelang_tglmulai;
    }

    public void setLelang_tglmulai(String lelang_tglmulai) {
        this.lelang_tglmulai = lelang_tglmulai;
    }

    public String getLelang_tglselesai() {
        return lelang_tglselesai;
    }

    public void setLelang_tglselesai(String lelang_tglselesai) {
        this.lelang_tglselesai = lelang_tglselesai;
    }

    public String getLelang_judul() {
        return lelang_judul;
    }

    public void setLelang_judul(String lelang_judul) {
        this.lelang_judul = lelang_judul;
    }

    public String getLelang_userid() {
        return lelang_userid;
    }

    public void setLelang_userid(String lelang_userid) {
        this.lelang_userid = lelang_userid;
    }

    public String getLelang_pembayaran() {
        return lelang_pembayaran;
    }

    public void setLelang_pembayaran(String lelang_pembayaran) {
        this.lelang_pembayaran = lelang_pembayaran;
    }

    public String getLelang_alamat() {
        return lelang_alamat;
    }

    public void setLelang_alamat(String lelang_alamat) {
        this.lelang_alamat = lelang_alamat;
    }

    public int getLelang_id() {
        return lelang_id;
    }

    public void setLelang_id(int lelang_id) {
        this.lelang_id = lelang_id;
    }

    public int getLelang_kota() {
        return lelang_kota;
    }

    public void setLelang_kota(int lelang_kota) {
        this.lelang_kota = lelang_kota;
    }

    public long getLelang_anggaran() {
        return lelang_anggaran;
    }

    public void setLelang_anggaran(long lelang_anggaran) {
        this.lelang_anggaran = lelang_anggaran;
    }

    public String getLelang_kotanama() {
        return lelang_kotanama;
    }

    public void setLelang_kotanama(String lelang_kotanama) {
        this.lelang_kotanama = lelang_kotanama;
    }

    public String getLelang_provnama() {
        return lelang_provnama;
    }

    public void setLelang_provnama(String lelang_provnama) {
        this.lelang_provnama = lelang_provnama;
    }

    @Override
    public String toString() {
        return "Lelang{" +
                "lelang_deskripsi='" + lelang_deskripsi + '\'' +
                ", lelang_tglmulai='" + lelang_tglmulai + '\'' +
                ", lelang_tglselesai='" + lelang_tglselesai + '\'' +
                ", lelang_judul='" + lelang_judul + '\'' +
                ", lelang_userid='" + lelang_userid + '\'' +
                ", lelang_pembayaran='" + lelang_pembayaran + '\'' +
                ", lelang_alamat='" + lelang_alamat + '\'' +
                ", lelang_kotanama='" + lelang_kotanama + '\'' +
                ", lelang_provnama='" + lelang_provnama + '\'' +
                ", lelang_id=" + lelang_id +
                ", lelang_kota=" + lelang_kota +
                ", lelang_status=" + lelang_status +
                ", lelang_anggaran=" + lelang_anggaran +
                ", pekerjaan=" + pekerjaan +
                '}';
    }
}
