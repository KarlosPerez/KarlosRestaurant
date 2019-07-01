package com.karlosprojects.androidkarlosrestaurant.MainActivity.View;

import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.karlosprojects.androidkarlosrestaurant.MainActivity.Model.User;

public interface MainActivityView {
    void validateUser();
    void goToActivity(User currentUser, Class<?> activityClass);
    void getAccountBuilder(AccountKitConfiguration.AccountKitConfigurationBuilder builder);

    void showUnsuccessMessage(String message);
    void showThrowableMessage(String message);
    void showErrorMessage(String message);

    void showProgressDialog();
    void hideProgressDialog();
    boolean isProgressDialogShowing();
}
