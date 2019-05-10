package com.annie.annieforchild.ui.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.annie.annieforchild.R;

/**
 * Created by wanglei on 2019/5/6.
 */

public class NetSpecialDetailViewHolder2 extends RecyclerView.ViewHolder {
    public RecyclerView recycler;
    public TextView title;

    public NetSpecialDetailViewHolder2(View itemView) {
        super(itemView);
        recycler = itemView.findViewById(R.id.net_special_net_recycler);
        title = itemView.findViewById(R.id.net_special_new_title);
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
