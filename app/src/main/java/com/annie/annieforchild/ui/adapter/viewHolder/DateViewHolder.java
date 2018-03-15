package com.annie.annieforchild.ui.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.annie.annieforchild.R;

/**
 * Created by WangLei on 2018/2/28 0028
 */

public class DateViewHolder extends RecyclerView.ViewHolder {
    public TextView week, day, line;

    public DateViewHolder(View itemView) {
        super(itemView);
        week = itemView.findViewById(R.id.week_text);
        day = itemView.findViewById(R.id.day_text);
        line = itemView.findViewById(R.id.d_line);
    }
}
