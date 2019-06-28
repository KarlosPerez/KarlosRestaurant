package com.karlosprojects.androidkarlosrestaurant.MainActivity.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dmax.dialog.SpotsDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;
import com.karlosprojects.androidkarlosrestaurant.HomeActivity.view.HomeActivity;
import com.karlosprojects.androidkarlosrestaurant.MainActivity.Model.UserModel;
import com.karlosprojects.androidkarlosrestaurant.R;
import com.karlosprojects.androidkarlosrestaurant.Retrofit.IRestaurantAPI;
import com.karlosprojects.androidkarlosrestaurant.Retrofit.RetrofitClient;
import com.karlosprojects.androidkarlosrestaurant.UpdateInfoActivity.view.UpdateInfoActivity;
import com.karlosprojects.androidkarlosrestaurant.Utils.Common;

public class MainActivity extends AppCompatActivity implements MainActivityView {

    IRestaurantAPI iRestaurantAPI;
    CompositeDisposable compositeDisposable;
    AlertDialog dialog;

    private static final int APP_REQUEST_CODE = 1234;
    @BindView(R.id.btn_sign_in)
    Button btn_sign_in;

    @OnClick(R.id.btn_sign_in)
    public void loginUser() {
        Intent intent = new Intent(this, AccountKitActivity.class);
        AccountKitConfiguration.AccountKitConfigurationBuilder builder =
                new AccountKitConfiguration.AccountKitConfigurationBuilder(LoginType.PHONE,
                        AccountKitActivity.ResponseType.TOKEN);
        intent.putExtra(AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,
                builder.build());
        startActivityForResult(intent, APP_REQUEST_CODE);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == APP_REQUEST_CODE) {
            AccountKitLoginResult loginResult = data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);
            String toastMessage;
            if(loginResult.getError() != null) {
                toastMessage = loginResult.getError().getErrorType().getMessage();
                Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show();
            } else if (loginResult.wasCancelled()) {
                toastMessage = "Login Cancelled";
                Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show();
            } else {
                dialog.show();
                AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
                    @Override
                    public void onSuccess(Account account) {
                        compositeDisposable.add(iRestaurantAPI.getUser(Common.API_KEY, account.getId())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<UserModel>() {
                            @Override
                            public void accept(UserModel userModel) throws Exception {
                                //if user already in database
                                if(userModel.isSuccess()) {
                                    Common.currentUser = userModel.getResult().get(0);
                                    startActivity(new Intent(MainActivity.this, HomeActivity.class));
                                    finish();
                                } else { //if user not registered
                                    startActivity(new Intent(MainActivity.this, UpdateInfoActivity.class));
                                    finish();
                                }
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                dialog.dismiss();
                                Toast.makeText(MainActivity.this, "[GET USER]"+throwable.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }));
                    }

                    @Override
                    public void onError(AccountKitError accountKitError) {
                        Toast.makeText(MainActivity.this, "[ACCOUNT KIT ERROR]"+ accountKitError.getErrorType().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        initAPI();
    }

    private void init() {
        ButterKnife.bind(this);
        dialog = new SpotsDialog.Builder()
                .setCancelable(false)
                .setContext(this)
                .build();
    }

    private void initAPI() {
        iRestaurantAPI = RetrofitClient.getInstance(Common.API_RESTAURANT_ENDPOINT).create(IRestaurantAPI.class);
    }
}
