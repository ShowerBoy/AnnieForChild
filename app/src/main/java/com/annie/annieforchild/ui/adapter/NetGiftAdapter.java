package com.annie.annieforchild.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.annie.annieforchild.R;
import com.annie.annieforchild.bean.net.Gift;
import com.annie.annieforchild.ui.adapter.viewHolder.NetGiftViewHolder;
import com.annie.annieforchild.ui.interfaces.OnRecyclerItemClickListener;

import java.util.List;

/**
 * Created by wanglei on 2018/9/25.
 */

public class NetGiftAdapter extends RecyclerView.Adapter<NetGiftViewHolder> {
    private Context context;
    private List<Gift> lists;
    private LayoutInflater inflater;
    private OnRecyclerItemClickListener listener;
    private int type; //0:NetSuggest  1:GiftActivity

    public NetGiftAdapter(Context context, List<Gift> lists, int type, OnRecyclerItemClickListener listener) {
        this.context = context;
        this.lists = lists;
        this.type = type;
        this.listener = listener;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public NetGiftViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        NetGiftViewHolder holder = null;
        holder = new NetGiftViewHolder(inflater.inflate(R.layout.activity_net_gift, viewGroup, false));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(NetGiftViewHolder netGiftViewHolder, int i) {
        if (type == 0) {
            netGiftViewHolder.help.setVisibility(View.VISIBLE);
            netGiftViewHolder.name.setText(lists.get(i).getName());
        } else {
            netGiftViewHolder.help.setVisibility(View.GONE);
            if (lists.get(i).isSelect()) {
                netGiftViewHolder.name.setText(lists.get(i).getName());
            } else {

            }
        }
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }
}
