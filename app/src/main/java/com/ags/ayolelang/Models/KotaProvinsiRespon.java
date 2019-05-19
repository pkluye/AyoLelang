package com.ags.ayolelang.Models;

public class KotaProvinsiRespon {
    private boolean error;
    private String message;
    private KotaProvinsi data;

    public KotaProvinsiRespon(boolean error, String message, KotaProvinsi data) {
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

    public KotaProvinsi getData() {
        return data;
    }

    public void setData(KotaProvinsi data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "KotaProvinsiRespon{" +
                "error=" + error +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
