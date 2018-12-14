package com.annie.annieforchild.ui.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.annie.annieforchild.R;

/**
 * Created by wanglei on 2018/9/22.
 */

public class NetWorkViewHolder extends RecyclerView.ViewHolder {
    public RecyclerView recyclerView;
    public TextView title;

    public NetWorkViewHolder(View itemView) {
        super(itemView);
        recyclerView = itemView.findViewById(R.id.network_recycler);
        title = itemView.findViewById(R.id.network_title);
    }
}
