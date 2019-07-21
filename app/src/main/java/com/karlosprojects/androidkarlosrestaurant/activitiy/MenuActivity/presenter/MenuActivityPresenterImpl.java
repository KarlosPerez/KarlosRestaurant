package com.karlosprojects.androidkarlosrestaurant.activitiy.MenuActivity.presenter;

import com.karlosprojects.androidkarlosrestaurant.activitiy.MenuActivity.interactor.MenuActivityInteractor;
import com.karlosprojects.androidkarlosrestaurant.activitiy.MenuActivity.interactor.MenuActivityInteractorImpl;
import com.karlosprojects.androidkarlosrestaurant.activitiy.MenuActivity.view.MenuActivityView;
import com.karlosprojects.androidkarlosrestaurant.database.CartDataSource;
import com.karlosprojects.androidkarlosrestaurant.model.MenuModel;

public class MenuActivityPresenterImpl implements MenuActivityPresenter {

    private MenuActivityView menuActivityView;
    private MenuActivityInteractor menuActivityInteractor;

    public MenuActivityPresenterImpl(MenuActivityView menuActivityView) {
        this.menuActivityView = menuActivityView;
        menuActivityInteractor = new MenuActivityInteractorImpl(this);
    }

    @Override
    public void showUnsuccessMessage(String message) {
        if (menuActivityView.isProgressDialogShowing()) hideProgressDialog();
        menuActivityView.showUnsuccessMessage(message);
    }

    @Override
    public void showThrowableMessage(String message) {
        if (menuActivityView.isProgressDialogShowing()) hideProgressDialog();
        menuActivityView.showThrowableMessage(message);
    }

    @Override
    public void showErrorMessage(String message) {
        if (menuActivityView.isProgressDialogShowing()) hideProgressDialog();
        menuActivityView.showErrorMessage(message);
    }

    @Override
    public void detachDisposable() {
        menuActivityInteractor.detachDisposable();
    }

    @Override
    public void setupToolbar(String restaurantName) {
        menuActivityView.setupToolbar(restaurantName);
    }

    @Override
    public void showProgressDialog() {
        menuActivityView.showProgressDialog();
    }

    @Override
    public void hideProgressDialog() {
        menuActivityView.hideProgressDialog();
    }

    @Override
    public boolean isProgressDialogShowing() {
        return menuActivityView.isProgressDialogShowing();
    }

    @Override
    public void showMenuRestaurantById(MenuModel menuModel) {
        if (menuActivityView.isProgressDialogShowing()) hideProgressDialog();
        menuActivityView.showMenuRestaurantById(menuModel);
    }

    @Override
    public void loadRestaurantBanner(String imageUrl) {
        if (menuActivityView.isProgressDialogShowing()) hideProgressDialog();
        menuActivityView.loadRestaurantBanner(imageUrl);
    }

    @Override
    public void showCartItemCountOnBadge(String itemCount) {
        menuActivityView.showCartItemCountOnBadge(itemCount);
    }

    @Override
    public void getCountCartByRestaurant(CartDataSource cartDataSource) {
        menuActivityInteractor.getCountCartByRestaurant(cartDataSource);
    }

    @Override
    public void registerEvent() {
        menuActivityInteractor.registerEvent();
    }

    @Override
    public void unregisterEvent() {
        menuActivityInteractor.unregisterEvent();
    }
}
