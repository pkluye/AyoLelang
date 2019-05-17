package com.ags.ayolelang.Models;

import java.util.ArrayList;

public class KotaResponArray {
    private boolean error;
    private String message;
    private ArrayList<Kota> data;

    public KotaResponArray() {
    }

    public KotaResponArray(boolean error, String message, ArrayList<Kota> data) {
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

    public ArrayList<Kota> getData() {
        return data;
    }

    public void setData(ArrayList<Kota> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "KotaResponArray{" +
                "error=" + error +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
