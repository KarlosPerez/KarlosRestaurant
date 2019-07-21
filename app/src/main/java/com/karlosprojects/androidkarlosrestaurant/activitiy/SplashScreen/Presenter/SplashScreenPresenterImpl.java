package com.karlosprojects.androidkarlosrestaurant.activitiy.SplashScreen.Presenter;

import com.facebook.accountkit.Account;
import com.karlosprojects.androidkarlosrestaurant.activitiy.MainActivity.Model.User;
import com.karlosprojects.androidkarlosrestaurant.activitiy.SplashScreen.Interactor.SplashScreenInteractor;
import com.karlosprojects.androidkarlosrestaurant.activitiy.SplashScreen.Interactor.SplashScreenInteractorImpl;
import com.karlosprojects.androidkarlosrestaurant.activitiy.SplashScreen.View.SplashScreenView;

public class SplashScreenPresenterImpl implements SplashScreenPresenter {

    private SplashScreenView splashScreenView;
    private SplashScreenInteractor splashScreenInteractor;

    public SplashScreenPresenterImpl(SplashScreenView splashScreenView) {
        this.splashScreenView = splashScreenView;
        splashScreenInteractor = new SplashScreenInteractorImpl(this);
    }

    @Override
    public void getUserInformation(Account account) {
        splashScreenInteractor.getUserInformation(account);
    }

    @Override
    public void goToActivity(User currentUser, Class<?> activityClass) {
        if(isProgressDialogShowing()) splashScreenView.hideProgressDialog();
        splashScreenView.goToActivity(currentUser, activityClass);
    }

    @Override
    public void showThrowableMessage(String message) {
        splashScreenView.showThrowableMessage(message);
    }

    @Override
    public void showProgressDialog() {
        splashScreenView.showProgressDialog();
    }

    @Override
    public void hideProgressDialog() {
        splashScreenView.hideProgressDialog();
    }

    @Override
    public boolean isProgressDialogShowing() {
        return splashScreenView.isProgressDialogShowing();
    }

    @Override
    public void detachDisposable() {
        splashScreenInteractor.detachDisposable();
    }

}
