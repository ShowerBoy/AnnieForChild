package com.annie.annieforchild.ui.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.annie.annieforchild.R;

/**
 * Created by wanglei on 2018/11/14.
 */

public class NetSuggestViewHolder extends RecyclerView.ViewHolder {
    public ImageView image;

    public NetSuggestViewHolder(View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.net_suggest_image);
    }
}
