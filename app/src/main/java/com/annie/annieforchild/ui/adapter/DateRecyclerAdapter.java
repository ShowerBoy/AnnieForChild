package com.annie.annieforchild.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.annie.annieforchild.R;
import com.annie.annieforchild.bean.DateBean;
import com.annie.annieforchild.ui.adapter.viewHolder.DateViewHolder;
import com.annie.annieforchild.ui.interfaces.OnRecyclerItemClickListener;

import java.util.List;

/**
 * 课表日期选择适配器
 * Created by WangLei on 2018/2/28 0028
 */

public class DateRecyclerAdapter extends RecyclerView.Adapter<DateViewHolder> {
    private Context context;
    private List<DateBean> lists;
    private LayoutInflater inflater;
    private OnRecyclerItemClickListener listener;
    private int screenwidth;

    public DateRecyclerAdapter(Context context, List<DateBean> lists, int screenwidth, OnRecyclerItemClickListener listener) {
        this.context = context;
        this.lists = lists;
        this.listener = listener;
        this.screenwidth = screenwidth;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public DateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        DateViewHolder holder = null;
        View view = inflater.inflate(R.layout.activity_date_item, parent, false);
        view.getLayoutParams().width = screenwidth / 7;
        holder = new DateViewHolder(view);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(view);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                listener.onItemLongClick(view);
                return true;
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(DateViewHolder holder, int position) {
        holder.week.setText(lists.get(position).getWeek());
        holder.day.setText(lists.get(position).getMonth() + "-" + lists.get(position).getDay());
        if (lists.get(position).isSelect()) {
            holder.week.setTextColor(context.getResources().getColor(R.color.button_orange));
            holder.day.setTextColor(context.getResources().getColor(R.color.button_orange));
//            holder.line.setBackgroundColor(context.getResources().getColor(R.color.button_orange));
        } else {
            holder.week.setTextColor(context.getResources().getColor(R.color.text_color));
            holder.day.setTextColor(context.getResources().getColor(R.color.text_color));
//            holder.line.setBackgroundColor(context.getResources().getColor(R.color.clarity));
        }
    }

    @Override
    public int getItemCount() {
        return lists != null ? lists.size() : 0;
    }
}
