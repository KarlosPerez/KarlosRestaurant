package com.karlosprojects.androidkarlosrestaurant.SplashScreen.Interactor;

import com.facebook.accountkit.Account;

public interface SplashScreenInteractor {
    void getUserInformation(Account account);
    void detachDisposable();
}
