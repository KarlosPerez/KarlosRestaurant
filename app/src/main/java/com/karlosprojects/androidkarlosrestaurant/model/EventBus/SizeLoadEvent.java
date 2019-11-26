package com.karlosprojects.androidkarlosrestaurant.model.EventBus;

import com.karlosprojects.androidkarlosrestaurant.model.Size;

import java.util.List;

public class SizeLoadEvent {
    private boolean success;
    private List<Size> sizeList;

    public SizeLoadEvent(boolean success, List<Size> sizeList) {
        this.success = success;
        this.sizeList = sizeList;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<Size> getSizeList() {
        return sizeList;
    }

    public void setSizeList(List<Size> sizeList) {
        this.sizeList = sizeList;
    }
}
