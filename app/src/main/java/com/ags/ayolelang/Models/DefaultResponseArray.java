package com.ags.ayolelang.Models;

import java.util.ArrayList;

public class DefaultResponseArray {
    private boolean error;
    private String message;
    private ArrayList<Object> data;

    public DefaultResponseArray() {
    }

    public DefaultResponseArray(boolean error, String message, ArrayList<Object> data) {
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

    public ArrayList<Object> getData() {
        return data;
    }

    public void setData(ArrayList<Object> data) {
        this.data = data;
    }
}
