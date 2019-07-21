package com.karlosprojects.androidkarlosrestaurant.MenuActivity.repository;

import com.karlosprojects.androidkarlosrestaurant.MenuActivity.presenter.MenuActivityPresenter;
import com.karlosprojects.androidkarlosrestaurant.Retrofit.IRestaurantAPI;
import com.karlosprojects.androidkarlosrestaurant.Retrofit.RetrofitClient;
import com.karlosprojects.androidkarlosrestaurant.Utils.Common;
import com.karlosprojects.androidkarlosrestaurant.database.CartDataSource;
import com.karlosprojects.androidkarlosrestaurant.model.EventBus.MenuItemEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.reactivex.SingleObserver;
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

    @Override
    public void getCountCartByRestaurant(CartDataSource cartDataSource) {

        cartDataSource.countItemCart(Common.currentUser.getUserPhone(),
                Common.currentRestaurant.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Integer integer) {
                        displayCartItemCountOnBadge(String.valueOf(integer));
                    }

                    @Override
                    public void onError(Throwable e) {
                        showThrowableMessage("[COUNT CART] "+e.getMessage());
                    }
                });
    }

    private void displayCartItemCountOnBadge(String itemCount) {
        menuActivityPresenter.showCartItemCountOnBadge(itemCount);
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
