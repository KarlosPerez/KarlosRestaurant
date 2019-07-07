package com.karlosprojects.androidkarlosrestaurant.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.karlosprojects.androidkarlosrestaurant.R;
import com.karlosprojects.androidkarlosrestaurant.interfaces.IFoodDetailOnCartClickListener;
import com.karlosprojects.androidkarlosrestaurant.model.Food;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FoodListAdapter extends RecyclerView.Adapter<FoodListAdapter.FoodListViewHolder> {

    Context context;
    List<Food> foodList;

    public FoodListAdapter(Context context, List<Food> foodList) {
        this.context = context;
        this.foodList = foodList;
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
                Toast.makeText(context, "Detail Click", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Cart Clic", Toast.LENGTH_SHORT).show();
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

        public void setListener(IFoodDetailOnCartClickListener listener) {
            this.listener = listener;
        }

        Unbinder unbinder;

        public FoodListViewHolder(@NonNull View itemView) {

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
