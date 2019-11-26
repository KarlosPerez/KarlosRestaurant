package com.karlosprojects.androidkarlosrestaurant.activitiy.fooddetail.presenter;

import com.karlosprojects.androidkarlosrestaurant.activitiy.fooddetail.repository.FoodDetailRepository;
import com.karlosprojects.androidkarlosrestaurant.activitiy.fooddetail.repository.FoodDetailRepositoryImpl;
import com.karlosprojects.androidkarlosrestaurant.activitiy.fooddetail.view.FoodDetailActivityView;
import com.karlosprojects.androidkarlosrestaurant.database.CartDataSource;
import com.karlosprojects.androidkarlosrestaurant.database.CartDatabase;
import com.karlosprojects.androidkarlosrestaurant.database.LocalCartDataSource;

import io.reactivex.disposables.CompositeDisposable;

public class FoodDetailPresenterImpl implements FoodDetailPresenter {

    private FoodDetailActivityView foodDetailView;
    private FoodDetailRepository foodDetailRepository;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private CartDataSource cartDataSource;

    public FoodDetailPresenterImpl(FoodDetailActivityView foodDetailView) {
        this.foodDetailView = foodDetailView;
        foodDetailRepository = new FoodDetailRepositoryImpl(this, compositeDisposable);

    }

    @Override
    public void detachDisposable() {

    }

    @Override
    public void onSuccessLoadToolbar(String title) {

    }
}
