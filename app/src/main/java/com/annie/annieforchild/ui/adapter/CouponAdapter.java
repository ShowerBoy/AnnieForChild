package com.annie.annieforchild.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.CheckDoubleClickListener;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.OnCheckDoubleClick;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.net.Address;
import com.annie.annieforchild.bean.net.DiscountRecord;
import com.annie.annieforchild.presenter.NetWorkPresenter;
import com.annie.annieforchild.ui.activity.net.AddAddressActivity;
import com.annie.annieforchild.ui.activity.net.MyAddressActivity;
import com.annie.annieforchild.ui.activity.net.NetWorkActivity;
import com.annie.annieforchild.ui.adapter.viewHolder.AddressViewHolder;
import com.annie.annieforchild.ui.adapter.viewHolder.CouponViewHolder;
import com.annie.annieforchild.ui.interfaces.OnRecyclerItemClickListener;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.util.List;

/**
 */

public class CouponAdapter extends RecyclerView.Adapter<CouponViewHolder> {
    private Context context;
    private List<DiscountRecord> lists;
    private LayoutInflater inflater;
    private String tag;
    private Activity activity;

    public CouponAdapter(Activity activity, Context context, List<DiscountRecord> lists, String tag) {
        this.context = context;
        this.activity=activity;
        this.lists = lists;
        this.tag = tag;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public CouponViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        CouponViewHolder holder = null;
        holder = new CouponViewHolder(inflater.inflate(R.layout.activity_coupon_item, viewGroup, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(CouponViewHolder holder, int i) {
       holder.couponPrice.setText(lists.get(i).getMoney());
       holder.couponName.setText(lists.get(i).getTitle());
       holder.couponSub.setText(lists.get(i).getSuit());
       switch (lists.get(i).getIsnot()){// # 使用状态 0 未使用  1 已使用  2 已过期
           case "0":
               holder.btUse.setVisibility(View.VISIBLE);
               holder.couponlayout.setBackgroundResource(R.drawable.coupon_unuse);
               holder.couponTime.setText(lists.get(i).getDuetime()+"到期");
               break;
           case "1":
               holder.btUse.setVisibility(View.GONE);
               holder.couponlayout.setBackgroundResource(R.drawable.card_coupons_overuse);
               holder.couponTime.setText(lists.get(i).getDuetime()+"到期");
               break;
           case "2":
               holder.btUse.setVisibility(View.GONE);
               holder.couponlayout.setBackgroundResource(R.drawable.card_coupons_overdue2);
               holder.couponTime.setText(lists.get(i).getDuetime()+"到期");
               break;
       }
       holder.btUse.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
              if(tag.equals("confirm")){//从订单页面进入选择
                  Intent intent = new Intent();
                  Bundle bundle = new Bundle();
                  bundle.putString("couponid", lists.get(i).getId());
                  bundle.putString("couponprice", lists.get(i).getMoney());
                  intent.putExtras(bundle);
                  activity.setResult(3, intent);
                  activity.finish();
              }else{
                  Intent intent = new Intent();
                  intent.setClass(activity, NetWorkActivity.class);
                  intent.putExtra("to_exp", "2");
                  activity.startActivity(intent);
                  activity.finish();
              }
           }
       });
    }

    @Override
    public int getItemCount() {
        return lists != null ? lists.size() : 0;
    }
}
