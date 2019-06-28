package com.karlosprojects.androidkarlosrestaurant.SplashScreen.View;

import androidx.appcompat.app.AppCompatActivity;
import dmax.dialog.SpotsDialog;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.karlosprojects.androidkarlosrestaurant.MainActivity.Model.User;
import com.karlosprojects.androidkarlosrestaurant.MainActivity.View.MainActivity;
import com.karlosprojects.androidkarlosrestaurant.SplashScreen.Presenter.SplashScreenPresenter;
import com.karlosprojects.androidkarlosrestaurant.SplashScreen.Presenter.SplashScreenPresenterImpl;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class SplashScreen extends AppCompatActivity implements SplashScreenView {

    AlertDialog alertDialog;
    private SplashScreenPresenter splashScreenPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initComponents();
        handleDevicePermissions();
        delaySplashScreen();
    }

    private void initComponents() {
        splashScreenPresenter = new SplashScreenPresenterImpl(this);
        alertDialog = new SpotsDialog.Builder()
                .setContext(this)
                .setCancelable(false)
                .build();
    }

    private void delaySplashScreen() {
        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                startActivity(new Intent(SplashScreen.this, MainActivity.class));
            }
        }, 3000);*/
    }

    private void handleDevicePermissions() {
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
                            @Override
                            public void onSuccess(Account account) {
                                showProgressDialog();
                                getUserInformation(account);
                            }

                            @Override
                            public void onError(AccountKitError accountKitError) {
                                showErrorMessage("Not Signed in! Please sign in");
                                startActivity(new Intent(SplashScreen.this, MainActivity.class));
                                finish();
                            }
                        });
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        showErrorMessage("You must accept this permission to use our app");
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {}
                }).check();
    }

    @Override
    public void getUserInformation(Account account) {
        splashScreenPresenter.getUserInformation(account);
    }

    @Override
    public void goToActivity(User currentUser, Class<?> activityClass) {
        Intent intent = new Intent(SplashScreen.this, activityClass);
        startActivity(intent);
        finish();
    }

    ////////////////////////////////////////////////////////////////////////////
    @Override
    public void showSuccessMessage(String message) {

    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(SplashScreen.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showThrowableMessage(String message) {
        Toast.makeText(SplashScreen.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressDialog() {
        alertDialog.show();
    }

    @Override
    public void hideProgressDialog() {
        alertDialog.dismiss();
    }

    @Override
    public boolean isProgressDialogShowing() {
        return alertDialog.isShowing();
    }

    ////////////////////////////////////////////////////////////////////////////
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (splashScreenPresenter != null) {
            splashScreenPresenter.detachDisposable();
        }
    }

}
