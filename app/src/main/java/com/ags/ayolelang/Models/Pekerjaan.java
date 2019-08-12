package com.ags.ayolelang.Models;

import java.io.Serializable;

public class Pekerjaan implements Serializable {
    private String pekerjaan_ukuran, pekerjaan_bahan, pekerjaan_catatan,pekerjaan_jmlsisi,pekerjaan_laminasi;
    private int pekerjaan_id, pekerjaan_lelangid,pekerjaan_jumlah,pekerjaan_kategoriid;
    private long pekerjaan_harga,hargaTawaran;
    private int pekerjaan_status;

    public Pekerjaan() {
    }

    public Pekerjaan(String pekerjaan_ukuran, String pekerjaan_bahan, String pekerjaan_catatan, int pekerjaan_jumlah, int pekerjaan_kategoriid, long pekerjaan_harga,String pekerjaan_jmlsisi, String pekerjaan_laminasi) {
        this.pekerjaan_ukuran = pekerjaan_ukuran;
        this.pekerjaan_bahan = pekerjaan_bahan;
        this.pekerjaan_catatan = pekerjaan_catatan;
        this.pekerjaan_jumlah = pekerjaan_jumlah;
        this.pekerjaan_kategoriid = pekerjaan_kategoriid;
        this.pekerjaan_harga = pekerjaan_harga;
        this.pekerjaan_jmlsisi = pekerjaan_jmlsisi;
        this.pekerjaan_laminasi = pekerjaan_laminasi;
    }

    public Pekerjaan(String pekerjaan_ukuran, String pekerjaan_bahan, String pekerjaan_catatan, int pekerjaan_id, int pekerjaan_jumlah, int pekerjaan_kategoriid, long pekerjaan_harga,String pekerjaan_jmlsisi, String pekerjaan_laminasi) {
        this.pekerjaan_ukuran = pekerjaan_ukuran;
        this.pekerjaan_bahan = pekerjaan_bahan;
        this.pekerjaan_catatan = pekerjaan_catatan;
        this.pekerjaan_id = pekerjaan_id;
        this.pekerjaan_jumlah = pekerjaan_jumlah;
        this.pekerjaan_kategoriid = pekerjaan_kategoriid;
        this.pekerjaan_harga = pekerjaan_harga;
        this.pekerjaan_jmlsisi = pekerjaan_jmlsisi;
        this.pekerjaan_laminasi = pekerjaan_laminasi;
    }

    public Pekerjaan(String pekerjaan_ukuran, String pekerjaan_bahan, String pekerjaan_catatan, int pekerjaan_id, int pekerjaan_lelangid, int pekerjaan_jumlah, int pekerjaan_kategoriid, long pekerjaan_harga, int pekerjaan_status) {
        this.pekerjaan_ukuran = pekerjaan_ukuran;
        this.pekerjaan_bahan = pekerjaan_bahan;
        this.pekerjaan_catatan = pekerjaan_catatan;
        this.pekerjaan_id = pekerjaan_id;
        this.pekerjaan_lelangid = pekerjaan_lelangid;
        this.pekerjaan_jumlah = pekerjaan_jumlah;
        this.pekerjaan_kategoriid = pekerjaan_kategoriid;
        this.pekerjaan_harga = pekerjaan_harga;
        this.pekerjaan_status = pekerjaan_status;
    }

    public Pekerjaan(String pekerjaan_ukuran, String pekerjaan_bahan, String pekerjaan_catatan, String pekerjaan_jmlsisi, String pekerjaan_laminasi, int pekerjaan_id, int pekerjaan_lelangid, int pekerjaan_jumlah, int pekerjaan_kategoriid, long pekerjaan_harga, int pekerjaan_status) {
        this.pekerjaan_ukuran = pekerjaan_ukuran;
        this.pekerjaan_bahan = pekerjaan_bahan;
        this.pekerjaan_catatan = pekerjaan_catatan;
        this.pekerjaan_jmlsisi = pekerjaan_jmlsisi;
        this.pekerjaan_laminasi = pekerjaan_laminasi;
        this.pekerjaan_id = pekerjaan_id;
        this.pekerjaan_lelangid = pekerjaan_lelangid;
        this.pekerjaan_jumlah = pekerjaan_jumlah;
        this.pekerjaan_kategoriid = pekerjaan_kategoriid;
        this.pekerjaan_harga = pekerjaan_harga;
        this.pekerjaan_status = pekerjaan_status;
    }

    public long getHargaTawaran() {
        return hargaTawaran;
    }

    public void setHargaTawaran(long hargaTawaran) {
        this.hargaTawaran = hargaTawaran;
    }

    public String getPekerjaan_jmlsisi() {
        return pekerjaan_jmlsisi;
    }

    public void setPekerjaan_jmlsisi(String pekerjaan_jmlsisi) {
        this.pekerjaan_jmlsisi = pekerjaan_jmlsisi;
    }

    public String getPekerjaan_laminasi() {
        return pekerjaan_laminasi;
    }

    public void setPekerjaan_laminasi(String pekerjaan_laminasi) {
        this.pekerjaan_laminasi = pekerjaan_laminasi;
    }

    public int getPekerjaan_status() {
        return pekerjaan_status;
    }

    public void setPekerjaan_status(int pekerjaan_status) {
        this.pekerjaan_status = pekerjaan_status;
    }

    public int getPekerjaan_kategoriid() {
        return pekerjaan_kategoriid;
    }

    public void setPekerjaan_kategoriid(int pekerjaan_kategoriid) {
        this.pekerjaan_kategoriid = pekerjaan_kategoriid;
    }

    public String getPekerjaan_ukuran() {
        return pekerjaan_ukuran;
    }

    public void setPekerjaan_ukuran(String pekerjaan_ukuran) {
        this.pekerjaan_ukuran = pekerjaan_ukuran;
    }

    public String getPekerjaan_bahan() {
        return pekerjaan_bahan;
    }

    public void setPekerjaan_bahan(String pekerjaan_bahan) {
        this.pekerjaan_bahan = pekerjaan_bahan;
    }

    public String getPekerjaan_catatan() {
        return pekerjaan_catatan;
    }

    public void setPekerjaan_catatan(String pekerjaan_catatan) {
        this.pekerjaan_catatan = pekerjaan_catatan;
    }

    public int getPekerjaan_id() {
        return pekerjaan_id;
    }

    public void setPekerjaan_id(int pekerjaan_id) {
        this.pekerjaan_id = pekerjaan_id;
    }

    public int getPekerjaan_lelangid() {
        return pekerjaan_lelangid;
    }

    public void setPekerjaan_lelangid(int pekerjaan_lelangid) {
        this.pekerjaan_lelangid = pekerjaan_lelangid;
    }

    public int getPekerjaan_jumlah() {
        return pekerjaan_jumlah;
    }

    public void setPekerjaan_jumlah(int pekerjaan_jumlah) {
        this.pekerjaan_jumlah = pekerjaan_jumlah;
    }

    public long getPekerjaan_harga() {
        return pekerjaan_harga;
    }

    public void setPekerjaan_harga(long pekerjaan_harga) {
        this.pekerjaan_harga = pekerjaan_harga;
    }

    @Override
    public String toString() {
        return "Pekerjaan{" +
                "pekerjaan_ukuran='" + pekerjaan_ukuran + '\'' +
                ", pekerjaan_bahan='" + pekerjaan_bahan + '\'' +
                ", pekerjaan_catatan='" + pekerjaan_catatan + '\'' +
                ", pekerjaan_jmlsisi='" + pekerjaan_jmlsisi + '\'' +
                ", pekerjaan_laminasi='" + pekerjaan_laminasi + '\'' +
                ", pekerjaan_id=" + pekerjaan_id +
                ", pekerjaan_lelangid=" + pekerjaan_lelangid +
                ", pekerjaan_jumlah=" + pekerjaan_jumlah +
                ", pekerjaan_kategoriid=" + pekerjaan_kategoriid +
                ", pekerjaan_harga=" + pekerjaan_harga +
                ", pekerjaan_status=" + pekerjaan_status +
                '}';
    }
}
