package com.karlosprojects.androidkarlosrestaurant.UpdateInfoActivity.repository;

import com.facebook.accountkit.Account;
import com.karlosprojects.androidkarlosrestaurant.HomeActivity.view.HomeActivity;
import com.karlosprojects.androidkarlosrestaurant.MainActivity.Model.UpdateUserModel;
import com.karlosprojects.androidkarlosrestaurant.MainActivity.Model.User;
import com.karlosprojects.androidkarlosrestaurant.Retrofit.IRestaurantAPI;
import com.karlosprojects.androidkarlosrestaurant.Retrofit.RetrofitClient;
import com.karlosprojects.androidkarlosrestaurant.UpdateInfoActivity.presenter.UpdateInfoActivityPresenter;
import com.karlosprojects.androidkarlosrestaurant.UpdateInfoActivity.view.UpdateInfoActivity;
import com.karlosprojects.androidkarlosrestaurant.Utils.Common;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class UpdateInfoActivityRepositoryImpl implements UpdateInfoActivityRepository {

    private UpdateInfoActivityPresenter updateInfoActivityPresenter;
    private IRestaurantAPI iRestaurantAPI;
    private CompositeDisposable compositeDisposable;

    public UpdateInfoActivityRepositoryImpl(UpdateInfoActivityPresenter updateInfoActivityPresenter) {
        this.updateInfoActivityPresenter = updateInfoActivityPresenter;
        initAPI();
        compositeDisposable = new CompositeDisposable();
    }

    private void initAPI() {
        iRestaurantAPI = RetrofitClient.getInstance(Common.API_RESTAURANT_ENDPOINT).create(IRestaurantAPI.class);
    }

    @Override
    public void updateUserInfo(Account account, String user_name, String user_address) {
        Disposable disposable = iRestaurantAPI.updateUserInfo(
                Common.API_KEY,
                account.getPhoneNumber().toString(),
                user_name,
                user_address,
                account.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(updateUserModel -> {
                    if(updateUserModel.isSuccess()) {
                        getUserInfo(account);
                    } else {
                        updateInfoActivityPresenter.showUnsuccessMessage("[UPDATE USER API RETURN]" + updateUserModel.getMessage());
                    }
                }, throwable -> updateInfoActivityPresenter.showThrowableMessage("[UPDATE USER API]" + throwable.getMessage()));
        bindToLifeCycle(disposable);
    }

    @Override
    public void getUserInfo(Account account) {
        Disposable disposable = iRestaurantAPI.getUser(Common.API_KEY, account.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userModel -> {
                    if(userModel.isSuccess()) {
                        Common.currentUser = userModel.getResult().get(0);
                        goToActivity(Common.currentUser, HomeActivity.class);
                    } else {
                        updateInfoActivityPresenter.showUnsuccessMessage("[GET USER RESULT]" + userModel.getResult());
                    }
                }, throwable -> updateInfoActivityPresenter.showUnsuccessMessage("[GET USER]"+throwable.getMessage()));
        bindToLifeCycle(disposable);
    }

    @Override
    public void goToActivity(User currentUser, Class<?> activityClass) {
        updateInfoActivityPresenter.goToActivity(currentUser, activityClass);
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
