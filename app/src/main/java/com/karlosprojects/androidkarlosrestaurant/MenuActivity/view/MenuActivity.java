package com.karlosprojects.androidkarlosrestaurant.MenuActivity.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.karlosprojects.androidkarlosrestaurant.MenuActivity.presenter.MenuActivityPresenter;
import com.karlosprojects.androidkarlosrestaurant.MenuActivity.presenter.MenuActivityPresenterImpl;
import com.karlosprojects.androidkarlosrestaurant.R;
import com.karlosprojects.androidkarlosrestaurant.Utils.Common;
import com.karlosprojects.androidkarlosrestaurant.Utils.SpaceItemDecoration;
import com.karlosprojects.androidkarlosrestaurant.adapter.CategoryAdapter;
import com.karlosprojects.androidkarlosrestaurant.model.MenuModel;
import com.nex3z.notificationbadge.NotificationBadge;
import com.squareup.picasso.Picasso;
import java.util.Objects;

public class MenuActivity extends AppCompatActivity implements MenuActivityView {

    @BindView(R.id.img_restaurant)
    ImageView img_restaurant;
    @BindView(R.id.recycler_category)
    RecyclerView recycler_category;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton btn_cart;
    @BindView(R.id.badge)
    NotificationBadge badge;

    AlertDialog alertDialog;
    CategoryAdapter categoryAdapter;
    MenuActivityPresenter menuActivityPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        initComponents();

        btn_cart.setOnClickListener(view -> {
            //implement later
        });
    }

    private void initComponents() {
        ButterKnife.bind(this);
        alertDialog = new SpotsDialog.Builder()
                .setCancelable(false)
                .setContext(this)
                .build();

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return placeMenuItemsInGrid(position);
                }
        });

        recycler_category.setLayoutManager(layoutManager);
        recycler_category.addItemDecoration(new SpaceItemDecoration(8));
        menuActivityPresenter = new MenuActivityPresenterImpl(this);
    }

    //This code will select item view type if item is last, it will set full width on Grid
    private int placeMenuItemsInGrid(int position) {
        if(categoryAdapter != null) {
            switch (categoryAdapter.getItemViewType(position)) {
                case Common.DEFAULT_COLUMN_COUNT: return 1;
                case Common.FULL_WIDTH_COLUMN: return 2;
                default: return -1;
            }
        } else {
            return -1;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        if (menuActivityPresenter != null)
            menuActivityPresenter.detachDisposable();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        menuActivityPresenter.registerEvent();
        super.onResume();
    }

    @Override
    protected void onStop() {
        menuActivityPresenter.unregisterEvent();
        super.onStop();
    }

    @Override
    public void showMenuRestaurantById(MenuModel menuModel) {
        categoryAdapter = new CategoryAdapter(MenuActivity.this, menuModel.getResult());
        recycler_category.setAdapter(categoryAdapter);
    }

    @Override
    public void loadRestaurantBanner(String imageUrl) {
        Picasso.get().load(imageUrl).into(img_restaurant);
    }

    @Override
    public void setupToolbar(String restaurantName) {
        toolbar.setTitle(restaurantName);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void showUnsuccessMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showThrowableMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressDialog() {
        alertDialog.show();
    }

    @Override
    public void hideProgressDialog() {
        alertDialog.hide();
    }

    @Override
    public boolean isProgressDialogShowing() {
        return alertDialog.isShowing();
    }
}
