package com.annie.annieforchild.ui.adapter.viewHolder;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.annie.annieforchild.R;

/**
 * Created by zr on 2019/1/14.
 */

public class NetExperienceDetailsViewHolder extends RecyclerView.ViewHolder {
    public TextView week,lesson_1_name,lesson_2_name;
    public LinearLayout lesson1, lesson2, lesson3, lesson4;
    public ConstraintLayout details_relative;
    public ImageView lesson_2_img;

    public NetExperienceDetailsViewHolder(View itemView) {
        super(itemView);
        lesson_2_img=itemView.findViewById(R.id.lesson_2_img);
        details_relative=itemView.findViewById(R.id.details_relative);
        week = itemView.findViewById(R.id.net_details_week);
        lesson_1_name = itemView.findViewById(R.id.lesson_1_name);
        lesson_2_name = itemView.findViewById(R.id.lesson_2_name);
        lesson1 = itemView.findViewById(R.id.lesson_1);
        lesson2 = itemView.findViewById(R.id.lesson_2);
        lesson3 = itemView.findViewById(R.id.lesson_3);
        lesson4 = itemView.findViewById(R.id.lesson_4);
    }
}
