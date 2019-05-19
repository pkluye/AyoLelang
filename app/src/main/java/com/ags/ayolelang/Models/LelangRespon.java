package com.ags.ayolelang.Models;

public class LelangRespon {
    private boolean error;
    private String message;
    private Lelang data;

    public LelangRespon() {
    }

    public LelangRespon(boolean error, String message, Lelang data) {
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

    public Lelang getData() {
        return data;
    }

    public void setData(Lelang data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "LelangRespon{" +
                "error=" + error +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
