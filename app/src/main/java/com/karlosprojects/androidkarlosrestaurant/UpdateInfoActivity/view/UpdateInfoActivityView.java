package com.karlosprojects.androidkarlosrestaurant.UpdateInfoActivity.view;


import com.facebook.accountkit.Account;
import com.karlosprojects.androidkarlosrestaurant.MainActivity.Model.User;

public interface UpdateInfoActivityView {
    void updateUserInfo(Account account, String user_name, String user_address);

    void goToActivity(User currentUser, Class<?> activityClass);

    void showSuccessMessage(String message);
    void showUnsuccessMessage(String message);
    void showErrorMessage(String message);
    void showThrowableMessage(String message);
}
