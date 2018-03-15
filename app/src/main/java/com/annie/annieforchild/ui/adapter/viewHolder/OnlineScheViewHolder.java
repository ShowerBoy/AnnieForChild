package com.annie.annieforchild.ui.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.annie.annieforchild.R;

/**
 * Created by WangLei on 2018/3/1 0001
 */

public class OnlineScheViewHolder extends RecyclerView.ViewHolder{
    public TextView line, scheduleTime, scheduleName;
    public RelativeLayout selectSpot;

    public OnlineScheViewHolder(View itemView) {
        super(itemView);
        line = itemView.findViewById(R.id.sche_line2);
        scheduleTime = itemView.findViewById(R.id.online_schedule_time);
        scheduleName = itemView.findViewById(R.id.online_schedule_name);
        selectSpot = itemView.findViewById(R.id.select_spot2);
    }
}
