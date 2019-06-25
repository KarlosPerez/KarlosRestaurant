package com.karlosprojects.androidkarlosrestaurant.SplashScreen.Presenter;

import android.app.Activity;

import com.facebook.accountkit.Account;
import com.karlosprojects.androidkarlosrestaurant.HomeActivity.HomeActivity;
import com.karlosprojects.androidkarlosrestaurant.MainActivity.Model.User;

public interface SplashScreenPresenter {
    void getUserInformation(Account account);
    void goToActivity(User currentUser, Class<?> activityClass);

    void showThrowableMessage(String message);

    void detachDisposable();
}
