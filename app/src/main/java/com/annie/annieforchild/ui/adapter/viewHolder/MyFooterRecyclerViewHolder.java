package com.annie.annieforchild.ui.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.annie.annieforchild.R;

/**
 * Created by WangLei on 2018/2/11 0011
 */

public class MyFooterRecyclerViewHolder extends RecyclerView.ViewHolder {
    public TextView myfooterItemText;

    public MyFooterRecyclerViewHolder(View itemView) {
        super(itemView);
        myfooterItemText = itemView.findViewById(R.id.myfooter_item_text);
    }
}
