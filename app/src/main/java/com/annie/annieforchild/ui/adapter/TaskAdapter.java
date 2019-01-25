package com.annie.annieforchild.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.CheckDoubleClickListener;
import com.annie.annieforchild.Utils.OnCheckDoubleClick;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.task.Task;
import com.annie.annieforchild.ui.activity.lesson.TaskContentActivity;
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
        if (lists.get(i).getTasktime() != null) {
            taskViewHolder.taskTimes.setText("第" + lists.get(i).getTasktime() + "次课");
        }
        if (lists.get(i).getWeek() != null) {
            taskViewHolder.taskTimes.setText("第" + lists.get(i).getWeek() + "周");
        }
        taskViewHolder.date.setText(lists.get(i).getDate());
        taskViewHolder.classname.setText("班级：" + lists.get(i).getClassname());
        taskViewHolder.teacher.setText("教师：" + lists.get(i).getTeacher());
        taskViewHolder.title.setText(lists.get(i).getTitle());
        taskViewHolder.flowerCard.setText("奖励花卡:" + lists.get(i).getFlowercard());
        if (lists.get(i).getStatus() == 0) {
            taskViewHolder.statusLayout.setVisibility(View.GONE);
        } else {
            taskViewHolder.statusLayout.setVisibility(View.VISIBLE);
        }
        String taskscore = lists.get(i).getTaskscore();
        int score = Integer.parseInt(taskscore);
        if (score >= 5) {
            taskViewHolder.star1.setImageResource(R.drawable.icon_starsmall);
            taskViewHolder.star2.setImageResource(R.drawable.icon_starsmall);
            taskViewHolder.star3.setImageResource(R.drawable.icon_starsmall);
            taskViewHolder.star4.setImageResource(R.drawable.icon_starsmall);
            taskViewHolder.star5.setImageResource(R.drawable.icon_starsmall);
        } else if (score == 4) {
            taskViewHolder.star1.setImageResource(R.drawable.icon_starsmall);
            taskViewHolder.star2.setImageResource(R.drawable.icon_starsmall);
            taskViewHolder.star3.setImageResource(R.drawable.icon_starsmall);
            taskViewHolder.star4.setImageResource(R.drawable.icon_starsmall);
            taskViewHolder.star5.setImageResource(R.drawable.icon_starsmall_un);
        } else if (score == 3) {
            taskViewHolder.star1.setImageResource(R.drawable.icon_starsmall);
            taskViewHolder.star2.setImageResource(R.drawable.icon_starsmall);
            taskViewHolder.star3.setImageResource(R.drawable.icon_starsmall);
            taskViewHolder.star4.setImageResource(R.drawable.icon_starsmall_un);
            taskViewHolder.star5.setImageResource(R.drawable.icon_starsmall_un);
        } else if (score == 2) {
            taskViewHolder.star1.setImageResource(R.drawable.icon_starsmall);
            taskViewHolder.star2.setImageResource(R.drawable.icon_starsmall);
            taskViewHolder.star3.setImageResource(R.drawable.icon_starsmall_un);
            taskViewHolder.star4.setImageResource(R.drawable.icon_starsmall_un);
            taskViewHolder.star5.setImageResource(R.drawable.icon_starsmall_un);
        } else if (score == 1) {
            taskViewHolder.star1.setImageResource(R.drawable.icon_starsmall);
            taskViewHolder.star2.setImageResource(R.drawable.icon_starsmall_un);
            taskViewHolder.star3.setImageResource(R.drawable.icon_starsmall_un);
            taskViewHolder.star4.setImageResource(R.drawable.icon_starsmall_un);
            taskViewHolder.star5.setImageResource(R.drawable.icon_starsmall_un);
        } else {
            taskViewHolder.star1.setImageResource(R.drawable.icon_starsmall_un);
            taskViewHolder.star2.setImageResource(R.drawable.icon_starsmall_un);
            taskViewHolder.star3.setImageResource(R.drawable.icon_starsmall_un);
            taskViewHolder.star4.setImageResource(R.drawable.icon_starsmall_un);
            taskViewHolder.star5.setImageResource(R.drawable.icon_starsmall_un);
        }

        taskViewHolder.remark.setOnClickListener(new CheckDoubleClickListener(new OnCheckDoubleClick() {
            @Override
            public void onCheckDoubleClick(View view) {
                //查看点评
                SystemUtils.setBackGray((Activity) context, true);
                SystemUtils.getTaskRemark(context, lists.get(i).getTaskscore(), lists.get(i).getAveragescore(), lists.get(i).getRemark()).showAtLocation(SystemUtils.popupView, Gravity.CENTER, 0, 0);
            }
        }));
        taskViewHolder.doTask.setOnClickListener(new CheckDoubleClickListener(new OnCheckDoubleClick() {
            @Override
            public void onCheckDoubleClick(View view) {
                Intent intent = new Intent(context, TaskContentActivity.class);
                intent.putExtra("classid", lists.get(i).getClassid());
                intent.putExtra("type", lists.get(i).getType());
                if (lists.get(i).getType() == 0) {
                    intent.putExtra("taskTime", lists.get(i).getTasktime());
                } else {
                    intent.putExtra("week", lists.get(i).getWeek());
                }
                intent.putExtra("tabPosition", -1);
                context.startActivity(intent);
            }
        }));
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }
}
