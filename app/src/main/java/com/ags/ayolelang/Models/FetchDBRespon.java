package com.ags.ayolelang.Models;

public class FetchDBRespon {
    private boolean error;
    private String message;
    private FetchDB data;

    public FetchDBRespon(boolean error, String message, FetchDB data) {
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

    public FetchDB getData() {
        return data;
    }

    public void setData(FetchDB data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "FetchDBRespon{" +
                "error=" + error +
                ", message='" + message + '\'' +
                ", data=" + data.toString() +
                '}';
    }
}
