package com.annie.annieforchild.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.annie.annieforchild.R;
import com.annie.annieforchild.ui.adapter.viewHolder.LyricViewHolder;

import java.util.List;

/**
 * Created by wanglei on 2018/12/18.
 */

public class LyricAdapter extends RecyclerView.Adapter<LyricViewHolder> {
    private Context context;
    private List<String> lists;
    private LayoutInflater inflater;

    public LyricAdapter(Context context, List<String> lists) {
        this.context = context;
        this.lists = lists;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public LyricViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LyricViewHolder holder = null;
        holder = new LyricViewHolder(inflater.inflate(R.layout.activity_lyric_item, viewGroup, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(LyricViewHolder lyricViewHolder, int i) {
        lyricViewHolder.lyric.setText(lists.get(i));
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }
}
