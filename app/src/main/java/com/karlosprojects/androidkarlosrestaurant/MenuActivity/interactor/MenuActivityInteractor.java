package com.karlosprojects.androidkarlosrestaurant.MenuActivity.interactor;

import com.karlosprojects.androidkarlosrestaurant.database.CartDataSource;

public interface MenuActivityInteractor {
    void getCountCartByRestaurant(CartDataSource cartDataSource);
    void detachDisposable();

    void registerEvent();
    void unregisterEvent();
}
