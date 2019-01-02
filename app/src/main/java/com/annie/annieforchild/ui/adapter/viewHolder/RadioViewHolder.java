package com.annie.annieforchild.ui.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.annie.annieforchild.R;

/**
 * Created by wanglei on 2018/12/14.
 */

public class RadioViewHolder extends RecyclerView.ViewHolder {
    public RecyclerView recycler;
    public TextView title;

    public RadioViewHolder(View itemView) {
        super(itemView);
        recycler = itemView.findViewById(R.id.radio_list_recycler);
        title = itemView.findViewById(R.id.radio_title);
    }
}
