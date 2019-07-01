package com.karlosprojects.androidkarlosrestaurant.Utils;

import com.karlosprojects.androidkarlosrestaurant.MainActivity.Model.User;
import com.karlosprojects.androidkarlosrestaurant.model.Restaurant;

public class Common {
    public static final String API_RESTAURANT_ENDPOINT = "http://192.168.1.6:3000/";
    public static final String API_KEY = "1234"; //Temporary
    public static final int DEFAULT_COLUMN_COUNT = 0;
    public static final int FULL_WIDTH_COLUMN = 1;
    public static User currentUser;
    public static Restaurant currentRestaurant;
}