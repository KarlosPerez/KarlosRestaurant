package com.karlosprojects.androidkarlosrestaurant.HomeActivity.presenter;

import com.karlosprojects.androidkarlosrestaurant.HomeActivity.interactor.HomeActivityInteractor;
import com.karlosprojects.androidkarlosrestaurant.HomeActivity.interactor.HomeActivityInteractorImpl;
import com.karlosprojects.androidkarlosrestaurant.HomeActivity.view.HomeActivityView;
import com.karlosprojects.androidkarlosrestaurant.model.Restaurant;

import java.util.List;

public class HomeActivityPresenterImpl implements HomeActivityPresenter {

    private HomeActivityView homeActivityView;
    private HomeActivityInteractor homeActivityInteractor;

    public HomeActivityPresenterImpl(HomeActivityView homeActivityView) {
        this.homeActivityView = homeActivityView;
        homeActivityInteractor = new HomeActivityInteractorImpl(this);
    }

    @Override
    public void displayRestaurant(List<Restaurant> restaurantList) {
        if (homeActivityView.isProgressDialogShowing()) hideProgressDialog();
        homeActivityView.displayRestaurant(restaurantList);
    }

    @Override
    public void displayBanner(List<Restaurant> restaurantList) {
        if (homeActivityView.isProgressDialogShowing()) hideProgressDialog();
        homeActivityView.displayBanner(restaurantList);
    }

    @Override
    public void getRestaurantInfo() {
        homeActivityInteractor.getRestaurantInfo();
    }

    @Override
    public void showProgressDialog() {
        homeActivityView.showProgressDialog();
    }

    @Override
    public void hideProgressDialog() {
        homeActivityView.hideProgressDialog();
    }

    @Override
    public boolean isProgressDialogShowing() {
        return homeActivityView.isProgressDialogShowing();
    }

    @Override
    public void showUnsuccessMessage(String message) {
        if (homeActivityView.isProgressDialogShowing()) hideProgressDialog();
        homeActivityView.showUnsuccessMessage(message);
    }

    @Override
    public void showThrowableMessage(String message) {
        if (homeActivityView.isProgressDialogShowing()) hideProgressDialog();
        homeActivityView.showThrowableMessage(message);
    }

    @Override
    public void detachDisposable() {
        homeActivityInteractor.detachDisposable();
    }

    @Override
    public void registerEvent() {
        homeActivityInteractor.registerEvent();
    }

    @Override
    public void unregisterEvent() {
        homeActivityInteractor.unregisterEvent();
    }
}
