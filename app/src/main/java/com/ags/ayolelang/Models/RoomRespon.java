package com.ags.ayolelang.Models;

import java.util.ArrayList;

public class RoomRespon {
    private boolean error;
    private String message;
    private ArrayList<RoomPesan> data;

    public RoomRespon(boolean error, String message, ArrayList<RoomPesan> data) {
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

    public ArrayList<RoomPesan> getData() {
        return data;
    }

    public void setData(ArrayList<RoomPesan> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RoomRespon{" +
                "error=" + error +
                ", message='" + message + '\'' +
                ", data=" + data.size() +
                '}';
    }
}
