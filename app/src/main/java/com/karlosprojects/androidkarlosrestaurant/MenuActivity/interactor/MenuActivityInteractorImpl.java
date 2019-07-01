package com.karlosprojects.androidkarlosrestaurant.MenuActivity.interactor;

import com.karlosprojects.androidkarlosrestaurant.MenuActivity.presenter.MenuActivityPresenter;
import com.karlosprojects.androidkarlosrestaurant.MenuActivity.repository.MenuActivityRepository;
import com.karlosprojects.androidkarlosrestaurant.MenuActivity.repository.MenuActivityRepositoryImpl;

public class MenuActivityInteractorImpl implements MenuActivityInteractor {

    private MenuActivityRepository menuActivityRepository;

    public MenuActivityInteractorImpl(MenuActivityPresenter menuActivityPresenter) {
        menuActivityRepository = new MenuActivityRepositoryImpl(menuActivityPresenter);
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
