package com.karlosprojects.androidkarlosrestaurant.activitiy.UpdateInfoActivity.interactor;

import com.facebook.accountkit.Account;

public interface UpdateInfoActivityInteractor {
    void updateUserInfo(Account account, String user_name, String user_address);

    void detachDisposable();
}
