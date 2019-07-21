package com.karlosprojects.androidkarlosrestaurant.activitiy.MainActivity.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dmax.dialog.SpotsDialog;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.karlosprojects.androidkarlosrestaurant.activitiy.MainActivity.Model.User;
import com.karlosprojects.androidkarlosrestaurant.activitiy.MainActivity.Presenter.MainActivityPresenter;
import com.karlosprojects.androidkarlosrestaurant.activitiy.MainActivity.Presenter.MainActivityPresenterImpl;
import com.karlosprojects.androidkarlosrestaurant.R;

public class MainActivity extends AppCompatActivity implements MainActivityView {

    AlertDialog alertDialog;
    Intent intent;
    private MainActivityPresenter mainActivityPresenter;

    private static final int APP_REQUEST_CODE = 1234;

    @BindView(R.id.btn_sign_in)
    Button btn_sign_in;

    @OnClick(R.id.btn_sign_in)
    public void validateUser() {
        intent = new Intent(this, AccountKitActivity.class);
        mainActivityPresenter.validateUser();
    }

    @Override
    public void goToActivity(User currentUser, Class<?> activityClass) {
        startActivity(new Intent(MainActivity.this, activityClass));
        finish();
    }

    @Override
    public void getAccountBuilder(AccountKitConfiguration.AccountKitConfigurationBuilder builder) {
        intent.putExtra(AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,
                builder.build());
        startActivityForResult(intent, APP_REQUEST_CODE);
    }

    @Override
    public void showUnsuccessMessage(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showThrowableMessage(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onStop() {
        if(mainActivityPresenter != null)
            mainActivityPresenter.detachDisposable();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        if(mainActivityPresenter != null)
            mainActivityPresenter.detachDisposable();
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == APP_REQUEST_CODE) {
            mainActivityPresenter.getAccountKitLoginResult(data);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
    }

    private void initComponents() {
        ButterKnife.bind(this);
        alertDialog = new SpotsDialog.Builder()
                .setCancelable(false)
                .setContext(this)
                .build();
        mainActivityPresenter = new MainActivityPresenterImpl(this);
    }

}
