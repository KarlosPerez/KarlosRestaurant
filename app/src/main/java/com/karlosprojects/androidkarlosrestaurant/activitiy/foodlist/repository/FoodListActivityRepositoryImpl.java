package com.karlosprojects.androidkarlosrestaurant.activitiy.foodlist.repository;

import com.karlosprojects.androidkarlosrestaurant.Retrofit.IRestaurantAPI;
import com.karlosprojects.androidkarlosrestaurant.Retrofit.RetrofitClient;
import com.karlosprojects.androidkarlosrestaurant.Utils.Common;
import com.karlosprojects.androidkarlosrestaurant.activitiy.foodlist.presenter.FoodListActivityPresenter;
import com.karlosprojects.androidkarlosrestaurant.model.EventBus.FoodListEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FoodListActivityRepositoryImpl implements FoodListActivityRepository {

    private IRestaurantAPI iRestaurantAPI;
    private CompositeDisposable compositeDisposable;
    private FoodListActivityPresenter foodListActivityPresenter;

    public FoodListActivityRepositoryImpl(FoodListActivityPresenter foodListActivityPresenter, CompositeDisposable compositeDisposable) {
        this.foodListActivityPresenter = foodListActivityPresenter;
        this.compositeDisposable = compositeDisposable;
        initAPI();
    }

    private void initAPI() {
        iRestaurantAPI = RetrofitClient.getInstance(Common.API_RESTAURANT_ENDPOINT).create(IRestaurantAPI.class);
    }

    @Override
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void loadFoodListByCategory(FoodListEvent event) {
        if (event.isSuccess()) {
            foodListActivityPresenter.onSuccessLoadBanner(event.getCategory().getImage());
            foodListActivityPresenter.onSuccessLoadToolbar(event.getCategory().getName());
            displayFoodList(event);
        }
    }

    @Override
    public void displayFoodList(FoodListEvent event) {
        Disposable disposable = iRestaurantAPI.getFoodOfMenu(Common.API_KEY, event.getCategory().getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(foodModel -> {
                    if(foodModel.isSuccess()) {
                        foodListActivityPresenter.onSuccessLoadFoodList(foodModel);
                    } else {
                        showUnsuccessMessage("[GET FOOD RESULT] "+foodModel.getMessage());
                    }
                }, throwable -> showThrowableMessage("[GET FOOD] "+ throwable.getMessage()));
        bindToLifeCicle(disposable);
    }

    private void bindToLifeCicle(Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    @Override
    public void showUnsuccessMessage(String message) {
        foodListActivityPresenter.onUnsuccessMessage(message);
    }

    @Override
    public void showThrowableMessage(String message) {
        foodListActivityPresenter.onThrowableMessage(message);
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
