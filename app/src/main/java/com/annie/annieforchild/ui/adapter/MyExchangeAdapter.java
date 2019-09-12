package com.annie.annieforchild.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.annie.annieforchild.R;
import com.annie.annieforchild.bean.GoldExchanges;
import com.annie.annieforchild.ui.adapter.viewHolder.MyExchangeViewHolder;

import java.util.List;

/**
 * Created by WangLei on 2018/2/2 0002
 */

public class MyExchangeAdapter extends RecyclerView.Adapter<MyExchangeViewHolder> {
    private Context context;
    private List<GoldExchanges> lists;
    private LayoutInflater inflater;

    public MyExchangeAdapter(Context context, List<GoldExchanges> lists) {
        this.context = context;
        this.lists = lists;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MyExchangeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyExchangeViewHolder holder = null;
        holder = new MyExchangeViewHolder(inflater.inflate(R.layout.activity_my_exchange_list_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyExchangeViewHolder holder, int position) {
        holder.exchange_name.setText(lists.get(position).getDetail());
        holder.exchange_time.setText(lists.get(position).getTime().substring(0, 4) + "-" + lists.get(position).getTime().substring(4, 6) + "-" + lists.get(position).getTime().substring(6, 8));
        if (lists.get(position).getAction() == 0) {
            holder.exchange_value.setText("-" + lists.get(position).getCount());
        } else {
            holder.exchange_value.setText("+" + lists.get(position).getCount());
        }

    }

    @Override
    public int getItemCount() {
        return lists != null ? lists.size() : 0;
    }
}
