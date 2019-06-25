package com.karlosprojects.androidkarlosrestaurant.SplashScreen.View;

import com.facebook.accountkit.Account;
import com.karlosprojects.androidkarlosrestaurant.MainActivity.Model.User;

public interface SplashScreenView {
    void getUserInformation(Account account);
    void goToActivity(User currentUser, Class<?> activityClass);

    void showSuccessMessage(String message);
    void showErrorMessage(String message);
    void showThrowableMessage(String message);
}
