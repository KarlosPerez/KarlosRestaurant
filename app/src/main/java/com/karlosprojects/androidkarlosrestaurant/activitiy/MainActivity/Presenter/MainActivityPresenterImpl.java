package com.karlosprojects.androidkarlosrestaurant.activitiy.MainActivity.Presenter;

import android.content.Intent;

import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.karlosprojects.androidkarlosrestaurant.activitiy.MainActivity.Interactor.MainActivityInteractor;
import com.karlosprojects.androidkarlosrestaurant.activitiy.MainActivity.Interactor.MainActivityInteractorImpl;
import com.karlosprojects.androidkarlosrestaurant.activitiy.MainActivity.Model.User;
import com.karlosprojects.androidkarlosrestaurant.activitiy.MainActivity.View.MainActivityView;

public class MainActivityPresenterImpl implements MainActivityPresenter {

    private MainActivityView mainActivityView;
    private MainActivityInteractor mainActivityInteractor;

    public MainActivityPresenterImpl(MainActivityView mainActivityView) {
        this.mainActivityView = mainActivityView;
        mainActivityInteractor = new MainActivityInteractorImpl(this);
    }

    public void goToActivity(User currentUser, Class<?> activityClass) {
        mainActivityView.goToActivity(currentUser, activityClass);
    }

    @Override
    public void showUnsuccessMessage(String message) {
        mainActivityView.showUnsuccessMessage(message);
    }

    @Override
    public void showThrowableMessage(String message) {
        mainActivityView.showThrowableMessage(message);
    }

    @Override
    public void showErrorMessage(String message) {
        mainActivityView.showErrorMessage(message);
    }

    @Override
    public void detachDisposable() {
        mainActivityInteractor.detachDisposable();
    }

    @Override
    public void showProgressDialog() {

    }

    @Override
    public void hideProgressDialog() {

    }

    @Override
    public boolean isProgressDialogShowing() {
        return false;
    }

    @Override
    public void validateUser() {
        mainActivityInteractor.validateUser();
    }

    @Override
    public void sendAccountBuilder(AccountKitConfiguration.AccountKitConfigurationBuilder builder) {
        mainActivityView.getAccountBuilder(builder);
    }

    @Override
    public void getAccountKitLoginResult(Intent data) {
        mainActivityInteractor.getAccountKitLoginResult(data);
    }
}
