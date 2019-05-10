package com.annie.annieforchild.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.annie.annieforchild.R;
import com.annie.annieforchild.bean.net.experience.EveryTaskBean;
import com.annie.annieforchild.ui.adapter.viewHolder.EveryDayTaskViewHolder;
import com.annie.annieforchild.ui.interfaces.OnRecyclerItemClickListener;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by wanglei on 2019/5/8.
 */

public class EveryDayTaskAdapter extends RecyclerView.Adapter<EveryDayTaskViewHolder> {
    private Context context;
    private List<String> lists;
    private LayoutInflater inflater;

    public EveryDayTaskAdapter(Context context, List<String> lists) {
        this.context = context;
        this.lists = lists;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public EveryDayTaskViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        EveryDayTaskViewHolder holder = null;
        holder = new EveryDayTaskViewHolder(inflater.inflate(R.layout.activity_every_day_task_item, viewGroup, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(EveryDayTaskViewHolder everyDayTaskViewHolder, int i) {
        Glide.with(context).load(lists.get(i)).error(R.drawable.image_error).into(everyDayTaskViewHolder.image);
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }
}
