package com.karlosprojects.androidkarlosrestaurant.database;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

public class LocalCartDataSource implements CartDataSource {

    private  CartDao cartDao;

    public LocalCartDataSource(CartDao cartDao) {
        this.cartDao = cartDao;
    }

    @Override
    public Flowable<List<CartItem>> getAllCart(String userPhone, int restaurantId) {
        return cartDao.getAllCart(userPhone, restaurantId);
    }

    @Override
    public Single<Integer> countItemCart(String userPhone, int restaurantId) {
        return cartDao.countItemCart(userPhone, restaurantId);
    }

    @Override
    public Single<Long> sumPrice(String userPhone, int restaurantId) {
        return cartDao.sumPrice(userPhone, restaurantId);
    }

    @Override
    public Single<CartItem> getItemInCart(String foodId, String userPhone, int restaurantId) {
        return cartDao.getItemInCart(foodId, userPhone, restaurantId);
    }

    @Override
    public Completable insertOrReplaceAll(CartItem... cartItems) {
        return cartDao.insertOrReplaceAll(cartItems);
    }

    @Override
    public Single<Integer> updateCart(CartItem cartItem) {
        return cartDao.updateCart(cartItem);
    }

    @Override
    public Single<Integer> deleteCart(CartItem cartItem) {
        return cartDao.deleteCart(cartItem);
    }

    @Override
    public Single<Integer> clearCart(String userPhone, int restaurantId) {
        return cartDao.clearCart(userPhone, restaurantId);
    }
}
