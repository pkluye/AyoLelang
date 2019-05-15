package com.ags.ayolelang.Models;

public class Kategori {
    private int kategori_id;
    private int kategori_parentid;
    private int kategori_subparentid;
    private String kategori_nama;

    public Kategori() {
    }

    public Kategori(int kategori_id, int kategori_parentid, int kategori_subparentid, String kategori_nama) {
        this.kategori_id = kategori_id;
        this.kategori_parentid = kategori_parentid;
        this.kategori_subparentid = kategori_subparentid;
        this.kategori_nama = kategori_nama;
    }

    public int getKategori_id() {
        return kategori_id;
    }

    public void setKategori_id(int kategori_id) {
        this.kategori_id = kategori_id;
    }

    public int getKategori_parentid() {
        return kategori_parentid;
    }

    public void setKategori_parentid(int kategori_parentid) {
        this.kategori_parentid = kategori_parentid;
    }

    public int getKategori_subparentid() {
        return kategori_subparentid;
    }

    public void setKategori_subparentid(int kategori_subparentid) {
        this.kategori_subparentid = kategori_subparentid;
    }

    public String getKategori_nama() {
        return kategori_nama;
    }

    public void setKategori_nama(String kategori_nama) {
        this.kategori_nama = kategori_nama;
    }

}
