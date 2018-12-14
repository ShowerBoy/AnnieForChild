package com.annie.annieforchild.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.annie.annieforchild.R;
import com.annie.annieforchild.bean.task.Task;
import com.annie.annieforchild.ui.activity.lesson.TaskDetailsActivity;
import com.annie.annieforchild.ui.adapter.viewHolder.TaskViewHolder;

import java.util.List;

/**
 * Created by wanglei on 2018/8/22.
 */

public class TaskAdapter extends RecyclerView.Adapter<TaskViewHolder> {
    private Context context;
    private List<Task> lists;
    private LayoutInflater inflater;

    public TaskAdapter(Context context, List<Task> lists) {
        this.context = context;
        this.lists = lists;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        TaskViewHolder holder = null;
        holder = new TaskViewHolder(inflater.inflate(R.layout.activity_task_item, viewGroup, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(TaskViewHolder taskViewHolder, int i) {
        taskViewHolder.date.setText(lists.get(i).getDate());
        taskViewHolder.classname.setText("班级：" + lists.get(i).getClassname());
        taskViewHolder.teacher.setText("教师：" + lists.get(i).getTeacher());
        taskViewHolder.title.setText(lists.get(i).getTitle());
        if (lists.get(i).getStatus() == 0) {
            taskViewHolder.doTask.setText("做作业");
            taskViewHolder.doTask.setBackgroundResource(R.drawable.icon_orange_btn2);
        } else {
            taskViewHolder.doTask.setText("已提交");
            taskViewHolder.doTask.setBackgroundResource(R.drawable.icon_green_btn);
        }
        taskViewHolder.relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lists.get(i).getStatus() == 0) {
                    Intent intent = new Intent(context, TaskDetailsActivity.class);
                    intent.putExtra("taskid", lists.get(i).getTaskid());
                    intent.putExtra("state", 0);
                    context.startActivity(intent);
                } else {
                    Intent intent = new Intent(context, TaskDetailsActivity.class);
                    intent.putExtra("taskid", lists.get(i).getTaskid());
                    intent.putExtra("state", 1);
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }
}
