package com.karlosprojects.androidkarlosrestaurant.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.karlosprojects.androidkarlosrestaurant.R;
import com.karlosprojects.androidkarlosrestaurant.Utils.Common;
import com.karlosprojects.androidkarlosrestaurant.foodlist.view.FoodListActivity;
import com.karlosprojects.androidkarlosrestaurant.interfaces.IOnRecyclerViewClickListener;
import com.karlosprojects.androidkarlosrestaurant.model.Category;
import com.karlosprojects.androidkarlosrestaurant.model.EventBus.FoodListEvent;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryAdapterViewHolder> {

    Context context;
    List<Category> categoryList;

    public CategoryAdapter(Context context, List<Category> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public CategoryAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_category_list, parent, false);
        return new CategoryAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapterViewHolder holder, int position) {
        Picasso.get().load(categoryList.get(position).getImage()).into(holder.img_category);
        holder.txt_category.setText(categoryList.get(position).getName());

        holder.setListener((view, position1) -> {
            //Send sticky post event to foodListActivity
            EventBus.getDefault().postSticky(new FoodListEvent(true, categoryList.get(position1)));
            context.startActivity(new Intent(context, FoodListActivity.class));
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class CategoryAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.img_category)
        ImageView img_category;
        @BindView(R.id.txt_category)
        TextView txt_category;

        IOnRecyclerViewClickListener listener;

        Unbinder unbinder;

        public void setListener(IOnRecyclerViewClickListener listener) {
            this.listener = listener;
        }

        public CategoryAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onClick(view, getAdapterPosition());
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(categoryList.size() == 1)
            return Common.DEFAULT_COLUMN_COUNT;
        else {
            if(categoryList.size() % 2 == 0)
                return Common.DEFAULT_COLUMN_COUNT;
            else {
                return (position > 1 && position == categoryList.size() -1)
                        ? Common.FULL_WIDTH_COLUMN : Common.DEFAULT_COLUMN_COUNT;
            }
        }
    }
}
