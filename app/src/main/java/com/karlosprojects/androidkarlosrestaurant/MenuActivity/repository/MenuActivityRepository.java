package com.karlosprojects.androidkarlosrestaurant.MenuActivity.repository;

public interface MenuActivityRepository {
    void showUnsuccessMessage(String message);
    void showThrowableMessage(String message);
    void showErrorMessage(String message);

    void detachDisposable();

    void registerEvent();
    void unregisterEvent();
}
