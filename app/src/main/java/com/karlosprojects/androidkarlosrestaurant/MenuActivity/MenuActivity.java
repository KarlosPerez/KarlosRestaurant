package com.karlosprojects.androidkarlosrestaurant.MenuActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
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
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.karlosprojects.androidkarlosrestaurant.R;
import com.karlosprojects.androidkarlosrestaurant.Retrofit.IRestaurantAPI;
import com.karlosprojects.androidkarlosrestaurant.Retrofit.RetrofitClient;
import com.karlosprojects.androidkarlosrestaurant.Utils.Common;
import com.karlosprojects.androidkarlosrestaurant.Utils.SpaceItemDecoration;
import com.karlosprojects.androidkarlosrestaurant.adapter.CategoryAdapter;
import com.karlosprojects.androidkarlosrestaurant.model.EventBus.MenuItemEvent;
import com.karlosprojects.androidkarlosrestaurant.model.MenuModel;
import com.nex3z.notificationbadge.NotificationBadge;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Objects;

public class MenuActivity extends AppCompatActivity {

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

    private IRestaurantAPI iRestaurantAPI;
    private CompositeDisposable compositeDisposable;
    AlertDialog alertDialog;
    CategoryAdapter categoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        initComponents();
        initAPI();

        btn_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //implement later
            }
        });
    }

    private void initAPI() {
        iRestaurantAPI = RetrofitClient.getInstance(Common.API_RESTAURANT_ENDPOINT).create(IRestaurantAPI.class);
    }

    private void initComponents() {
        ButterKnife.bind(this);
        alertDialog = new SpotsDialog.Builder()
                .setCancelable(false)
                .setContext(this)
                .build();
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        //This code will select item view type
        //if item is last, it will set full width on Grid
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
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
        });
        recycler_category.setLayoutManager(layoutManager);
        recycler_category.addItemDecoration(new SpaceItemDecoration(8));
        //menuActivityPresenter = new MenuActivityPresenterImpl(this);
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
        //MenuActivityPresenter.detachDisposable();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        //MenuActivityPresenter.registerEvent();
        EventBus.getDefault().register(this);
        super.onResume();
    }

    @Override
    protected void onStop() {
        //MenuActivityPresenter.unregisterEvent();
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void loadMenuRestaurant(MenuItemEvent event) {
        if(event.isSuccess()) {
            Picasso.get().load(event.getRestaurant().getImage()).into(img_restaurant);
            toolbar.setTitle(event.getRestaurant().getName());

            setSupportActionBar(toolbar);
            Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            //request category by restaurant ID
            compositeDisposable.add(iRestaurantAPI.getCategories(
                    Common.API_KEY, event.getRestaurant().getId())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Consumer<MenuModel>() {
                @Override
                public void accept(MenuModel menuModel) throws Exception {
                    categoryAdapter = new CategoryAdapter(MenuActivity.this, menuModel.getResult());
                    recycler_category.setAdapter(categoryAdapter);
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {
                    Toast.makeText(MenuActivity.this, "[GET CATEGORY] "+throwable.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }));
        } else {

        }
    }
}
