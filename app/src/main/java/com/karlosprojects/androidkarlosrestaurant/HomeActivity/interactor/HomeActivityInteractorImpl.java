package com.karlosprojects.androidkarlosrestaurant.HomeActivity.interactor;

import com.karlosprojects.androidkarlosrestaurant.HomeActivity.Repository.HomeActivityRepository;
import com.karlosprojects.androidkarlosrestaurant.HomeActivity.Repository.HomeActivityRepositoryImpl;
import com.karlosprojects.androidkarlosrestaurant.HomeActivity.presenter.HomeActivityPresenter;

public class HomeActivityInteractorImpl implements HomeActivityInteractor {

    private HomeActivityRepository homeActivityRepository;

    public HomeActivityInteractorImpl(HomeActivityPresenter homeActivityPresenter) {
        homeActivityRepository = new HomeActivityRepositoryImpl(homeActivityPresenter);
    }

    @Override
    public void getRestaurantInfo() {
        homeActivityRepository.getRestaurantInfo();
    }

    @Override
    public void detachDisposable() {
        homeActivityRepository.detachDisposable();
    }

    @Override
    public void registerEvent() {
        homeActivityRepository.registerEvent();
    }

    @Override
    public void unregisterEvent() {
        homeActivityRepository.unregisterEvent();
    }
}
