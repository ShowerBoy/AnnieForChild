package com.annie.annieforchild.ui.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.annie.annieforchild.R;

/**
 */

public class CouponViewHolder extends RecyclerView.ViewHolder {
    public TextView couponPrice, couponName, couponSub,couponTime;
    public Button btUse;
    public RelativeLayout couponlayout;

    public CouponViewHolder(View itemView) {
        super(itemView);
        couponPrice = itemView.findViewById(R.id.coupon_price);
        couponName=itemView.findViewById(R.id.coupon_name);
        couponSub=itemView.findViewById(R.id.coupon_sub);
        couponTime=itemView.findViewById(R.id.coupon_time);
        btUse=itemView.findViewById(R.id.bt_use);
        couponlayout=itemView.findViewById(R.id.couponlayout);
    }
}
