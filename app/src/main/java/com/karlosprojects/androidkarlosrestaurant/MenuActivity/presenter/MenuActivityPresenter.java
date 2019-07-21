package com.karlosprojects.androidkarlosrestaurant.MenuActivity.presenter;

import com.karlosprojects.androidkarlosrestaurant.database.CartDataSource;
import com.karlosprojects.androidkarlosrestaurant.model.MenuModel;

public interface MenuActivityPresenter {
    void showUnsuccessMessage(String message);
    void showThrowableMessage(String message);
    void showErrorMessage(String message);

    void detachDisposable();
    void setupToolbar(String restaurantName);

    void showProgressDialog();
    void hideProgressDialog();
    boolean isProgressDialogShowing();

    void showMenuRestaurantById(MenuModel menuModel);
    void loadRestaurantBanner(String imageUrl);
    void showCartItemCountOnBadge(String itemCount);
    void getCountCartByRestaurant(CartDataSource cartDataSource);

    void registerEvent();
    void unregisterEvent();
}
