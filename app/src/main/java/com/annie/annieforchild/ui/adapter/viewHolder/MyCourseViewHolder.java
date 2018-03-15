package com.annie.annieforchild.ui.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.annie.annieforchild.R;

/**
 * Created by Administrator on 2018/1/16 0016.
 */

public class MyCourseViewHolder extends RecyclerView.ViewHolder {

    public ImageView image_myCourse;
    public TextView name_myCourse;

    public MyCourseViewHolder(View itemView) {
        super(itemView);
        image_myCourse = itemView.findViewById(R.id.image_myCourse);
        name_myCourse = itemView.findViewById(R.id.name_myCourse);
    }
}
