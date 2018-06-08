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
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.material.Material;
import com.annie.annieforchild.bean.schedule.Schedule;
import com.annie.annieforchild.bean.song.Song;
import com.annie.annieforchild.presenter.CollectionPresenter;
import com.annie.annieforchild.presenter.GrindEarPresenter;
import com.annie.annieforchild.ui.activity.lesson.AddOnlineScheActivity;
import com.annie.annieforchild.ui.activity.pk.PracticeActivity;
import com.annie.annieforchild.ui.adapter.viewHolder.SongViewHolder;
import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by wanglei on 2018/3/24.
 */

public class SongAdapter extends RecyclerView.Adapter<SongViewHolder> {
    private Context context;
    private List<Song> lists;
    private GrindEarPresenter presenter;
    private int type;
    private int classId;
    private int audioType;
    private int audioSource;
    private LayoutInflater inflater;

    public SongAdapter(Context context, List<Song> lists, GrindEarPresenter presenter, int type, int classId, int audioType, int audioSource) {
        this.context = context;
        this.lists = lists;
        this.presenter = presenter;
        this.type = type;
        this.classId = classId;
        this.audioType = audioType;
        this.audioSource = audioSource;
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
        Glide.with(context).load(lists.get(i).getBookImageUrl()).into(songViewHolder.songImage);
        songViewHolder.songName.setText(lists.get(i).getBookName());
        songViewHolder.songCount.setText(lists.get(i).getCount() + "");
        if (lists.get(i).getIsCollected() == 0) {
            songViewHolder.collect.setTextColor(context.getResources().getColor(R.color.text_color));
            Drawable drawable = ContextCompat.getDrawable(context, R.drawable.icon_song_uncollect);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            songViewHolder.collect.setCompoundDrawables(drawable, null, null, null);
        } else {
            songViewHolder.collect.setTextColor(context.getResources().getColor(R.color.text_orange));
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
        songViewHolder.songDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PracticeActivity.class);
                intent.putExtra("song", lists.get(i));
                intent.putExtra("audioType", audioType);
                intent.putExtra("audioSource", audioSource);
                context.startActivity(intent);
            }
        });
        songViewHolder.collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lists.get(i).getIsCollected() == 0) {
                    presenter.collectCourse(type, lists.get(i).getBookId(), classId);
                } else {
                    presenter.cancelCollection(type, lists.get(i).getBookId(), classId);
                }
            }
        });
        songViewHolder.addMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lists.get(i).getIsJoinMaterial() == 0) {
                    presenter.joinMaterial(lists.get(i).getBookId(), classId);
                } else {
                    presenter.cancelMaterial(lists.get(i).getBookId(), classId);
                }
            }
        });
        songViewHolder.addCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //加入课程
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
        });
    }

    @Override
    public int getItemCount() {
        return lists != null ? lists.size() : 0;
    }
}
