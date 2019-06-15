package com.annie.annieforchild.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.annie.annieforchild.R;
import com.annie.annieforchild.bean.nectar.NectarBean;
import com.annie.annieforchild.ui.adapter.viewHolder.MyNectarViewHolder;

import java.util.List;

/**
 * 花蜜适配器
 * Created by WangLei on 2018/3/7 0007
 */

public class MyNectarAdapter extends RecyclerView.Adapter<MyNectarViewHolder> {
    private Context context;
    private List<NectarBean> lists;
    private LayoutInflater inflater;

    public MyNectarAdapter(Context context, List<NectarBean> lists) {
        this.context = context;
        this.lists = lists;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MyNectarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyNectarViewHolder holder = null;
        holder = new MyNectarViewHolder(inflater.inflate(R.layout.activity_my_nectar_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyNectarViewHolder holder, int position) {
        holder.incomeName.setText(lists.get(position).getDetail());
        int second;
        if (lists.get(position).getDuration() != null) {
            second = Integer.parseInt(lists.get(position).getDuration());
        } else {
            second = 0;
        }
        if (lists.get(position).getType() == 0) {
            if (second >= 60) {
                holder.incomeTime.setText("（" + second / 60 + "分钟）");
            } else {
                if (second != 0) {
                    holder.incomeTime.setText("（" + second + "秒）");
                } else {
                    holder.incomeTime.setText("");
                }
            }
        } else {
            holder.incomeTime.setText("");
        }

        holder.incomeDate.setText(lists.get(position).getTime().substring(0, 4) + "-" + lists.get(position).getTime().substring(4, 6) + "-" + lists.get(position).getTime().substring(6, 8));
        if (lists.get(position).getType() == 0) {
            holder.incomeCount.setText("+" + lists.get(position).getCount() + "");
        } else {
            holder.incomeCount.setText("-" + lists.get(position).getCount() + "");
        }
    }

    @Override
    public int getItemCount() {
        return lists != null ? lists.size() : 0;
    }
}
