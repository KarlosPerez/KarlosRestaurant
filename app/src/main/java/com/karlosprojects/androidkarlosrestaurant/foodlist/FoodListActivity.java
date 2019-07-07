package com.karlosprojects.androidkarlosrestaurant.foodlist;

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

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.karlosprojects.androidkarlosrestaurant.R;
import com.karlosprojects.androidkarlosrestaurant.Retrofit.IRestaurantAPI;
import com.karlosprojects.androidkarlosrestaurant.Retrofit.RetrofitClient;
import com.karlosprojects.androidkarlosrestaurant.Utils.Common;
import com.karlosprojects.androidkarlosrestaurant.adapter.FoodListAdapter;
import com.karlosprojects.androidkarlosrestaurant.model.EventBus.FoodListEvent;
import com.karlosprojects.androidkarlosrestaurant.model.FoodModel;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Objects;

public class FoodListActivity extends AppCompatActivity {

    CompositeDisposable disposable;
    IRestaurantAPI iRestaurantAPI;
    AlertDialog alertDialog;

    @BindView(R.id.img_category)
    ImageView img_category;
    @BindView(R.id.recycler_food_list)
    RecyclerView recycler_food_list;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onDestroy() {
        disposable.clear();
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        EventBus.getDefault().register(this);
        EventBus.getDefault().unregister(this);
        super.onStart();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void loadFoodListByCategory(FoodListEvent event) {
        if (event.isSuccess()) {
            Picasso.get().load(event.getCategory().getImage()).into(img_category);
            toolbar.setTitle(event.getCategory().getName());

            setSupportActionBar(toolbar);
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

            alertDialog.show();

            disposable.add(iRestaurantAPI.getFoodOfMenu(Common.API_KEY, event.getCategory().getId())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(foodModel -> {
                if(foodModel.isSuccess()) {
                    alertDialog.dismiss();
                    FoodListAdapter adapter = new FoodListAdapter(FoodListActivity.this, foodModel.getResult());
                    recycler_food_list.setAdapter(adapter);
                } else {
                    alertDialog.dismiss();
                    Toast.makeText(FoodListActivity.this, "[GET FOOD RESULT]"+foodModel.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }, throwable -> {
                alertDialog.dismiss();
                Toast.makeText(FoodListActivity.this, "[GET FOOD] "+ throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }));
        }
    }

    @Override
    protected void onStop() {
        disposable.clear();
        super.onStop();
    }

    private void initAPI() {
        iRestaurantAPI = RetrofitClient.getInstance(Common.API_RESTAURANT_ENDPOINT).create(IRestaurantAPI.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        initComponents();
        initAPI();
    }

    private void initComponents() {
        ButterKnife.bind(this);
        disposable = new CompositeDisposable();
        alertDialog = new SpotsDialog.Builder().setContext(FoodListActivity.this).setCancelable(false).build();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recycler_food_list.setHasFixedSize(true);
        recycler_food_list.setLayoutManager(layoutManager);
        recycler_food_list.addItemDecoration(new DividerItemDecoration(this, layoutManager.getOrientation()));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
