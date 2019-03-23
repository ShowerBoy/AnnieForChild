package com.annie.annieforchild.ui.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.annie.annieforchild.R;

/**
 * Created by wanglei on 2019/3/22.
 */

public class SpecialPreheatViewHolder extends RecyclerView.ViewHolder {
    public TextView preTitle;
    public RecyclerView recycler;

    public SpecialPreheatViewHolder(View itemView) {
        super(itemView);
        preTitle = itemView.findViewById(R.id.special_preheat_pretitle);
        recycler = itemView.findViewById(R.id.special_preheat_recycler);
    }
}
