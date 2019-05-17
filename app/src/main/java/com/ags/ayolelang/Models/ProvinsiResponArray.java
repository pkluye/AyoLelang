package com.ags.ayolelang.Models;

import java.util.ArrayList;

public class ProvinsiResponArray {
    private boolean error;
    private String message;
    private ArrayList<Provinsi> data;

    public ProvinsiResponArray() {
    }

    public ProvinsiResponArray(boolean error, String message, ArrayList<Provinsi> data) {
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

    public ArrayList<Provinsi> getData() {
        return data;
    }

    public void setData(ArrayList<Provinsi> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ProvinsiResponArray{" +
                "error=" + error +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
