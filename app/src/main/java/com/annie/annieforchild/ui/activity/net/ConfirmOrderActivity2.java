package com.annie.annieforchild.ui.activity.net;

import android.app.Dialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.CheckDoubleClickListener;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.OnCheckDoubleClick;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.order.OrderDetail;
import com.annie.annieforchild.presenter.NetWorkPresenter;
import com.annie.annieforchild.presenter.imp.NetWorkPresenterImp;
import com.annie.annieforchild.view.info.ViewInfo;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;
import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.Subscribe;

/**
 * 我的订单确认
 * Created by wanglei on 2019/4/3.
 */

public class ConfirmOrderActivity2 extends BaseActivity implements ViewInfo, OnCheckDoubleClick {
    private TextView state, name, phone, address, productName, productSuggest, productPrice, materialPrice, totalPrice, wechat, payment, orderId, orderDate, orderTime, deliveryDate, payBtn;
    private ImageView back, productImage;
    private CheckDoubleClickListener listener;
    private NetWorkPresenter presenter;
    private AlertHelper helper;
    private Dialog dialog;
    private OrderDetail orderDetail;
    private int orderIncId;

    {
        setRegister(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_confirm_order2;
    }

    @Override
    protected void initView() {
        state = findViewById(R.id.confirm_order2_state);
        name = findViewById(R.id.order_name);
        phone = findViewById(R.id.order_phone);
        address = findViewById(R.id.order_address);
        productName = findViewById(R.id.order_product_name);
        productSuggest = findViewById(R.id.order_product_text);
        productPrice = findViewById(R.id.order_price);
        materialPrice = findViewById(R.id.order_material_price);
        totalPrice = findViewById(R.id.order_total_price);
        wechat = findViewById(R.id.order_wechat);
        payment = findViewById(R.id.order_payment);
        orderId = findViewById(R.id.order_id);
        orderDate = findViewById(R.id.order_date);
        orderTime = findViewById(R.id.order_time);
        deliveryDate = findViewById(R.id.order_delivery_date);
        productImage = findViewById(R.id.order_product_img);
        payBtn = findViewById(R.id.confirm_order2_pay_btn);
        back = findViewById(R.id.confirm_order_back2);
        listener = new CheckDoubleClickListener(this);
        payBtn.setOnClickListener(listener);
        back.setOnClickListener(listener);

        orderIncId = getIntent().getIntExtra("orderIncId", 0);
    }

    @Override
    protected void initData() {
        helper = new AlertHelper(this);
        dialog = helper.LoadingDialog();
        presenter = new NetWorkPresenterImp(this, this);
        presenter.initViewAndData();
        presenter.getMyOrderDetail(orderIncId);
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_GETMYORDERDETAIL) {
            orderDetail = (OrderDetail) message.obj;
            refresh();
        }
    }

    private void refresh() {
        if (orderDetail != null) {
            if (orderDetail.getShowStatus() == 1) {
                state.setText("待支付");
                payBtn.setVisibility(View.VISIBLE);
            } else if (orderDetail.getShowStatus() == 2) {
                state.setText("已支付");
                payBtn.setVisibility(View.GONE);
            } else if (orderDetail.getShowStatus() == 3) {
                state.setText("已关闭");
                payBtn.setVisibility(View.GONE);
            } else if (orderDetail.getShowStatus() == 4) {
                state.setText("已退款");
                payBtn.setVisibility(View.GONE);
            } else {
                state.setText("已发货");
                payBtn.setVisibility(View.GONE);
            }
            name.setText("收件人：" + orderDetail.getName());
            phone.setText(orderDetail.getTelphone());
            address.setText("收件地址：" + orderDetail.getAddress());
            Glide.with(this).load(orderDetail.getPic()).error(R.drawable.image_error).into(productImage);
            productName.setText(orderDetail.getProductCourseName());
            productSuggest.setText(orderDetail.getSynopsis());
            productPrice.setText("￥" + orderDetail.getPreferentialPrice());
            materialPrice.setText("￥" + orderDetail.getTeachingMaterialPrice());
            totalPrice.setText("￥" + orderDetail.getPrice());
            wechat.setText(orderDetail.getWxnumber());
            if (orderDetail.getPaytype() == 0) {
                payment.setText("支付宝");
            } else {
                payment.setText("微信支付");
            }
            orderId.setText("订单编号：" + orderDetail.getOrderid());
            orderDate.setText("下单时间：" + (orderDetail.getAddtime() != null ? orderDetail.getAddtime() : ""));
            orderTime.setText("付款时间：" + (orderDetail.getPaytime() != null ? orderDetail.getPaytime() : ""));
            deliveryDate.setText("发货时间：" + (orderDetail.getSendtime() != null ? orderDetail.getSendtime() : ""));
        }
    }

    @Override
    public void onCheckDoubleClick(View view) {
        switch (view.getId()) {
            case R.id.confirm_order_back2:
                finish();
                break;
            case R.id.confirm_order2_pay_btn:

                break;
        }
    }

    @Override
    public void showInfo(String info) {
        Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoad() {
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }

    @Override
    public void dismissLoad() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
