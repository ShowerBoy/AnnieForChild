package com.annie.annieforchild.ui.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.annie.annieforchild.R;

/**
 * Created by wanglei on 2019/1/4.
 */

public class ProductionViewHolder extends RecyclerView.ViewHolder {
    public ImageView image, play, like;
    public TextView name, date, playTimes, likeTimes;
    public LinearLayout playLayout, likeLayout;

    public ProductionViewHolder(View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.production_image);
        play = itemView.findViewById(R.id.production_play);
        like = itemView.findViewById(R.id.production_like);
        name = itemView.findViewById(R.id.production_name);
        date = itemView.findViewById(R.id.production_date);
        playTimes = itemView.findViewById(R.id.production_play_times);
        likeTimes = itemView.findViewById(R.id.production_like_times);
        playLayout = itemView.findViewById(R.id.production_play_layout);
        likeLayout = itemView.findViewById(R.id.production_like_layout);
    }
}
