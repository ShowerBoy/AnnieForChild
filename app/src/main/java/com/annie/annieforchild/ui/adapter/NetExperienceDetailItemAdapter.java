package com.annie.annieforchild.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.annie.annieforchild.R;
import com.annie.annieforchild.bean.net.experience.ExpItemBeanItem;
import com.annie.annieforchild.bean.net.netexpclass.NetExp_item_new;
import com.annie.annieforchild.ui.adapter.viewHolder.NetExperienceDetailItemViewHolder;
import com.annie.annieforchild.ui.interfaces.OnRecyclerItemClickListener;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by wanglei on 2019/4/16.
 */

public class NetExperienceDetailItemAdapter extends RecyclerView.Adapter<NetExperienceDetailItemViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<ExpItemBeanItem> lists;
    private OnRecyclerItemClickListener listener;

    public NetExperienceDetailItemAdapter(Context context, List<ExpItemBeanItem> lists, OnRecyclerItemClickListener listener) {
        this.context = context;
        this.lists = lists;
        this.listener = listener;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public NetExperienceDetailItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        NetExperienceDetailItemViewHolder holder = null;
        holder = new NetExperienceDetailItemViewHolder(inflater.inflate(R.layout.activity_net_experience_detail_item, viewGroup, false));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(NetExperienceDetailItemViewHolder netExperienceDetailItemViewHolder, int i) {
        Glide.with(context).load(lists.get(i).getIcon_url()).into(netExperienceDetailItemViewHolder.image);
        netExperienceDetailItemViewHolder.title.setText(lists.get(i).getUnit_name());
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }
}
