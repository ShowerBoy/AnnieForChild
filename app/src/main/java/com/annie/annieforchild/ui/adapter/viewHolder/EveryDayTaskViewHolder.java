package com.annie.annieforchild.ui.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.annie.annieforchild.R;

/**
 * Created by wanglei on 2019/5/8.
 */

public class EveryDayTaskViewHolder extends RecyclerView.ViewHolder {
    public ImageView image;

    public EveryDayTaskViewHolder(View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.every_day_image);
    }
}
