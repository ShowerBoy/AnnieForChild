package com.annie.annieforchild.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.annie.annieforchild.R;
import com.annie.annieforchild.bean.song.MusicPart;
import com.annie.annieforchild.ui.adapter.viewHolder.MusicListViewHolder;
import com.annie.annieforchild.ui.interfaces.OnRecyclerItemClickListener;

import java.util.List;

/**
 * Created by wanglei on 2018/7/24.
 */

public class MusicListAdapter extends RecyclerView.Adapter<MusicListViewHolder> {
    private Context context;
    private List<MusicPart> lists;
    private LayoutInflater inflater;
    private OnRecyclerItemClickListener listener;

    public MusicListAdapter(Context context, List<MusicPart> lists, OnRecyclerItemClickListener listener) {
        this.context = context;
        this.lists = lists;
        this.listener = listener;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MusicListViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        MusicListViewHolder holder = null;
        holder = new MusicListViewHolder(inflater.inflate(R.layout.activity_music_list_item, viewGroup, false));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(MusicListViewHolder musicListViewHolder, int i) {
        musicListViewHolder.name.setText(lists.get(i).getName());
        if (lists.get(i).isPlaying()) {
            musicListViewHolder.name.setTextColor(context.getResources().getColor(R.color.text_orange));
        } else {
            musicListViewHolder.name.setTextColor(context.getResources().getColor(R.color.text_black));
        }
        musicListViewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }
}
