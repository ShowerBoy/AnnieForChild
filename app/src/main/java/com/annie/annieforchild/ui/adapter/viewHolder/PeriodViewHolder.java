package com.annie.annieforchild.ui.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.annie.annieforchild.R;

/**
 * Created by wanglei on 2018/8/22.
 */

public class PeriodViewHolder extends RecyclerView.ViewHolder {
    public TextView date, state, consume, surplus, status;
    public ImageView help;

    public PeriodViewHolder(View itemView) {
        super(itemView);
        date = itemView.findViewById(R.id.period_date);
        state = itemView.findViewById(R.id.period_state);
        consume = itemView.findViewById(R.id.period_consume);
        surplus = itemView.findViewById(R.id.period_surplus);
        status = itemView.findViewById(R.id.period_status);
        help = itemView.findViewById(R.id.period_help);
    }
}
