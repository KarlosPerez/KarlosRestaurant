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

public interface CartDao {
    /**
     * Only load cart by restaraunt ID because each restaurant id will hace order receipt different
     * Each restaurant have different link payment, so we can't make 1 cart for all
     */
    @Query("SELECT * FROM Cart WHERE userPhone=:userPhone AND restaurantId=:restaurantId")
    Flowable<List<CartItem>> getAllCart (String userPhone, int restaurantId);

    @Query("SELECT COUNT (*) FROM Cart WHERE userPhone=:userPhone and restaurantId=:restaurantId")
    Single<Integer> countItemCart (String userPhone, int restaurantId);

    @Query("SELECT SUM(foodPrice*foodQuantity)+(foodExtraPrice*foodQuantity) FROM Cart WHERE userPhone=:userPhone and restaurantId=:restaurantId")
    Single<Long> sumPrice (String userPhone, int restaurantId);

    @Query("SELECT * from Cart WHERE foodId=:foodId and userPhone=:userPhone and restaurantId=:restaurantId")
    Single<CartItem> getItemInCart (String foodId, String userPhone, int restaurantId);

    @Insert(onConflict = OnConflictStrategy.REPLACE) //If conflict foodId, we will update information
    Completable insertOrReplaceAll (CartItem... cartItems);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    Single<Integer> updateCart(CartItem cartItem);

    @Delete
    Single<Integer> deleteCart(CartItem cartItem);

    @Query("DELETE FROM Cart WHERE userPhone=:userPhone AND restaurantId=:restaurantId")
    Single<Integer> clearCart(String userPhone, int restaurantId);
}
