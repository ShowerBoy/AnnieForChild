package com.annie.annieforchild.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.annie.annieforchild.R;
import com.annie.annieforchild.bean.net.SpecInfoInfo;
import com.annie.annieforchild.ui.adapter.viewHolder.NetSpecialConInfoViewHolder;
import com.annie.annieforchild.ui.interfaces.OnRecyclerItemClickListener;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by wanglei on 2019/3/14.
 */

public class NetSpecialConInfoAdapter extends RecyclerView.Adapter<NetSpecialConInfoViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<SpecInfoInfo> lists;
    private OnRecyclerItemClickListener listener;

    public NetSpecialConInfoAdapter(Context context, List<SpecInfoInfo> lists, OnRecyclerItemClickListener listener) {
        this.context = context;
        this.lists = lists;
        this.listener = listener;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public NetSpecialConInfoViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        NetSpecialConInfoViewHolder holder = null;
        holder = new NetSpecialConInfoViewHolder(inflater.inflate(R.layout.activity_net_special_coninfo_item, viewGroup, false));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(NetSpecialConInfoViewHolder netSpecialConInfoViewHolder, int i) {
        netSpecialConInfoViewHolder.title.setText(lists.get(i).getFcategoryname() != null ? lists.get(i).getFcategoryname() : "");
        if (lists.get(i).getType().equals("0")) {
            Glide.with(context).load(R.drawable.classleaf_icon_prepare1).into(netSpecialConInfoViewHolder.image);
        } else {
            Glide.with(context).load(R.drawable.class3to5_icon_class).into(netSpecialConInfoViewHolder.image);
        }
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }
}
