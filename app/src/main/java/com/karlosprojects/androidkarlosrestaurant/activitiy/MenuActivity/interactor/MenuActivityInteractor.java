package com.karlosprojects.androidkarlosrestaurant.activitiy.MenuActivity.interactor;

import com.karlosprojects.androidkarlosrestaurant.database.CartDataSource;

public interface MenuActivityInteractor {
    void getCountCartByRestaurant(CartDataSource cartDataSource);
    void detachDisposable();

    void registerEvent();
    void unregisterEvent();
}
