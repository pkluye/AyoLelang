package com.ags.ayolelang.Models;

public class SingleRoomRespon {
    private boolean error;
    private String message;
    private RoomPesan data;

    public SingleRoomRespon(boolean error, String message, RoomPesan data) {
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

    public RoomPesan getData() {
        return data;
    }

    public void setData(RoomPesan data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RoomRespon{" +
                "error=" + error +
                ", message='" + message + '\'' +
                ", data=" + data.toString() +
                '}';
    }
}
