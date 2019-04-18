package com.annie.annieforchild.ui.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.annie.annieforchild.R;
import com.annie.annieforchild.bean.net.netexpclass.Info_new;
import com.annie.annieforchild.ui.adapter.viewHolder.NetExperienceDetailNewViewHolder;

import java.util.List;

/**
 * Created by wanglei on 2019/4/16.
 */

public class NetExperienceDetailNewAdapter extends RecyclerView.Adapter<NetExperienceDetailNewViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<Info_new> lists;
    private NetExperienceDetailItemAdapter adapter;

    public NetExperienceDetailNewAdapter(Context context, List<Info_new> lists) {
        this.context = context;
        this.lists = lists;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public NetExperienceDetailNewViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        NetExperienceDetailNewViewHolder holder = null;
        holder = new NetExperienceDetailNewViewHolder(inflater.inflate(R.layout.activity_net_experience_new_item, viewGroup, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(NetExperienceDetailNewViewHolder netExperienceDetailNewViewHolder, int i) {
        if (lists.get(i).getIsshow().equals("0")) {
            netExperienceDetailNewViewHolder.setVisibility(false);
        } else {
            netExperienceDetailNewViewHolder.setVisibility(true);
            netExperienceDetailNewViewHolder.title.setText(lists.get(i).getFchaptername());
            adapter = new NetExperienceDetailItemAdapter(context, lists.get(i).getInfo());
            GridLayoutManager layoutManager = new GridLayoutManager(context, 2);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            netExperienceDetailNewViewHolder.recycler.setLayoutManager(layoutManager);
            netExperienceDetailNewViewHolder.recycler.setNestedScrollingEnabled(false);
            netExperienceDetailNewViewHolder.recycler.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }

    }

    @Override
    public int getItemCount() {
        return lists.size();
    }
}
