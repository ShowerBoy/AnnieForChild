package com.annie.annieforchild.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.CheckDoubleClickListener;
import com.annie.annieforchild.Utils.OnCheckDoubleClick;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.material.Material;
import com.annie.annieforchild.bean.song.Song;
import com.annie.annieforchild.bean.task.Homework;
import com.annie.annieforchild.presenter.GrindEarPresenter;
import com.annie.annieforchild.ui.activity.lesson.AddOnlineScheActivity;
import com.annie.annieforchild.ui.activity.pk.PracticeActivity;
import com.annie.annieforchild.ui.adapter.viewHolder.TaskDetailsViewHolder;
import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by wanglei on 2018/8/23.
 */

public class TaskDetailsAdapter extends RecyclerView.Adapter<TaskDetailsViewHolder> {
    private Context context;
    private List<Homework> lists;
    private LayoutInflater inflater;
    private GrindEarPresenter presenter;
    private boolean isLike, isListen;
    private int taskid, state, likes, listen;

    public TaskDetailsAdapter(Context context, List<Homework> lists, GrindEarPresenter presenter, int taskid, int state) {
        this.context = context;
        this.lists = lists;
        this.presenter = presenter;
        this.state = state;
        this.taskid = taskid;
        isLike = false;
        isListen = false;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public TaskDetailsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        TaskDetailsViewHolder holder = null;
        holder = new TaskDetailsViewHolder(inflater.inflate(R.layout.activity_task_details_item, viewGroup, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(TaskDetailsViewHolder taskDetailsViewHolder, int i) {
        taskDetailsViewHolder.title.setText(lists.get(i).getBookname());
        taskDetailsViewHolder.requirement.setText(lists.get(i).getTaskrequirement() != null ? lists.get(i).getTaskrequirement() : "");
        Glide.with(context).load(lists.get(i).getBookimage()).error(R.drawable.image_recording_empty).into(taskDetailsViewHolder.image);
        if (lists.get(i).getIsjoinmaterial() == 0) {
            taskDetailsViewHolder.addCourse.setImageResource(R.drawable.image_add_course_t);
        } else {
            taskDetailsViewHolder.addCourse.setImageResource(R.drawable.image_add_course_f);
        }
        if (lists.get(i).getIsfinish() == 0) {
            taskDetailsViewHolder.finishBtn.setText("完成");
            taskDetailsViewHolder.finishBtn.setBackgroundResource(R.drawable.icon_orange_btn2);
        } else {
            taskDetailsViewHolder.finishBtn.setText("已完成");
            taskDetailsViewHolder.finishBtn.setBackgroundResource(R.drawable.icon_gray_btn);
        }
        if (lists.get(i).getLikes() == 1) {
            taskDetailsViewHolder.heart1.setImageResource(R.drawable.icon_heart_t);
            taskDetailsViewHolder.heart2.setImageResource(R.drawable.icon_heart_f);
            taskDetailsViewHolder.heart3.setImageResource(R.drawable.icon_heart_f);
        } else if (lists.get(i).getLikes() == 2) {
            taskDetailsViewHolder.heart1.setImageResource(R.drawable.icon_heart_t);
            taskDetailsViewHolder.heart2.setImageResource(R.drawable.icon_heart_t);
            taskDetailsViewHolder.heart3.setImageResource(R.drawable.icon_heart_f);
        } else if (lists.get(i).getLikes() == 3) {
            taskDetailsViewHolder.heart1.setImageResource(R.drawable.icon_heart_t);
            taskDetailsViewHolder.heart2.setImageResource(R.drawable.icon_heart_t);
            taskDetailsViewHolder.heart3.setImageResource(R.drawable.icon_heart_t);
        } else {
            taskDetailsViewHolder.heart1.setImageResource(R.drawable.icon_heart_f);
            taskDetailsViewHolder.heart2.setImageResource(R.drawable.icon_heart_f);
            taskDetailsViewHolder.heart3.setImageResource(R.drawable.icon_heart_f);
        }
        if (lists.get(i).getListen() == 1) {
            taskDetailsViewHolder.star.setImageResource(R.drawable.icon_star2_t);
            taskDetailsViewHolder.moon.setImageResource(R.drawable.icon_moon_f);
            taskDetailsViewHolder.sun.setImageResource(R.drawable.icon_sun_f);
        } else if (lists.get(i).getListen() == 2) {
            taskDetailsViewHolder.star.setImageResource(R.drawable.icon_star2_t);
            taskDetailsViewHolder.moon.setImageResource(R.drawable.icon_moon_t);
            taskDetailsViewHolder.sun.setImageResource(R.drawable.icon_sun_f);
        } else if (lists.get(i).getListen() == 3) {
            taskDetailsViewHolder.star.setImageResource(R.drawable.icon_star2_t);
            taskDetailsViewHolder.moon.setImageResource(R.drawable.icon_moon_t);
            taskDetailsViewHolder.sun.setImageResource(R.drawable.icon_sun_t);
        } else {
            taskDetailsViewHolder.star.setImageResource(R.drawable.icon_star2_f);
            taskDetailsViewHolder.moon.setImageResource(R.drawable.icon_moon_f);
            taskDetailsViewHolder.sun.setImageResource(R.drawable.icon_sun_f);
        }
        taskDetailsViewHolder.finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lists.get(i).getIsfinish() == 0) {
                    if (isLike && isListen) {
                        presenter.completeTask(taskid, likes, listen, lists.get(i).getHomeworkid());
                    } else {
                        SystemUtils.show(context, "请选择“喜欢程度”和“听的情况”");
                    }
                }
            }
        });
        taskDetailsViewHolder.heart1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lists.get(i).getIsfinish() == 0) {
                    isLike = true;
                    likes = 1;
                    taskDetailsViewHolder.heart1.setImageResource(R.drawable.icon_heart_t);
                    taskDetailsViewHolder.heart2.setImageResource(R.drawable.icon_heart_f);
                    taskDetailsViewHolder.heart3.setImageResource(R.drawable.icon_heart_f);
                }
            }
        });
        taskDetailsViewHolder.heart2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lists.get(i).getIsfinish() == 0) {
                    isLike = true;
                    likes = 2;
                    taskDetailsViewHolder.heart1.setImageResource(R.drawable.icon_heart_t);
                    taskDetailsViewHolder.heart2.setImageResource(R.drawable.icon_heart_t);
                    taskDetailsViewHolder.heart3.setImageResource(R.drawable.icon_heart_f);
                }
            }
        });
        taskDetailsViewHolder.heart3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lists.get(i).getIsfinish() == 0) {
                    isLike = true;
                    likes = 3;
                    taskDetailsViewHolder.heart1.setImageResource(R.drawable.icon_heart_t);
                    taskDetailsViewHolder.heart2.setImageResource(R.drawable.icon_heart_t);
                    taskDetailsViewHolder.heart3.setImageResource(R.drawable.icon_heart_t);
                }
            }
        });
        taskDetailsViewHolder.star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lists.get(i).getIsfinish() == 0) {
                    isListen = true;
                    listen = 1;
                    taskDetailsViewHolder.star.setImageResource(R.drawable.icon_star2_t);
                    taskDetailsViewHolder.moon.setImageResource(R.drawable.icon_moon_f);
                    taskDetailsViewHolder.sun.setImageResource(R.drawable.icon_sun_f);
                }
            }
        });
        taskDetailsViewHolder.moon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lists.get(i).getIsfinish() == 0) {
                    isListen = true;
                    listen = 2;
                    taskDetailsViewHolder.star.setImageResource(R.drawable.icon_star2_t);
                    taskDetailsViewHolder.moon.setImageResource(R.drawable.icon_moon_t);
                    taskDetailsViewHolder.sun.setImageResource(R.drawable.icon_sun_f);
                }
            }
        });
        taskDetailsViewHolder.sun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lists.get(i).getIsfinish() == 0) {
                    isListen = true;
                    listen = 3;
                    taskDetailsViewHolder.star.setImageResource(R.drawable.icon_star2_t);
                    taskDetailsViewHolder.moon.setImageResource(R.drawable.icon_moon_t);
                    taskDetailsViewHolder.sun.setImageResource(R.drawable.icon_sun_t);
                }
            }
        });
        taskDetailsViewHolder.linear.setOnClickListener(new CheckDoubleClickListener(new OnCheckDoubleClick() {
            @Override
            public void onCheckDoubleClick(View view) {
                if (lists.get(i).getBookid() != 0) {
                    Song song = new Song();
                    song.setBookId(lists.get(i).getBookid());
                    song.setBookImageUrl(lists.get(i).getBookimage());
                    song.setBookName(lists.get(i).getBookname());
                    Intent intent = new Intent(context, PracticeActivity.class);
                    intent.putExtra("song", song);
                    intent.putExtra("type", 0);
                    intent.putExtra("audioType", 3);
                    intent.putExtra("audioSource", 12);
                    intent.putExtra("homeworkid", lists.get(i).getHomeworkid());
                    int bookType;
                    if (lists.get(i).getType().equals("moerduo")) {
                        bookType = 0;
                    } else {
                        bookType = 1;
                    }
                    intent.putExtra("bookType", bookType);
                    context.startActivity(intent);
                }
            }
        }));
        taskDetailsViewHolder.addCourse.setOnClickListener(new CheckDoubleClickListener(new OnCheckDoubleClick() {
            @Override
            public void onCheckDoubleClick(View view) {
                if (lists.get(i).getIsjoinmaterial() == 0) {
                    //加入课表
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
                    String date = simpleDateFormat.format(new Date());
                    Material material = new Material();
                    material.setImageUrl(lists.get(i).getBookimage());
                    material.setMaterialId(lists.get(i).getBookid());
                    material.setName(lists.get(i).getBookname());
                    Intent intent = new Intent(context, AddOnlineScheActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("material", material);
                    bundle.putString("date", date);
                    bundle.putInt("audioType", 3);
                    bundle.putInt("audioSource", 12);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            }
        }));
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }
}
