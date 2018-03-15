package com.annie.annieforchild.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.annie.annieforchild.R;
import com.annie.annieforchild.bean.HelpBean;
import com.annie.annieforchild.bean.Notice;
import com.annie.annieforchild.ui.adapter.viewHolder.HelpViewHolder;
import com.annie.annieforchild.ui.interfaces.OnRecyclerItemClickListener;

import java.util.List;

/**
 * 帮助文档适配器
 * Created by WangLei on 2018/3/8 0008
 */

public class HelpAdapter extends RecyclerView.Adapter<HelpViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<HelpBean> lists;
    private OnRecyclerItemClickListener listener;

    public HelpAdapter(Context context, List<HelpBean> lists, OnRecyclerItemClickListener listener) {
        this.context = context;
        this.lists = lists;
        this.listener = listener;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public HelpViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        HelpViewHolder holder = null;
        holder = new HelpViewHolder(inflater.inflate(R.layout.activity_help_item, parent, false));
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
    public void onBindViewHolder(HelpViewHolder holder, int position) {
        holder.helpTitle.setText(lists.get(position).getTitle());
        holder.helpContent.setText(lists.get(position).getDetail());
    }

    @Override
    public int getItemCount() {
        return lists != null ? lists.size() : 0;
    }
}
