package com.karlosprojects.androidkarlosrestaurant.MainActivity.Repository;

import android.content.Intent;

import com.facebook.accountkit.Account;
import com.karlosprojects.androidkarlosrestaurant.MainActivity.Model.User;

public interface MainActivityRepository {
    void validateUser();
    void goToActivity(User currentUser, Class<?> activityClass);
    void getAccountKitLoginResult(Intent data);
    void onSuccessAccountKitLoginResult(Account account);

    void showUnsuccessMessage(String message);
    void showThrowableMessage(String message);
    void showErrorMessage(String message);

    void detachDisposable();
}
