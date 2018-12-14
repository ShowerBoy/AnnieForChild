package com.annie.annieforchild.ui.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.annie.annieforchild.R;

/**
 * Created by wanglei on 2018/10/22.
 */

public class LessonViewHolder extends RecyclerView.ViewHolder {
    public TextView name;
    public RelativeLayout layout;

    public LessonViewHolder(View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.lesson_name);
        layout = itemView.findViewById(R.id.lesson_layout);
    }
}
