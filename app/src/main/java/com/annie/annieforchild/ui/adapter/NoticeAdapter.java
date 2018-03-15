package com.annie.annieforchild.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.serializer.ListSerializer;
import com.annie.annieforchild.R;
import com.annie.annieforchild.bean.Notice;
import com.annie.annieforchild.ui.adapter.viewHolder.NoticeViewHolder;
import com.annie.annieforchild.ui.interfaces.OnRecyclerItemClickListener;

import java.util.List;

/**
 * 通知适配器
 * Created by WangLei on 2018/2/26 0026
 */

public class NoticeAdapter extends RecyclerView.Adapter<NoticeViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<Notice> lists;
    private OnRecyclerItemClickListener listener;

    public NoticeAdapter(Context context, List<Notice> lists, OnRecyclerItemClickListener listener) {
        this.context = context;
        this.lists = lists;
        this.listener = listener;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public NoticeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        NoticeViewHolder holder = null;
        holder = new NoticeViewHolder(inflater.inflate(R.layout.activity_notice_item, parent, false));
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
    public void onBindViewHolder(NoticeViewHolder holder, int position) {
        holder.noticeType.setText(lists.get(position).getTag());
        holder.noticeTitle.setText(lists.get(position).getTitle());
        holder.noticeTime.setText(lists.get(position).getTime().substring(0, 4) + "-" + lists.get(position).getTime().substring(4, 6) + "-" + lists.get(position).getTime().substring(6, 8));
        holder.noticeContent.setText(lists.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return lists != null ? lists.size() : 0;
    }
}
