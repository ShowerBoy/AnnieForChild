package com.annie.annieforchild.ui.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.annie.annieforchild.R;

import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.schedule.TotalSchedule;
import com.annie.annieforchild.bean.song.Song;
import com.annie.annieforchild.presenter.SchedulePresenter;
import com.annie.annieforchild.ui.activity.pk.BookPlayActivity2;
import com.annie.annieforchild.ui.activity.pk.PracticeActivity;
import com.annie.annieforchild.ui.adapter.viewHolder.ScheduleViewHolder;
import com.bumptech.glide.Glide;

import java.io.Serializable;

/**
 * Created by wanglei on 2018/8/8.
 */

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleViewHolder> {
    private Context context;
    private TotalSchedule totalSchedule;
    private LayoutInflater inflater;
    private SchedulePresenter presenter;
    private int tag;

    public ScheduleAdapter(Context context, TotalSchedule totalSchedule, SchedulePresenter presenter, int tag) {
        this.context = context;
        this.totalSchedule = totalSchedule;
        this.presenter = presenter;
        this.tag = tag;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ScheduleViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        ScheduleViewHolder viewHolder = null;
        viewHolder = new ScheduleViewHolder(inflater.inflate(R.layout.activity_schedule_item, viewGroup, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ScheduleViewHolder scheduleViewHolder, int i) {
        if (totalSchedule.getOffline() != null && totalSchedule.getOffline().size() != 0) {
            if (totalSchedule.getOffline().size() > i) {
                scheduleViewHolder.scheduleLayout.setVisibility(View.VISIBLE);
                scheduleViewHolder.scheduleLayout1.setVisibility(View.VISIBLE);
                scheduleViewHolder.line.setVisibility(View.VISIBLE);
                scheduleViewHolder.icon.setImageResource(R.drawable.icon_small_orange_circle);
                scheduleViewHolder.time.setText(totalSchedule.getOffline().get(i).getStart() + "-" + totalSchedule.getOffline().get(i).getStop());
                scheduleViewHolder.title.setText("线下课程");
                scheduleViewHolder.delete.setVisibility(View.GONE);
                scheduleViewHolder.titleLayout.setVisibility(View.GONE);
                scheduleViewHolder.text.setText(totalSchedule.getOffline().get(i).getDetail());
            } else {
                scheduleViewHolder.titleLayout.setVisibility(View.VISIBLE);
                scheduleViewHolder.scheduleLayout.setVisibility(View.VISIBLE);
                scheduleViewHolder.scheduleLayout1.setVisibility(View.VISIBLE);
                i = i - totalSchedule.getOffline().size();
                if (totalSchedule.getOnline() != null && totalSchedule.getOnline().size() != 0) {
                    if (totalSchedule.getOnline().size() > i) {
                        scheduleViewHolder.icon.setImageResource(R.drawable.icon_small_green_circle);
                        scheduleViewHolder.time.setText(totalSchedule.getOnline().get(i).getStart() + "-" + totalSchedule.getOnline().get(i).getStop());
                        scheduleViewHolder.title.setText("线上课程");
                        scheduleViewHolder.line.setVisibility(View.VISIBLE);
                        scheduleViewHolder.delete.setVisibility(View.GONE);
                        Glide.with(context).load(totalSchedule.getOnline().get(i).getBookImageUrl()).into(scheduleViewHolder.image);
                        scheduleViewHolder.text.setText(totalSchedule.getOnline().get(i).getDetail());
                    } else {
                        i = i - totalSchedule.getOnline().size();
                        if (totalSchedule.getFamily() != null && totalSchedule.getFamily().size() != 0) {
                            if (totalSchedule.getFamily().size() > i) {
                                scheduleViewHolder.icon.setImageResource(R.drawable.icon_small_red_circle);
                                scheduleViewHolder.time.setText(totalSchedule.getFamily().get(i).getStart() + "-" + totalSchedule.getFamily().get(i).getStop());
                                scheduleViewHolder.title.setText("自选课表");
                                scheduleViewHolder.line.setVisibility(View.VISIBLE);
                                if (tag == 0) {
                                    scheduleViewHolder.delete.setVisibility(View.VISIBLE);
                                } else {
                                    scheduleViewHolder.delete.setVisibility(View.GONE);
                                }
                                int finalI1 = i;
                                scheduleViewHolder.delete.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        SystemUtils.GeneralDialog(context, "删除课表").setMessage("确定删除此课表？")
                                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        presenter.deleteSchedule(totalSchedule.getFamily().get(finalI1).getScheduleId());
                                                    }
                                                })
                                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.dismiss();
                                                    }
                                                })
                                                .show();
                                    }
                                });
                                Glide.with(context).load(totalSchedule.getFamily().get(i).getBookImageUrl()).into(scheduleViewHolder.image);
                                scheduleViewHolder.text.setText(totalSchedule.getFamily().get(i).getDetail());
                                int finalI = i;
                                scheduleViewHolder.scheduleLayout.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Song song = new Song();
                                        song.setBookName(totalSchedule.getFamily().get(finalI).getDetail());
                                        song.setBookImageUrl(totalSchedule.getFamily().get(finalI).getBookImageUrl());
                                        song.setBookId(totalSchedule.getFamily().get(finalI).getBookId());

                                        if (totalSchedule.getFamily().get(finalI).getAudioType() == 1) {
                                            if (totalSchedule.getFamily().get(finalI).getAudioSource() == 8) {
                                                Intent intent = new Intent(context, BookPlayActivity2.class);
                                                intent.putExtra("bookId", song.getBookId());
                                                intent.putExtra("imageUrl", song.getBookImageUrl());
                                                intent.putExtra("audioType", totalSchedule.getFamily().get(finalI).getAudioType());
                                                intent.putExtra("audioSource", totalSchedule.getFamily().get(finalI).getAudioSource());
                                                intent.putExtra("title", song.getBookName());
                                                context.startActivity(intent);
                                            } else {
                                                int musicPosition = 0;
                                                for (int j = 0; j < totalSchedule.getMoerduolist().size(); j++) {
                                                    if (totalSchedule.getMoerduolist().get(j).getBookId() == totalSchedule.getFamily().get(finalI).getBookId()) {
                                                        musicPosition = j;
                                                    }
                                                }
                                                Intent intent = new Intent(context, PracticeActivity.class);
                                                intent.putExtra("song", song);
                                                intent.putExtra("type", 0);
                                                intent.putExtra("audioType", totalSchedule.getFamily().get(finalI).getAudioType());
                                                intent.putExtra("audioSource", totalSchedule.getFamily().get(finalI).getAudioSource());
                                                intent.putExtra("collectType", totalSchedule.getFamily().get(finalI).getAudioType() + 1);
                                                intent.putExtra("songList", (Serializable) totalSchedule.getMoerduolist());
                                                intent.putExtra("musicPosition", musicPosition);
                                                intent.putExtra("bookType", 1);
                                                context.startActivity(intent);
                                            }
                                        } else {
                                            int musicPosition = 0;
                                            for (int j = 0; j < totalSchedule.getMoerduolist().size(); j++) {
                                                if (totalSchedule.getMoerduolist().get(j).getBookId() == totalSchedule.getFamily().get(finalI).getBookId()) {
                                                    musicPosition = j;
                                                }
                                            }
                                            Intent intent = new Intent(context, PracticeActivity.class);
                                            intent.putExtra("song", song);
                                            intent.putExtra("type", 0);
                                            intent.putExtra("audioType", totalSchedule.getFamily().get(finalI).getAudioType());
                                            intent.putExtra("audioSource", totalSchedule.getFamily().get(finalI).getAudioSource());
                                            intent.putExtra("collectType", totalSchedule.getFamily().get(finalI).getAudioType() + 1);
                                            intent.putExtra("songList", (Serializable) totalSchedule.getMoerduolist());
                                            intent.putExtra("musicPosition", musicPosition);
                                            intent.putExtra("bookType", 0);
                                            context.startActivity(intent);
                                        }
                                    }
                                });
                            } else {
                                i = i - totalSchedule.getFamily().size();
                                if (totalSchedule.getTeacher() != null && totalSchedule.getTeacher().size() != 0) {
                                    scheduleViewHolder.time.setText(totalSchedule.getTeacher().get(i).getStart() + "-" + totalSchedule.getTeacher().get(i).getStop());
                                    scheduleViewHolder.icon.setImageResource(R.drawable.icon_small_blue_circle);
                                    scheduleViewHolder.title.setText("教师作业");
                                    if (tag == 0) {
                                        scheduleViewHolder.delete.setVisibility(View.VISIBLE);
                                    } else {
                                        scheduleViewHolder.delete.setVisibility(View.GONE);
                                    }
                                    int finalI1 = i;
                                    scheduleViewHolder.delete.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            SystemUtils.GeneralDialog(context, "删除课表").setMessage("确定删除此课表？")
                                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            presenter.deleteSchedule(totalSchedule.getTeacher().get(finalI1).getScheduleId());
                                                        }
                                                    })
                                                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            dialog.dismiss();
                                                        }
                                                    })
                                                    .show();
                                        }
                                    });
                                    scheduleViewHolder.text.setText(totalSchedule.getTeacher().get(i).getDetail());
                                    Glide.with(context).load(totalSchedule.getTeacher().get(i).getBookImageUrl()).into(scheduleViewHolder.image);
                                    scheduleViewHolder.line.setVisibility(View.GONE);
                                    int finalI2 = i;
                                    scheduleViewHolder.scheduleLayout.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Song song = new Song();
                                            song.setBookName(totalSchedule.getTeacher().get(finalI2).getDetail());
                                            song.setBookImageUrl(totalSchedule.getTeacher().get(finalI2).getBookImageUrl());
                                            song.setBookId(totalSchedule.getTeacher().get(finalI2).getBookId());

                                            if (totalSchedule.getTeacher().get(finalI2).getAudioType() == 1) {
                                                if (totalSchedule.getTeacher().get(finalI2).getAudioSource() == 8) {
                                                    Intent intent = new Intent(context, BookPlayActivity2.class);
                                                    intent.putExtra("bookId", song.getBookId());
                                                    intent.putExtra("imageUrl", song.getBookImageUrl());
                                                    intent.putExtra("audioType", totalSchedule.getTeacher().get(finalI2).getAudioType());
                                                    intent.putExtra("audioSource", totalSchedule.getTeacher().get(finalI2).getAudioSource());
                                                    intent.putExtra("title", song.getBookName());
                                                    context.startActivity(intent);
                                                } else {
                                                    int musicPosition = 0;
                                                    for (int j = 0; j < totalSchedule.getMoerduolist().size(); j++) {
                                                        if (totalSchedule.getMoerduolist().get(j).getBookId() == totalSchedule.getTeacher().get(finalI2).getBookId()) {
                                                            musicPosition = j;
                                                        }
                                                    }
                                                    Intent intent = new Intent(context, PracticeActivity.class);
                                                    intent.putExtra("song", song);
                                                    intent.putExtra("type", 0);
                                                    intent.putExtra("audioType", totalSchedule.getTeacher().get(finalI2).getAudioType());
                                                    intent.putExtra("audioSource", totalSchedule.getTeacher().get(finalI2).getAudioSource());
                                                    intent.putExtra("collectType", totalSchedule.getTeacher().get(finalI2).getAudioType() + 1);
                                                    intent.putExtra("songList", (Serializable) totalSchedule.getMoerduolist());
                                                    intent.putExtra("musicPosition", musicPosition);
                                                    intent.putExtra("bookType", 1);
                                                    context.startActivity(intent);
                                                }
                                            } else {
                                                int musicPosition = 0;
                                                for (int j = 0; j < totalSchedule.getMoerduolist().size(); j++) {
                                                    if (totalSchedule.getMoerduolist().get(j).getBookId() == totalSchedule.getTeacher().get(finalI2).getBookId()) {
                                                        musicPosition = j;
                                                    }
                                                }
                                                Intent intent = new Intent(context, PracticeActivity.class);
                                                intent.putExtra("song", song);
                                                intent.putExtra("type", 0);
                                                intent.putExtra("audioType", totalSchedule.getTeacher().get(finalI2).getAudioType());
                                                intent.putExtra("audioSource", totalSchedule.getTeacher().get(finalI2).getAudioSource());
                                                intent.putExtra("collectType", totalSchedule.getTeacher().get(finalI2).getAudioType() + 1);
                                                intent.putExtra("songList", (Serializable) totalSchedule.getMoerduolist());
                                                intent.putExtra("musicPosition", musicPosition);
                                                intent.putExtra("bookType", 0);
                                                context.startActivity(intent);
                                            }
                                        }
                                    });
                                } else {
                                    scheduleViewHolder.scheduleLayout.setVisibility(View.GONE);
                                    scheduleViewHolder.scheduleLayout1.setVisibility(View.GONE);
                                    scheduleViewHolder.line.setVisibility(View.GONE);
                                }
                            }
                        }
                    }
                } else if (totalSchedule.getFamily() != null && totalSchedule.getFamily().size() != 0) {
                    if (totalSchedule.getFamily().size() > i) {
                        scheduleViewHolder.icon.setImageResource(R.drawable.icon_small_red_circle);
                        scheduleViewHolder.time.setText(totalSchedule.getFamily().get(i).getStart() + "-" + totalSchedule.getFamily().get(i).getStop());
                        scheduleViewHolder.title.setText("自选课表");
                        if (tag == 0) {
                            scheduleViewHolder.delete.setVisibility(View.VISIBLE);
                        } else {
                            scheduleViewHolder.delete.setVisibility(View.GONE);
                        }
                        int finalI1 = i;
                        scheduleViewHolder.delete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                SystemUtils.GeneralDialog(context, "删除课表").setMessage("确定删除此课表？")
                                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                presenter.deleteSchedule(totalSchedule.getFamily().get(finalI1).getScheduleId());
                                            }
                                        })
                                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        })
                                        .show();
                            }
                        });
                        scheduleViewHolder.line.setVisibility(View.VISIBLE);
                        Glide.with(context).load(totalSchedule.getFamily().get(i).getBookImageUrl()).into(scheduleViewHolder.image);
                        scheduleViewHolder.text.setText(totalSchedule.getFamily().get(i).getDetail());
                        int finalI = i;
                        scheduleViewHolder.scheduleLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Song song = new Song();
                                song.setBookName(totalSchedule.getFamily().get(finalI).getDetail());
                                song.setBookImageUrl(totalSchedule.getFamily().get(finalI).getBookImageUrl());
                                song.setBookId(totalSchedule.getFamily().get(finalI).getBookId());

                                if (totalSchedule.getFamily().get(finalI).getAudioType() == 1) {
                                    if (totalSchedule.getFamily().get(finalI).getAudioSource() == 8) {
                                        Intent intent = new Intent(context, BookPlayActivity2.class);
                                        intent.putExtra("bookId", song.getBookId());
                                        intent.putExtra("imageUrl", song.getBookImageUrl());
                                        intent.putExtra("audioType", totalSchedule.getFamily().get(finalI).getAudioType());
                                        intent.putExtra("audioSource", totalSchedule.getFamily().get(finalI).getAudioSource());
                                        intent.putExtra("title", song.getBookName());
                                        context.startActivity(intent);
                                    } else {
                                        int musicPosition = 0;
                                        for (int j = 0; j < totalSchedule.getMoerduolist().size(); j++) {
                                            if (totalSchedule.getMoerduolist().get(j).getBookId() == totalSchedule.getFamily().get(finalI).getBookId()) {
                                                musicPosition = j;
                                            }
                                        }
                                        Intent intent = new Intent(context, PracticeActivity.class);
                                        intent.putExtra("song", song);
                                        intent.putExtra("type", 0);
                                        intent.putExtra("audioType", totalSchedule.getFamily().get(finalI).getAudioType());
                                        intent.putExtra("audioSource", totalSchedule.getFamily().get(finalI).getAudioSource());
                                        intent.putExtra("collectType", totalSchedule.getFamily().get(finalI).getAudioType() + 1);
                                        intent.putExtra("songList", (Serializable) totalSchedule.getMoerduolist());
                                        intent.putExtra("musicPosition", musicPosition);
                                        intent.putExtra("bookType", 1);
                                        context.startActivity(intent);
                                    }
                                } else {
                                    int musicPosition = 0;
                                    for (int j = 0; j < totalSchedule.getMoerduolist().size(); j++) {
                                        if (totalSchedule.getMoerduolist().get(j).getBookId() == totalSchedule.getFamily().get(finalI).getBookId()) {
                                            musicPosition = j;
                                        }
                                    }
                                    Intent intent = new Intent(context, PracticeActivity.class);
                                    intent.putExtra("song", song);
                                    intent.putExtra("type", 0);
                                    intent.putExtra("audioType", totalSchedule.getFamily().get(finalI).getAudioType());
                                    intent.putExtra("audioSource", totalSchedule.getFamily().get(finalI).getAudioSource());
                                    intent.putExtra("collectType", totalSchedule.getFamily().get(finalI).getAudioType() + 1);
                                    intent.putExtra("songList", (Serializable) totalSchedule.getMoerduolist());
                                    intent.putExtra("musicPosition", musicPosition);
                                    intent.putExtra("bookType", 0);
                                    context.startActivity(intent);
                                }
                            }
                        });
                    } else {
                        i = i - totalSchedule.getFamily().size();
                        if (totalSchedule.getTeacher() != null && totalSchedule.getTeacher().size() != 0) {
                            scheduleViewHolder.time.setText(totalSchedule.getTeacher().get(i).getStart() + "-" + totalSchedule.getTeacher().get(i).getStop());
                            scheduleViewHolder.icon.setImageResource(R.drawable.icon_small_blue_circle);
                            scheduleViewHolder.title.setText("教师作业");
                            if (tag == 0) {
                                scheduleViewHolder.delete.setVisibility(View.VISIBLE);
                            } else {
                                scheduleViewHolder.delete.setVisibility(View.GONE);
                            }
                            int finalI1 = i;
                            scheduleViewHolder.delete.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    SystemUtils.GeneralDialog(context, "删除课表").setMessage("确定删除此课表？")
                                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    presenter.deleteSchedule(totalSchedule.getTeacher().get(finalI1).getScheduleId());
                                                }
                                            })
                                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                }
                                            })
                                            .show();
                                }
                            });
                            scheduleViewHolder.text.setText(totalSchedule.getTeacher().get(i).getDetail());
                            Glide.with(context).load(totalSchedule.getTeacher().get(i).getBookImageUrl()).into(scheduleViewHolder.image);
                            scheduleViewHolder.line.setVisibility(View.GONE);
                            int finalI2 = i;
                            scheduleViewHolder.scheduleLayout.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Song song = new Song();
                                    song.setBookName(totalSchedule.getTeacher().get(finalI2).getDetail());
                                    song.setBookImageUrl(totalSchedule.getTeacher().get(finalI2).getBookImageUrl());
                                    song.setBookId(totalSchedule.getTeacher().get(finalI2).getBookId());

                                    if (totalSchedule.getTeacher().get(finalI2).getAudioType() == 1) {
                                        if (totalSchedule.getTeacher().get(finalI2).getAudioSource() == 8) {
                                            Intent intent = new Intent(context, BookPlayActivity2.class);
                                            intent.putExtra("bookId", song.getBookId());
                                            intent.putExtra("imageUrl", song.getBookImageUrl());
                                            intent.putExtra("audioType", totalSchedule.getTeacher().get(finalI2).getAudioType());
                                            intent.putExtra("audioSource", totalSchedule.getTeacher().get(finalI2).getAudioSource());
                                            intent.putExtra("title", song.getBookName());
                                            context.startActivity(intent);
                                        } else {
                                            int musicPosition = 0;
                                            for (int j = 0; j < totalSchedule.getMoerduolist().size(); j++) {
                                                if (totalSchedule.getMoerduolist().get(j).getBookId() == totalSchedule.getTeacher().get(finalI2).getBookId()) {
                                                    musicPosition = j;
                                                }
                                            }
                                            Intent intent = new Intent(context, PracticeActivity.class);
                                            intent.putExtra("song", song);
                                            intent.putExtra("type", 0);
                                            intent.putExtra("audioType", totalSchedule.getTeacher().get(finalI2).getAudioType());
                                            intent.putExtra("audioSource", totalSchedule.getTeacher().get(finalI2).getAudioSource());
                                            intent.putExtra("collectType", totalSchedule.getTeacher().get(finalI2).getAudioType() + 1);
                                            intent.putExtra("songList", (Serializable) totalSchedule.getMoerduolist());
                                            intent.putExtra("musicPosition", musicPosition);
                                            intent.putExtra("bookType", 1);
                                            context.startActivity(intent);
                                        }
                                    } else {
                                        int musicPosition = 0;
                                        for (int j = 0; j < totalSchedule.getMoerduolist().size(); j++) {
                                            if (totalSchedule.getMoerduolist().get(j).getBookId() == totalSchedule.getTeacher().get(finalI2).getBookId()) {
                                                musicPosition = j;
                                            }
                                        }
                                        Intent intent = new Intent(context, PracticeActivity.class);
                                        intent.putExtra("song", song);
                                        intent.putExtra("type", 0);
                                        intent.putExtra("audioType", totalSchedule.getTeacher().get(finalI2).getAudioType());
                                        intent.putExtra("audioSource", totalSchedule.getTeacher().get(finalI2).getAudioSource());
                                        intent.putExtra("collectType", totalSchedule.getTeacher().get(finalI2).getAudioType() + 1);
                                        intent.putExtra("songList", (Serializable) totalSchedule.getMoerduolist());
                                        intent.putExtra("musicPosition", musicPosition);
                                        intent.putExtra("bookType", 0);
                                        context.startActivity(intent);
                                    }
                                }
                            });
                        } else {
                            scheduleViewHolder.scheduleLayout.setVisibility(View.GONE);
                            scheduleViewHolder.scheduleLayout1.setVisibility(View.GONE);
                            scheduleViewHolder.line.setVisibility(View.GONE);
                        }
                    }
                } else if (totalSchedule.getTeacher() != null && totalSchedule.getTeacher().size() != 0) {
                    scheduleViewHolder.titleLayout.setVisibility(View.GONE);
                    scheduleViewHolder.scheduleLayout.setVisibility(View.VISIBLE);
                    scheduleViewHolder.scheduleLayout1.setVisibility(View.VISIBLE);
                    scheduleViewHolder.time.setText(totalSchedule.getTeacher().get(i).getStart() + "-" + totalSchedule.getTeacher().get(i).getStop());
                    scheduleViewHolder.icon.setImageResource(R.drawable.icon_small_blue_circle);
                    scheduleViewHolder.title.setText("教师作业");
                    if (tag == 0) {
                        scheduleViewHolder.delete.setVisibility(View.VISIBLE);
                    } else {
                        scheduleViewHolder.delete.setVisibility(View.GONE);
                    }
                    int finalI1 = i;
                    scheduleViewHolder.delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SystemUtils.GeneralDialog(context, "删除课表").setMessage("确定删除此课表？")
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            presenter.deleteSchedule(totalSchedule.getTeacher().get(finalI1).getScheduleId());
                                        }
                                    })
                                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    })
                                    .show();
                        }
                    });
                    scheduleViewHolder.text.setText(totalSchedule.getTeacher().get(i).getDetail());
                    Glide.with(context).load(totalSchedule.getTeacher().get(i).getBookImageUrl()).into(scheduleViewHolder.image);
                    scheduleViewHolder.line.setVisibility(View.GONE);
                    int finalI2 = i;
                    scheduleViewHolder.scheduleLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Song song = new Song();
                            song.setBookName(totalSchedule.getTeacher().get(finalI2).getDetail());
                            song.setBookImageUrl(totalSchedule.getTeacher().get(finalI2).getBookImageUrl());
                            song.setBookId(totalSchedule.getTeacher().get(finalI2).getBookId());

                            if (totalSchedule.getTeacher().get(finalI2).getAudioType() == 1) {
                                if (totalSchedule.getTeacher().get(finalI2).getAudioSource() == 8) {
                                    Intent intent = new Intent(context, BookPlayActivity2.class);
                                    intent.putExtra("bookId", song.getBookId());
                                    intent.putExtra("imageUrl", song.getBookImageUrl());
                                    intent.putExtra("audioType", totalSchedule.getTeacher().get(finalI2).getAudioType());
                                    intent.putExtra("audioSource", totalSchedule.getTeacher().get(finalI2).getAudioSource());
                                    intent.putExtra("title", song.getBookName());
                                    context.startActivity(intent);
                                } else {
                                    int musicPosition = 0;
                                    for (int j = 0; j < totalSchedule.getMoerduolist().size(); j++) {
                                        if (totalSchedule.getMoerduolist().get(j).getBookId() == totalSchedule.getTeacher().get(finalI2).getBookId()) {
                                            musicPosition = j;
                                        }
                                    }
                                    Intent intent = new Intent(context, PracticeActivity.class);
                                    intent.putExtra("song", song);
                                    intent.putExtra("type", 0);
                                    intent.putExtra("audioType", totalSchedule.getTeacher().get(finalI2).getAudioType());
                                    intent.putExtra("audioSource", totalSchedule.getTeacher().get(finalI2).getAudioSource());
                                    intent.putExtra("collectType", totalSchedule.getTeacher().get(finalI2).getAudioType() + 1);
                                    intent.putExtra("songList", (Serializable) totalSchedule.getMoerduolist());
                                    intent.putExtra("musicPosition", musicPosition);
                                    intent.putExtra("bookType", 1);
                                    context.startActivity(intent);
                                }
                            } else {
                                int musicPosition = 0;
                                for (int j = 0; j < totalSchedule.getMoerduolist().size(); j++) {
                                    if (totalSchedule.getMoerduolist().get(j).getBookId() == totalSchedule.getTeacher().get(finalI2).getBookId()) {
                                        musicPosition = j;
                                    }
                                }
                                Intent intent = new Intent(context, PracticeActivity.class);
                                intent.putExtra("song", song);
                                intent.putExtra("type", 0);
                                intent.putExtra("audioType", totalSchedule.getTeacher().get(finalI2).getAudioType());
                                intent.putExtra("audioSource", totalSchedule.getTeacher().get(finalI2).getAudioSource());
                                intent.putExtra("collectType", totalSchedule.getTeacher().get(finalI2).getAudioType() + 1);
                                intent.putExtra("songList", (Serializable) totalSchedule.getMoerduolist());
                                intent.putExtra("musicPosition", musicPosition);
                                intent.putExtra("bookType", 0);
                                context.startActivity(intent);
                            }
                        }
                    });
                } else {
                    scheduleViewHolder.scheduleLayout.setVisibility(View.GONE);
                    scheduleViewHolder.scheduleLayout1.setVisibility(View.GONE);
                    scheduleViewHolder.line.setVisibility(View.GONE);
                }
            }
        } else if (totalSchedule.getOnline() != null && totalSchedule.getOnline().size() != 0) {
            if (totalSchedule.getOnline().size() > i) {
                scheduleViewHolder.scheduleLayout.setVisibility(View.VISIBLE);
                scheduleViewHolder.scheduleLayout1.setVisibility(View.VISIBLE);
                scheduleViewHolder.icon.setImageResource(R.drawable.icon_small_green_circle);
                scheduleViewHolder.time.setText(totalSchedule.getOnline().get(i).getStart() + "-" + totalSchedule.getOnline().get(i).getStop());
                scheduleViewHolder.title.setText("线上课程");
                scheduleViewHolder.line.setVisibility(View.VISIBLE);
                if (tag == 0) {
                    scheduleViewHolder.delete.setVisibility(View.VISIBLE);
                } else {
                    scheduleViewHolder.delete.setVisibility(View.GONE);
                }
                int finalI1 = i;
                scheduleViewHolder.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SystemUtils.GeneralDialog(context, "删除课表").setMessage("确定删除此课表？")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        presenter.deleteSchedule(totalSchedule.getTeacher().get(finalI1).getScheduleId());
                                    }
                                })
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .show();
                    }
                });
                Glide.with(context).load(totalSchedule.getOnline().get(i).getBookImageUrl()).into(scheduleViewHolder.image);
                scheduleViewHolder.text.setText(totalSchedule.getOnline().get(i).getDetail());
            } else {
                scheduleViewHolder.titleLayout.setVisibility(View.VISIBLE);
                scheduleViewHolder.scheduleLayout.setVisibility(View.VISIBLE);
                scheduleViewHolder.scheduleLayout1.setVisibility(View.VISIBLE);
                i = i - totalSchedule.getOnline().size();
                if (totalSchedule.getFamily() != null && totalSchedule.getFamily().size() != 0) {
                    if (totalSchedule.getFamily().size() > i) {
                        scheduleViewHolder.icon.setImageResource(R.drawable.icon_small_red_circle);
                        scheduleViewHolder.time.setText(totalSchedule.getFamily().get(i).getStart() + "-" + totalSchedule.getFamily().get(i).getStop());
                        scheduleViewHolder.title.setText("自选课表");
                        if (tag == 0) {
                            scheduleViewHolder.delete.setVisibility(View.VISIBLE);
                        } else {
                            scheduleViewHolder.delete.setVisibility(View.GONE);
                        }
                        int finalI1 = i;
                        scheduleViewHolder.delete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                SystemUtils.GeneralDialog(context, "删除课表").setMessage("确定删除此课表？")
                                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                presenter.deleteSchedule(totalSchedule.getFamily().get(finalI1).getScheduleId());
                                            }
                                        })
                                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        })
                                        .show();
                            }
                        });
                        scheduleViewHolder.line.setVisibility(View.VISIBLE);
                        Glide.with(context).load(totalSchedule.getFamily().get(i).getBookImageUrl()).into(scheduleViewHolder.image);
                        scheduleViewHolder.text.setText(totalSchedule.getFamily().get(i).getDetail());
                        int finalI = i;
                        scheduleViewHolder.scheduleLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Song song = new Song();
                                song.setBookName(totalSchedule.getFamily().get(finalI).getDetail());
                                song.setBookImageUrl(totalSchedule.getFamily().get(finalI).getBookImageUrl());
                                song.setBookId(totalSchedule.getFamily().get(finalI).getBookId());

                                if (totalSchedule.getFamily().get(finalI).getAudioType() == 1) {
                                    if (totalSchedule.getFamily().get(finalI).getAudioSource() == 8) {
                                        Intent intent = new Intent(context, BookPlayActivity2.class);
                                        intent.putExtra("bookId", song.getBookId());
                                        intent.putExtra("imageUrl", song.getBookImageUrl());
                                        intent.putExtra("audioType", totalSchedule.getFamily().get(finalI).getAudioType());
                                        intent.putExtra("audioSource", totalSchedule.getFamily().get(finalI).getAudioSource());
                                        intent.putExtra("title", song.getBookName());
                                        context.startActivity(intent);
                                    } else {
                                        int musicPosition = 0;
                                        for (int j = 0; j < totalSchedule.getMoerduolist().size(); j++) {
                                            if (totalSchedule.getMoerduolist().get(j).getBookId() == totalSchedule.getFamily().get(finalI).getBookId()) {
                                                musicPosition = j;
                                            }
                                        }
                                        Intent intent = new Intent(context, PracticeActivity.class);
                                        intent.putExtra("song", song);
                                        intent.putExtra("type", 0);
                                        intent.putExtra("audioType", totalSchedule.getFamily().get(finalI).getAudioType());
                                        intent.putExtra("audioSource", totalSchedule.getFamily().get(finalI).getAudioSource());
                                        intent.putExtra("collectType", totalSchedule.getFamily().get(finalI).getAudioType() + 1);
                                        intent.putExtra("songList", (Serializable) totalSchedule.getMoerduolist());
                                        intent.putExtra("musicPosition", musicPosition);
                                        intent.putExtra("bookType", 1);
                                        context.startActivity(intent);
                                    }
                                } else {
                                    int musicPosition = 0;
                                    for (int j = 0; j < totalSchedule.getMoerduolist().size(); j++) {
                                        if (totalSchedule.getMoerduolist().get(j).getBookId() == totalSchedule.getFamily().get(finalI).getBookId()) {
                                            musicPosition = j;
                                        }
                                    }
                                    Intent intent = new Intent(context, PracticeActivity.class);
                                    intent.putExtra("song", song);
                                    intent.putExtra("type", 0);
                                    intent.putExtra("audioType", totalSchedule.getFamily().get(finalI).getAudioType());
                                    intent.putExtra("audioSource", totalSchedule.getFamily().get(finalI).getAudioSource());
                                    intent.putExtra("collectType", totalSchedule.getFamily().get(finalI).getAudioType() + 1);
                                    intent.putExtra("songList", (Serializable) totalSchedule.getMoerduolist());
                                    intent.putExtra("musicPosition", musicPosition);
                                    intent.putExtra("bookType", 0);
                                    context.startActivity(intent);
                                }
                            }
                        });
                    } else {
                        i = i - totalSchedule.getFamily().size();
                        if (totalSchedule.getTeacher() != null && totalSchedule.getTeacher().size() != 0) {
                            scheduleViewHolder.time.setText(totalSchedule.getTeacher().get(i).getStart() + "-" + totalSchedule.getTeacher().get(i).getStop());
                            scheduleViewHolder.icon.setImageResource(R.drawable.icon_small_blue_circle);
                            scheduleViewHolder.title.setText("教师作业");
                            if (tag == 0) {
                                scheduleViewHolder.delete.setVisibility(View.VISIBLE);
                            } else {
                                scheduleViewHolder.delete.setVisibility(View.GONE);
                            }
                            int finalI1 = i;
                            scheduleViewHolder.delete.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    SystemUtils.GeneralDialog(context, "删除课表").setMessage("确定删除此课表？")
                                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    presenter.deleteSchedule(totalSchedule.getTeacher().get(finalI1).getScheduleId());
                                                }
                                            })
                                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                }
                                            })
                                            .show();
                                }
                            });
                            scheduleViewHolder.text.setText(totalSchedule.getTeacher().get(i).getDetail());
                            Glide.with(context).load(totalSchedule.getTeacher().get(i).getBookImageUrl()).into(scheduleViewHolder.image);
                            scheduleViewHolder.line.setVisibility(View.GONE);
                            int finalI2 = i;
                            scheduleViewHolder.scheduleLayout.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Song song = new Song();
                                    song.setBookName(totalSchedule.getTeacher().get(finalI2).getDetail());
                                    song.setBookImageUrl(totalSchedule.getTeacher().get(finalI2).getBookImageUrl());
                                    song.setBookId(totalSchedule.getTeacher().get(finalI2).getBookId());

                                    if (totalSchedule.getTeacher().get(finalI2).getAudioType() == 1) {
                                        if (totalSchedule.getTeacher().get(finalI2).getAudioSource() == 8) {
                                            Intent intent = new Intent(context, BookPlayActivity2.class);
                                            intent.putExtra("bookId", song.getBookId());
                                            intent.putExtra("imageUrl", song.getBookImageUrl());
                                            intent.putExtra("audioType", totalSchedule.getTeacher().get(finalI2).getAudioType());
                                            intent.putExtra("audioSource", totalSchedule.getTeacher().get(finalI2).getAudioSource());
                                            intent.putExtra("title", song.getBookName());
                                            context.startActivity(intent);
                                        } else {
                                            int musicPosition = 0;
                                            for (int j = 0; j < totalSchedule.getMoerduolist().size(); j++) {
                                                if (totalSchedule.getMoerduolist().get(j).getBookId() == totalSchedule.getTeacher().get(finalI2).getBookId()) {
                                                    musicPosition = j;
                                                }
                                            }
                                            Intent intent = new Intent(context, PracticeActivity.class);
                                            intent.putExtra("song", song);
                                            intent.putExtra("type", 0);
                                            intent.putExtra("audioType", totalSchedule.getTeacher().get(finalI2).getAudioType());
                                            intent.putExtra("audioSource", totalSchedule.getTeacher().get(finalI2).getAudioSource());
                                            intent.putExtra("collectType", totalSchedule.getTeacher().get(finalI2).getAudioType() + 1);
                                            intent.putExtra("songList", (Serializable) totalSchedule.getMoerduolist());
                                            intent.putExtra("musicPosition", musicPosition);
                                            intent.putExtra("bookType", 1);
                                            context.startActivity(intent);
                                        }
                                    } else {
                                        int musicPosition = 0;
                                        for (int j = 0; j < totalSchedule.getMoerduolist().size(); j++) {
                                            if (totalSchedule.getMoerduolist().get(j).getBookId() == totalSchedule.getTeacher().get(finalI2).getBookId()) {
                                                musicPosition = j;
                                            }
                                        }
                                        Intent intent = new Intent(context, PracticeActivity.class);
                                        intent.putExtra("song", song);
                                        intent.putExtra("type", 0);
                                        intent.putExtra("audioType", totalSchedule.getTeacher().get(finalI2).getAudioType());
                                        intent.putExtra("audioSource", totalSchedule.getTeacher().get(finalI2).getAudioSource());
                                        intent.putExtra("collectType", totalSchedule.getTeacher().get(finalI2).getAudioType() + 1);
                                        intent.putExtra("songList", (Serializable) totalSchedule.getMoerduolist());
                                        intent.putExtra("musicPosition", musicPosition);
                                        intent.putExtra("bookType", 0);
                                        context.startActivity(intent);
                                    }
                                }
                            });
                        } else {
                            scheduleViewHolder.scheduleLayout.setVisibility(View.GONE);
                            scheduleViewHolder.scheduleLayout1.setVisibility(View.GONE);
                            scheduleViewHolder.line.setVisibility(View.GONE);
                        }
                    }
                }
            }
        } else if (totalSchedule.getFamily() != null && totalSchedule.getFamily().size() != 0) {
            if (totalSchedule.getFamily().size() > i) {
                scheduleViewHolder.scheduleLayout.setVisibility(View.VISIBLE);
                scheduleViewHolder.scheduleLayout1.setVisibility(View.VISIBLE);
                scheduleViewHolder.icon.setImageResource(R.drawable.icon_small_red_circle);
                scheduleViewHolder.time.setText(totalSchedule.getFamily().get(i).getStart() + "-" + totalSchedule.getFamily().get(i).getStop());
                scheduleViewHolder.title.setText("自选课表");
                if (tag == 0) {
                    scheduleViewHolder.delete.setVisibility(View.VISIBLE);
                } else {
                    scheduleViewHolder.delete.setVisibility(View.GONE);
                }
                int finalI1 = i;
                scheduleViewHolder.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SystemUtils.GeneralDialog(context, "删除课表").setMessage("确定删除此课表？")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        presenter.deleteSchedule(totalSchedule.getFamily().get(finalI1).getScheduleId());
                                    }
                                })
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .show();
                    }
                });
                scheduleViewHolder.line.setVisibility(View.VISIBLE);
                Glide.with(context).load(totalSchedule.getFamily().get(i).getBookImageUrl()).into(scheduleViewHolder.image);
                scheduleViewHolder.text.setText(totalSchedule.getFamily().get(i).getDetail());
                int finalI = i;
                scheduleViewHolder.scheduleLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Song song = new Song();
                        song.setBookName(totalSchedule.getFamily().get(finalI).getDetail());
                        song.setBookImageUrl(totalSchedule.getFamily().get(finalI).getBookImageUrl());
                        song.setBookId(totalSchedule.getFamily().get(finalI).getBookId());

                        if (totalSchedule.getFamily().get(finalI).getAudioType() == 1) {
                            if (totalSchedule.getFamily().get(finalI).getAudioSource() == 8) {
                                Intent intent = new Intent(context, BookPlayActivity2.class);
                                intent.putExtra("bookId", song.getBookId());
                                intent.putExtra("imageUrl", song.getBookImageUrl());
                                intent.putExtra("audioType", totalSchedule.getFamily().get(finalI).getAudioType());
                                intent.putExtra("audioSource", totalSchedule.getFamily().get(finalI).getAudioSource());
                                intent.putExtra("title", song.getBookName());
                                context.startActivity(intent);
                            } else {
                                int musicPosition = 0;
                                for (int j = 0; j < totalSchedule.getMoerduolist().size(); j++) {
                                    if (totalSchedule.getMoerduolist().get(j).getBookId() == totalSchedule.getFamily().get(finalI).getBookId()) {
                                        musicPosition = j;
                                    }
                                }
                                Intent intent = new Intent(context, PracticeActivity.class);
                                intent.putExtra("song", song);
                                intent.putExtra("type", 0);
                                intent.putExtra("audioType", totalSchedule.getFamily().get(finalI).getAudioType());
                                intent.putExtra("audioSource", totalSchedule.getFamily().get(finalI).getAudioSource());
                                intent.putExtra("collectType", totalSchedule.getFamily().get(finalI).getAudioType() + 1);
                                intent.putExtra("songList", (Serializable) totalSchedule.getMoerduolist());
                                intent.putExtra("musicPosition", musicPosition);
                                intent.putExtra("bookType", 1);
                                context.startActivity(intent);
                            }
                        } else {
                            int musicPosition = 0;
                            for (int j = 0; j < totalSchedule.getMoerduolist().size(); j++) {
                                if (totalSchedule.getMoerduolist().get(j).getBookId() == totalSchedule.getFamily().get(finalI).getBookId()) {
                                    musicPosition = j;
                                }
                            }
                            Intent intent = new Intent(context, PracticeActivity.class);
                            intent.putExtra("song", song);
                            intent.putExtra("type", 0);
                            intent.putExtra("audioType", totalSchedule.getFamily().get(finalI).getAudioType());
                            intent.putExtra("audioSource", totalSchedule.getFamily().get(finalI).getAudioSource());
                            intent.putExtra("collectType", totalSchedule.getFamily().get(finalI).getAudioType() + 1);
                            intent.putExtra("songList", (Serializable) totalSchedule.getMoerduolist());
                            intent.putExtra("musicPosition", musicPosition);
                            intent.putExtra("bookType", 0);
                            context.startActivity(intent);
                        }
                    }
                });
            } else {
                scheduleViewHolder.titleLayout.setVisibility(View.VISIBLE);
                scheduleViewHolder.scheduleLayout.setVisibility(View.VISIBLE);
                scheduleViewHolder.scheduleLayout1.setVisibility(View.VISIBLE);
                i = i - totalSchedule.getFamily().size();
                if (totalSchedule.getTeacher() != null && totalSchedule.getTeacher().size() != 0) {
                    scheduleViewHolder.time.setText(totalSchedule.getTeacher().get(i).getStart() + "-" + totalSchedule.getTeacher().get(i).getStop());
                    scheduleViewHolder.icon.setImageResource(R.drawable.icon_small_blue_circle);
                    scheduleViewHolder.title.setText("教师作业");
                    if (tag == 0) {
                        scheduleViewHolder.delete.setVisibility(View.VISIBLE);
                    } else {
                        scheduleViewHolder.delete.setVisibility(View.GONE);
                    }
                    int finalI1 = i;
                    scheduleViewHolder.delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SystemUtils.GeneralDialog(context, "删除课表").setMessage("确定删除此课表？")
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            presenter.deleteSchedule(totalSchedule.getTeacher().get(finalI1).getScheduleId());
                                        }
                                    })
                                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    })
                                    .show();
                        }
                    });
                    scheduleViewHolder.text.setText(totalSchedule.getTeacher().get(i).getDetail());
                    Glide.with(context).load(totalSchedule.getTeacher().get(i).getBookImageUrl()).into(scheduleViewHolder.image);
                    scheduleViewHolder.line.setVisibility(View.GONE);
                    int finalI2 = i;
                    scheduleViewHolder.scheduleLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Song song = new Song();
                            song.setBookName(totalSchedule.getTeacher().get(finalI2).getDetail());
                            song.setBookImageUrl(totalSchedule.getTeacher().get(finalI2).getBookImageUrl());
                            song.setBookId(totalSchedule.getTeacher().get(finalI2).getBookId());

                            if (totalSchedule.getTeacher().get(finalI2).getAudioType() == 1) {
                                if (totalSchedule.getTeacher().get(finalI2).getAudioSource() == 8) {
                                    Intent intent = new Intent(context, BookPlayActivity2.class);
                                    intent.putExtra("bookId", song.getBookId());
                                    intent.putExtra("imageUrl", song.getBookImageUrl());
                                    intent.putExtra("audioType", totalSchedule.getTeacher().get(finalI2).getAudioType());
                                    intent.putExtra("audioSource", totalSchedule.getTeacher().get(finalI2).getAudioSource());
                                    intent.putExtra("title", song.getBookName());
                                    context.startActivity(intent);
                                } else {
                                    int musicPosition = 0;
                                    for (int j = 0; j < totalSchedule.getMoerduolist().size(); j++) {
                                        if (totalSchedule.getMoerduolist().get(j).getBookId() == totalSchedule.getTeacher().get(finalI2).getBookId()) {
                                            musicPosition = j;
                                        }
                                    }
                                    Intent intent = new Intent(context, PracticeActivity.class);
                                    intent.putExtra("song", song);
                                    intent.putExtra("type", 0);
                                    intent.putExtra("audioType", totalSchedule.getTeacher().get(finalI2).getAudioType());
                                    intent.putExtra("audioSource", totalSchedule.getTeacher().get(finalI2).getAudioSource());
                                    intent.putExtra("collectType", totalSchedule.getTeacher().get(finalI2).getAudioType() + 1);
                                    intent.putExtra("songList", (Serializable) totalSchedule.getMoerduolist());
                                    intent.putExtra("musicPosition", musicPosition);
                                    intent.putExtra("bookType", 1);
                                    context.startActivity(intent);
                                }
                            } else {
                                int musicPosition = 0;
                                for (int j = 0; j < totalSchedule.getMoerduolist().size(); j++) {
                                    if (totalSchedule.getMoerduolist().get(j).getBookId() == totalSchedule.getTeacher().get(finalI2).getBookId()) {
                                        musicPosition = j;
                                    }
                                }
                                Intent intent = new Intent(context, PracticeActivity.class);
                                intent.putExtra("song", song);
                                intent.putExtra("type", 0);
                                intent.putExtra("audioType", totalSchedule.getTeacher().get(finalI2).getAudioType());
                                intent.putExtra("audioSource", totalSchedule.getTeacher().get(finalI2).getAudioSource());
                                intent.putExtra("collectType", totalSchedule.getTeacher().get(finalI2).getAudioType() + 1);
                                intent.putExtra("songList", (Serializable) totalSchedule.getMoerduolist());
                                intent.putExtra("musicPosition", musicPosition);
                                intent.putExtra("bookType", 0);
                                context.startActivity(intent);
                            }
                        }
                    });
                } else {
                    scheduleViewHolder.scheduleLayout.setVisibility(View.GONE);
                    scheduleViewHolder.scheduleLayout1.setVisibility(View.GONE);
                    scheduleViewHolder.line.setVisibility(View.GONE);
                }
            }
        } else if (totalSchedule.getTeacher() != null && totalSchedule.getTeacher().size() != 0) {
            if (totalSchedule.getTeacher().size() > 0) {
                scheduleViewHolder.titleLayout.setVisibility(View.GONE);
                scheduleViewHolder.scheduleLayout.setVisibility(View.VISIBLE);
                scheduleViewHolder.scheduleLayout1.setVisibility(View.VISIBLE);
                scheduleViewHolder.time.setText(totalSchedule.getTeacher().get(i).getStart() + "-" + totalSchedule.getTeacher().get(i).getStop());
                scheduleViewHolder.icon.setImageResource(R.drawable.icon_small_blue_circle);
                scheduleViewHolder.title.setText("教师作业");
                if (tag == 0) {
                    scheduleViewHolder.delete.setVisibility(View.VISIBLE);
                } else {
                    scheduleViewHolder.delete.setVisibility(View.GONE);
                }
                int finalI1 = i;
                scheduleViewHolder.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SystemUtils.GeneralDialog(context, "删除课表").setMessage("确定删除此课表？")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        presenter.deleteSchedule(totalSchedule.getTeacher().get(finalI1).getScheduleId());
                                    }
                                })
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .show();
                    }
                });
                scheduleViewHolder.text.setText(totalSchedule.getTeacher().get(i).getDetail());
                Glide.with(context).load(totalSchedule.getTeacher().get(i).getBookImageUrl()).into(scheduleViewHolder.image);
                scheduleViewHolder.line.setVisibility(View.GONE);
                int finalI2 = i;
                scheduleViewHolder.scheduleLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Song song = new Song();
                        song.setBookName(totalSchedule.getTeacher().get(finalI2).getDetail());
                        song.setBookImageUrl(totalSchedule.getTeacher().get(finalI2).getBookImageUrl());
                        song.setBookId(totalSchedule.getTeacher().get(finalI2).getBookId());

                        if (totalSchedule.getTeacher().get(finalI2).getAudioType() == 1) {
                            if (totalSchedule.getTeacher().get(finalI2).getAudioSource() == 8) {
                                Intent intent = new Intent(context, BookPlayActivity2.class);
                                intent.putExtra("bookId", song.getBookId());
                                intent.putExtra("imageUrl", song.getBookImageUrl());
                                intent.putExtra("audioType", totalSchedule.getTeacher().get(finalI2).getAudioType());
                                intent.putExtra("audioSource", totalSchedule.getTeacher().get(finalI2).getAudioSource());
                                intent.putExtra("title", song.getBookName());
                                context.startActivity(intent);
                            } else {
                                int musicPosition = 0;
                                for (int j = 0; j < totalSchedule.getMoerduolist().size(); j++) {
                                    if (totalSchedule.getMoerduolist().get(j).getBookId() == totalSchedule.getTeacher().get(finalI2).getBookId()) {
                                        musicPosition = j;
                                    }
                                }
                                Intent intent = new Intent(context, PracticeActivity.class);
                                intent.putExtra("song", song);
                                intent.putExtra("type", 0);
                                intent.putExtra("audioType", totalSchedule.getTeacher().get(finalI2).getAudioType());
                                intent.putExtra("audioSource", totalSchedule.getTeacher().get(finalI2).getAudioSource());
                                intent.putExtra("collectType", totalSchedule.getTeacher().get(finalI2).getAudioType() + 1);
                                intent.putExtra("songList", (Serializable) totalSchedule.getMoerduolist());
                                intent.putExtra("musicPosition", musicPosition);
                                intent.putExtra("bookType", 1);
                                context.startActivity(intent);
                            }
                        } else {
                            int musicPosition = 0;
                            for (int j = 0; j < totalSchedule.getMoerduolist().size(); j++) {
                                if (totalSchedule.getMoerduolist().get(j).getBookId() == totalSchedule.getTeacher().get(finalI2).getBookId()) {
                                    musicPosition = j;
                                }
                            }
                            Intent intent = new Intent(context, PracticeActivity.class);
                            intent.putExtra("song", song);
                            intent.putExtra("type", 0);
                            intent.putExtra("audioType", totalSchedule.getTeacher().get(finalI2).getAudioType());
                            intent.putExtra("audioSource", totalSchedule.getTeacher().get(finalI2).getAudioSource());
                            intent.putExtra("collectType", totalSchedule.getTeacher().get(finalI2).getAudioType() + 1);
                            intent.putExtra("songList", (Serializable) totalSchedule.getMoerduolist());
                            intent.putExtra("musicPosition", musicPosition);
                            intent.putExtra("bookType", 0);
                            context.startActivity(intent);
                        }
                    }

                });
            }
        } else {
            scheduleViewHolder.scheduleLayout.setVisibility(View.GONE);
            scheduleViewHolder.scheduleLayout1.setVisibility(View.GONE);
            scheduleViewHolder.line.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return (totalSchedule.getOffline() != null ? totalSchedule.getOffline().size() : 0)
                + (totalSchedule.getOnline() != null ? totalSchedule.getOnline().size() : 0)
                + (totalSchedule.getFamily() != null ? totalSchedule.getFamily().size() : 0) + 1;
    }
}
