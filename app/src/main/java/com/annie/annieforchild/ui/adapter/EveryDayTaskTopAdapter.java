package com.annie.annieforchild.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.annie.annieforchild.R;
import com.annie.annieforchild.bean.net.experience.EveryTaskBean;
import com.annie.annieforchild.ui.adapter.viewHolder.EveryDayTaskTopViewHolder;
import com.annie.annieforchild.ui.interfaces.OnRecyclerItemClickListener;

import java.util.List;

/**
 * Created by wanglei on 2019/5/8.
 */

public class EveryDayTaskTopAdapter extends RecyclerView.Adapter<EveryDayTaskTopViewHolder> {
    private Context context;
    private List<EveryTaskBean> lists;
    private LayoutInflater inflater;
    private OnRecyclerItemClickListener listener;

    public EveryDayTaskTopAdapter(Context context, List<EveryTaskBean> lists, OnRecyclerItemClickListener listener) {
        this.context = context;
        this.lists = lists;
        this.listener = listener;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public EveryDayTaskTopViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        EveryDayTaskTopViewHolder holder = null;
        holder = new EveryDayTaskTopViewHolder(inflater.inflate(R.layout.activity_every_day_task_top, viewGroup, false));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(EveryDayTaskTopViewHolder everyDayTaskTopViewHolder, int i) {
        if (lists.get(i).getStatus() == 3) {
            everyDayTaskTopViewHolder.title.setBackgroundResource(R.drawable.icon_task_t);
            everyDayTaskTopViewHolder.title.setTextColor(context.getResources().getColor(R.color.white));
            everyDayTaskTopViewHolder.title.setText(lists.get(i).getTitle());
        } else {
            if (lists.get(i).getIsSelect()) {
                everyDayTaskTopViewHolder.title.setBackgroundResource(R.drawable.icon_task_today);
                everyDayTaskTopViewHolder.title.setTextColor(context.getResources().getColor(R.color.white));
                everyDayTaskTopViewHolder.title.setText(lists.get(i).getTitle());
            } else {
                everyDayTaskTopViewHolder.title.setBackgroundResource(R.drawable.icon_task_f);
                everyDayTaskTopViewHolder.title.setTextColor(context.getResources().getColor(R.color.text_orange));
                everyDayTaskTopViewHolder.title.setText(lists.get(i).getTitle());
            }
        }
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }
}
