package com.annie.annieforchild.ui.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.annie.annieforchild.R;

/**
 * Created by wanglei on 2019/3/14.
 */

public class NetSpecialConInfoViewHolder extends RecyclerView.ViewHolder {
    public TextView title;
    public ImageView image;

    public NetSpecialConInfoViewHolder(View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.nsc_text);
        image = itemView.findViewById(R.id.nsc_image);
    }
}
