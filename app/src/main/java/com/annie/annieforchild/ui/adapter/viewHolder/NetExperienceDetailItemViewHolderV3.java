package com.annie.annieforchild.ui.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.annie.annieforchild.R;

/**
 * 体验课V3
 */

public class NetExperienceDetailItemViewHolderV3 extends RecyclerView.ViewHolder {
    public TextView title;

    public NetExperienceDetailItemViewHolderV3(View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.net_experience_item_title);
    }
}
