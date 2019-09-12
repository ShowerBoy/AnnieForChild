package com.annie.annieforchild.ui.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.annie.annieforchild.R;

/**
 * Created by wanglei on 2019/4/12.
 */

public class MyCourseTopViewHolder extends RecyclerView.ViewHolder {
    public TextView title;

    public MyCourseTopViewHolder(View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.my_course_top_title);
    }
}
