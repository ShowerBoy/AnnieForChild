package com.annie.annieforchild.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.annie.annieforchild.R;
import com.annie.annieforchild.bean.rank.RankingBean;
import com.annie.annieforchild.ui.adapter.viewHolder.RankingViewHolder;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by WangLei on 2018/1/31 0031
 */

public class RankingRecyclerAdapter extends RecyclerView.Adapter<RankingViewHolder> {
    private Context context;
    private List<RankingBean> lists;
    private LayoutInflater inflater;

    public RankingRecyclerAdapter(Context context, List<RankingBean> lists) {
        this.context = context;
        this.lists = lists;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RankingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RankingViewHolder holder = null;
        holder = new RankingViewHolder(inflater.inflate(R.layout.activity_ranking_list_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RankingViewHolder holder, int position) {
        holder.ranking_id.setText(lists.get(position).getRanking() + "");
        Glide.with(context).load(lists.get(position).getHeadPic()).into(holder.ranking_image);
        holder.ranking_name.setText(lists.get(position).getName());
        holder.ranking_time.setText(lists.get(position).getRanking_time());
    }

    @Override
    public int getItemCount() {
        return lists != null ? lists.size() : 0;
    }
}
