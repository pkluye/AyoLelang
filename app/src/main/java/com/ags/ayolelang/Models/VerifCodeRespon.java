package com.ags.ayolelang.Models;

public class VerifCodeRespon {
    private boolean error;
    private String message;
    private Verification verification;

    public VerifCodeRespon() {
    }

    public VerifCodeRespon(boolean error, String message, Verification verification) {
        this.error = error;
        this.message = message;
        this.verification = verification;
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

    public Verification getVerification() {
        return verification;
    }

    public void setVerification(Verification verification) {
        this.verification = verification;
    }
}
