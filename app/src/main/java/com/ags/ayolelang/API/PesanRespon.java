package com.ags.ayolelang.API;

import com.ags.ayolelang.Models.Pesan;

import java.util.ArrayList;

public class PesanRespon {
    private boolean error;
    private String message;
    private ArrayList<Pesan> data;

    public PesanRespon(boolean error, String message, ArrayList<Pesan> data) {
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

    public ArrayList<Pesan> getData() {
        return data;
    }

    public void setData(ArrayList<Pesan> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "PesanRespon{" +
                "error=" + error +
                ", message='" + message + '\'' +
                ", data=" + data.size() +
                '}';
    }
}
