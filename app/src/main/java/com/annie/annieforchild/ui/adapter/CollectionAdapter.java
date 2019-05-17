package com.annie.annieforchild.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.Collection;
import com.annie.annieforchild.bean.song.Song;
import com.annie.annieforchild.ui.activity.VideoActivity;
import com.annie.annieforchild.ui.activity.VideoActivity_new;
import com.annie.annieforchild.ui.activity.pk.PracticeActivity;
import com.annie.annieforchild.ui.adapter.viewHolder.CollectionViewHolder;
import com.annie.annieforchild.ui.interfaces.OnMyItemClickListener;
import com.annie.annieforchild.ui.interfaces.OnRecyclerItemClickListener;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * 收藏适配器
 * Created by WangLei on 2018/2/27 0027
 */

public class CollectionAdapter extends RecyclerView.Adapter<CollectionViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<Collection> lists;
    private int type, audioType, collectType, bookType;
    private OnMyItemClickListener listener;

    public CollectionAdapter(Context context, List<Collection> lists, int type, int audioType, int collectType, int bookType, OnMyItemClickListener listener) {
        this.context = context;
        this.lists = lists;
        this.audioType = audioType;
        this.type = type;
        this.collectType = collectType;
        this.bookType = bookType;
        this.listener = listener;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public CollectionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CollectionViewHolder holder = null;
        holder = new CollectionViewHolder(inflater.inflate(R.layout.activity_collection_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(CollectionViewHolder holder, int position) {
        Glide.with(context).load(lists.get(position).getImageUrl()).into(holder.collection_image);
        holder.collection_name.setText(lists.get(position).getName());
        holder.uncollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(position);
            }
        });
        holder.total.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lists.get(position).getAudioSource() == 100) {
//                    SystemUtils.startVideo(context, lists.get(position).getAnimationUrl());
//                    Intent intent = new Intent(context, VideoActivity.class);
                    Intent intent = new Intent(context, VideoActivity_new.class);
                    intent.putExtra("url", lists.get(position).getAnimationUrl());
                    intent.putExtra("imageUrl", lists.get(position).getImageUrl());
                    intent.putExtra("name", lists.get(position).getName());
                    intent.putExtra("id", lists.get(position).getCourseId());
                    intent.putExtra("isTime", true);
                    context.startActivity(intent);
                } else {
                    Song song = new Song();
                    song.setBookName(lists.get(position).getName());
                    song.setBookImageUrl(lists.get(position).getImageUrl());
                    song.setBookId(lists.get(position).getCourseId());
                    song.setIsmoerduo(lists.get(position).getIsmoerduo());
                    song.setIsyuedu(lists.get(position).getIsyuedu());
                    song.setIskouyu(lists.get(position).getIskouyu());
                    Intent intent = new Intent(context, PracticeActivity.class);
                    intent.putExtra("song", song);
                    intent.putExtra("type", type);
                    intent.putExtra("audioType", audioType);
                    intent.putExtra("audioSource", lists.get(position).getAudioSource());
                    intent.putExtra("collectType", collectType);
                    intent.putExtra("bookType", bookType);
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return lists != null ? lists.size() : 0;
    }
}
