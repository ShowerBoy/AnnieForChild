package com.annie.annieforchild.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.annie.annieforchild.R;
import com.annie.annieforchild.bean.schedule.Schedule;
import com.annie.annieforchild.ui.adapter.viewHolder.OfflineScheViewHolder;

import java.util.List;

/**
 * 线下课程适配器
 * Created by WangLei on 2018/3/1 0001
 */

public class OfflineScheAdapter extends RecyclerView.Adapter<OfflineScheViewHolder> {
    private Context context;
    private List<Schedule> lists;
    private LayoutInflater inflater;

    public OfflineScheAdapter(Context context, List<Schedule> lists) {
        this.context = context;
        this.lists = lists;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public OfflineScheViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        OfflineScheViewHolder holder = null;
        holder = new OfflineScheViewHolder(inflater.inflate(R.layout.activity_offline_sche_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(OfflineScheViewHolder holder, int position) {
        holder.scheduleTime.setText(lists.get(position).getStart() + "-" + lists.get(position).getStop());
        holder.scheduleName.setText(lists.get(position).getDetail());
        holder.selectSpot.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return lists != null ? lists.size() : 0;
    }
}
