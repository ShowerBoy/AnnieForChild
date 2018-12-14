package com.annie.annieforchild.ui.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.annie.annieforchild.R;

/**
 * Created by wanglei on 2018/9/27.
 */

public class AddressViewHolder extends RecyclerView.ViewHolder {
    public ImageView edit;
    public RelativeLayout layout;
    public TextView name, phone, address;

    public AddressViewHolder(View itemView) {
        super(itemView);
        edit = itemView.findViewById(R.id.address_edit);
        name = itemView.findViewById(R.id.address_name);
        layout = itemView.findViewById(R.id.address_item);
        phone = itemView.findViewById(R.id.address_phone);
        address = itemView.findViewById(R.id.address_address);
    }
}
