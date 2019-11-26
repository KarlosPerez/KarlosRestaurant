package com.karlosprojects.androidkarlosrestaurant.model;

import java.util.List;

public class AddonModel {
    private boolean success;
    private String message;
    private List<Addon> reesult;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Addon> getReesult() {
        return reesult;
    }

    public void setReesult(List<Addon> reesult) {
        this.reesult = reesult;
    }
}
