package com.annie.annieforchild.ui.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.annie.annieforchild.R;

/**
 * Created by WangLei on 2018/1/16 0016
 */

public class InformationViewHolder extends RecyclerView.ViewHolder {
    public ImageView info_image;
    public TextView info_text;
    public TextView info_read_time;
    public ImageView info_collect;

    public InformationViewHolder(View itemView) {
        super(itemView);
        info_image = itemView.findViewById(R.id.info_image);
        info_text = itemView.findViewById(R.id.info_text);
        info_read_time = itemView.findViewById(R.id.info_read_time);
        info_collect = itemView.findViewById(R.id.info_collect);
    }
}
