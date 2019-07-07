package com.karlosprojects.androidkarlosrestaurant.foodlist.view;

import com.karlosprojects.androidkarlosrestaurant.model.FoodModel;

public interface FoodListActivityView {

    void showBannerImage(String image);
    void showToolbarTitle(String title);

    void showFoodList(FoodModel foodList);

    void showProgressDialog();
    void hideProgressDialog();
    boolean isProgressDialogShowing();

    void showUnsuccessMessage(String message);
    void showThrowableMessage(String message);
}
