package com.annie.annieforchild.ui.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.annie.annieforchild.R;

/**
 * Created by wanglei on 2018/8/22.
 */

public class TaskViewHolder extends RecyclerView.ViewHolder {
    public TextView date, title, classname, teacher, doTask;
    public RelativeLayout relative;

    public TaskViewHolder(View itemView) {
        super(itemView);
        date = itemView.findViewById(R.id.task_date);
        title = itemView.findViewById(R.id.task_title);
        classname = itemView.findViewById(R.id.task_class);
        teacher = itemView.findViewById(R.id.task_teacher);
        doTask = itemView.findViewById(R.id.do_task);
        relative = itemView.findViewById(R.id.task_relative);
    }
}
