package com.karlosprojects.androidkarlosrestaurant.SplashScreen.Repository;

import com.facebook.accountkit.Account;

public interface SplashScreenRepository {
    void getUserInformation(Account account);
    void detachDisposable();
}
