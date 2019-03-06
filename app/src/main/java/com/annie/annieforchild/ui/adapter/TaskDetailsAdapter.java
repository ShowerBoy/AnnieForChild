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
import com.annie.annieforchild.bean.task.TaskBean;
import com.annie.annieforchild.bean.task.TaskFade;
import com.annie.annieforchild.presenter.GrindEarPresenter;
import com.annie.annieforchild.ui.activity.VideoActivity;
import com.annie.annieforchild.ui.activity.lesson.AddOnlineScheActivity;
import com.annie.annieforchild.ui.activity.pk.PracticeActivity;
import com.annie.annieforchild.ui.adapter.viewHolder.TaskDetailsViewHolder;
import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wanglei on 2018/8/23.
 */

public class TaskDetailsAdapter extends RecyclerView.Adapter<TaskDetailsViewHolder> {
    private Context context;
    private List<Homework> lists;
    private List<TaskFade> homeworkList;
    private LayoutInflater inflater;
    private GrindEarPresenter presenter;
    //    private boolean isLike, isListen;
    private int taskid, type, position;

    public TaskDetailsAdapter(Context context, List<Homework> lists, GrindEarPresenter presenter, int taskid, int type) {
        this.context = context;
        this.lists = lists;
        this.presenter = presenter;
        this.taskid = taskid;
        this.type = type;
        homeworkList = new ArrayList<>();
        for (int i = 0; i < lists.size(); i++) {
            TaskFade taskFade = new TaskFade();
            taskFade.setLike(false);
            taskFade.setListen(false);
            homeworkList.add(taskFade);
        }
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
        taskDetailsViewHolder.classify.setText(lists.get(i).getBookClassify() != null ? lists.get(i).getBookClassify() : "");
        taskDetailsViewHolder.requirement.setText(lists.get(i).getTaskrequirement() != null ? lists.get(i).getTaskrequirement() : "");
        Glide.with(context).load(lists.get(i).getBookimage()).error(R.drawable.image_recording_empty).into(taskDetailsViewHolder.image);
        taskDetailsViewHolder.chapter.setText(lists.get(i).getChaptertitle() != null ? lists.get(i).getChaptertitle() : "");
        if (lists.get(i).getBookid() == 0) {
            taskDetailsViewHolder.addCourse.setVisibility(View.GONE);
        } else {
            taskDetailsViewHolder.addCourse.setVisibility(View.VISIBLE);
        }
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
                    if (homeworkList.get(i).isLike() && homeworkList.get(i).isListen()) {
                        position = i;
                        presenter.completeTask(taskid, type, homeworkList.get(i).getLikes(), homeworkList.get(i).getListens(), lists.get(i).getHomeworkid());
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
//                    isLike = true;
//                    likes = 1;
                    homeworkList.get(i).setLike(true);
                    homeworkList.get(i).setLikes(1);
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
//                    isLike = true;
//                    likes = 2;
                    homeworkList.get(i).setLike(true);
                    homeworkList.get(i).setLikes(2);
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
//                    isLike = true;
//                    likes = 3;
                    homeworkList.get(i).setLike(true);
                    homeworkList.get(i).setLikes(3);
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
//                    isListen = true;
//                    listen = 1;
                    homeworkList.get(i).setListen(true);
                    homeworkList.get(i).setListens(1);
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
//                    isListen = true;
//                    listen = 2;
                    homeworkList.get(i).setListen(true);
                    homeworkList.get(i).setListens(2);
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
//                    isListen = true;
//                    listen = 3;
                    homeworkList.get(i).setListen(true);
                    homeworkList.get(i).setListens(3);
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
                    intent.putExtra("homeworktype", type);
                    int bookType;
                    int iType;
                    if (lists.get(i).getType().equals("moerduo")) {
                        bookType = 0;
                        iType = 0;
                    } else if (lists.get(i).getType().equals("yuedu")) {
                        bookType = 1;
                        iType = 1;
                    } else {
                        bookType = 1;
                        iType = 2;
                    }
                    intent.putExtra("bookType", bookType);
                    intent.putExtra("iType", iType);
                    context.startActivity(intent);
                } else {
                    if (lists.get(i).getAnimationUrl() != null && lists.get(i).getAnimationUrl().length() != 0) {
                        //TODO：播放动画
                        Intent intent = new Intent(context, VideoActivity.class);
                        intent.putExtra("url", lists.get(i).getAnimationUrl());
                        intent.putExtra("imageUrl", lists.get(i).getBookimage());
                        intent.putExtra("name", lists.get(i).getBookname());
                        intent.putExtra("id", lists.get(i).getBookid());
                        context.startActivity(intent);
                    } else {

                    }
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

    public List<TaskFade> getHomeworkList() {
        return homeworkList;
    }

    public void setHomeworkList(List<TaskFade> homeworkList) {
        this.homeworkList = homeworkList;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
