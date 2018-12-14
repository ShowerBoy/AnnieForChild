package com.annie.annieforchild.ui.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.annie.annieforchild.R;

/**
 * Created by wanglei on 2018/8/8.
 */

public class ScheduleViewHolder extends RecyclerView.ViewHolder {
    public ImageView icon, image, delete;
    public TextView time, title, text, line;
    public RelativeLayout scheduleLayout, scheduleLayout1, titleLayout;

    public ScheduleViewHolder(View itemView) {
        super(itemView);
        icon = itemView.findViewById(R.id.schedule_icon);
        image = itemView.findViewById(R.id.schedule_title_image);
        time = itemView.findViewById(R.id.schedule_time);
        title = itemView.findViewById(R.id.schedule_title);
        delete = itemView.findViewById(R.id.schedule_delete);
        text = itemView.findViewById(R.id.schedule_title_text);
        line = itemView.findViewById(R.id.schedule_line);
        scheduleLayout = itemView.findViewById(R.id.schedule_relative);
        scheduleLayout1 = itemView.findViewById(R.id.schedule_relative1);
        titleLayout = itemView.findViewById(R.id.schedule_title_layout);
    }
}
