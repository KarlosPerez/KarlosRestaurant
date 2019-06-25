package com.karlosprojects.androidkarlosrestaurant.SplashScreen.Repository;

import android.content.Intent;

import com.facebook.accountkit.Account;
import com.karlosprojects.androidkarlosrestaurant.HomeActivity.HomeActivity;
import com.karlosprojects.androidkarlosrestaurant.MainActivity.Model.UserModel;
import com.karlosprojects.androidkarlosrestaurant.MainActivity.View.MainActivity;
import com.karlosprojects.androidkarlosrestaurant.Retrofit.IRestaurantAPI;
import com.karlosprojects.androidkarlosrestaurant.Retrofit.RetrofitClient;
import com.karlosprojects.androidkarlosrestaurant.SplashScreen.Presenter.SplashScreenPresenter;
import com.karlosprojects.androidkarlosrestaurant.Utils.Common;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SplashScreenRepositoryImpl implements SplashScreenRepository {

    private SplashScreenPresenter splashScreenPresenter;
    private IRestaurantAPI iRestaurantAPI;
    private CompositeDisposable compositeDisposable;

    public SplashScreenRepositoryImpl(SplashScreenPresenter splashScreenPresenter) {
        this.splashScreenPresenter = splashScreenPresenter;
        initAPI();
        compositeDisposable = new CompositeDisposable();
    }

    private void initAPI() {
        iRestaurantAPI = RetrofitClient.getInstance(Common.API_RESTAURANT_ENDPOINT).create(IRestaurantAPI.class);
    }

    @Override
    public void getUserInformation(Account account) {
        compositeDisposable.add(iRestaurantAPI.getUser(Common.API_KEY, account.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<UserModel>() {
                    @Override
                    public void accept(UserModel userModel) throws Exception {
                        if(userModel.isSuccess()) {
                            Common.currentUser = userModel.getResult().get(0);
                            splashScreenPresenter.goToActivity(Common.currentUser, HomeActivity.class);
                        } else {
                            splashScreenPresenter.goToActivity(Common.currentUser, MainActivity.class);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        splashScreenPresenter.showThrowableMessage("[GET USER API]");
                    }
                }));
    }
}
