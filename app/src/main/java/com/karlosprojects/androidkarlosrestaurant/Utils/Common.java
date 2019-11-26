package com.karlosprojects.androidkarlosrestaurant.Utils;

import com.karlosprojects.androidkarlosrestaurant.activitiy.MainActivity.Model.User;
import com.karlosprojects.androidkarlosrestaurant.model.Addon;
import com.karlosprojects.androidkarlosrestaurant.model.Restaurant;

import java.util.AbstractList;
import java.util.HashSet;
import java.util.Set;

public class Common {
    public static final String API_RESTAURANT_ENDPOINT = "http://192.168.1.6:3000/";
    public static final String API_KEY = "1234"; //Temporary
    public static final int DEFAULT_COLUMN_COUNT = 0;
    public static final int FULL_WIDTH_COLUMN = 1;
    public static User currentUser;
    public static Restaurant currentRestaurant;
    public static Set<Addon> addonList = new HashSet<>();
}