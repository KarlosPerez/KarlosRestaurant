package com.karlosprojects.androidkarlosrestaurant.activitiy.foodlist.presenter;

import com.karlosprojects.androidkarlosrestaurant.model.FoodModel;

public interface FoodListActivityPresenter {

    void onSuccessLoadFoodList(FoodModel foodList);

    void onSuccessLoadBanner(String image);
    void onSuccessLoadToolbar(String title);

    void onUnsuccessMessage(String message);
    void onThrowableMessage(String message);

    void detachDisposable();

    void onRegisterEvent();
    void onUnregisterEvent();

    void showProgressDialog();
    void hideProgressDialog();
    boolean isProgressDialogShowing();
}
