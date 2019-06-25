package com.karlosprojects.androidkarlosrestaurant.MainActivity.Presenter;

import com.karlosprojects.androidkarlosrestaurant.MainActivity.Interactor.MainActivityInteractor;
import com.karlosprojects.androidkarlosrestaurant.MainActivity.View.MainActivityView;

public class MainActivityPresenterImpl implements MainActivityPresenter {

    MainActivityView mainActivityView;
    MainActivityInteractor mainActivityInteractor;

    public MainActivityPresenterImpl(MainActivityView mainActivityView) {
        this.mainActivityView = mainActivityView;
    }

    @Override
    public void loginUser() {

    }
}
