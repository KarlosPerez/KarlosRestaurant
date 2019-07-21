package com.karlosprojects.androidkarlosrestaurant.activitiy.MenuActivity.repository;

import com.karlosprojects.androidkarlosrestaurant.database.CartDataSource;

public interface MenuActivityRepository {
    void showUnsuccessMessage(String message);
    void showThrowableMessage(String message);
    void showErrorMessage(String message);
    void getCountCartByRestaurant(CartDataSource cartDataSource);

    void detachDisposable();

    void registerEvent();
    void unregisterEvent();
}
