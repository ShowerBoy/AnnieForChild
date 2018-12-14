package com.annie.annieforchild.ui.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.annie.annieforchild.R;

/**
 * Created by wanglei on 2018/7/19.
 */

public class MeiriyiViewHolder extends RecyclerView.ViewHolder {
    public ImageView image;
    public TextView text;

    public MeiriyiViewHolder(View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.meiriyi_image);
        text = itemView.findViewById(R.id.meiriyi_text);
    }
}
