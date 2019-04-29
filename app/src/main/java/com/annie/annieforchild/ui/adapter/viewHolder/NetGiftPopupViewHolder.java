package com.annie.annieforchild.ui.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.annie.annieforchild.R;

/**
 * Created by wanglei on 2019/4/28.
 */

public class NetGiftPopupViewHolder extends RecyclerView.ViewHolder {
    public ImageView check;
    public TextView name, content;

    public NetGiftPopupViewHolder(View itemView) {
        super(itemView);
        check = itemView.findViewById(R.id.net_gift_check);
        name = itemView.findViewById(R.id.net_gift_name);
        content = itemView.findViewById(R.id.net_gift_content);
    }
}
