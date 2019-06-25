package com.karlosprojects.androidkarlosrestaurant.SplashScreen.Presenter;

import android.app.Activity;

import com.facebook.accountkit.Account;
import com.karlosprojects.androidkarlosrestaurant.MainActivity.Model.User;
import com.karlosprojects.androidkarlosrestaurant.SplashScreen.Interactor.SplashScreenInteractor;
import com.karlosprojects.androidkarlosrestaurant.SplashScreen.Interactor.SplashScreenInteractorImpl;
import com.karlosprojects.androidkarlosrestaurant.SplashScreen.View.SplashScreenView;

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
        splashScreenView.goToActivity(currentUser, activityClass);
    }

    @Override
    public void showThrowableMessage(String message) {
        splashScreenView.showThrowableMessage(message);
    }

    @Override
    public void detachDisposable() {
        splashScreenInteractor.detachDisposable();
    }

}
