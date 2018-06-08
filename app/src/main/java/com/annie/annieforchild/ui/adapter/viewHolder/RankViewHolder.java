package com.annie.annieforchild.ui.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.annie.annieforchild.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by wanglei on 2018/5/29.
 */

public class RankViewHolder extends RecyclerView.ViewHolder {
    public CircleImageView headpic;
    public ImageView medal, hexagon;
    public TextView rank_number, name, duration, like;

    public RankViewHolder(View itemView) {
        super(itemView);
        headpic = itemView.findViewById(R.id.rank_item_headpic);
        medal = itemView.findViewById(R.id.medal);
        hexagon = itemView.findViewById(R.id.hexagon);
        rank_number = itemView.findViewById(R.id.rank_number);
        name = itemView.findViewById(R.id.rank_item_name);
        duration = itemView.findViewById(R.id.rank_item_duration);
        like = itemView.findViewById(R.id.rank_item_like);
    }
}
