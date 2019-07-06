package com.karlosprojects.androidkarlosrestaurant.model;

import java.util.List;

public class FoodModel {
    private boolean success;
    private String message;
    private List<Food> result;

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

    public List<Food> getResult() {
        return result;
    }

    public void setResult(List<Food> result) {
        this.result = result;
    }
}
