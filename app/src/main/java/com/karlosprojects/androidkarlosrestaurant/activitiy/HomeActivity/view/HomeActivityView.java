package com.karlosprojects.androidkarlosrestaurant.activitiy.HomeActivity.view;

import com.karlosprojects.androidkarlosrestaurant.model.Restaurant;

import java.util.List;

public interface HomeActivityView {
    void displayRestaurant(List<Restaurant> restaurantList);
    void displayBanner(List<Restaurant> restaurantList);

    void getRestaurantInfo();
    void signOut();

    void showProgressDialog();
    void hideProgressDialog();
    boolean isProgressDialogShowing();

    void showUnsuccessMessage(String message);
    void showThrowableMessage(String message);
}
