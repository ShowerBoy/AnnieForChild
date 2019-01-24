package com.annie.annieforchild.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.annie.annieforchild.R;
import com.annie.annieforchild.ui.adapter.viewHolder.NetSuggestViewHolder;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL;

/**
 * Created by wanglei on 2018/11/14.
 */

public class NetSuggestAdapter extends RecyclerView.Adapter<NetSuggestViewHolder> {
    private Context context;
    private List<String> lists,list_middle;
    private LayoutInflater inflater;


    public NetSuggestAdapter(Context context, List<String> lists, List<String> list_middle) {
        this.context = context;
        this.lists = lists;
        this.list_middle=list_middle;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public NetSuggestViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        NetSuggestViewHolder holder = null;

        holder = new NetSuggestViewHolder(inflater.inflate(R.layout.activity_net_suggest_fragment_item, viewGroup, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(NetSuggestViewHolder netSuggestViewHolder, int i) {
        Glide.with(context).load(lists.get(i)).into(netSuggestViewHolder.image);
    }

    @Override
    public int getItemCount() {
        return lists.size()+1;
    }


}
