package com.karlosprojects.androidkarlosrestaurant.MainActivity.Repository;

import android.content.Intent;
import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;
import com.karlosprojects.androidkarlosrestaurant.HomeActivity.view.HomeActivity;
import com.karlosprojects.androidkarlosrestaurant.MainActivity.Model.User;
import com.karlosprojects.androidkarlosrestaurant.MainActivity.Presenter.MainActivityPresenter;
import com.karlosprojects.androidkarlosrestaurant.Retrofit.IRestaurantAPI;
import com.karlosprojects.androidkarlosrestaurant.Retrofit.RetrofitClient;
import com.karlosprojects.androidkarlosrestaurant.UpdateInfoActivity.view.UpdateInfoActivity;
import com.karlosprojects.androidkarlosrestaurant.Utils.Common;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivityRepositoryImpl implements MainActivityRepository {

    private IRestaurantAPI iRestaurantAPI;
    private CompositeDisposable compositeDisposable;
    private MainActivityPresenter mainActivityPresenter;

    public MainActivityRepositoryImpl(MainActivityPresenter mainActivityPresenter) {
        this.mainActivityPresenter = mainActivityPresenter;
        initAPI();
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void validateUser() {
        AccountKitConfiguration.AccountKitConfigurationBuilder builder =
                new AccountKitConfiguration.AccountKitConfigurationBuilder(
                        LoginType.PHONE,
                        AccountKitActivity.ResponseType.TOKEN);
        mainActivityPresenter.sendAccountBuilder(builder);
    }

    @Override
    public void getAccountKitLoginResult(Intent data) {
        AccountKitLoginResult loginResult = data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);
        if(loginResult.getError() != null) {
            showErrorMessage(loginResult.getError().getErrorType().getMessage());
        } else if (loginResult.wasCancelled()) {
            showUnsuccessMessage("Login Cancelled");
        } else {
            //dialog.show();
            AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
                @Override
                public void onSuccess(Account account) {
                    onSuccessAccountKitLoginResult(account);
                }

                @Override
                public void onError(AccountKitError accountKitError) {
                   showErrorMessage("[ACCOUNT KIT ERROR]"+ accountKitError.getErrorType().getMessage());
                }
            });
        }
    }

    @Override
    public void onSuccessAccountKitLoginResult(Account account) {
        Disposable disposable = iRestaurantAPI.getUser(Common.API_KEY, account.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userModel -> {
                    //if user already in database
                    if(userModel.isSuccess()) {
                        Common.currentUser = userModel.getResult().get(0);
                        goToActivity(userModel.getResult().get(0), HomeActivity.class);
                    } else { //if user not registered
                        goToActivity(userModel.getResult().get(0), UpdateInfoActivity.class);
                    }
                }, throwable -> showThrowableMessage("[GET USER]"+throwable.getMessage()));
        bindToLifeCycle(disposable);
    }

    private void bindToLifeCycle(Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    @Override
    public void goToActivity(User currentUser, Class<?> activityClass) {
        mainActivityPresenter.goToActivity(currentUser, activityClass);
    }

    private void initAPI() {
        iRestaurantAPI = RetrofitClient.getInstance(Common.API_RESTAURANT_ENDPOINT).create(IRestaurantAPI.class);
    }

    @Override
    public void detachDisposable() {
        compositeDisposable.clear();
    }

    @Override
    public void showUnsuccessMessage(String message) {
        mainActivityPresenter.showUnsuccessMessage(message);
    }

    @Override
    public void showErrorMessage(String message) {
        mainActivityPresenter.showErrorMessage(message);
    }

    @Override
    public void showThrowableMessage(String message) {
        mainActivityPresenter.showThrowableMessage(message);
    }
}
