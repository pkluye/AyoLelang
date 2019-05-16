package com.ags.ayolelang.Models;

import java.util.ArrayList;

public class KategoriResponArray {
    private boolean error;
    private String message;
    private ArrayList<Kategori> data;

    public KategoriResponArray() {
    }

    public KategoriResponArray(boolean error, String message, ArrayList<Kategori> data) {
        this.error = error;
        this.message = message;
        this.data = data;
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

    public ArrayList<Kategori> getData() {
        return data;
    }

    public void setData(ArrayList<Kategori> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "KategoriResponArray{" +
                "error=" + error +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
