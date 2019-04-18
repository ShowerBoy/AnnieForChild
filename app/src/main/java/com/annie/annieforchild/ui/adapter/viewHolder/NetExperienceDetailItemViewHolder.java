package com.annie.annieforchild.ui.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.annie.annieforchild.R;

/**
 * Created by wanglei on 2019/4/16.
 */

public class NetExperienceDetailItemViewHolder extends RecyclerView.ViewHolder {
    public ImageView image;
    public TextView title;

    public NetExperienceDetailItemViewHolder(View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.net_experience_item_image);
        title = itemView.findViewById(R.id.net_experience_item_title);
    }
}
