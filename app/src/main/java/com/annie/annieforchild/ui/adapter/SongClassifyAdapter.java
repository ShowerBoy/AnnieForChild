package com.annie.annieforchild.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.annie.annieforchild.R;
import com.annie.annieforchild.bean.song.SongClassify;
import com.annie.annieforchild.ui.adapter.viewHolder.SongClassifyViewHolder;
import com.annie.annieforchild.ui.interfaces.OnRecyclerItemClickListener;

import java.util.List;

/**
 * Created by wanglei on 2018/3/23.
 */

public class SongClassifyAdapter extends RecyclerView.Adapter<SongClassifyViewHolder> {
    private Context context;
    private List<SongClassify> lists;
    private OnRecyclerItemClickListener listener;
    private LayoutInflater inflater;

    public SongClassifyAdapter(Context context, List<SongClassify> lists, OnRecyclerItemClickListener listener) {
        this.context = context;
        this.lists = lists;
        this.listener = listener;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public SongClassifyViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        SongClassifyViewHolder holder = null;
        holder = new SongClassifyViewHolder(inflater.inflate(R.layout.activity_song_classify_item, parent, false));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listener.onItemLongClick(v);
                return true;
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(SongClassifyViewHolder holder, int i) {
        holder.classifyTitle.setText(lists.get(i).getTitle());
        if (lists.get(i).isSelected()) {
            holder.classifyTitle.setTextColor(context.getResources().getColor(R.color.button_orange));
        } else {
            holder.classifyTitle.setTextColor(context.getResources().getColor(R.color.text_color));
        }
    }

    @Override
    public int getItemCount() {
        return lists != null ? lists.size() : 0;
    }
}
