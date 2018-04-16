package com.annie.annieforchild.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.song.Song;
import com.annie.annieforchild.ui.activity.pk.PracticeActivity;
import com.annie.annieforchild.ui.adapter.viewHolder.SongViewHolder;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by wanglei on 2018/3/24.
 */

public class SongAdapter extends RecyclerView.Adapter<SongViewHolder> {
    private Context context;
    private List<Song> lists;
    private LayoutInflater inflater;

    public SongAdapter(Context context, List<Song> lists) {
        this.context = context;
        this.lists = lists;
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
        songViewHolder.songDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PracticeActivity.class);
                intent.putExtra("song", lists.get(i));
                context.startActivity(intent);
            }
        });
        songViewHolder.collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SystemUtils.show(context, "收藏" + lists.get(i).getBookId());
            }
        });
        songViewHolder.addMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SystemUtils.show(context, "加入教材" + lists.get(i).getBookId());
            }
        });
        songViewHolder.addCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SystemUtils.show(context, "加入课程" + lists.get(i).getBookId());
            }
        });


    }


    @Override
    public int getItemCount() {
        return lists != null ? lists.size() : 0;
    }
}
