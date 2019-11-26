package com.karlosprojects.androidkarlosrestaurant.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.karlosprojects.androidkarlosrestaurant.R;
import com.karlosprojects.androidkarlosrestaurant.Utils.Common;
import com.karlosprojects.androidkarlosrestaurant.model.Addon;
import com.karlosprojects.androidkarlosrestaurant.model.EventBus.AddOnEventChange;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class AddonAdapter extends RecyclerView.Adapter<AddonAdapter.AddonViewHolder> {

    Context context;
    List<Addon> addonList;

    public AddonAdapter(Context context, List<Addon> addonList) {
        this.context = context;
        this.addonList = addonList;
    }

    @NonNull
    @Override
    public AddonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AddonViewHolder(LayoutInflater.from(context).inflate(R.layout.item_addon_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AddonViewHolder holder, int position) {
        holder.ckb_addon.setText(new StringBuilder(addonList.get(position).getName())
                .append(" +(" + context.getString(R.string.money_sign))
                .append(addonList.get(position).getExtraPrice())
                .append(")"));

        holder.ckb_addon.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked) {
                Common.addonList.add(addonList.get(position));
                EventBus.getDefault().postSticky(new AddOnEventChange(true, addonList.get(position)));
            } else {
                Common.addonList.add(addonList.get(position));
                EventBus.getDefault().postSticky(new AddOnEventChange(false, addonList.get(position)));
            }
        });
    }

    @Override
    public int getItemCount() {
        return addonList.size();
    }

    public class AddonViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ckb_addon)
        CheckBox ckb_addon;

        Unbinder unbinder;

        public AddonViewHolder(@NonNull View itemView) {
            super(itemView);

            unbinder = ButterKnife.bind(this, itemView);
        }
    }
}
