package com.ags.ayolelang.Models;

public class Verification {
    private int id_verification;
    private String username,type,code;

    public Verification() {
    }

    public Verification(int id_verification, String username, String type, String code) {
        this.id_verification = id_verification;
        this.username = username;
        this.type = type;
        this.code = code;
    }

    public int getId_verification() {
        return id_verification;
    }

    public void setId_verification(int id_verification) {
        this.id_verification = id_verification;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
