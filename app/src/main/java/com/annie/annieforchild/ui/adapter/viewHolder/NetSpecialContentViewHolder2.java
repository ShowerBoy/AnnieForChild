package com.annie.annieforchild.ui.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.annie.annieforchild.R;

/**
 * Created by wanglei on 2019/3/14.
 */

public class NetSpecialContentViewHolder2 extends RecyclerView.ViewHolder {
    public TextView title;
    public RecyclerView recycler;

    public NetSpecialContentViewHolder2(View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.nsc_title);
        recycler = itemView.findViewById(R.id.nsc_recycler);
    }
}
