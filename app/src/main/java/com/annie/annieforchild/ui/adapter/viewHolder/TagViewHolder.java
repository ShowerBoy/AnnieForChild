package com.annie.annieforchild.ui.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.annie.annieforchild.R;
import com.google.android.flexbox.FlexboxLayout;

/**
 * Created by wanglei on 2018/9/14.
 */

public class TagViewHolder extends RecyclerView.ViewHolder {
    public TextView tag;
    public FlexboxLayout flexboxLayout;

    public TagViewHolder(View itemView) {
        super(itemView);
        tag = itemView.findViewById(R.id.tag_title);
        flexboxLayout = itemView.findViewById(R.id.tag_flexbox_layout);
    }
}
