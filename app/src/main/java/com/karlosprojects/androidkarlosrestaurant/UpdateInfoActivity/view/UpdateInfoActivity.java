package com.karlosprojects.androidkarlosrestaurant.UpdateInfoActivity.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.karlosprojects.androidkarlosrestaurant.HomeActivity.view.HomeActivity;
import com.karlosprojects.androidkarlosrestaurant.MainActivity.Model.UpdateUserModel;
import com.karlosprojects.androidkarlosrestaurant.MainActivity.Model.User;
import com.karlosprojects.androidkarlosrestaurant.MainActivity.Model.UserModel;
import com.karlosprojects.androidkarlosrestaurant.R;
import com.karlosprojects.androidkarlosrestaurant.Retrofit.IRestaurantAPI;
import com.karlosprojects.androidkarlosrestaurant.Retrofit.RetrofitClient;
import com.karlosprojects.androidkarlosrestaurant.SplashScreen.View.SplashScreen;
import com.karlosprojects.androidkarlosrestaurant.UpdateInfoActivity.presenter.UpdateInfoActivityPresenter;
import com.karlosprojects.androidkarlosrestaurant.UpdateInfoActivity.presenter.UpdateInfoActivityPresenterImpl;
import com.karlosprojects.androidkarlosrestaurant.Utils.Common;

public class UpdateInfoActivity extends AppCompatActivity implements UpdateInfoActivityView {

    IRestaurantAPI iRestaurantAPI;
    CompositeDisposable compositeDisposable;
    AlertDialog dialog;
    UpdateInfoActivityPresenter updateInfoActivityPresenter;

    @BindView(R.id.edt_user_name)
    EditText edt_user_name;
    @BindView(R.id.edt_user_address)
    EditText edt_user_address;
    @BindView(R.id.btn_update)
    Button btn_update;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onDestroy() {
        updateInfoActivityPresenter.detachDisposable();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_info);
        initComponents();

        btn_update.setOnClickListener(v -> AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
            @Override
            public void onSuccess(Account account) {
                updateUserInfo(
                        account,
                        edt_user_name.getText().toString(),
                        edt_user_address.getText().toString());
            }

            @Override
            public void onError(AccountKitError accountKitError) {
                showErrorMessage("[ACCOUNT KIT ERROR]");
            }
        }));
    }

    @Override
    public void updateUserInfo(Account account, String user_name, String user_address) {
        updateInfoActivityPresenter.updateUserInfo(account, user_name, user_address);
    }

    @Override
    public void goToActivity(User currentUser, Class<?> activityClass) {
        Intent intent = new Intent(UpdateInfoActivity.this, activityClass);
        startActivity(intent);
        finish();
    }

    @Override
    public void showSuccessMessage(String message) {
        Toast.makeText(UpdateInfoActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showUnsuccessMessage(String message) {
        Toast.makeText(UpdateInfoActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(UpdateInfoActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showThrowableMessage(String message) {
        Toast.makeText(UpdateInfoActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home) {
            finish(); //close this activity
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initComponents() {
        ButterKnife.bind(this);
        updateInfoActivityPresenter = new UpdateInfoActivityPresenterImpl(this);
        dialog = new SpotsDialog.Builder()
                .setCancelable(false)
                .setContext(this)
                .build();

        toolbar.setTitle(getString(R.string.update_information));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }


}
