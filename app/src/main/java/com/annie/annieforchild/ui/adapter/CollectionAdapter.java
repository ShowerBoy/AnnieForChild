package com.annie.annieforchild.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.Collection;
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
    private int type;
    private OnMyItemClickListener listener;

    public CollectionAdapter(Context context, List<Collection> lists, int type, OnMyItemClickListener listener) {
        this.context = context;
        this.lists = lists;
        this.type = type;
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
        Glide.with(context).load(lists.get(position).getImageUrl()).placeholder(R.drawable.lesson_grind_ear).into(holder.collection_image);
        holder.collection_name.setText(lists.get(position).getName());
        holder.uncollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lists != null ? lists.size() : 0;
    }
}
