package com.annie.annieforchild.ui.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.annie.annieforchild.R;

/**
 * Created by wanglei on 2018/8/22.
 */

public class TaskViewHolder extends RecyclerView.ViewHolder {
    public TextView date, title, classname, teacher, doTask, taskTimes, flowerCard, remark;
    public ImageView star1, star2, star3, star4, star5;
    public RelativeLayout relative, statusLayout;

    public TaskViewHolder(View itemView) {
        super(itemView);
        date = itemView.findViewById(R.id.task_date);
        title = itemView.findViewById(R.id.task_title);
        classname = itemView.findViewById(R.id.task_class);
        teacher = itemView.findViewById(R.id.task_teacher);
        doTask = itemView.findViewById(R.id.do_task);
        taskTimes = itemView.findViewById(R.id.task_times);
        flowerCard = itemView.findViewById(R.id.task_flowercard);
        remark = itemView.findViewById(R.id.task_remark);
        star1 = itemView.findViewById(R.id.task_star_1);
        star2 = itemView.findViewById(R.id.task_star_2);
        star3 = itemView.findViewById(R.id.task_star_3);
        star4 = itemView.findViewById(R.id.task_star_4);
        star5 = itemView.findViewById(R.id.task_star_5);
        relative = itemView.findViewById(R.id.task_relative);
        statusLayout = itemView.findViewById(R.id.task_status_layout);
    }
}
