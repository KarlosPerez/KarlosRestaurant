package com.karlosprojects.androidkarlosrestaurant.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.karlosprojects.androidkarlosrestaurant.activitiy.MenuActivity.view.MenuActivity;
import com.karlosprojects.androidkarlosrestaurant.R;
import com.karlosprojects.androidkarlosrestaurant.Utils.Common;
import com.karlosprojects.androidkarlosrestaurant.interfaces.IOnRecyclerViewClickListener;
import com.karlosprojects.androidkarlosrestaurant.model.EventBus.MenuItemEvent;
import com.karlosprojects.androidkarlosrestaurant.model.Restaurant;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class RestaurantListAdapter extends RecyclerView.Adapter<RestaurantListAdapter.RestaurantListViewHolder> {

    Context context;
    List<Restaurant> restaurantList;

    public RestaurantListAdapter(Context context, List<Restaurant> restaurantList) {
        this.context = context;
        this.restaurantList = restaurantList;
    }

    @NonNull
    @Override
    public RestaurantListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_restaurant_list, parent, false);
        return new RestaurantListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantListViewHolder holder, int position) {
        Picasso.get().load(restaurantList.get(position).getImage()).into(holder.img_restaurant);
        holder.txt_restaurant_address.setText(new StringBuilder(restaurantList.get(position).getAddress()));
        holder.txt_restaurant_name.setText(new StringBuilder(restaurantList.get(position).getName()));

        holder.setListener((view, position1) -> {
            Common.currentRestaurant = restaurantList.get(position1);
            //Here use postSticky, that mean this event will listen from other activity
            //it will different with just 'post'
            EventBus.getDefault().postSticky(new MenuItemEvent(true, restaurantList.get(position1)));
            context.startActivity(new Intent(context, MenuActivity.class));
        });
    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

    public class RestaurantListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.txt_restaurant_name)
        TextView txt_restaurant_name;
        @BindView(R.id.txt_restaurant_address)
        TextView txt_restaurant_address;
        @BindView(R.id.image_restaurant)
        ImageView img_restaurant;

        IOnRecyclerViewClickListener listener;

        public void setListener(IOnRecyclerViewClickListener listener) {
            this.listener = listener;
        }

        Unbinder unbinder;

        public RestaurantListViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onClick(view, getAdapterPosition());
        }
    }
}
