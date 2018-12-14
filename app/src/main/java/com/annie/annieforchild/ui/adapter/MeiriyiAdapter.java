package com.annie.annieforchild.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.annie.annieforchild.R;
import com.annie.annieforchild.bean.song.Song;
import com.annie.annieforchild.ui.adapter.viewHolder.MeiriyiViewHolder;
import com.annie.annieforchild.ui.interfaces.OnRecyclerItemClickListener;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by wanglei on 2018/7/19.
 */

public class MeiriyiAdapter extends RecyclerView.Adapter<MeiriyiViewHolder> {
    private Context context;
    private List<Song> lists;
    private OnRecyclerItemClickListener listener;
    private LayoutInflater inflater;

    public MeiriyiAdapter(Context context, List<Song> lists, OnRecyclerItemClickListener listener) {
        this.context = context;
        this.lists = lists;
        this.listener = listener;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MeiriyiViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        MeiriyiViewHolder holder = null;
        holder = new MeiriyiViewHolder(inflater.inflate(R.layout.activity_meiriyi_item, viewGroup, false));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(MeiriyiViewHolder meiriyiViewHolder, int i) {
        Glide.with(context).load(lists.get(i).getBookImageUrl()).into(meiriyiViewHolder.image);
        meiriyiViewHolder.text.setText(lists.get(i).getBookName());
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }
}
