package com.karlosprojects.androidkarlosrestaurant.HomeActivity.Repository;

import com.karlosprojects.androidkarlosrestaurant.model.Restaurant;

import java.util.List;

public interface HomeActivityRepository {
    void getRestaurantInfo();
    void displayRestaurant(List<Restaurant> restaurantList);
    void displayBanner(List<Restaurant> restaurantList);

    void showUnsuccessMessage(String message);
    void showThrowableMessage(String message);

    void detachDisposable();

    void registerEvent();
    void unregisterEvent();
}
