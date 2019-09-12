package com.annie.annieforchild.ui.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.annie.annieforchild.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by WangLei on 2018/2/26 0026
 */

public class MemberViewHolder extends RecyclerView.ViewHolder {
    public CircleImageView headpic;
    public TextView name;

    public MemberViewHolder(View itemView) {
        super(itemView);
        headpic = itemView.findViewById(R.id.member_headpic);
        name = itemView.findViewById(R.id.member_name);
    }
}
