package com.annie.annieforchild.ui.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.annie.annieforchild.R;

/**
 * Created by wanglei on 2018/9/25.
 */

public class NetGiftViewHolder extends RecyclerView.ViewHolder {
    public TextView name;
    public ImageView help;

    public NetGiftViewHolder(View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.gift_name);
        help = itemView.findViewById(R.id.gift_help);
    }
}
