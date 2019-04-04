package com.annie.annieforchild.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.CheckDoubleClickListener;
import com.annie.annieforchild.Utils.OnCheckDoubleClick;
import com.annie.annieforchild.bean.order.MyOrder;
import com.annie.annieforchild.presenter.NetWorkPresenter;
import com.annie.annieforchild.ui.adapter.viewHolder.MyOrderViewHolder;
import com.annie.annieforchild.ui.interfaces.OnRecyclerItemClickListener;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by wanglei on 2019/4/3.
 */

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderViewHolder> {
    private Context context;
    private List<MyOrder> lists;
    private LayoutInflater inflater;
    private NetWorkPresenter presenter;
    private OnRecyclerItemClickListener listener;
    private int paytype, orderIncrId, tag;
    private String buyPrice;

    public MyOrderAdapter(Context context, List<MyOrder> lists, int tag, OnRecyclerItemClickListener listener, NetWorkPresenter presenter) {
        this.context = context;
        this.lists = lists;
        this.tag = tag;
        this.listener = listener;
        this.presenter = presenter;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MyOrderViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        MyOrderViewHolder holder = null;
        holder = new MyOrderViewHolder(inflater.inflate(R.layout.activity_my_order_item, viewGroup, false));
        holder.itemView.setOnClickListener(new CheckDoubleClickListener(new OnCheckDoubleClick() {
            @Override
            public void onCheckDoubleClick(View view) {
                listener.onItemClick(view);
            }
        }));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyOrderViewHolder myOrderViewHolder, int i) {
        Glide.with(context).load(lists.get(i).getPic()).error(R.drawable.image_error).into(myOrderViewHolder.image);
        myOrderViewHolder.date.setText("订单信息：" + lists.get(i).getAddtime());
        myOrderViewHolder.title.setText(lists.get(i).getProductCourseName());
        myOrderViewHolder.suggest.setText(lists.get(i).getSynopsis());
        if (lists.get(i).getMaterial() == 0) {
            myOrderViewHolder.price.setText("共计：￥" + lists.get(i).getPrice());
        } else {
            myOrderViewHolder.price.setText("共计：￥" + lists.get(i).getPrice() + "（含教材￥" + lists.get(i).getTeachingMaterialPrice() + "）");
        }
        if (lists.get(i).getShowStatus() == 1) {
            myOrderViewHolder.state.setText("待支付");
            myOrderViewHolder.state.setTextColor(context.getResources().getColor(R.color.text_orange));
            myOrderViewHolder.operateLayout.setVisibility(View.VISIBLE);
        } else if (lists.get(i).getShowStatus() == 2) {
            myOrderViewHolder.state.setText("已支付");
            myOrderViewHolder.state.setTextColor(context.getResources().getColor(R.color.text_color));
            myOrderViewHolder.operateLayout.setVisibility(View.GONE);
        } else if (lists.get(i).getShowStatus() == 3) {
            myOrderViewHolder.state.setText("已关闭");
            myOrderViewHolder.state.setTextColor(context.getResources().getColor(R.color.text_color));
            myOrderViewHolder.operateLayout.setVisibility(View.GONE);
        } else if (lists.get(i).getShowStatus() == 4) {
            myOrderViewHolder.state.setText("已退款");
            myOrderViewHolder.state.setTextColor(context.getResources().getColor(R.color.text_color));
            myOrderViewHolder.operateLayout.setVisibility(View.GONE);
        } else {
            myOrderViewHolder.state.setText("已发货");
            myOrderViewHolder.state.setTextColor(context.getResources().getColor(R.color.text_color));
            myOrderViewHolder.operateLayout.setVisibility(View.GONE);
        }
        myOrderViewHolder.confirm.setOnClickListener(new CheckDoubleClickListener(new OnCheckDoubleClick() {
            @Override
            public void onCheckDoubleClick(View view) {
                paytype = lists.get(i).getPaytype();
                orderIncrId = lists.get(i).getOrderIncrId();
                buyPrice = lists.get(i).getPrice();
                presenter.continuePay(lists.get(i).getOrderIncrId(), lists.get(i).getPaytype(), tag);
            }
        }));
        myOrderViewHolder.cancel.setOnClickListener(new CheckDoubleClickListener(new OnCheckDoubleClick() {
            @Override
            public void onCheckDoubleClick(View view) {
                paytype = lists.get(i).getPaytype();
                orderIncrId = lists.get(i).getOrderIncrId();
                buyPrice = lists.get(i).getPrice();
                presenter.cancelOrder(lists.get(i).getOrderIncrId(), lists.get(i).getPaytype(), tag);
            }
        }));
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public int getPaytype() {
        return paytype;
    }

    public void setPaytype(int paytype) {
        this.paytype = paytype;
    }

    public int getOrderIncrId() {
        return orderIncrId;
    }

    public void setOrderIncrId(int orderIncrId) {
        this.orderIncrId = orderIncrId;
    }

    public String getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(String buyPrice) {
        this.buyPrice = buyPrice;
    }
}
