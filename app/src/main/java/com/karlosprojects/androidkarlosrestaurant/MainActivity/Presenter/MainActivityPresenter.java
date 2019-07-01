package com.karlosprojects.androidkarlosrestaurant.MainActivity.Presenter;

import android.content.Intent;

import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.karlosprojects.androidkarlosrestaurant.MainActivity.Model.User;

public interface MainActivityPresenter {
    void validateUser();
    void sendAccountBuilder(AccountKitConfiguration.AccountKitConfigurationBuilder builder);
    void getAccountKitLoginResult(Intent data);
    void goToActivity(User currentUser, Class<?> activityClass);

    void showUnsuccessMessage(String message);
    void showThrowableMessage(String message);
    void showErrorMessage(String message);

    void detachDisposable();

    void showProgressDialog();
    void hideProgressDialog();
    boolean isProgressDialogShowing();
}
