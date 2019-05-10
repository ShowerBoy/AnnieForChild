package com.annie.annieforchild.ui.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.annie.annieforchild.R;

/**
 * Created by wanglei on 2019/5/8.
 */

public class EveryDayTaskTopViewHolder extends RecyclerView.ViewHolder {
    public TextView title;

    public EveryDayTaskTopViewHolder(View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.every_day_top_title);
    }
}
