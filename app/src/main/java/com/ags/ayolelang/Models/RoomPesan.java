package com.ags.ayolelang.Models;

public class RoomPesan {
    private int room_id;
    private String room_toppesan;
    private String room_tanggalpesan;
    private String room_user1;
    private String room_user2;

    public RoomPesan(int room_id, String room_toppesan, String room_tanggalpesan, String room_user1, String room_user2) {
        this.room_id = room_id;
        this.room_toppesan = room_toppesan;
        this.room_tanggalpesan = room_tanggalpesan;
        this.room_user1 = room_user1;
        this.room_user2 = room_user2;
    }

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public String getRoom_toppesan() {
        return room_toppesan;
    }

    public void setRoom_toppesan(String room_toppesan) {
        this.room_toppesan = room_toppesan;
    }

    public String getRoom_tanggalpesan() {
        return room_tanggalpesan;
    }

    public void setRoom_tanggalpesan(String room_tanggalpesan) {
        this.room_tanggalpesan = room_tanggalpesan;
    }

    public String getRoom_user1() {
        return room_user1;
    }

    public void setRoom_user1(String room_user1) {
        this.room_user1 = room_user1;
    }

    public String getRoom_user2() {
        return room_user2;
    }

    public void setRoom_user2(String room_user2) {
        this.room_user2 = room_user2;
    }
}
