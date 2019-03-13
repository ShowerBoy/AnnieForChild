package com.annie.annieforchild.ui.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.annie.annieforchild.R;

/**
 * Created by wanglei on 2019/3/13.
 */

public class NetSpecialViewHolder extends RecyclerView.ViewHolder {
    public ImageView image, hadbuy;
    public TextView title, summary, event, price, bottomLine;

    public NetSpecialViewHolder(View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.net_image);
        hadbuy = itemView.findViewById(R.id.net_hadbuy);
        title = itemView.findViewById(R.id.net_title);
        summary = itemView.findViewById(R.id.net_summary);
        event = itemView.findViewById(R.id.net_event);
        price = itemView.findViewById(R.id.net_price);
        bottomLine=itemView.findViewById(R.id.bottom_line);
    }
}
