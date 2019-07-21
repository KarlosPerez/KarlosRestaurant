package com.karlosprojects.androidkarlosrestaurant.activitiy.foodlist.repository;

import com.karlosprojects.androidkarlosrestaurant.model.EventBus.FoodListEvent;

public interface FoodListActivityRepository {

    void loadFoodListByCategory(FoodListEvent event);
    void displayFoodList(FoodListEvent event);

    void showUnsuccessMessage(String message);
    void showThrowableMessage(String message);

    void registerEvent();
    void unregisterEvent();

}
