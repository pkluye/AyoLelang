package com.ags.ayolelang.Models;

import java.util.ArrayList;

public class KategoriRespon {
    private boolean error;
    private String message;
    private ArrayList<Kategori> kategori;

    public KategoriRespon() {
    }

    public KategoriRespon(boolean error, String message, ArrayList<Kategori> kategori) {
        this.error = error;
        this.message = message;
        this.kategori = kategori;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Kategori> getKategori() {
        return kategori;
    }

    public void setKategori(ArrayList<Kategori> kategori) {
        this.kategori = kategori;
    }
}
