package com.annie.annieforchild.ui.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.annie.annieforchild.R;

/**
 * Created by wanglei on 2018/4/24.
 */

public class MaterialViewHolder extends RecyclerView.ViewHolder {
    public ImageView image;
    public TextView text;

    public MaterialViewHolder(View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.material_image);
        text = itemView.findViewById(R.id.material_text);
    }
}
