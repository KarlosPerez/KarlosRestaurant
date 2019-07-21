package com.karlosprojects.androidkarlosrestaurant.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Cart")
public class CartItem {
    @PrimaryKey
    @NonNull
    @ColumnInfo (name = "foodId")
    private int foodId;

    @ColumnInfo (name = "foodName")
    private String foodName;

    @ColumnInfo (name = "foodImage")
    private String foodImage;

    @ColumnInfo (name = "foodPrice")
    private double foodPrice;

    @ColumnInfo (name = "foodQuantity")
    private int foodQuantity;

    @ColumnInfo (name = "userPhone")
    private String userPhone;

    @ColumnInfo (name = "restaurantId")
    private int restaurantId;

    @ColumnInfo (name = "foodAddon")
    private String foodAddon;

    @ColumnInfo (name = "foodExtraPrice")
    private double foodExtraPrice;

    public CartItem() {
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodImage() {
        return foodImage;
    }

    public void setFoodImage(String foodImage) {
        this.foodImage = foodImage;
    }

    public double getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(double foodPrice) {
        this.foodPrice = foodPrice;
    }

    public int getFoodQuantity() {
        return foodQuantity;
    }

    public void setFoodQuantity(int foodQuantity) {
        this.foodQuantity = foodQuantity;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getFoodAddon() {
        return foodAddon;
    }

    public void setFoodAddon(String foodAddon) {
        this.foodAddon = foodAddon;
    }

    public double getFoodExtraPrice() {
        return foodExtraPrice;
    }

    public void setFoodExtraPrice(double foodExtraPrice) {
        this.foodExtraPrice = foodExtraPrice;
    }
}
