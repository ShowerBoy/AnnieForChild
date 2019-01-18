package com.annie.annieforchild.ui.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.annie.annieforchild.R;

/**
 * Created by zr on 2019/1/14.
 */

public class NetExperienceDetailsViewHolder extends RecyclerView.ViewHolder {
    public TextView week, lesson1, lesson2, lesson3, lesson4;

    public NetExperienceDetailsViewHolder(View itemView) {
        super(itemView);
        week = itemView.findViewById(R.id.net_details_week);
        lesson1 = itemView.findViewById(R.id.lesson_1);
        lesson2 = itemView.findViewById(R.id.lesson_2);
        lesson3 = itemView.findViewById(R.id.lesson_3);
        lesson4 = itemView.findViewById(R.id.lesson_4);
    }
}
