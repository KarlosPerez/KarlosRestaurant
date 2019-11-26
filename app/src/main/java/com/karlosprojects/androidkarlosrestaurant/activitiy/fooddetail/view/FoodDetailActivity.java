package com.karlosprojects.androidkarlosrestaurant.activitiy.fooddetail.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.karlosprojects.androidkarlosrestaurant.R;
import com.karlosprojects.androidkarlosrestaurant.Retrofit.IRestaurantAPI;
import com.karlosprojects.androidkarlosrestaurant.Retrofit.RetrofitClient;
import com.karlosprojects.androidkarlosrestaurant.Utils.Common;
import com.karlosprojects.androidkarlosrestaurant.activitiy.fooddetail.presenter.FoodDetailPresenter;
import com.karlosprojects.androidkarlosrestaurant.adapter.AddonAdapter;
import com.karlosprojects.androidkarlosrestaurant.database.CartDataSource;
import com.karlosprojects.androidkarlosrestaurant.database.CartDatabase;
import com.karlosprojects.androidkarlosrestaurant.database.CartItem;
import com.karlosprojects.androidkarlosrestaurant.database.LocalCartDataSource;
import com.karlosprojects.androidkarlosrestaurant.model.AddonModel;
import com.karlosprojects.androidkarlosrestaurant.model.EventBus.AddOnEventChange;
import com.karlosprojects.androidkarlosrestaurant.model.EventBus.AddonLoadEvent;
import com.karlosprojects.androidkarlosrestaurant.model.EventBus.FoodDetailEvent;
import com.karlosprojects.androidkarlosrestaurant.model.EventBus.SizeLoadEvent;
import com.karlosprojects.androidkarlosrestaurant.model.Food;
import com.karlosprojects.androidkarlosrestaurant.model.Size;
import com.karlosprojects.androidkarlosrestaurant.model.SizeModel;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class FoodDetailActivity extends AppCompatActivity {

    @BindView(R.id.food_detail_fab_add_to_cart)
    FloatingActionButton food_detail_fab_add_to_cart;
    @BindView(R.id.food_detail_btn_view_cart)
    Button food_detail_btn_view_cart;
    @BindView(R.id.food_detail_txt_price)
    TextView food_detail_txt_price;
    @BindView(R.id.food_detail_rdi_food_size)
    RadioGroup food_detail_rdi_food_size;
    @BindView(R.id.food_detail_recycler_addon)
    RecyclerView food_detail_recycler_addon;
    @BindView(R.id.food_detail_txt_description)
    TextView food_detail_txt_description;
    @BindView(R.id.food_detail_img_food_detail)
    ImageView food_detail_img_food_detail;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    AlertDialog alertDialog;

    private FoodDetailPresenter foodDetailPresenter;
    private CartDataSource cartDataSource;
    private IRestaurantAPI iRestaurantAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    Food selectedFood;
    Double originalPrice;
    String sizeSelected;
    private double sizePrice=0.0;
    private double extraPrice;
    private Double addonPrice=0.0;

    @Override
    protected void onDestroy() {
        if(foodDetailPresenter != null)
            foodDetailPresenter.detachDisposable();
        super.onDestroy();
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
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        initComponents();
    }

    private void initComponents() {
        ButterKnife.bind(this);
        cartDataSource = new LocalCartDataSource(CartDatabase.getInstance(this).cartDao());

        alertDialog = new SpotsDialog.Builder().setContext(FoodDetailActivity.this).setCancelable(false).build();
        iRestaurantAPI = RetrofitClient.getInstance(Common.API_RESTAURANT_ENDPOINT).create(IRestaurantAPI.class);

        food_detail_fab_add_to_cart.setOnClickListener(v -> {
            CartItem cartItem = new CartItem();
            cartItem.setFoodId(selectedFood.getId());
            cartItem.setFoodName(selectedFood.getName());
            cartItem.setFoodPrice(selectedFood.getPrice());
            cartItem.setFoodImage(selectedFood.getImage());
            cartItem.setFoodQuantity(1);
            cartItem.setUserPhone(Common.currentUser.getUserPhone());
            cartItem.setRestaurantId(Common.currentRestaurant.getId());
            cartItem.setFoodSize(new Gson().toJson(Common.addonList));
            cartItem.setFoodAddon(sizeSelected);
            cartItem.setFoodExtraPrice(extraPrice);

            compositeDisposable.add(cartDataSource.insertOrReplaceAll(cartItem)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(() -> Toast.makeText(FoodDetailActivity.this, "Added to Cart", Toast.LENGTH_SHORT).show(),
                            throwable -> Toast.makeText(FoodDetailActivity.this, "[ADD CART] "+throwable.getMessage(), Toast.LENGTH_SHORT).show())
            );
        });
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void displayFoodDetail(FoodDetailEvent foodDetailEvent) {
        if(foodDetailEvent.isSuccess()) {
            toolbar.setTitle(foodDetailEvent.getFood().getName());
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            selectedFood = foodDetailEvent.getFood();
            originalPrice = foodDetailEvent.getFood().getPrice();

            food_detail_txt_price.setText(String.valueOf(originalPrice));
            food_detail_txt_description.setText(foodDetailEvent.getFood().getDescription());
            Picasso.get().load(foodDetailEvent.getFood().getImage()).into(food_detail_img_food_detail);

            if(foodDetailEvent.getFood().isSize() && foodDetailEvent.getFood().isAddon()) {
                //Load size and addon from server
                alertDialog.show();
                compositeDisposable.add(iRestaurantAPI.getSizeOfFood(Common.API_KEY, foodDetailEvent.getFood().getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(sizeModel -> {
                    //Send local event bus
                    EventBus.getDefault().post(new SizeLoadEvent(true, sizeModel.getResult()));
                    //Load addon after load size
                    alertDialog.show();
                    compositeDisposable.add(iRestaurantAPI.getAddonOfFood(Common.API_KEY, foodDetailEvent.getFood().getId())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(addonModel -> {
                                alertDialog.dismiss();
                                EventBus.getDefault().post(new AddonLoadEvent(true, addonModel.getReesult()));
                            }, throwable -> {
                                alertDialog.dismiss();
                                Toast.makeText(FoodDetailActivity.this, "[LOAD ADDON]" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                            }));
                }, throwable -> {
                    alertDialog.dismiss();
                    Toast.makeText(FoodDetailActivity.this, "[LOAD SIZE]"+ throwable.getMessage(), Toast.LENGTH_SHORT).show();
                }));
            } else {
                if(foodDetailEvent.getFood().isSize()) { // if food only have size
                    compositeDisposable.add(iRestaurantAPI.getSizeOfFood(Common.API_KEY, foodDetailEvent.getFood().getId())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(sizeModel -> {
                                //Send local event bus
                                EventBus.getDefault().post(new SizeLoadEvent(true, sizeModel.getResult()));

                            }, throwable -> {
                                alertDialog.dismiss();
                                Toast.makeText(FoodDetailActivity.this, "[LOAD SIZE]"+ throwable.getMessage(), Toast.LENGTH_SHORT).show();
                            }));
                }
                if(foodDetailEvent.getFood().isAddon()) {
                    compositeDisposable.add(iRestaurantAPI.getAddonOfFood(Common.API_KEY, foodDetailEvent.getFood().getId())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(addonModel -> {
                                alertDialog.dismiss();
                                EventBus.getDefault().post(new AddonLoadEvent(true, addonModel.getReesult()));
                            }, throwable -> {
                                alertDialog.dismiss();
                                Toast.makeText(FoodDetailActivity.this, "[LOAD ADDON]" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                            }));
                }
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void displaySize(SizeLoadEvent event) {
        if(event.isSuccess()) {
            //Create radio button base on size length
            for (Size size: event.getSizeList()) {
                RadioButton radioButton = new RadioButton(this);
                radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked) {
                            sizePrice = size.getExtraPrice();
                        } else {
                            sizePrice =- size.getExtraPrice();
                        }
                        calculatePrice();
                        sizeSelected = size.getDescription();
                    }
                });

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
                radioButton.setLayoutParams(params);
                radioButton.setText(size.getDescription());
                radioButton.setTag(size.getExtraPrice());

                food_detail_rdi_food_size.addView(radioButton);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void displayAddon(AddonLoadEvent event) {
        if(event.isSuccess()) {
            food_detail_recycler_addon.setHasFixedSize(true);
            food_detail_recycler_addon.setLayoutManager(new LinearLayoutManager(this));
            food_detail_recycler_addon.setAdapter(new AddonAdapter(FoodDetailActivity.this, event.getAddonList()));
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void priceChange(AddOnEventChange eventChange) {
        if(eventChange.isAdd()) {
            addonPrice += eventChange.getAddon().getExtraPrice();
        } else {
            addonPrice -= eventChange.getAddon().getExtraPrice();
        }
        calculatePrice();
    }

    private void calculatePrice() {
        extraPrice = 0.0;
        double newPrice;

        extraPrice += sizePrice;
        extraPrice += addonPrice;

        newPrice = originalPrice + extraPrice;

        food_detail_txt_price.setText(String.valueOf(newPrice));
    }
}
