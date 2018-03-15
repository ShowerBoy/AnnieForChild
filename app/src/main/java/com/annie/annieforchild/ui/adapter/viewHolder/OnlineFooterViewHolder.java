package com.annie.annieforchild.ui.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.annie.annieforchild.R;

/**
 * Created by WangLei on 2018/3/1 0001
 */

public class OnlineFooterViewHolder extends RecyclerView.ViewHolder {
    public RelativeLayout addSchedule;

    public OnlineFooterViewHolder(View itemView) {
        super(itemView);
        addSchedule = itemView.findViewById(R.id.add_schedule);
    }
}
