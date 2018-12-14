package com.annie.annieforchild.ui.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.annie.annieforchild.R;

/**
 * Created by wanglei on 2018/10/24.
 */

public class GiftViewHolder extends RecyclerView.ViewHolder {
    public TextView title, content;
    public CheckBox checkBox;

    public GiftViewHolder(View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.gift_item_title);
        content = itemView.findViewById(R.id.gift_item_content);
        checkBox = itemView.findViewById(R.id.gift_check);
    }
}
