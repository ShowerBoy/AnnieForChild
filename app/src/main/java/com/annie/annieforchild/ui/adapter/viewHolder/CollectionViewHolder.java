package com.annie.annieforchild.ui.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.annie.annieforchild.R;

/**
 * Created by WangLei on 2018/2/27 0027
 */

public class CollectionViewHolder extends RecyclerView.ViewHolder {
    public ImageView collection_image, uncollect;
    public TextView collection_name;
    public RelativeLayout total;

    public CollectionViewHolder(View itemView) {
        super(itemView);
        collection_image = itemView.findViewById(R.id.collection_image);
        collection_name = itemView.findViewById(R.id.collection_name);
        uncollect = itemView.findViewById(R.id.uncollect);
        total = itemView.findViewById(R.id.collection_total_layout);
    }
}
