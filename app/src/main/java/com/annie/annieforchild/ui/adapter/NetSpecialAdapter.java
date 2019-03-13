package com.annie.annieforchild.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.annie.annieforchild.R;
import com.annie.annieforchild.bean.net.NetClass;
import com.annie.annieforchild.ui.adapter.viewHolder.NetBeanViewHolder;
import com.annie.annieforchild.ui.adapter.viewHolder.NetSpecialViewHolder;
import com.annie.annieforchild.ui.interfaces.OnRecyclerItemClickListener;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by wanglei on 2019/3/13.
 */

public class NetSpecialAdapter extends RecyclerView.Adapter<NetSpecialViewHolder> {
    private Context context;
    private List<NetClass> lists;
    private LayoutInflater inflater;
    private OnRecyclerItemClickListener listener;

    public NetSpecialAdapter(Context context, List<NetClass> lists, OnRecyclerItemClickListener listener) {
        this.context = context;
        this.lists = lists;
        this.listener = listener;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public NetSpecialViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        NetSpecialViewHolder holder = null;
        holder = new NetSpecialViewHolder(inflater.inflate(R.layout.activity_netspecial_item, viewGroup, false));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(NetSpecialViewHolder netSpecialViewHolder, int i) {
        Glide.with(context).load(lists.get(i).getNetImageUrl()).error(R.drawable.image_error).into(netSpecialViewHolder.image);
        netSpecialViewHolder.title.setText(lists.get(i).getNetName());
        netSpecialViewHolder.summary.setText(lists.get(i).getNetSummary());
        if (lists.get(i).getEvent() == null) {
            netSpecialViewHolder.event.setVisibility(View.GONE);
        } else {
            netSpecialViewHolder.event.setVisibility(View.VISIBLE);
            netSpecialViewHolder.event.setText(lists.get(i).getEvent());
        }
        netSpecialViewHolder.price.setText("￥" + lists.get(i).getPrice());
        if (lists.get(i).getIsBuy() == 0) {
            netSpecialViewHolder.hadbuy.setVisibility(View.GONE);
        } else {
            netSpecialViewHolder.hadbuy.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return lists.size();
    }
}
