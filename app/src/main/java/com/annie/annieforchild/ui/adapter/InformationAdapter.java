package com.annie.annieforchild.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.Infors;
import com.annie.annieforchild.ui.activity.Details;
import com.annie.annieforchild.ui.adapter.viewHolder.InformationViewHolder;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by WangLei on 2018/1/16 0016
 */

public class InformationAdapter extends RecyclerView.Adapter<InformationViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<Infors> lists;

    public InformationAdapter(Context context, List<Infors> lists) {
        this.context = context;
        this.lists = lists;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public InformationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        InformationViewHolder holder = new InformationViewHolder(inflater.inflate(R.layout.activity_item_information, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(InformationViewHolder holder, int position) {
        Glide.with(context).load(R.drawable.test_image).fitCenter().placeholder(R.drawable.test_image).into(holder.info_image);
        if (lists.get(position).getCollect()) {
            holder.info_collect.setImageResource(R.drawable.collect_star_t);
        } else {
            holder.info_collect.setImageResource(R.drawable.collect_star_f);
        }
        holder.info_read_time.setText(lists.get(position).getText());
        holder.info_read_time.setText(lists.get(position).getRead_time() + "次阅读");
        holder.info_collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lists.get(position).getCollect()) {
                    SystemUtils.show(context, "取消收藏");
                } else {
                    SystemUtils.show(context, "收藏成功");
                }
                lists.get(position).setCollect(!lists.get(position).getCollect());
                notifyDataSetChanged();
            }
        });
        holder.info_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                intent.setClass(context, Details.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lists != null ? lists.size() : 0;
    }
}
