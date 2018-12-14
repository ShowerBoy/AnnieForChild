package com.annie.annieforchild.ui.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.annie.annieforchild.R;

/**
 * Created by wanglei on 2018/10/22.
 */

public class NetDetailsViewHolder extends RecyclerView.ViewHolder {
    public TextView week, tag, lesson1, lesson2, lesson3, lesson4;
    public RelativeLayout relative;
    public ImageView image;

    public NetDetailsViewHolder(View itemView) {
        super(itemView);
        week = itemView.findViewById(R.id.net_details_week);
        tag = itemView.findViewById(R.id.net_details_tag);
        relative = itemView.findViewById(R.id.details_relative);
        lesson1 = itemView.findViewById(R.id.lesson_1);
        lesson2 = itemView.findViewById(R.id.lesson_2);
        lesson3 = itemView.findViewById(R.id.lesson_3);
        lesson4 = itemView.findViewById(R.id.lesson_4);
        image = itemView.findViewById(R.id.net_details_image);
    }
}
