package com.karlosprojects.androidkarlosrestaurant.activitiy.SplashScreen.Repository;

import com.facebook.accountkit.Account;
import com.karlosprojects.androidkarlosrestaurant.activitiy.HomeActivity.view.HomeActivity;
import com.karlosprojects.androidkarlosrestaurant.Retrofit.IRestaurantAPI;
import com.karlosprojects.androidkarlosrestaurant.Retrofit.RetrofitClient;
import com.karlosprojects.androidkarlosrestaurant.activitiy.SplashScreen.Presenter.SplashScreenPresenter;
import com.karlosprojects.androidkarlosrestaurant.activitiy.UpdateInfoActivity.view.UpdateInfoActivity;
import com.karlosprojects.androidkarlosrestaurant.Utils.Common;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
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
        Disposable disposable = iRestaurantAPI.getUser(Common.API_KEY, account.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userModel -> {
                    if(userModel.isSuccess()) {
                        Common.currentUser = userModel.getResult().get(0);
                        splashScreenPresenter.goToActivity(Common.currentUser, HomeActivity.class);
                    } else {
                        splashScreenPresenter.goToActivity(Common.currentUser, UpdateInfoActivity.class);
                    }
                }, throwable -> splashScreenPresenter.showThrowableMessage("[GET USER API]"));
        bindToLifeCycle(disposable);
    }

    @Override
    public void detachDisposable() {
        compositeDisposable.clear();
    }

    /**
     * Binds a disposable to this presenter lifecycle
     * @param disposable Disposable to be added
     */
    private void bindToLifeCycle(Disposable disposable) {
        compositeDisposable.add(disposable);
    }
}
