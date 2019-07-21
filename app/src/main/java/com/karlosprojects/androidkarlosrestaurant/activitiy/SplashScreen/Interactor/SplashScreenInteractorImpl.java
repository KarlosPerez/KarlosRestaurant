package com.karlosprojects.androidkarlosrestaurant.activitiy.SplashScreen.Interactor;

import com.facebook.accountkit.Account;
import com.karlosprojects.androidkarlosrestaurant.activitiy.SplashScreen.Presenter.SplashScreenPresenter;
import com.karlosprojects.androidkarlosrestaurant.activitiy.SplashScreen.Repository.SplashScreenRepository;
import com.karlosprojects.androidkarlosrestaurant.activitiy.SplashScreen.Repository.SplashScreenRepositoryImpl;

public class SplashScreenInteractorImpl implements SplashScreenInteractor {

    private SplashScreenRepository splashScreenRepository;

    public SplashScreenInteractorImpl(SplashScreenPresenter splashScreenPresenter) {
        splashScreenRepository = new SplashScreenRepositoryImpl(splashScreenPresenter);
    }

    @Override
    public void getUserInformation(Account account) {
        splashScreenRepository.getUserInformation(account);
    }

    @Override
    public void detachDisposable() {
        splashScreenRepository.detachDisposable();
    }
}
