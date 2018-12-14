package com.annie.annieforchild.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.annie.annieforchild.R;
import com.annie.annieforchild.ui.adapter.viewHolder.CommitBookViewHolder;

import java.util.List;

/**
 * Created by wanglei on 2018/7/10.
 */

public class CommitBookAdapter extends RecyclerView.Adapter<CommitBookViewHolder> {
    private Context context;
    private List<String> lists;
    private LayoutInflater inflater;

    public CommitBookAdapter(Context context, List<String> lists) {
        this.context = context;
        this.lists = lists;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public CommitBookViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        CommitBookViewHolder holder = null;
        holder = new CommitBookViewHolder(inflater.inflate(R.layout.activity_commit_item, viewGroup, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(CommitBookViewHolder commitBookViewHolder, int i) {
        commitBookViewHolder.textView.setText(lists.get(i));
    }

    @Override
    public int getItemCount() {
        return lists != null ? lists.size() : 0;
    }
}
