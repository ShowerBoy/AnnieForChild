package com.annie.annieforchild.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.CheckDoubleClickListener;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.OnCheckDoubleClick;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.material.Material;
import com.annie.annieforchild.bean.song.Song;
import com.annie.annieforchild.presenter.GrindEarPresenter;
import com.annie.annieforchild.ui.activity.lesson.AddOnlineScheActivity;
import com.annie.annieforchild.ui.activity.pk.PracticeActivity;
import com.annie.annieforchild.ui.adapter.viewHolder.SongViewHolder;
import com.bumptech.glide.Glide;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.jiguang.analytics.android.api.CountEvent;
import cn.jiguang.analytics.android.api.JAnalyticsInterface;

/**
 * Created by wanglei on 2018/3/24.
 */

public class SongAdapter extends RecyclerView.Adapter<SongViewHolder> {
    private Context context;
    private List<Song> lists;
    private GrindEarPresenter presenter;
    private int collectType;
    private int classId;
    private int audioType;
    private int audioSource;
    private int type;
    private LayoutInflater inflater;

    public SongAdapter(Context context, List<Song> lists, GrindEarPresenter presenter, int collectType, int classId, int audioType, int audioSource, int type) {
        this.context = context;
        this.lists = lists;
        this.presenter = presenter;
        this.collectType = collectType;
        this.classId = classId;
        this.audioType = audioType;
        this.audioSource = audioSource;
        this.type = type;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public SongViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        SongViewHolder holder = null;
        holder = new SongViewHolder(inflater.inflate(R.layout.activity_song_item, viewGroup, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(SongViewHolder songViewHolder, int i) {
        Glide.with(context).load(lists.get(i).getBookImageUrl()).placeholder(R.drawable.image_loading).error(R.drawable.image_loading).into(songViewHolder.songImage);
        songViewHolder.songName.setText(lists.get(i).getBookName());
        songViewHolder.songCount.setText(lists.get(i).getCount() + "");
        if (lists.get(i).getIsCollected() == 0) {
            songViewHolder.collect.setTextColor(context.getResources().getColor(R.color.text_color));
            songViewHolder.collect.setText("收藏");
            Drawable drawable = ContextCompat.getDrawable(context, R.drawable.icon_song_uncollect);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            songViewHolder.collect.setCompoundDrawables(drawable, null, null, null);
        } else {
            songViewHolder.collect.setTextColor(context.getResources().getColor(R.color.text_orange));
            songViewHolder.collect.setText("已收藏");
            Drawable drawable = ContextCompat.getDrawable(context, R.drawable.icon_song_collect);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            songViewHolder.collect.setCompoundDrawables(drawable, null, null, null);
        }
        if (lists.get(i).getIsJoinMaterial() == 0) {
            songViewHolder.addMaterial.setTextColor(context.getResources().getColor(R.color.text_color));
            songViewHolder.addMaterial.setText("加入教材");
            Drawable drawable = ContextCompat.getDrawable(context, R.drawable.icon_add_material_f);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            songViewHolder.addMaterial.setCompoundDrawables(drawable, null, null, null);
        } else {
            songViewHolder.addMaterial.setTextColor(context.getResources().getColor(R.color.text_orange));
            songViewHolder.addMaterial.setText("已加入教材");
            Drawable drawable = ContextCompat.getDrawable(context, R.drawable.icon_add_material_t);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            songViewHolder.addMaterial.setCompoundDrawables(drawable, null, null, null);
        }

        if (lists.get(i).getJurisdiction() == 0) {
            songViewHolder.lock.setVisibility(View.VISIBLE);
            if (lists.get(i).getIsusenectar() == 0) {
                songViewHolder.lock.setImageResource(R.drawable.icon_lock_book_f);
            } else {
                songViewHolder.lock.setImageResource(R.drawable.icon_lock_book_t);
            }
        } else {
            songViewHolder.lock.setVisibility(View.GONE);
        }

        songViewHolder.songDetail.setOnClickListener(new CheckDoubleClickListener(new OnCheckDoubleClick() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onCheckDoubleClick(View view) {
                if (lists.get(i).getJurisdiction() == 0) {
                    if (lists.get(i).getIsusenectar() == 1) {
                        SystemUtils.setBackGray((Activity) context, true);
                        SystemUtils.getSuggestPopup(context, "需要" + lists.get(i).getNectar() + "花蜜才能解锁哦", "解锁", "取消", presenter, -1, lists.get(i).getNectar(), lists.get(i).getBookName(), lists.get(i).getBookId(), classId).showAtLocation(SystemUtils.popupView, Gravity.CENTER, 0, 0);
                    }
                } else {
//                    if (audioSource == 8) {
//                        if (MusicService.isPlay) {
//                            MusicService.stop();
//                        }
//                        Intent intent = new Intent(context, BookPlayActivity2.class);
//                        intent.putExtra("bookId", lists.get(i).getBookId());
//                        intent.putExtra("imageUrl", lists.get(i).getBookImageUrl());
//                        intent.putExtra("audioType", audioType);
//                        intent.putExtra("audioSource", audioSource);
//                        intent.putExtra("title", lists.get(i).getBookName());
//                        context.startActivity(intent);
//                    } else {
                    Intent intent = new Intent(context, PracticeActivity.class);
                    intent.putExtra("song", lists.get(i));
                    intent.putExtra("songList", (Serializable) lists);
                    intent.putExtra("type", type);
                    intent.putExtra("audioType", audioType);
                    intent.putExtra("audioSource", audioSource);
                    intent.putExtra("collectType", collectType);
                    intent.putExtra("musicPosition", i);
                    if (audioType == 1 || audioType == 2) {
                        intent.putExtra("bookType", 1);
                    } else {
                        intent.putExtra("bookType", 0);
                    }
                    context.startActivity(intent);
//                    }
                }
            }
        }));
        songViewHolder.collect.setOnClickListener(new CheckDoubleClickListener(new OnCheckDoubleClick() {
            @Override
            public void onCheckDoubleClick(View view) {
                if (lists.get(i).getJurisdiction() == 1) {
                    if (lists.get(i).getIsCollected() == 0) {
                        CountEvent Event_50201 = new CountEvent(MethodCode.A050201);
                        JAnalyticsInterface.onEvent(context, Event_50201);
                        presenter.collectCourse(collectType, audioSource, lists.get(i).getBookId(), classId);
                    } else {
                        presenter.cancelCollection(collectType, audioSource, lists.get(i).getBookId(), classId);
                    }
                }
            }
        }));
        songViewHolder.addMaterial.setOnClickListener(new CheckDoubleClickListener(new OnCheckDoubleClick() {
            @Override
            public void onCheckDoubleClick(View view) {
                if (lists.get(i).getJurisdiction() == 1) {
                    if (lists.get(i).getIsJoinMaterial() == 0) {
                        CountEvent Event_50202 = new CountEvent(MethodCode.A050202);
                        JAnalyticsInterface.onEvent(context, Event_50202);
                        presenter.joinMaterial(lists.get(i).getBookId(), audioSource, audioType, classId);
                    } else {
                        presenter.cancelMaterial(lists.get(i).getBookId(), classId);
                    }
                }
            }
        }));
        songViewHolder.addCourse.setOnClickListener(new CheckDoubleClickListener(new OnCheckDoubleClick() {
            @Override
            public void onCheckDoubleClick(View view) {
                //加入课表
                if (lists.get(i).getJurisdiction() == 1) {
                    CountEvent Event_50203 = new CountEvent(MethodCode.A050203);
                    JAnalyticsInterface.onEvent(context, Event_50203);
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
                    String date = simpleDateFormat.format(new Date());
                    Material material = new Material();
                    material.setImageUrl(lists.get(i).getBookImageUrl());
                    material.setMaterialId(lists.get(i).getBookId());
                    material.setName(lists.get(i).getBookName());
                    Intent intent = new Intent(context, AddOnlineScheActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("material", material);
                    bundle.putString("date", date);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            }
        }));
    }

    @Override
    public int getItemCount() {
        return lists != null ? lists.size() : 0;
    }
}
