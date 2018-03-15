package com.annie.annieforchild.ui.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.annie.annieforchild.R;

/**
 * Created by WangLei on 2018/1/16 0016
 */

public class RecommendViewHolder extends RecyclerView.ViewHolder {
    public ImageView image_recommend;
    public TextView name_recommend;

    public RecommendViewHolder(View itemView) {
        super(itemView);
        image_recommend = itemView.findViewById(R.id.image_recommend);
        name_recommend = itemView.findViewById(R.id.name_recommend);
    }
}
