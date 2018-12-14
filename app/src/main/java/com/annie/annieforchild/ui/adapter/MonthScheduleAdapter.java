package com.annie.annieforchild.ui.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.views.RecyclerLinearLayoutManager;
import com.annie.annieforchild.bean.schedule.TotalSchedule;
import com.annie.annieforchild.presenter.SchedulePresenter;
import com.annie.annieforchild.ui.adapter.viewHolder.MonthScheduleViewHolder;

import java.util.List;

/**
 * Created by wanglei on 2018/8/16.
 */

public class MonthScheduleAdapter extends RecyclerView.Adapter<MonthScheduleViewHolder> {
    private Context context;
    private List<TotalSchedule> lists;
    private LayoutInflater inflater;
    private ScheduleAdapter adapter;
    private SchedulePresenter presenter;
    private RecyclerLinearLayoutManager linearLayoutManager;

    public MonthScheduleAdapter(Context context, List<TotalSchedule> lists, SchedulePresenter presenter) {
        this.context = context;
        this.lists = lists;
        this.presenter = presenter;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MonthScheduleViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        MonthScheduleViewHolder holder = null;
        holder = new MonthScheduleViewHolder(inflater.inflate(R.layout.activity_month_schedule_item, viewGroup, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MonthScheduleViewHolder monthScheduleViewHolder, int i) {
        monthScheduleViewHolder.date.setText(lists.get(i).getTime());
        linearLayoutManager = new RecyclerLinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.setScrollEnabled(false);
        monthScheduleViewHolder.recycler.setLayoutManager(linearLayoutManager);
        adapter = new ScheduleAdapter(context, lists.get(i), presenter, 1);
        monthScheduleViewHolder.recycler.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }
}
