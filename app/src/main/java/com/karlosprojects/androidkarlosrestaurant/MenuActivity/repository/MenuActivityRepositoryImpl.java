package com.karlosprojects.androidkarlosrestaurant.MenuActivity.repository;

import com.karlosprojects.androidkarlosrestaurant.MenuActivity.presenter.MenuActivityPresenter;
import com.karlosprojects.androidkarlosrestaurant.Retrofit.IRestaurantAPI;
import com.karlosprojects.androidkarlosrestaurant.Retrofit.RetrofitClient;
import com.karlosprojects.androidkarlosrestaurant.Utils.Common;
import com.karlosprojects.androidkarlosrestaurant.model.EventBus.MenuItemEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MenuActivityRepositoryImpl implements MenuActivityRepository {

    private IRestaurantAPI iRestaurantAPI;
    private CompositeDisposable compositeDisposable;
    private MenuActivityPresenter menuActivityPresenter;

    public MenuActivityRepositoryImpl(MenuActivityPresenter menuActivityPresenter) {
        this.menuActivityPresenter = menuActivityPresenter;
        initAPI();
        compositeDisposable = new CompositeDisposable();
    }

    private void initAPI() {
        iRestaurantAPI = RetrofitClient.getInstance(Common.API_RESTAURANT_ENDPOINT).create(IRestaurantAPI.class);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void loadMenuRestaurant(MenuItemEvent event) {
        if(event.isSuccess()) {
            menuActivityPresenter.loadRestaurantBanner(event.getRestaurant().getImage());
            menuActivityPresenter.setupToolbar(event.getRestaurant().getName());
            displayMenu(event.getRestaurant().getId());
        } else {
            showUnsuccessMessage("[RESTAURANT LOAD] Unsuccess");
        }
    }

    private void displayMenu(int restaurantId) {
        Disposable disposable = iRestaurantAPI.getCategories(Common.API_KEY, restaurantId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(menuModel -> menuActivityPresenter.showMenuRestaurantById(menuModel),
                        throwable -> showThrowableMessage("[GET CATEGORY] "+throwable.getMessage()));
        bindToLifeCycle(disposable);
    }

    private void bindToLifeCycle(Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    @Override
    public void showUnsuccessMessage(String message) {
        menuActivityPresenter.showUnsuccessMessage(message);
    }

    @Override
    public void showThrowableMessage(String message) {
        menuActivityPresenter.showThrowableMessage(message);
    }

    @Override
    public void showErrorMessage(String message) {

    }

    @Override
    public void detachDisposable() {
        compositeDisposable.clear();
    }

    @Override
    public void registerEvent() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void unregisterEvent() {
        EventBus.getDefault().unregister(this);
    }
}
