package com.ags.ayolelang.Models;

import java.util.ArrayList;

public class UserRespon {
    private boolean error;
    private String message;
    private User data;

    public UserRespon() {
    }

    public UserRespon(boolean error, String message, User data) {
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

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }
}
