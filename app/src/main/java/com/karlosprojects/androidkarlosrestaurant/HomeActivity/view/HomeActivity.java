package com.karlosprojects.androidkarlosrestaurant.HomeActivity.view;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import com.facebook.accountkit.AccountKit;

import android.view.View;

import com.google.android.material.navigation.NavigationView;
import com.karlosprojects.androidkarlosrestaurant.HomeActivity.presenter.HomeActivityPresenter;
import com.karlosprojects.androidkarlosrestaurant.HomeActivity.presenter.HomeActivityPresenterImpl;
import com.karlosprojects.androidkarlosrestaurant.MainActivity.View.MainActivity;
import com.karlosprojects.androidkarlosrestaurant.R;
import com.karlosprojects.androidkarlosrestaurant.Retrofit.IRestaurantAPI;
import com.karlosprojects.androidkarlosrestaurant.Retrofit.RetrofitClient;
import com.karlosprojects.androidkarlosrestaurant.Services.PicassoImageLoadingService;
import com.karlosprojects.androidkarlosrestaurant.Utils.Common;
import com.karlosprojects.androidkarlosrestaurant.adapter.RestaurantListAdapter;
import com.karlosprojects.androidkarlosrestaurant.adapter.RestaurantSliderAdapter;
import com.karlosprojects.androidkarlosrestaurant.model.Restaurant;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;
import io.reactivex.disposables.CompositeDisposable;
import ss.com.bannerslider.Slider;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, HomeActivityView {

    TextView txt_user_name, txt_user_phone;

    @BindView(R.id.banner_slider)
    Slider banner_slider;
    @BindView(R.id.recycler_restaurant)
    RecyclerView recycler_restaurant;

    AlertDialog alertDialog;
    HomeActivityPresenter homeActivityPresenter;

    @Override
    protected void onDestroy() {
        if(homeActivityPresenter != null)
            homeActivityPresenter.detachDisposable();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initComponents();

        homeActivityPresenter = new HomeActivityPresenterImpl(this);
        getRestaurantInfo();

    }

    public void getRestaurantInfo() {
        if(!alertDialog.isShowing()) showProgressDialog();
        homeActivityPresenter.getRestaurantInfo();
    }

    private void initComponents() {
        ButterKnife.bind(this);
        alertDialog = new SpotsDialog.Builder().setContext(this).setCancelable(false).build();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        drawer.closeDrawer(GravityCompat.START);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);

        txt_user_name = headerView.findViewById(R.id.txt_user_name);
        txt_user_phone = headerView.findViewById(R.id.txt_user_phone);

        txt_user_name.setText(Common.currentUser.getName());
        txt_user_phone.setText(Common.currentUser.getUserPhone());

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recycler_restaurant.setLayoutManager(layoutManager);
        recycler_restaurant.setHasFixedSize(true);
        recycler_restaurant.addItemDecoration(new DividerItemDecoration(this, layoutManager.getOrientation()));

        Slider.init(new PicassoImageLoadingService());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_log_out) {
            signOut();
        } else if (id == R.id.nav_nearby) {
            signOut();
        } else if (id == R.id.nav_order_history) {
            signOut();
        } else if (id == R.id.nav_update_info) {
            signOut();
        }

        return true;
    }

    public void signOut() {
        AlertDialog confirmDialog = new AlertDialog.Builder(this).setTitle(R.string.hint_log_out)
                .setMessage("Do you really want to sign out?")
                .setNegativeButton("CANCEL", (dialog, which) -> hideProgressDialog())
                .setPositiveButton("OK", (dialog, which) -> {
                    Common.currentUser = null;
                    AccountKit.logOut();
                    Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }).create();

        confirmDialog.show();
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
    public void showUnsuccessMessage(String message) {
        Toast.makeText(HomeActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showThrowableMessage(String message) {
        Toast.makeText(HomeActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    /*
     *REGISTER EVENT BUS
     */
    @Override
    protected void onResume() {
        super.onResume();
        homeActivityPresenter.registerEvent();
    }

    @Override
    protected void onStop() {
        if(homeActivityPresenter != null) {
            homeActivityPresenter.detachDisposable();
            homeActivityPresenter.unregisterEvent();
        }
        super.onStop();
    }

    public void displayRestaurant(List<Restaurant> restaurantList) {
        RestaurantListAdapter adapter = new RestaurantListAdapter(this, restaurantList);
        recycler_restaurant.setAdapter(adapter);
    }

    public void displayBanner(List<Restaurant> restaurantList) {
        banner_slider.setAdapter(new RestaurantSliderAdapter(restaurantList));
    }
}
