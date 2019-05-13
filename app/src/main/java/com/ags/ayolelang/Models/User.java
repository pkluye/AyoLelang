package com.ags.ayolelang.Models;

public class User {

    String username,email,phone,address,photo;
    boolean status;

    public User() {
    }

    public User(String username, String email, String phone, String address, String photo, boolean status) {
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.photo = photo;
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
