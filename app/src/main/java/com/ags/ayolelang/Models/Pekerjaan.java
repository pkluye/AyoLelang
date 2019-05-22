package com.ags.ayolelang.Models;

public class Pekerjaan {
    private String pekerjaan_ukuran, pekerjaan_bahan, pekerjaan_catatan, pekerjaan_fileurl,pekerjaan_kategorinama;
    private int pekerjaan_id, pekerjaan_lelangid,pekerjaan_jumlah,pekerjaan_kategoriid;
    private long pekerjaan_harga;
    private boolean pekerjaan_status;

    public Pekerjaan() {
    }

    public Pekerjaan(String pekerjaan_ukuran, String pekerjaan_bahan, String pekerjaan_catatan, String pekerjaan_fileurl, String pekerjaan_kategorinama, int pekerjaan_id, int pekerjaan_lelangid, int pekerjaan_jumlah, int pekerjaan_kategoriid, long pekerjaan_harga, boolean pekerjaan_status) {
        this.pekerjaan_ukuran = pekerjaan_ukuran;
        this.pekerjaan_bahan = pekerjaan_bahan;
        this.pekerjaan_catatan = pekerjaan_catatan;
        this.pekerjaan_fileurl = pekerjaan_fileurl;
        this.pekerjaan_kategorinama = pekerjaan_kategorinama;
        this.pekerjaan_id = pekerjaan_id;
        this.pekerjaan_lelangid = pekerjaan_lelangid;
        this.pekerjaan_jumlah = pekerjaan_jumlah;
        this.pekerjaan_kategoriid = pekerjaan_kategoriid;
        this.pekerjaan_harga = pekerjaan_harga;
        this.pekerjaan_status = pekerjaan_status;
    }

    public String getPekerjaan_kategorinama() {
        return pekerjaan_kategorinama;
    }

    public void setPekerjaan_kategorinama(String pekerjaan_kategorinama) {
        this.pekerjaan_kategorinama = pekerjaan_kategorinama;
    }

    public boolean isPekerjaan_status() {
        return pekerjaan_status;
    }

    public void setPekerjaan_status(boolean pekerjaan_status) {
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

    public String getPekerjaan_fileurl() {
        return pekerjaan_fileurl;
    }

    public void setPekerjaan_fileurl(String pekerjaan_fileurl) {
        this.pekerjaan_fileurl = pekerjaan_fileurl;
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
                ", pekerjaan_fileurl='" + pekerjaan_fileurl + '\'' +
                ", pekerjaan_kategorinama='" + pekerjaan_kategorinama + '\'' +
                ", pekerjaan_id=" + pekerjaan_id +
                ", pekerjaan_lelangid=" + pekerjaan_lelangid +
                ", pekerjaan_jumlah=" + pekerjaan_jumlah +
                ", pekerjaan_kategoriid=" + pekerjaan_kategoriid +
                ", pekerjaan_harga=" + pekerjaan_harga +
                ", pekerjaan_status=" + pekerjaan_status +
                '}';
    }
}
