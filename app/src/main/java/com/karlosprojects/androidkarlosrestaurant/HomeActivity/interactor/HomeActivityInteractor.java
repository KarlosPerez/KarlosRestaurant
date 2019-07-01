package com.karlosprojects.androidkarlosrestaurant.HomeActivity.interactor;

import com.karlosprojects.androidkarlosrestaurant.model.EventBus.RestaurantLoadEvent;
import com.karlosprojects.androidkarlosrestaurant.model.Restaurant;

import java.util.List;

public interface HomeActivityInteractor {
    void getRestaurantInfo();
    void detachDisposable();

    void registerEvent();
    void unregisterEvent();
}
