package com.karlosprojects.androidkarlosrestaurant.activitiy.fooddetail.repository;

import android.widget.Toolbar;

import com.karlosprojects.androidkarlosrestaurant.Retrofit.IRestaurantAPI;
import com.karlosprojects.androidkarlosrestaurant.Retrofit.RetrofitClient;
import com.karlosprojects.androidkarlosrestaurant.Utils.Common;
import com.karlosprojects.androidkarlosrestaurant.activitiy.fooddetail.presenter.FoodDetailPresenter;
import com.karlosprojects.androidkarlosrestaurant.database.CartDataSource;
import com.karlosprojects.androidkarlosrestaurant.database.CartDatabase;
import com.karlosprojects.androidkarlosrestaurant.database.LocalCartDataSource;
import com.karlosprojects.androidkarlosrestaurant.model.EventBus.FoodDetailEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.reactivex.disposables.CompositeDisposable;

public class FoodDetailRepositoryImpl implements FoodDetailRepository {

    private IRestaurantAPI iRestaurantAPI;
    private CompositeDisposable compositeDisposable;
    private FoodDetailPresenter foodDetailPresenter;

    public FoodDetailRepositoryImpl(FoodDetailPresenter foodDetailPresenter, CompositeDisposable compositeDisposable) {
        this.foodDetailPresenter = foodDetailPresenter;
        this.compositeDisposable = compositeDisposable;
        initAPI();
    }

    private void initAPI() {
        iRestaurantAPI = RetrofitClient.getInstance(Common.API_RESTAURANT_ENDPOINT).create(IRestaurantAPI.class);
    }

    @Override
    public void registerEvent() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void unregisterEvent() {
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void displayFoodDetail(FoodDetailEvent foodDetailEvent) {
        if (foodDetailEvent.isSuccess()) {
             foodDetailPresenter.onSuccessLoadToolbar(foodDetailEvent.getFood().getName());
        }
    }
}
