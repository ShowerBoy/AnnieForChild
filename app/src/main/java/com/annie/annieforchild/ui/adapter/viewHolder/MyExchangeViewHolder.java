package com.annie.annieforchild.ui.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.annie.annieforchild.R;

/**
 * Created by WangLei on 2018/2/2 0002
 */

public class MyExchangeViewHolder extends RecyclerView.ViewHolder {
    public TextView exchange_name, exchange_time, exchange_value;

    public MyExchangeViewHolder(View itemView) {
        super(itemView);
        exchange_name = itemView.findViewById(R.id.item_exchange_name);
        exchange_time = itemView.findViewById(R.id.item_exchange_time);
        exchange_value = itemView.findViewById(R.id.item_exchange_value);
    }
}
