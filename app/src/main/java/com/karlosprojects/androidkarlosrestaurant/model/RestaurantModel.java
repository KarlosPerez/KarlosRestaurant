package com.karlosprojects.androidkarlosrestaurant.model;

import java.util.List;

public class RestaurantModel {
    private boolean success;
    private String message;
    private List<Restaurant> result;

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

    public List<Restaurant> getResult() {
        return result;
    }

    public void setResult(List<Restaurant> result) {
        this.result = result;
    }

    public RestaurantModel(boolean success, String message, List<Restaurant> result) {
        this.success = success;
        this.message = message;
        this.result = result;
    }
}
