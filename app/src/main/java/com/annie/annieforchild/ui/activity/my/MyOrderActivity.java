package com.annie.annieforchild.ui.activity.my;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
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
import com.annie.annieforchild.bean.order.MyOrder;
import com.annie.annieforchild.bean.order.WechatOrderBean;
import com.annie.annieforchild.presenter.NetWorkPresenter;
import com.annie.annieforchild.presenter.imp.NetWorkPresenterImp;
import com.annie.annieforchild.ui.activity.net.AddAddressActivity;
import com.annie.annieforchild.ui.activity.net.ConfirmOrderActivity2;
import com.annie.annieforchild.ui.activity.net.NetWorkActivity;
import com.annie.annieforchild.ui.activity.net.PayFailActivity;
import com.annie.annieforchild.ui.activity.net.PaySuccessActivity;
import com.annie.annieforchild.ui.adapter.MyOrderAdapter;
import com.annie.annieforchild.ui.interfaces.OnRecyclerItemClickListener;
import com.annie.annieforchild.view.info.ViewInfo;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by wanglei on 2019/4/3.
 */

public class MyOrderActivity extends BaseActivity implements ViewInfo, OnCheckDoubleClick {
    private ImageView back, empty;
    private RecyclerView recycler;
    private NetWorkPresenter presenter;
    private MyOrderAdapter adapter;
    private List<MyOrder> lists;
    private CheckDoubleClickListener listener;
    private AlertHelper helper;
    private Dialog dialog;
    private static final int SDK_PAY_FLAG = 1;
    private int paytype;
    private String data;
    private IWXAPI wxapi;
    private int wx_status = -1;
    public static String buyPrice;
    private String wxout_trade_no = "";
    private int tag = 2;
    private String trade_status;

    {
        setRegister(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_order;
    }

    @Override
    protected void initView() {
        wxapi = WXAPIFactory.createWXAPI(this, SystemUtils.APP_ID, true);
        wxapi.registerApp(SystemUtils.APP_ID);
        back = findViewById(R.id.order_back);
        empty = findViewById(R.id.my_order_empty);
        recycler = findViewById(R.id.order_recycler);
        listener = new CheckDoubleClickListener(this);
        back.setOnClickListener(listener);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recycler.setLayoutManager(layoutManager);
    }

    @Override
    protected void initData() {
        helper = new AlertHelper(this);
        dialog = helper.LoadingDialog();
        presenter = new NetWorkPresenterImp(this, this);
        presenter.initViewAndData();
        lists = new ArrayList<>();
        adapter = new MyOrderAdapter(this, lists, tag, new OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view) {
                int position = recycler.getChildAdapterPosition(view);
                Intent intent = new Intent(MyOrderActivity.this, ConfirmOrderActivity2.class);
                intent.putExtra("orderIncrId", lists.get(position).getOrderIncrId());
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view) {

            }
        }, presenter);
        recycler.setAdapter(adapter);
        presenter.getMyOrderList();
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_GETMYORDERLIST) {
            List<MyOrder> list = (List<MyOrder>) message.obj;
            lists.clear();
            lists.addAll(list);
            if (lists.size() != 0) {
                empty.setVisibility(View.GONE);
            } else {
                empty.setVisibility(View.VISIBLE);
            }
            adapter.notifyDataSetChanged();
        } else if (message.what == MethodCode.EVENT_CONTINUEPAY + 90000 + tag) {
            paytype = adapter.getPaytype();
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
            paytype = adapter.getPaytype();
            if (paytype == 0) {
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
                    buyPrice = adapter.getBuyPrice();
                    Intent intent = new Intent(MyOrderActivity.this, PaySuccessActivity.class);
                    intent.putExtra("price", "MyOrderActivity");
                    intent.putExtra("info", aliOrderBean.getSecretaryInfo());
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(MyOrderActivity.this, PayFailActivity.class);
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
                    buyPrice = adapter.getBuyPrice();
                    Intent intent = new Intent(MyOrderActivity.this, PaySuccessActivity.class);
                    intent.putExtra("price", "MyOrderActivity");
                    intent.putExtra("info", wechatOrderBean.getSecretaryInfo());
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(MyOrderActivity.this, PayFailActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        } else if (message.what == MethodCode.EVENT_CONFIRMBUYSUC) {
            presenter.getMyOrderList();
        } else if (message.what == MethodCode.EVENT_CANCELORDER) {
            presenter.getMyOrderList();
        }
    }

    @Override
    public void onCheckDoubleClick(View view) {
        switch (view.getId()) {
            case R.id.order_back:
                finish();
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
                Intent intent = new Intent(MyOrderActivity.this, PayFailActivity.class);
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
                        Toast.makeText(MyOrderActivity.this, "支付取消", Toast.LENGTH_SHORT).show();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Intent intent = new Intent(MyOrderActivity.this, PayFailActivity.class);
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
                PayTask alipay = new PayTask(MyOrderActivity.this);
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
