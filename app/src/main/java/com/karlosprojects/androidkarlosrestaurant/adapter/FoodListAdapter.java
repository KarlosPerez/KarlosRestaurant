package com.karlosprojects.androidkarlosrestaurant.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.karlosprojects.androidkarlosrestaurant.R;
import com.karlosprojects.androidkarlosrestaurant.Utils.Common;
import com.karlosprojects.androidkarlosrestaurant.activitiy.fooddetail.view.FoodDetailActivity;
import com.karlosprojects.androidkarlosrestaurant.database.CartDataSource;
import com.karlosprojects.androidkarlosrestaurant.database.CartDatabase;
import com.karlosprojects.androidkarlosrestaurant.database.CartItem;
import com.karlosprojects.androidkarlosrestaurant.database.LocalCartDataSource;
import com.karlosprojects.androidkarlosrestaurant.interfaces.IFoodDetailOnCartClickListener;
import com.karlosprojects.androidkarlosrestaurant.model.EventBus.FoodDetailEvent;
import com.karlosprojects.androidkarlosrestaurant.model.Food;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.EventBusBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class FoodListAdapter extends RecyclerView.Adapter<FoodListAdapter.FoodListViewHolder> {

    private Context context;
    private List<Food> foodList;
    private CompositeDisposable compositeDisposable;
    private CartDataSource cartDataSource;

    public void onStop() {
        compositeDisposable.clear();
    }

    public FoodListAdapter(Context context, List<Food> foodList) {
        this.context = context;
        this.foodList = foodList;
        compositeDisposable = new CompositeDisposable();
        cartDataSource = new LocalCartDataSource(CartDatabase.getInstance(context).cartDao());
    }

    @NonNull
    @Override
    public FoodListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FoodListViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_food_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FoodListViewHolder holder, int position) {
        Picasso.get().load(foodList.get(position).getImage())
                .placeholder(R.drawable.app_icon).into(holder.img_food);
        holder.txt_food_name.setText(foodList.get(position).getName());
        holder.txt_food_price.setText(new StringBuilder(context.getString(R.string.money_sign))
        .append(foodList.get(position).getPrice()));

        holder.setListener((view, position1, isDetail) -> {
            if(isDetail) {
                context.startActivity(new Intent(context, FoodDetailActivity.class));
                EventBus.getDefault().postSticky(new FoodDetailEvent(true, foodList.get(position)));
            } else {
                //Cart create
                CartItem cartItem = new CartItem();
                cartItem.setFoodId(foodList.get(position).getId());
                cartItem.setFoodName(foodList.get(position).getName());
                cartItem.setFoodPrice(foodList.get(position).getPrice());
                cartItem.setFoodImage(foodList.get(position).getImage());
                cartItem.setFoodQuantity(1);
                cartItem.setUserPhone(Common.currentUser.getUserPhone());
                cartItem.setRestaurantId(Common.currentRestaurant.getId());
                cartItem.setFoodSize("NORMAL");
                cartItem.setFoodAddon("NORMAL");
                cartItem.setFoodExtraPrice(0.0);

                compositeDisposable.add(cartDataSource.insertOrReplaceAll(cartItem)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> Toast.makeText(context, "Added to Cart", Toast.LENGTH_SHORT).show(),
                        throwable -> Toast.makeText(context, "[ADD CART] "+throwable.getMessage(), Toast.LENGTH_SHORT).show())
                );
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public class FoodListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.img_food)
        ImageView img_food;
        @BindView(R.id.txt_food_name)
        TextView txt_food_name;
        @BindView(R.id.txt_food_price)
        TextView txt_food_price;
        @BindView(R.id.img_detail)
        ImageView img_detail;
        @BindView(R.id.img_cart)
        ImageView img_add_cart;

        IFoodDetailOnCartClickListener listener;

        void setListener(IFoodDetailOnCartClickListener listener) {
            this.listener = listener;
        }

        Unbinder unbinder;

        FoodListViewHolder(@NonNull View itemView) {

            super(itemView);
            unbinder = ButterKnife.bind(this, itemView);

            img_detail.setOnClickListener(this);
            img_add_cart.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(view.getId() == R.id.img_detail) {
                listener.onFoodItemClickListener(view, getAdapterPosition(), true);
            } else if(view.getId() == R.id.img_cart) {
                listener.onFoodItemClickListener(view, getAdapterPosition(), false);
            }
        }
    }
}
