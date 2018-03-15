package com.annie.annieforchild.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.annie.annieforchild.R;
import com.annie.annieforchild.bean.Record;
import com.annie.annieforchild.ui.adapter.viewHolder.MyRecordViewHolder;

import java.util.List;

/**
 * 我的录音适配器
 * Created by WangLei on 2018/3/8 0008
 */

public class MyRecordAdapter extends RecyclerView.Adapter<MyRecordViewHolder> {
    private Context context;
    private List<Record> lists;
    private LayoutInflater inflater;

    public MyRecordAdapter(Context context, List<Record> lists) {
        this.context = context;
        this.lists = lists;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MyRecordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyRecordViewHolder holder = null;
        holder = new MyRecordViewHolder(inflater.inflate(R.layout.activity_my_record_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyRecordViewHolder holder, int position) {
        holder.myRecordDate.setText(lists.get(position).getTime().substring(0, 4) + "-" + lists.get(position).getTime().substring(4, 6) + "-" + lists.get(position).getTime().substring(6, 8));
        holder.myRecordTime.setText("录音时长" + lists.get(position).getDuration() + "秒");
    }

    @Override
    public int getItemCount() {
        return lists != null ? lists.size() : 0;
    }
}
