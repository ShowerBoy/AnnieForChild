package com.annie.annieforchild.ui.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.annie.annieforchild.R;

/**
 * Created by wanglei on 2018/8/16.
 */

public class MonthScheduleViewHolder extends RecyclerView.ViewHolder {
    public TextView date;
    public RecyclerView recycler;

    public MonthScheduleViewHolder(View itemView) {
        super(itemView);
        date = itemView.findViewById(R.id.schedule_date);
        recycler=itemView.findViewById(R.id.month_schedule_recycler);
    }
}
