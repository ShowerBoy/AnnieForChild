package com.annie.annieforchild.ui.adapter.viewHolder;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.annie.annieforchild.R;

/**
 * Created by WangLei on 2018/1/31 0031
 */

public class RankingViewHolder extends RecyclerView.ViewHolder {
    public ImageView ranking_image;
    public TextView ranking_id, ranking_name, ranking_time;

    public RankingViewHolder(View itemView) {
        super(itemView);
        ranking_image = itemView.findViewById(R.id.ranking_headPic);
        ranking_id = itemView.findViewById(R.id.ranking_id);
        ranking_name = itemView.findViewById(R.id.ranking_name);
        ranking_time = itemView.findViewById(R.id.ranking_time);
    }
}
