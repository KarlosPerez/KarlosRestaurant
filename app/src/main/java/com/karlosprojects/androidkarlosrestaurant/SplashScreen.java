package com.karlosprojects.androidkarlosrestaurant;

import androidx.appcompat.app.AppCompatActivity;
import dmax.dialog.SpotsDialog;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.karlosprojects.androidkarlosrestaurant.HomeActivity.HomeActivity;
import com.karlosprojects.androidkarlosrestaurant.MainActivity.Model.UserModel;
import com.karlosprojects.androidkarlosrestaurant.MainActivity.View.MainActivity;
import com.karlosprojects.androidkarlosrestaurant.Retrofit.IRestaurantAPI;
import com.karlosprojects.androidkarlosrestaurant.Retrofit.RetrofitClient;
import com.karlosprojects.androidkarlosrestaurant.Utils.Common;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class SplashScreen extends AppCompatActivity {

    IRestaurantAPI iRestaurantAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();

        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {

                        AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
                            @Override
                            public void onSuccess(Account account) {

                                alertDialog.show();

                                compositeDisposable.add(iRestaurantAPI.getUser(Common.API_KEY, account.getId())
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Consumer<UserModel>() {
                                    @Override
                                    public void accept(UserModel userModel) throws Exception {
                                        if(userModel.isSuccess()) {
                                            Common.currentUser = userModel.getResult().get(0);
                                            Intent homeIntent = new Intent(SplashScreen.this, HomeActivity.class);
                                            startActivity(homeIntent);
                                            finish();
                                        } else {
                                            Intent homeIntent = new Intent(SplashScreen.this, MainActivity.class);
                                            startActivity(homeIntent);
                                            finish();
                                        }
                                        alertDialog.dismiss();
                                    }
                                }, new Consumer<Throwable>() {
                                    @Override
                                    public void accept(Throwable throwable) throws Exception {
                                        Toast.makeText(SplashScreen.this, "[GET USER API]", Toast.LENGTH_SHORT).show();
                                        alertDialog.dismiss();
                                    }
                                }));

                            }

                            @Override
                            public void onError(AccountKitError accountKitError) {
                                Toast.makeText(SplashScreen.this, "Not Sign in! Please sign in", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SplashScreen.this, MainActivity.class));
                                finish();
                            }
                        });
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        Toast.makeText(SplashScreen.this, "You must accept this permission to use our app", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                    }
                }).check();

        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                startActivity(new Intent(SplashScreen.this, MainActivity.class));
            }
        }, 3000);*/


    }

    private void init() {
        alertDialog = new SpotsDialog.Builder()
                .setContext(this)
                .setCancelable(false)
                .build();
        iRestaurantAPI = RetrofitClient.getInstance(Common.API_RESTAURANT_ENDPOINT).create(IRestaurantAPI.class);
    }
}
