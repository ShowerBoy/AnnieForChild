package com.annie.annieforchild.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.annie.annieforchild.R;
import com.annie.annieforchild.bean.net.GiftBean;
import com.annie.annieforchild.ui.adapter.viewHolder.NetGiftPopupViewHolder;
import com.annie.annieforchild.ui.interfaces.OnRecyclerItemClickListener;

import java.util.List;

/**
 * Created by wanglei on 2019/4/28.
 */

public class NetGiftPopupAdapter extends RecyclerView.Adapter<NetGiftPopupViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<GiftBean> lists;
    private OnRecyclerItemClickListener listener;

    public NetGiftPopupAdapter(Context context, List<GiftBean> lists, OnRecyclerItemClickListener listener) {
        this.context = context;
        this.lists = lists;
        this.listener = listener;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public NetGiftPopupViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        NetGiftPopupViewHolder holder = null;
        holder = new NetGiftPopupViewHolder(inflater.inflate(R.layout.activity_net_gift_item, viewGroup, false));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(NetGiftPopupViewHolder netGiftPopupViewHolder, int i) {
        if (lists.get(i).isSelect()) {
            netGiftPopupViewHolder.check.setImageResource(R.drawable.icon_net_gift_t);
        } else {
            netGiftPopupViewHolder.check.setImageResource(R.drawable.icon_net_gift_f);
        }
        netGiftPopupViewHolder.name.setText(lists.get(i).getTitle());
        netGiftPopupViewHolder.content.setText(lists.get(i).getGiftName());
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }
}
