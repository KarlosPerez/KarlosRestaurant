package com.karlosprojects.androidkarlosrestaurant.activitiy.MainActivity.Interactor;

import android.content.Intent;

import com.karlosprojects.androidkarlosrestaurant.activitiy.MainActivity.Presenter.MainActivityPresenter;
import com.karlosprojects.androidkarlosrestaurant.activitiy.MainActivity.Repository.MainActivityRepository;
import com.karlosprojects.androidkarlosrestaurant.activitiy.MainActivity.Repository.MainActivityRepositoryImpl;

public class MainActivityInteractorImpl implements MainActivityInteractor {

    private MainActivityRepository mainActivityRepository;

    public MainActivityInteractorImpl(MainActivityPresenter mainActivityPresenter) {
        mainActivityRepository = new MainActivityRepositoryImpl(mainActivityPresenter);
    }

    @Override
    public void validateUser() {
        mainActivityRepository.validateUser();
    }

    @Override
    public void getAccountKitLoginResult(Intent data) {
        mainActivityRepository.getAccountKitLoginResult(data);
    }

    @Override
    public void detachDisposable() {
        mainActivityRepository.detachDisposable();
    }
}
