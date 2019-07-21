package com.karlosprojects.androidkarlosrestaurant.activitiy.HomeActivity.interactor;

public interface HomeActivityInteractor {
    void getRestaurantInfo();
    void detachDisposable();

    void registerEvent();
    void unregisterEvent();
}
