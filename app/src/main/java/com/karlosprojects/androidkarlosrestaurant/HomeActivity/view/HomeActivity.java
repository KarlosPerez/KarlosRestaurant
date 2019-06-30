package com.karlosprojects.androidkarlosrestaurant.HomeActivity.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.facebook.accountkit.AccountKit;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import com.google.android.material.navigation.NavigationView;
import com.karlosprojects.androidkarlosrestaurant.MainActivity.View.MainActivity;
import com.karlosprojects.androidkarlosrestaurant.R;
import com.karlosprojects.androidkarlosrestaurant.Retrofit.IRestaurantAPI;
import com.karlosprojects.androidkarlosrestaurant.Retrofit.RetrofitClient;
import com.karlosprojects.androidkarlosrestaurant.Services.PicassoImageLoadingService;
import com.karlosprojects.androidkarlosrestaurant.Utils.Common;
import com.karlosprojects.androidkarlosrestaurant.adapter.RestaurantListAdapter;
import com.karlosprojects.androidkarlosrestaurant.adapter.RestaurantSliderAdapter;
import com.karlosprojects.androidkarlosrestaurant.model.EventBus.RestaurantLoadEvent;
import com.karlosprojects.androidkarlosrestaurant.model.Restaurant;
import com.karlosprojects.androidkarlosrestaurant.model.RestaurantModel;

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
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import ss.com.bannerslider.Slider;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView txt_user_name, txt_user_phone;

    @BindView(R.id.banner_slider)
    Slider banner_slider;
    @BindView(R.id.recycler_restaurant)
    RecyclerView recycler_restaurant;

    private IRestaurantAPI iRestaurantAPI;
    private CompositeDisposable compositeDisposable;
    AlertDialog dialog;

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        txt_user_name = headerView.findViewById(R.id.txt_user_name);
        txt_user_phone = headerView.findViewById(R.id.txt_user_phone);

        txt_user_name.setText(Common.currentUser.getName());
        txt_user_phone.setText(Common.currentUser.getUserPhone());

        initComponents();
        initView();
        
        loadRestaurant();
    }

    private void loadRestaurant() {
        dialog.show();

        compositeDisposable.add(iRestaurantAPI.getRestaurant(Common.API_KEY)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<RestaurantModel>() {
            @Override
            public void accept(RestaurantModel restaurantModel) throws Exception {
                //Here, we will use EventBus to send local event set adapter and slider
                EventBus.getDefault().post(new RestaurantLoadEvent(true, restaurantModel.getResult()));
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                EventBus.getDefault().post(new RestaurantLoadEvent(false, throwable.getMessage()));
            }
        }));

    }

    private void initView() {
        ButterKnife.bind(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recycler_restaurant.setLayoutManager(layoutManager);
        recycler_restaurant.setHasFixedSize(true);
        recycler_restaurant.addItemDecoration(new DividerItemDecoration(this, layoutManager.getOrientation()));
    }

    private void initComponents() {
        dialog = new SpotsDialog.Builder().setContext(this).setCancelable(false).build();
        iRestaurantAPI = RetrofitClient.getInstance(Common.API_RESTAURANT_ENDPOINT).create(IRestaurantAPI.class);
        compositeDisposable = new CompositeDisposable();

        Slider.init(new PicassoImageLoadingService());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
    public boolean onNavigationItemSelected(MenuItem item) {
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void signOut() {
        AlertDialog confirmDialog = new AlertDialog.Builder(this).setTitle(R.string.hint_log_out)
                .setMessage("Do you really want to sign out?")
                .setNegativeButton("CANCEL", (dialog, which) -> dialog.dismiss())
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

    /*
     *REGISTER EVENT BUS
     */

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    //Listen EventBus
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void proccessRestaurantLoadEvent(RestaurantLoadEvent event) {
        if (event.isSuccess()) {
            displayBanner(event.getRestaurantList());
            displayRestaurant(event.getRestaurantList());
        } else {
            Toast.makeText(this, "[RESTAURANT LOAD] "+event.getMessage(), Toast.LENGTH_SHORT).show();
        }
        dialog.dismiss();
    }

    private void displayRestaurant(List<Restaurant> restaurantList) {
        RestaurantListAdapter adapter = new RestaurantListAdapter(this, restaurantList);
        recycler_restaurant.setAdapter(adapter);
    }

    private void displayBanner(List<Restaurant> restaurantList) {
        banner_slider.setAdapter(new RestaurantSliderAdapter(restaurantList));
    }
}
