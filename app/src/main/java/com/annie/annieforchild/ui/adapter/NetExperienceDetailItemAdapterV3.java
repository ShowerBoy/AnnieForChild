package com.annie.annieforchild.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.annie.annieforchild.R;
import com.annie.annieforchild.bean.net.experience.ExpItemBeanItemV3;
import com.annie.annieforchild.ui.adapter.viewHolder.NetExperienceDetailItemViewHolderV3;
import com.annie.annieforchild.ui.interfaces.OnRecyclerItemClickListener;

import java.util.List;

/**
 * 体验课V3版
 */

public class NetExperienceDetailItemAdapterV3 extends RecyclerView.Adapter<NetExperienceDetailItemViewHolderV3> {
    private Context context;
    private LayoutInflater inflater;
    private List<ExpItemBeanItemV3> lists;
    private OnRecyclerItemClickListener listener;

    public NetExperienceDetailItemAdapterV3(Context context, List<ExpItemBeanItemV3> lists, OnRecyclerItemClickListener listener) {
        this.context = context;
        this.lists = lists;
        this.listener = listener;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public NetExperienceDetailItemViewHolderV3 onCreateViewHolder(ViewGroup viewGroup, int i) {
        NetExperienceDetailItemViewHolderV3 holder = null;
        holder = new NetExperienceDetailItemViewHolderV3(inflater.inflate(R.layout.activity_net_experience_detail_item_v3, viewGroup, false));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(NetExperienceDetailItemViewHolderV3 netExperienceDetailItemViewHolder, int i) {
//        Glide.with(context).load(lists.get(i).getIcon_url()).into(netExperienceDetailItemViewHolder.image);
        netExperienceDetailItemViewHolder.title.setText(lists.get(i).getUnit_name());
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }
}
