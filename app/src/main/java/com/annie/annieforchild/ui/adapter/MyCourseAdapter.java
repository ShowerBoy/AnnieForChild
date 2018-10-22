package com.annie.annieforchild.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.bean.net.NetClass;
import com.annie.annieforchild.presenter.imp.MainPresenterImp;
import com.annie.annieforchild.ui.adapter.viewHolder.MyCourseViewHolder;
import com.annie.annieforchild.ui.interfaces.OnRecyclerItemClickListener;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by WangLei on 2018/1/16 0016
 */

public class MyCourseAdapter extends RecyclerView.Adapter<MyCourseViewHolder> {
    private LayoutInflater inflater;
    private Context context;
    private List<NetClass> lists;
    private OnRecyclerItemClickListener listener;

    public MyCourseAdapter(Context context, List<NetClass> lists, OnRecyclerItemClickListener listener) {
        this.context = context;
        this.lists = lists;
        this.listener = listener;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MyCourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyCourseViewHolder viewHolder = null;
        View view = inflater.inflate(R.layout.activity_item_mycourse, parent, false);
        viewHolder = new MyCourseViewHolder(view);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyCourseViewHolder holder, int position) {
        Glide.with(context).load(lists.get(position).getNetImageUrl()).into(holder.image);
        holder.title.setText(lists.get(position).getNetName());
        holder.suggest.setText(lists.get(position).getNetSummary());
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

}
