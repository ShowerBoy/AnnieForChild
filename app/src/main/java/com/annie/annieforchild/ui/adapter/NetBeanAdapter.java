package com.annie.annieforchild.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.annie.annieforchild.R;
import com.annie.annieforchild.bean.net.NetClass;
import com.annie.annieforchild.ui.adapter.viewHolder.NetBeanViewHolder;
import com.annie.annieforchild.ui.interfaces.OnRecyclerItemClickListener;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by wanglei on 2018/9/22.
 */

public class NetBeanAdapter extends RecyclerView.Adapter<NetBeanViewHolder> {
    private Context context;
    private List<NetClass> lists;
    private LayoutInflater inflater;
    private OnRecyclerItemClickListener listener;

    public NetBeanAdapter(Context context, List<NetClass> lists, OnRecyclerItemClickListener listener) {
        this.context = context;
        this.lists = lists;
        this.listener = listener;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public NetBeanViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        NetBeanViewHolder holder = null;
        holder = new NetBeanViewHolder(inflater.inflate(R.layout.activity_netbean_item, viewGroup, false));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(NetBeanViewHolder netBeanViewHolder, int i) {
        Glide.with(context).load(lists.get(i).getNetImageUrl()).into(netBeanViewHolder.image);
        netBeanViewHolder.title.setText(lists.get(i).getNetName());
        netBeanViewHolder.summary.setText(lists.get(i).getNetSummary());
        if (lists.get(i).getEvent() == null) {
            netBeanViewHolder.event.setVisibility(View.GONE);
        } else {
            netBeanViewHolder.event.setVisibility(View.VISIBLE);
            netBeanViewHolder.event.setText(lists.get(i).getEvent());
        }
        netBeanViewHolder.price.setText(lists.get(i).getPrice() + "元");
        if (lists.get(i).getIsBuy() == 0) {
            netBeanViewHolder.hadbuy.setVisibility(View.GONE);
        } else {
            netBeanViewHolder.hadbuy.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }
}
