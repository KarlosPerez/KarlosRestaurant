package com.karlosprojects.androidkarlosrestaurant.database;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

public interface CartDataSource {

    Flowable<List<CartItem>> getAllCart (String userPhone, int restaurantId);
    Single<Integer> countItemCart (String userPhone, int restaurantId);
    Single<Long> sumPrice (String userPhone, int restaurantId);
    Single<CartItem> getItemInCart (String foodId, String userPhone, int restaurantId);
    Completable insertOrReplaceAll (CartItem... cartItems);
    Single<Integer> updateCart(CartItem cartItem);
    Single<Integer> deleteCart(CartItem cartItem);
    Single<Integer> clearCart(String userPhone, int restaurantId);
}
