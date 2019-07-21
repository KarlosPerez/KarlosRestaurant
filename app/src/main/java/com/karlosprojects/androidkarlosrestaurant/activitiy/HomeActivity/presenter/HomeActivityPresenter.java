package com.karlosprojects.androidkarlosrestaurant.activitiy.HomeActivity.presenter;

import com.karlosprojects.androidkarlosrestaurant.model.Restaurant;
import java.util.List;

public interface HomeActivityPresenter {
    void displayRestaurant(List<Restaurant> restaurantList);
    void displayBanner(List<Restaurant> restaurantList);

    void getRestaurantInfo();

    void showProgressDialog();
    void hideProgressDialog();
    boolean isProgressDialogShowing();

    void showUnsuccessMessage(String message);
    void showThrowableMessage(String message);

    void detachDisposable();

    void registerEvent();
    void unregisterEvent();
}
