package com.karlosprojects.androidkarlosrestaurant.UpdateInfoActivity.presenter;

import com.facebook.accountkit.Account;
import com.karlosprojects.androidkarlosrestaurant.MainActivity.Model.User;

public interface UpdateInfoActivityPresenter {
    void updateUserInfo(Account account, String user_name, String user_address);
    void goToActivity(User currentUser, Class<?> activityClass);

    void detachDisposable();

    void showUnsuccessMessage(String message);
    void showThrowableMessage(String message);

    void showProgressDialog();
    void hideProgressDialog();
    boolean isProgressDialogShowing();
}
