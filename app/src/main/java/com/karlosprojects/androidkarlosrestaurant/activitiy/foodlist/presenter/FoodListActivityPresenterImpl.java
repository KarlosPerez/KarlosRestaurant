package com.karlosprojects.androidkarlosrestaurant.activitiy.foodlist.presenter;

import com.karlosprojects.androidkarlosrestaurant.activitiy.foodlist.repository.FoodListActivityRepository;
import com.karlosprojects.androidkarlosrestaurant.activitiy.foodlist.repository.FoodListActivityRepositoryImpl;
import com.karlosprojects.androidkarlosrestaurant.activitiy.foodlist.view.FoodListActivityView;
import com.karlosprojects.androidkarlosrestaurant.model.FoodModel;

import io.reactivex.disposables.CompositeDisposable;

public class FoodListActivityPresenterImpl implements FoodListActivityPresenter {

    private FoodListActivityView foodListActivityView;
    private FoodListActivityRepository foodListActivityRepository;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public FoodListActivityPresenterImpl(FoodListActivityView foodListActivityView) {
        this.foodListActivityView = foodListActivityView;
        foodListActivityRepository = new FoodListActivityRepositoryImpl(this, compositeDisposable);
    }

    @Override
    public void onSuccessLoadFoodList(FoodModel foodList) {
        foodListActivityView.showFoodList(foodList);
    }

    @Override
    public void onSuccessLoadBanner(String image) {
        foodListActivityView.showBannerImage(image);
    }

    @Override
    public void onSuccessLoadToolbar(String title) {
        foodListActivityView.showToolbarTitle(title);
    }

    @Override
    public void onUnsuccessMessage(String message) {
        if (foodListActivityView.isProgressDialogShowing()) hideProgressDialog();
        foodListActivityView.showUnsuccessMessage(message);
    }

    @Override
    public void onThrowableMessage(String message) {
        if (foodListActivityView.isProgressDialogShowing()) hideProgressDialog();
        foodListActivityView.showThrowableMessage(message);
    }

    @Override
    public void detachDisposable() {
        compositeDisposable.clear();
    }

    @Override
    public void onRegisterEvent() {
        foodListActivityRepository.registerEvent();
    }

    @Override
    public void onUnregisterEvent() {
        foodListActivityRepository.unregisterEvent();
    }

    @Override
    public void showProgressDialog() {
        foodListActivityView.showProgressDialog();
    }

    @Override
    public void hideProgressDialog() {
        foodListActivityView.hideProgressDialog();
    }

    @Override
    public boolean isProgressDialogShowing() {
        return foodListActivityView.isProgressDialogShowing();
    }


}
