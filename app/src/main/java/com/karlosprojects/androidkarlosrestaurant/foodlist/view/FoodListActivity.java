package com.karlosprojects.androidkarlosrestaurant.foodlist.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.karlosprojects.androidkarlosrestaurant.R;
import com.karlosprojects.androidkarlosrestaurant.adapter.FoodListAdapter;
import com.karlosprojects.androidkarlosrestaurant.foodlist.presenter.FoodListActivityPresenter;
import com.karlosprojects.androidkarlosrestaurant.foodlist.presenter.FoodListActivityPresenterImpl;
import com.karlosprojects.androidkarlosrestaurant.model.FoodModel;
import com.squareup.picasso.Picasso;
import java.util.Objects;

public class FoodListActivity extends AppCompatActivity implements FoodListActivityView {

    @BindView(R.id.img_category)
    ImageView img_category;
    @BindView(R.id.recycler_food_list)
    RecyclerView recycler_food_list;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    AlertDialog alertDialog;
    private FoodListActivityPresenter foodListActivityPresenter;

    @Override
    protected void onDestroy() {
        if(foodListActivityPresenter != null)
            foodListActivityPresenter.detachDisposable();
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        foodListActivityPresenter.onRegisterEvent();
        super.onStart();
    }

    @Override
    protected void onStop() {
        if(foodListActivityPresenter != null) {
            foodListActivityPresenter.onUnregisterEvent();
            foodListActivityPresenter.detachDisposable();
        }
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        initComponents();
    }

    private void initComponents() {
        ButterKnife.bind(this);

        alertDialog = new SpotsDialog.Builder().setContext(FoodListActivity.this).setCancelable(false).build();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recycler_food_list.setHasFixedSize(true);
        recycler_food_list.setLayoutManager(layoutManager);
        recycler_food_list.addItemDecoration(new DividerItemDecoration(this, layoutManager.getOrientation()));

        foodListActivityPresenter = new FoodListActivityPresenterImpl(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showBannerImage(String image) {
        Picasso.get().load(image).into(img_category);
    }

    @Override
    public void showToolbarTitle(String title) {
        toolbar.setTitle(title);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public void showFoodList(FoodModel foodList) {
        FoodListAdapter adapter = new FoodListAdapter(FoodListActivity.this, foodList.getResult());
        recycler_food_list.setAdapter(adapter);
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
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showThrowableMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
