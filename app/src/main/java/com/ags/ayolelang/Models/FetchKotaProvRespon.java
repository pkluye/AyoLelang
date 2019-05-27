package com.ags.ayolelang.Models;

public class FetchKotaProvRespon {
    private boolean error;
    private String message;
    private FetchKotaProv data;

    public FetchKotaProvRespon(boolean error, String message, FetchKotaProv data) {
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

    public FetchKotaProv getData() {
        return data;
    }

    public void setData(FetchKotaProv data) {
        this.data = data;
    }

}
