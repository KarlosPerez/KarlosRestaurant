package com.karlosprojects.androidkarlosrestaurant.SplashScreen.Interactor;

import com.facebook.accountkit.Account;
import com.karlosprojects.androidkarlosrestaurant.SplashScreen.Presenter.SplashScreenPresenter;
import com.karlosprojects.androidkarlosrestaurant.SplashScreen.Repository.SplashScreenRepository;
import com.karlosprojects.androidkarlosrestaurant.SplashScreen.Repository.SplashScreenRepositoryImpl;

public class SplashScreenInteractorImpl implements SplashScreenInteractor {

    private SplashScreenPresenter splashScreenPresenter;
    private SplashScreenRepository splashScreenRepository;

    public SplashScreenInteractorImpl(SplashScreenPresenter splashScreenPresenter) {
        this.splashScreenPresenter = splashScreenPresenter;
        splashScreenRepository = new SplashScreenRepositoryImpl(splashScreenPresenter);
    }

    @Override
    public void getUserInformation(Account account) {
        splashScreenRepository.getUserInformation(account);
    }
}
