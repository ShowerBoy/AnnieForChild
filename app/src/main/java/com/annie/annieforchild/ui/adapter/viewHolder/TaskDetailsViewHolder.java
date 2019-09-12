package com.annie.annieforchild.ui.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.views.MyRoundImageView;

/**
 * Created by wanglei on 2018/8/23.
 */

public class TaskDetailsViewHolder extends RecyclerView.ViewHolder {
    public ImageView heart1, heart2, heart3, star, moon, sun, addCourse;
    public MyRoundImageView image;
    public TextView title, requirement, finishBtn, classify, chapter;
    public RelativeLayout linear;

    public TaskDetailsViewHolder(View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.task_details_image);
        heart1 = itemView.findViewById(R.id.heart_1);
        heart2 = itemView.findViewById(R.id.heart_2);
        heart3 = itemView.findViewById(R.id.heart_3);
        star = itemView.findViewById(R.id.task_star);
        moon = itemView.findViewById(R.id.task_moon);
        sun = itemView.findViewById(R.id.task_sun);
        title = itemView.findViewById(R.id.task_details_title);
        requirement = itemView.findViewById(R.id.task_details_requirement);
        addCourse = itemView.findViewById(R.id.task_add_course);
        finishBtn = itemView.findViewById(R.id.finish_task_btn);
        linear = itemView.findViewById(R.id.details_linear);
        classify = itemView.findViewById(R.id.task_details_classify);
        chapter=itemView.findViewById(R.id.task_chapter);
    }
}
