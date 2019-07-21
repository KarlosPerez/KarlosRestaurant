package com.karlosprojects.androidkarlosrestaurant.activitiy.MenuActivity.interactor;

import com.karlosprojects.androidkarlosrestaurant.activitiy.MenuActivity.presenter.MenuActivityPresenter;
import com.karlosprojects.androidkarlosrestaurant.activitiy.MenuActivity.repository.MenuActivityRepository;
import com.karlosprojects.androidkarlosrestaurant.activitiy.MenuActivity.repository.MenuActivityRepositoryImpl;
import com.karlosprojects.androidkarlosrestaurant.database.CartDataSource;

public class MenuActivityInteractorImpl implements MenuActivityInteractor {

    private MenuActivityRepository menuActivityRepository;

    public MenuActivityInteractorImpl(MenuActivityPresenter menuActivityPresenter) {
        menuActivityRepository = new MenuActivityRepositoryImpl(menuActivityPresenter);
    }

    @Override
    public void getCountCartByRestaurant(CartDataSource cartDataSource) {
        menuActivityRepository.getCountCartByRestaurant(cartDataSource);
    }

    @Override
    public void detachDisposable() {
        menuActivityRepository.detachDisposable();
    }

    @Override
    public void registerEvent() {
        menuActivityRepository.registerEvent();
    }

    @Override
    public void unregisterEvent() {
        menuActivityRepository.unregisterEvent();
    }
}
