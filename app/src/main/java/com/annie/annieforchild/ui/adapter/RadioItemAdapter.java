package com.annie.annieforchild.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.annie.annieforchild.R;
import com.annie.annieforchild.bean.radio.RadioTag;
import com.annie.annieforchild.ui.adapter.viewHolder.RadioItemViewHolder;
import com.annie.annieforchild.ui.interfaces.OnRecyclerItemClickListener;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by wanglei on 2018/12/14.
 */

public class RadioItemAdapter extends RecyclerView.Adapter<RadioItemViewHolder> {
    private Context context;
    private List<RadioTag> lists;
    private LayoutInflater inflater;
    private OnRecyclerItemClickListener listener;

    public RadioItemAdapter(Context context, List<RadioTag> lists, OnRecyclerItemClickListener listener) {
        this.context = context;
        this.lists = lists;
        this.listener = listener;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RadioItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        RadioItemViewHolder holder = null;
        holder = new RadioItemViewHolder(inflater.inflate(R.layout.activity_radio_item_item, viewGroup, false));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(RadioItemViewHolder radioItemViewHolder, int i) {
        Glide.with(context).load(lists.get(i).getRadioImageUrl()).placeholder(R.drawable.image_loading).error(R.drawable.image_loading).into(radioItemViewHolder.image);
        radioItemViewHolder.title.setText(lists.get(i).getRadioTitle());
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }
}
