package com.annie.annieforchild.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.annie.annieforchild.R;
import com.annie.annieforchild.bean.net.netexpclass.NetExp_item_new;
import com.annie.annieforchild.ui.adapter.viewHolder.NetExperienceDetailItemViewHolder;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by wanglei on 2019/4/16.
 */

public class NetExperienceDetailItemAdapter extends RecyclerView.Adapter<NetExperienceDetailItemViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<NetExp_item_new> lists;

    public NetExperienceDetailItemAdapter(Context context, List<NetExp_item_new> lists) {
        this.context = context;
        this.lists = lists;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public NetExperienceDetailItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        NetExperienceDetailItemViewHolder holder = null;
        holder = new NetExperienceDetailItemViewHolder(inflater.inflate(R.layout.activity_net_experience_detail_item, viewGroup, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(NetExperienceDetailItemViewHolder netExperienceDetailItemViewHolder, int i) {
        Glide.with(context).load(R.drawable.class3to5_icon_class).into(netExperienceDetailItemViewHolder.image);
        netExperienceDetailItemViewHolder.title.setText(lists.get(i).getFchaptername());
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }
}
