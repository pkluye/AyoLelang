package com.ags.ayolelang.Models;

public class SpecBarang {
    private int specbarang_id;
    private int specbarang_kategoriid;
    private String specbarang_ukuran;
    private String specbarang_bahan;
    private int specbarang_jmlsisi;
    private String specbarang_laminasi;
    private long specbarang_hargasatuan;
    private String specbarang_satuan;

    public SpecBarang(int specbarang_id, int specbarang_kategoriid, String specbarang_ukuran, String specbarang_bahan, int specbarang_jmlsisi, String specbarang_laminasi, long specbarang_hargasatuan, String specbarang_satuan) {
        this.specbarang_id = specbarang_id;
        this.specbarang_kategoriid = specbarang_kategoriid;
        this.specbarang_ukuran = specbarang_ukuran;
        this.specbarang_bahan = specbarang_bahan;
        this.specbarang_jmlsisi = specbarang_jmlsisi;
        this.specbarang_laminasi = specbarang_laminasi;
        this.specbarang_hargasatuan = specbarang_hargasatuan;
        this.specbarang_satuan = specbarang_satuan;
    }

    public int getSpecbarang_id() {
        return specbarang_id;
    }

    public void setSpecbarang_id(int specbarang_id) {
        this.specbarang_id = specbarang_id;
    }

    public int getSpecbarang_kategoriid() {
        return specbarang_kategoriid;
    }

    public void setSpecbarang_kategoriid(int specbarang_kategoriid) {
        this.specbarang_kategoriid = specbarang_kategoriid;
    }

    public String getSpecbarang_ukuran() {
        return specbarang_ukuran;
    }

    public void setSpecbarang_ukuran(String specbarang_ukuran) {
        this.specbarang_ukuran = specbarang_ukuran;
    }

    public String getSpecbarang_bahan() {
        return specbarang_bahan;
    }

    public void setSpecbarang_bahan(String specbarang_bahan) {
        this.specbarang_bahan = specbarang_bahan;
    }

    public int getSpecbarang_jmlsisi() {
        return specbarang_jmlsisi;
    }

    public void setSpecbarang_jmlsisi(int specbarang_jmlsisi) {
        this.specbarang_jmlsisi = specbarang_jmlsisi;
    }

    public String getSpecbarang_laminasi() {
        return specbarang_laminasi;
    }

    public void setSpecbarang_laminasi(String specbarang_laminasi) {
        this.specbarang_laminasi = specbarang_laminasi;
    }

    public long getSpecbarang_hargasatuan() {
        return specbarang_hargasatuan;
    }

    public void setSpecbarang_hargasatuan(long specbarang_hargasatuan) {
        this.specbarang_hargasatuan = specbarang_hargasatuan;
    }

    public String getSpecbarang_satuan() {
        return specbarang_satuan;
    }

    public void setSpecbarang_satuan(String specbarang_satuan) {
        this.specbarang_satuan = specbarang_satuan;
    }
}
