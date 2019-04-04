package com.annie.annieforchild.ui.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.annie.annieforchild.R;

/**
 * Created by wanglei on 2019/4/3.
 */

public class MyOrderViewHolder extends RecyclerView.ViewHolder {
    public TextView date, title, suggest, price, state;
    public ImageView image;
    public Button confirm, cancel;
    public LinearLayout operateLayout;

    public MyOrderViewHolder(View itemView) {
        super(itemView);
        date = itemView.findViewById(R.id.my_order_date);
        title = itemView.findViewById(R.id.my_order_title);
        suggest = itemView.findViewById(R.id.my_order_suggest);
        price = itemView.findViewById(R.id.my_order_price);
        state = itemView.findViewById(R.id.my_order_state);
        image = itemView.findViewById(R.id.my_order_image);
        confirm = itemView.findViewById(R.id.my_order_confirm);
        cancel = itemView.findViewById(R.id.my_order_cancel);
        operateLayout = itemView.findViewById(R.id.my_order_operate);
    }
}
