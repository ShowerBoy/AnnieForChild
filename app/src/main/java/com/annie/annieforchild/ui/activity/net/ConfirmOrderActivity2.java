package com.annie.annieforchild.ui.activity.net;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alipay.sdk.app.PayTask;
import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.CheckDoubleClickListener;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.OnCheckDoubleClick;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.PayResult;
import com.annie.annieforchild.bean.net.Payresulrinfo;
import com.annie.annieforchild.bean.net.WechatBean;
import com.annie.annieforchild.bean.order.AliOrderBean;
import com.annie.annieforchild.bean.order.OrderDetail;
import com.annie.annieforchild.bean.order.WechatOrderBean;
import com.annie.annieforchild.presenter.NetWorkPresenter;
import com.annie.annieforchild.presenter.imp.NetWorkPresenterImp;
import com.annie.annieforchild.view.info.ViewInfo;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;
import com.bumptech.glide.Glide;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Map;

/**
 * 我的订单确认
 * Created by wanglei on 2019/4/3.
 */

public class ConfirmOrderActivity2 extends BaseActivity implements ViewInfo, OnCheckDoubleClick {
    private TextView state, name, phone, address, cancel, productName, productSuggest, productPrice, materialPrice, totalPrice, wechat, payment, orderId, orderDate, orderTime, deliveryDate, payBtn;
    private ImageView back, productImage, empty;
    private RelativeLayout orderLayout, materialLayout;
    private CheckDoubleClickListener listener;
    private NetWorkPresenter presenter;
    private AlertHelper helper;
    private Dialog dialog;
    private OrderDetail orderDetail;
    private static final int SDK_PAY_FLAG = 1;
    private int orderIncrId;
    private int paytype;
    private String data;
    private IWXAPI wxapi;
    private int wx_status = -1;
    public static String buyPrice;
    private String wxout_trade_no = "";
    private int tag = 1;
    private String trade_status;
    private LinearLayout coupon_layout;
    private TextView coupon_info;


    {
        setRegister(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_confirm_order2;
    }

    @Override
    protected void initView() {
        coupon_layout=findViewById(R.id.coupon_layout);
        coupon_info=findViewById(R.id.coupon_info);
        wxapi = WXAPIFactory.createWXAPI(this, SystemUtils.APP_ID, true);
        wxapi.registerApp(SystemUtils.APP_ID);
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
        cancel = findViewById(R.id.order_cancel);
        empty = findViewById(R.id.confirm_order_empty);
        orderLayout = findViewById(R.id.confirm_order_layout);
        materialLayout = findViewById(R.id.order_material_layout);
        listener = new CheckDoubleClickListener(this);
        payBtn.setOnClickListener(listener);
        back.setOnClickListener(listener);
        cancel.setOnClickListener(listener);

        orderIncrId = getIntent().getIntExtra("orderIncrId", 0);
    }

    @Override
    protected void initData() {
        helper = new AlertHelper(this);
        dialog = helper.LoadingDialog();
        presenter = new NetWorkPresenterImp(this, this);
        presenter.initViewAndData();
        presenter.getMyOrderDetail(orderIncrId);
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
        } else if (message.what == MethodCode.EVENT_CONTINUEPAY + 90000 + tag) {
            if (paytype == 0) {
                data = (String) message.obj;
                if (data != null) {
                    payV2();
                }
            } else {
                WechatBean wechatBean = (WechatBean) message.obj;
                wxout_trade_no = wechatBean.getOut_trade_no();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        PayReq payReq = new PayReq();
                        payReq.appId = wechatBean.getAppId();
                        payReq.prepayId = wechatBean.getPrepayId();
                        payReq.packageValue = "Sign=WXPay";
                        payReq.nonceStr = wechatBean.getNonceStr();
                        payReq.partnerId = wechatBean.getPartnerId();
                        payReq.timeStamp = wechatBean.getTimeStamp();
                        payReq.sign = wechatBean.getSign();
                        wxapi.sendReq(payReq);
                    }
                }).start();
//                finish();
            }
        } else if (message.what == MethodCode.EVENT_PAY) {
            wx_status = (int) message.obj;
        } else if (message.what == MethodCode.EVENT_ORDERQUERY + 11000 + tag) {
            if (paytype == 0) {
//                String trade_status = (String) message.obj;
                AliOrderBean aliOrderBean = (AliOrderBean) message.obj;
//                String trade_status = (String) message.obj;
                trade_status = aliOrderBean.getAlipay_trade_query_response().getTrade_status();
                if (trade_status.equals("TRADE_SUCCESS")) {
                    /**
                     * {@link com.annie.annieforchild.ui.activity.my.MyOrderActivity#onMainEventThread(JTMessage)}
                     */
                    JTMessage message2 = new JTMessage();
                    message2.what = MethodCode.EVENT_CONFIRMBUYSUC;
                    message2.obj = 0;
                    EventBus.getDefault().post(message2);
                    Intent intent = new Intent(ConfirmOrderActivity2.this, PaySuccessActivity.class);
                    intent.putExtra("price", "ConfirmOrderActivity2");
                    intent.putExtra("info", aliOrderBean.getSecretaryInfo());
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(ConfirmOrderActivity2.this, PayFailActivity.class);
                    startActivity(intent);
                    finish();
                }
            } else {
                WechatOrderBean wechatOrderBean = (WechatOrderBean) message.obj;
//                String trade_status = (String) message.obj;
                trade_status = wechatOrderBean.getTrade_state();
                wxout_trade_no = "";
                if (trade_status.equals("SUCCESS")) {
                    /**
                     * {@link com.annie.annieforchild.ui.activity.my.MyOrderActivity#onMainEventThread(JTMessage)}
                     */
                    JTMessage message2 = new JTMessage();
                    message2.what = MethodCode.EVENT_CONFIRMBUYSUC;
                    message2.obj = 1;
                    EventBus.getDefault().post(message2);
                    Intent intent = new Intent(ConfirmOrderActivity2.this, PaySuccessActivity.class);
                    intent.putExtra("price", "ConfirmOrderActivity2");
                    intent.putExtra("info", wechatOrderBean.getSecretaryInfo());
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(ConfirmOrderActivity2.this, PayFailActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        } else if (message.what == MethodCode.EVENT_CANCELORDER) {
            String info = (String) message.obj;
            showInfo(info);
            finish();
        }
    }

    private void refresh() {
        if (orderDetail != null) {
            orderLayout.setVisibility(View.VISIBLE);
            empty.setVisibility(View.GONE);
            if (orderDetail.getShowStatus() == 1) {
                state.setText("待支付");
                payBtn.setVisibility(View.VISIBLE);
                cancel.setVisibility(View.VISIBLE);
            } else if (orderDetail.getShowStatus() == 2) {
                state.setText("已支付");
                payBtn.setVisibility(View.GONE);
                cancel.setVisibility(View.GONE);
            } else if (orderDetail.getShowStatus() == 3) {
                state.setText("已关闭");
                payBtn.setVisibility(View.GONE);
                cancel.setVisibility(View.GONE);
            } else if (orderDetail.getShowStatus() == 4) {
                state.setText("已退款");
                payBtn.setVisibility(View.GONE);
                cancel.setVisibility(View.GONE);
            } else {
                state.setText("已发货");
                payBtn.setVisibility(View.GONE);
                cancel.setVisibility(View.GONE);
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
            buyPrice = orderDetail.getPrice();
            if (orderDetail.getMaterial() == 0) {
                materialLayout.setVisibility(View.GONE);
            } else {
                materialLayout.setVisibility(View.VISIBLE);
            }

            wechat.setText(orderDetail.getWxnumber());
            if (orderDetail.getPaytype() == 0) {
                payment.setText("支付宝");
            } else {
                payment.setText("微信支付");
            }
            if(orderDetail.getCoupon()!=null && orderDetail.getCoupon().equals("0")){
                coupon_layout.setVisibility(View.GONE);
            }else{
                coupon_layout.setVisibility(View.VISIBLE);
                coupon_info.setText("-"+"￥"+orderDetail.getCoupon());
            }
            orderId.setText("订单编号：" + orderDetail.getOrderid());
            if (orderDetail.getAddtime() != null && orderDetail.getAddtime().length() != 0) {
                orderDate.setText("下单时间：" + orderDetail.getAddtime());
            } else {
                orderDate.setText("");
            }
            if (orderDetail.getPaytime() != null && orderDetail.getPaytime().length() != 0) {
                orderTime.setText("付款时间：" + orderDetail.getPaytime());
            } else {
                orderTime.setText("");
            }
            if (orderDetail.getSendtime() != null && orderDetail.getSendtime().length() != 0) {
                deliveryDate.setText("发货时间：" + orderDetail.getSendtime());
            } else {
                deliveryDate.setText("");
            }
        } else {
            orderLayout.setVisibility(View.GONE);
            empty.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onCheckDoubleClick(View view) {
        switch (view.getId()) {
            case R.id.confirm_order_back2:
                finish();
                break;
            case R.id.confirm_order2_pay_btn:
                paytype = orderDetail.getPaytype();
                presenter.continuePay(orderDetail.getOrderIncrId(), orderDetail.getPaytype(), tag);
                break;
            case R.id.order_cancel:
                paytype = orderDetail.getPaytype();
                presenter.cancelOrder(orderDetail.getOrderIncrId(), orderDetail.getPaytype(), tag);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (paytype == 1 && wxout_trade_no.length() > 0) {
            if (wx_status == 0) {
                presenter.OrderQuery("", wxout_trade_no, paytype, tag);
            } else if (wx_status == 2) {
                Toast.makeText(this, "支付取消", Toast.LENGTH_SHORT).show();
                wxout_trade_no = "";
            } else if (wx_status == 1) {//支付失败
                Intent intent = new Intent(ConfirmOrderActivity2.this, PayFailActivity.class);
                startActivity(intent);
                finish();
                wxout_trade_no = "";
            }
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);

                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();

                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        if (resultInfo.length() > 0) {
                            Payresulrinfo payresulrinfo = JSON.parseObject(resultInfo, Payresulrinfo.class);
                            presenter.OrderQuery(payresulrinfo.getAlipay_trade_app_pay_response().getTrade_no(),
                                    payresulrinfo.getAlipay_trade_app_pay_response().getOut_trade_no(),
                                    paytype, tag);
                        }
                        /**
                         *
                         * {@link NetWorkActivity#onMainEventThread(JTMessage)}
                         */
                        JTMessage message = new JTMessage();
                        message.what = MethodCode.EVENT_PAY;
                        message.obj = 3;
                        EventBus.getDefault().post(message);
                    } else if (TextUtils.equals(resultStatus, "6001")) {
                        Toast.makeText(ConfirmOrderActivity2.this, "支付取消", Toast.LENGTH_SHORT).show();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Intent intent = new Intent(ConfirmOrderActivity2.this, PayFailActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };

    private void payV2() {
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(ConfirmOrderActivity2.this);
                Map<String, String> result = alipay.payV2(data, true);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
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
