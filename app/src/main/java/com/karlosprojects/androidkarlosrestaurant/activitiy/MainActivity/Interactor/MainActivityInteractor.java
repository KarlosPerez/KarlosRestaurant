package com.karlosprojects.androidkarlosrestaurant.activitiy.MainActivity.Interactor;

import android.content.Intent;

public interface MainActivityInteractor {
    void validateUser();
    void getAccountKitLoginResult(Intent data);

    void detachDisposable();
}
