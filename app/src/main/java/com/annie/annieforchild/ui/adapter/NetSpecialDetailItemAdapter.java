package com.annie.annieforchild.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.annie.annieforchild.R;
import com.annie.annieforchild.bean.net.SpecInfoInfo;
import com.annie.annieforchild.bean.net.experience.ExpItemBeanItem;
import com.annie.annieforchild.ui.adapter.viewHolder.NetSpecialDetailItemViewHolder;
import com.annie.annieforchild.ui.interfaces.OnRecyclerItemClickListener;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by wanglei on 2019/5/6.
 */

public class NetSpecialDetailItemAdapter extends RecyclerView.Adapter<NetSpecialDetailItemViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<ExpItemBeanItem> lists;
    private OnRecyclerItemClickListener listener;

    public NetSpecialDetailItemAdapter(Context context, List<ExpItemBeanItem> lists, OnRecyclerItemClickListener listener) {
        this.context = context;
        this.lists = lists;
        this.listener = listener;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public NetSpecialDetailItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        NetSpecialDetailItemViewHolder holder = null;
        holder = new NetSpecialDetailItemViewHolder(inflater.inflate(R.layout.activity_net_special_detail_item2, viewGroup, false));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(NetSpecialDetailItemViewHolder netSpecialDetailItemViewHolder, int i) {
        netSpecialDetailItemViewHolder.title.setText(lists.get(i).getUnit_name() != null ? lists.get(i).getUnit_name() : "");
        Glide.with(context).load(lists.get(i).getIcon_url()).into(netSpecialDetailItemViewHolder.image);
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }
}
