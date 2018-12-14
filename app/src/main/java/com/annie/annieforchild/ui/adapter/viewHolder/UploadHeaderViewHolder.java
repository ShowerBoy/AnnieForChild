package com.annie.annieforchild.ui.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.annie.annieforchild.R;

/**
 * Created by wanglei on 2018/8/27.
 */

public class UploadHeaderViewHolder extends RecyclerView.ViewHolder {
    public ImageView addImage;

    public UploadHeaderViewHolder(View itemView) {
        super(itemView);
        addImage = itemView.findViewById(R.id.task_add_image);
    }
}
