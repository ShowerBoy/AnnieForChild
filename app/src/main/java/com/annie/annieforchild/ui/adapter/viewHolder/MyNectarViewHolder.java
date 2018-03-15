package com.annie.annieforchild.ui.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.annie.annieforchild.R;

/**
 * Created by WangLei on 2018/3/7 0007
 */

public class MyNectarViewHolder extends RecyclerView.ViewHolder {
    public TextView incomeName, incomeDate, incomeTime, incomeCount;

    public MyNectarViewHolder(View itemView) {
        super(itemView);
        incomeName = itemView.findViewById(R.id.nectar_income_name);
        incomeDate = itemView.findViewById(R.id.nectar_income_date);
        incomeTime = itemView.findViewById(R.id.nectar_income_time);
        incomeCount = itemView.findViewById(R.id.nectar_income_count);
    }
}
