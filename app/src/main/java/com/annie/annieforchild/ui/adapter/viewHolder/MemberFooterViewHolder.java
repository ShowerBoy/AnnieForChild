package com.annie.annieforchild.ui.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.annie.annieforchild.R;

/**
 * Created by WangLei on 2018/2/26 0026
 */

public class MemberFooterViewHolder extends RecyclerView.ViewHolder {
    public ImageView addMember;

    public MemberFooterViewHolder(View itemView) {
        super(itemView);
        addMember = itemView.findViewById(R.id.add_member);
    }
}
