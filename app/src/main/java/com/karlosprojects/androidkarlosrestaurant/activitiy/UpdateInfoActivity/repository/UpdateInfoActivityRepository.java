package com.karlosprojects.androidkarlosrestaurant.activitiy.UpdateInfoActivity.repository;

import com.facebook.accountkit.Account;
import com.karlosprojects.androidkarlosrestaurant.activitiy.MainActivity.Model.User;

public interface UpdateInfoActivityRepository {
    void updateUserInfo(Account account, String user_name, String user_address);
    void getUserInfo(Account account);
    void goToActivity(User currentUser, Class<?> activityClass);

    void detachDisposable();
}
