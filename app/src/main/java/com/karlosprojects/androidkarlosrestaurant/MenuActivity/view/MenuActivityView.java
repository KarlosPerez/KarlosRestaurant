package com.karlosprojects.androidkarlosrestaurant.MenuActivity.view;

import com.karlosprojects.androidkarlosrestaurant.model.MenuModel;

public interface MenuActivityView {

    void showMenuRestaurantById(MenuModel menuModel);
    void loadRestaurantBanner(String imageUrl);

    void setupToolbar(String restaurantName);

    void showUnsuccessMessage(String message);
    void showThrowableMessage(String message);
    void showErrorMessage(String message);

    void showProgressDialog();
    void hideProgressDialog();
    boolean isProgressDialogShowing();
}
