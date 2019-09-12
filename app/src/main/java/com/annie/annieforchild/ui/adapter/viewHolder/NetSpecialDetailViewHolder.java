package com.annie.annieforchild.ui.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.annie.annieforchild.R;

/**
 * Created by wanglei on 2019/3/13.
 */

public class NetSpecialDetailViewHolder extends RecyclerView.ViewHolder {
    public TextView title, nsdText1, nsdText2, nsdText3;
    public ImageView nsdImage1, nsdImage2, nsdImage3;
    public LinearLayout nsdTestLayout, nsdLayout1, nsdLayout2, nsdLayout3;
    public RecyclerView recycler;

    public NetSpecialDetailViewHolder(View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.nsd_title);
        nsdText1 = itemView.findViewById(R.id.nsd_test_1);
        nsdText2 = itemView.findViewById(R.id.nsd_test_2);
        nsdText3 = itemView.findViewById(R.id.nsd_test_3);
        nsdImage1 = itemView.findViewById(R.id.nsd_image_1);
        nsdImage2 = itemView.findViewById(R.id.nsd_image_2);
        nsdImage3 = itemView.findViewById(R.id.nsd_image_3);
        nsdTestLayout = itemView.findViewById(R.id.nsd_test_layout);
        nsdLayout1 = itemView.findViewById(R.id.nsd_layout_1);
        nsdLayout2 = itemView.findViewById(R.id.nsd_layout_2);
        nsdLayout3 = itemView.findViewById(R.id.nsd_layout_3);
        recycler = itemView.findViewById(R.id.nsd_recycler);
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
