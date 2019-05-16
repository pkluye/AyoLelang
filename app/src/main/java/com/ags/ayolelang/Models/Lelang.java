package com.ags.ayolelang.Models;

public class Lelang {
    private String lelang_deskripsi,lelang_tglmulai,lelang_tglselesai,lelang_judul,lelang_userid,lelang_pembayaran,lelang_alamat;
    private int lelang_id,lelang_kota;
    private long lelang_totalharga;

    public Lelang() {
    }

    public Lelang(String lelang_deskripsi, String lelang_tglmulai, String lelang_tglselesai, String lelang_judul, String lelang_userid, String lelang_pembayaran, String lelang_alamat, int lelang_id, int lelang_kota, long lelang_totalharga) {
        this.lelang_deskripsi = lelang_deskripsi;
        this.lelang_tglmulai = lelang_tglmulai;
        this.lelang_tglselesai = lelang_tglselesai;
        this.lelang_judul = lelang_judul;
        this.lelang_userid = lelang_userid;
        this.lelang_pembayaran = lelang_pembayaran;
        this.lelang_alamat = lelang_alamat;
        this.lelang_id = lelang_id;
        this.lelang_kota = lelang_kota;
        this.lelang_totalharga = lelang_totalharga;
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

    public long getLelang_totalharga() {
        return lelang_totalharga;
    }

    public void setLelang_totalharga(long lelang_totalharga) {
        this.lelang_totalharga = lelang_totalharga;
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
                ", lelang_id=" + lelang_id +
                ", lelang_kota=" + lelang_kota +
                ", lelang_totalharga=" + lelang_totalharga +
                '}';
    }
}
