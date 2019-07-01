package com.karlosprojects.androidkarlosrestaurant.HomeActivity.Repository;

import com.karlosprojects.androidkarlosrestaurant.HomeActivity.presenter.HomeActivityPresenter;
import com.karlosprojects.androidkarlosrestaurant.Retrofit.IRestaurantAPI;
import com.karlosprojects.androidkarlosrestaurant.Retrofit.RetrofitClient;
import com.karlosprojects.androidkarlosrestaurant.Utils.Common;
import com.karlosprojects.androidkarlosrestaurant.model.EventBus.RestaurantLoadEvent;
import com.karlosprojects.androidkarlosrestaurant.model.Restaurant;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HomeActivityRepositoryImpl implements HomeActivityRepository {

    private CompositeDisposable compositeDisposable;
    private IRestaurantAPI iRestaurantAPI;
    private HomeActivityPresenter homeActivityPresenter;

    public HomeActivityRepositoryImpl(HomeActivityPresenter homeActivityPresenter) {
        this.homeActivityPresenter = homeActivityPresenter;
        initAPI();
        compositeDisposable = new CompositeDisposable();
    }

    private void initAPI() {
        iRestaurantAPI = RetrofitClient.getInstance(Common.API_RESTAURANT_ENDPOINT).create(IRestaurantAPI.class);
    }

    public void getRestaurantInfo() {
        Disposable disposable = iRestaurantAPI.getRestaurant(Common.API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(restaurantModel -> {
                    //Here, we will use EventBus to send local event set adapter and slider
                    EventBus.getDefault().post(new RestaurantLoadEvent(true, restaurantModel.getResult()));
                }, throwable -> EventBus.getDefault().post(new RestaurantLoadEvent(false, throwable.getMessage())));
        bindToLifeCycle(disposable);
    }

    @Override
    public void displayRestaurant(List<Restaurant> restaurantList) {
        homeActivityPresenter.displayRestaurant(restaurantList);
    }

    @Override
    public void displayBanner(List<Restaurant> restaurantList) {
        homeActivityPresenter.displayBanner(restaurantList);
    }

    @Override
    public void showUnsuccessMessage(String message) {
        homeActivityPresenter.showUnsuccessMessage(message);
    }

    @Override
    public void showThrowableMessage(String message) {
        homeActivityPresenter.showThrowableMessage(message);
    }

    @Override
    public void detachDisposable() {
        compositeDisposable.clear();
    }

    private void bindToLifeCycle(Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void RestaurantLoadEvent(RestaurantLoadEvent event) {
        if (event.isSuccess()) {
            displayBanner(event.getRestaurantList());
            displayRestaurant(event.getRestaurantList());
        } else {
           showUnsuccessMessage("[RESTAURANT LOAD] "+event.getMessage());
        }
    }

    public void registerEvent() {
        EventBus.getDefault().register(this);
    }

    public void unregisterEvent() {
        EventBus.getDefault().unregister(this);
    }


}
