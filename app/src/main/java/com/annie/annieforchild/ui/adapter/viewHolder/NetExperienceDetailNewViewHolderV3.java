package com.annie.annieforchild.ui.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.annie.annieforchild.R;

/**
 * 体验课V3
 */

public class NetExperienceDetailNewViewHolderV3 extends RecyclerView.ViewHolder {
    public RecyclerView recycler;
    public ImageView net_v3_background_layout;
    public RelativeLayout net_v3_childlayout;
    public RelativeLayout net_v3_layout;
    public ImageView net_v3_lock;

    public NetExperienceDetailNewViewHolderV3(View itemView) {
        super(itemView);
        recycler = itemView.findViewById(R.id.net_experience_net_recycler);
        net_v3_background_layout = itemView.findViewById(R.id.net_v3_background_layout);
        net_v3_childlayout = itemView.findViewById(R.id.net_v3_childlayout);
        net_v3_layout = itemView.findViewById(R.id.net_v3_layout);
        net_v3_lock = itemView.findViewById(R.id.net_v3_lock);
    }

    public void setVisibility(boolean isVisible){
        RecyclerView.LayoutParams param = (RecyclerView.LayoutParams)itemView.getLayoutParams();
        if (isVisible){
            param.height = LinearLayout.LayoutParams.WRAP_CONTENT;
            param.width = LinearLayout.LayoutParams.MATCH_PARENT;
            itemView.setVisibility(View.VISIBLE);
        }else{
            itemView.setVisibility(View.GONE);
            param.height = 0;
            param.width = 0;
        }
        itemView.setLayoutParams(param);
    }
}
