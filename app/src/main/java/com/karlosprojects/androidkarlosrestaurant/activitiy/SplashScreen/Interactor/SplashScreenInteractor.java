package com.karlosprojects.androidkarlosrestaurant.activitiy.SplashScreen.Interactor;

import com.facebook.accountkit.Account;

public interface SplashScreenInteractor {
    void getUserInformation(Account account);
    void detachDisposable();
}
