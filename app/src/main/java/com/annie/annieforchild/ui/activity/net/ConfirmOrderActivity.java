package com.annie.annieforchild.ui.activity.net;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alipay.sdk.app.PayTask;
import com.alipay.sdk.auth.AlipaySDK;
import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.CheckDoubleClickListener;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.OnCheckDoubleClick;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.PayResult;
import com.annie.annieforchild.bean.book.Line;
import com.annie.annieforchild.bean.net.Gift;
import com.annie.annieforchild.bean.net.ListenAndRead;
import com.annie.annieforchild.bean.net.NetSuggest;
import com.annie.annieforchild.bean.net.Payresulrinfo;
import com.annie.annieforchild.bean.net.WechatBean;
import com.annie.annieforchild.bean.order.AliOrderBean;
import com.annie.annieforchild.bean.order.WechatOrderBean;
import com.annie.annieforchild.presenter.NetWorkPresenter;
import com.annie.annieforchild.presenter.imp.NetWorkPresenterImp;
import com.annie.annieforchild.ui.activity.my.MyCourseActivity;
import com.annie.annieforchild.ui.adapter.GiftAdapter;
import com.annie.annieforchild.ui.adapter.NetGiftAdapter;
import com.annie.annieforchild.ui.application.MyApplication;
import com.annie.annieforchild.ui.interfaces.OnRecyclerItemClickListener;
import com.annie.annieforchild.view.info.ViewInfo;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;
import com.bumptech.glide.Glide;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 订单确认
 * Created by wanglei on 2018/9/25.
 */

public class ConfirmOrderActivity extends BaseActivity implements ViewInfo, OnCheckDoubleClick {
    private ImageView back, product_img;
    private EditText confirmWechat;
    private RelativeLayout zhifubaoLayout, wechatLayout;
    private LinearLayout suggestLayout;
    private TextView name, phone, address, buyBtn, totalPrice, product_name, product_text, materialPrice;
    private TextView confirm_price;
    private ImageView zhifubao, weixin;
    private ConstraintLayout addressLayout;
    private NetWorkPresenter presenter;
    private List<Gift> lists;
    private List<Gift> selectList;
    private CheckDoubleClickListener listener;
    private int payment = 0; //支付方式 0:支付宝 1:微信
    private boolean isMaterial, isAddress = false; //是否选择配套教材
    private int netid, addressId = -1;
    private Double netPrice = 0.0, matPrice;
    private NetSuggest netSuggest;
    private String data, type, netimage;
    private AlertHelper helper;
    private Dialog dialog;
    //    private String giftId = "";
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;
    private int flag;//0:未选配套 1:已选
    private String netsummary;
    private IWXAPI wxapi;
    //    private int count; //礼包可选数
    private boolean canBuy = false; //能否购买
    public static String buyPrice;
    private String wxout_trade_no = "";
    private String wxText;
    private int wx_status = -1;
    private int isbuy = -1;
    private int tag = 3;
    private String trade_status;

    {
        setRegister(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_confirm_order;
    }

    @Override
    protected void initView() {
        wxapi = WXAPIFactory.createWXAPI(this, SystemUtils.APP_ID, true);
        wxapi.registerApp(SystemUtils.APP_ID);
//        wxapi.handleIntent(getIntent(), this);
        back = findViewById(R.id.confirm_order_back);
        name = findViewById(R.id.confirm_name);
        phone = findViewById(R.id.confirm_phone);
        address = findViewById(R.id.confirm_address);
        zhifubao = findViewById(R.id.checkbox_zhifubao);
        weixin = findViewById(R.id.checkbox_weixin);
        buyBtn = findViewById(R.id.buy_btn);
        totalPrice = findViewById(R.id.total_price);
        addressLayout = findViewById(R.id.address_layout);
        zhifubaoLayout = findViewById(R.id.zhifubao_layout);
        wechatLayout = findViewById(R.id.wechat_layout);
        product_name = findViewById(R.id.product_name);
        product_text = findViewById(R.id.product_text);
        confirm_price = findViewById(R.id.confirm_price);
        confirmWechat = findViewById(R.id.confirm_wechat);
        materialPrice = findViewById(R.id.material_price);
        suggestLayout = findViewById(R.id.suggest_material_layout);
        listener = new CheckDoubleClickListener(this);
        back.setOnClickListener(listener);
        addressLayout.setOnClickListener(listener);
        buyBtn.setOnClickListener(listener);
        confirmWechat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String strs = confirmWechat.getText().toString();
                String str = SystemUtils.stringFilter(strs.toString());
                if (!strs.equals(str)) {
                    confirmWechat.setText(str);
                    confirmWechat.setSelection(str.length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        zhifubaoLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                zhifubao.setSelected(true);
                weixin.setSelected(false);
                payment = 0;
                return false;
            }
        });
        wechatLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                zhifubao.setSelected(false);
                weixin.setSelected(true);
                payment = 1;
                return false;
            }
        });

        zhifubao.setSelected(true);
        netid = getIntent().getIntExtra("netid", 0);
        type = getIntent().getStringExtra("type");
        netimage = getIntent().getStringExtra("netimage");
        netsummary = getIntent().getStringExtra("netsummary");
        flag = getIntent().getIntExtra("flag", 0);
        if (type.equals("体验课")) {
            suggestLayout.setVisibility(View.GONE);
            addressLayout.setVisibility(View.GONE);
        } else {
            suggestLayout.setVisibility(View.VISIBLE);
            addressLayout.setVisibility(View.VISIBLE);
            if (flag == 0) {
                suggestLayout.setVisibility(View.GONE);
            } else {
                suggestLayout.setVisibility(View.VISIBLE);
            }
        }
        product_img = findViewById(R.id.product_img);
        Glide.with(this).load(netimage).into(product_img);
        product_text.setText(netsummary);

    }

    @Override
    protected void initData() {
        lists = new ArrayList<>();
        selectList = new ArrayList<>();
        helper = new AlertHelper(this);
        dialog = helper.LoadingDialog();
        presenter = new NetWorkPresenterImp(this, this);
        presenter.initViewAndData();
        presenter.confirmOrder(netid);
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onCheckDoubleClick(View view) {
        switch (view.getId()) {
            case R.id.confirm_order_back:
                finish();
                break;
            case R.id.address_layout:
                Intent intent = new Intent(this, MyAddressActivity.class);
                intent.putExtra("tag", 0);
                startActivity(intent);
                break;
            case R.id.buy_btn:
                if (type.equals("体验课")) {
                    if (confirmWechat.getText() != null && confirmWechat.getText().toString().length() != 0) {
                        wxText = confirmWechat.getText().toString().trim();
                        if (!canBuy) {
                            return;
                        }
                        presenter.buynum(netid, 2);
                    } else {
                        showInfo("请输入您的微信号");
                    }
                } else {
                    if (addressId != -1) {
                        if (confirmWechat.getText() != null && confirmWechat.getText().toString().length() != 0) {
                            wxText = confirmWechat.getText().toString().trim();
                            if (!canBuy) {
                                return;
                            }
                            presenter.buynum(netid, 2);
                        } else {
                            showInfo("请输入您的微信号");
                        }
                    } else {
                        showInfo("请添加收货地址");
                    }
                }
                break;
//            case R.id.gift_layout:
//                if (netSuggest.getGift() == null) {
//                    showInfo("暂无赠送礼包");
//                } else {
//                    Intent intent1 = new Intent(this, GiftActivity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable("gift", (Serializable) lists);
//                    bundle.putSerializable("select", (Serializable) selectList);
//                    bundle.putInt("count", count);
//                    intent1.putExtras(bundle);
//                    startActivityForResult(intent1, 1);
//                }
//                break;
            case R.id.zhifubao_layout:
                zhifubao.setSelected(true);
                weixin.setSelected(false);
                payment = 0;
                break;
            case R.id.wechat_layout:
                zhifubao.setSelected(false);
                zhifubao.setActivated(false);
                weixin.setSelected(true);
                weixin.setActivated(true);
                payment = 1;
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_CONFIRMORDER) {
            netSuggest = (NetSuggest) message.obj;
            refresh();
        } else if (message.what == MethodCode.EVENT_ADDRESS) {
            isAddress = true;
            addressId = (int) message.obj;
            refresh();
            presenter.confirmOrder(netid);
        } else if (message.what == MethodCode.EVENT_EDITADDRESS) {
            isAddress = false;
            addressId = -1;
            presenter.confirmOrder(netid);
        } else if (message.what == MethodCode.EVENT_DELETEADDRESS) {
            isAddress = false;
            addressId = -1;
            presenter.confirmOrder(netid);
        } else if (message.what == MethodCode.EVENT_BUYNETWORK) {
            if (payment == 0) {
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
            if (payment == 0) {
                AliOrderBean aliOrderBean = (AliOrderBean) message.obj;
//                String trade_status = (String) message.obj;
                trade_status = aliOrderBean.getAlipay_trade_query_response().getTrade_status();
                if (trade_status.equals("TRADE_SUCCESS")) {
                    Intent intent = new Intent(ConfirmOrderActivity.this, PaySuccessActivity.class);
                    intent.putExtra("price", "ConfirmOrderActivity");
                    intent.putExtra("info", aliOrderBean.getSecretaryInfo());
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(ConfirmOrderActivity.this, PayFailActivity.class);
                    startActivity(intent);
                    finish();
                }
            } else {
                WechatOrderBean wechatOrderBean = (WechatOrderBean) message.obj;
//                String trade_status = (String) message.obj;
                trade_status = wechatOrderBean.getTrade_state();
                if (trade_status.equals("SUCCESS")) {
                    Intent intent = new Intent(ConfirmOrderActivity.this, PaySuccessActivity.class);
                    intent.putExtra("price", "ConfirmOrderActivity");
                    intent.putExtra("info", wechatOrderBean.getSecretaryInfo());
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(ConfirmOrderActivity.this, PayFailActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        } else if (message.what == MethodCode.EVENT_BUYNUM1) {
            isbuy = (int) message.obj;//0名额已满 1已购买 2可以买 3无法购买
            if (isbuy == 2) {
//                if (selectList.size() < count) {
//                    showInfo("请选择足够数量的礼包");
//                } else {
//                    giftId = "";
//                    for (int i = 0; i < selectList.size(); i++) {
//                        if (i == 0) {
//                            giftId = giftId + selectList.get(i).getId();
//                        } else {
//                            giftId = giftId + "," + selectList.get(i).getId();
//                        }
//                    }
                if (payment == 0) {
                    presenter.buyNetWork(netid, addressId, flag, payment, wxText, "");
                } else {
                    if (!wxapi.isWXAppInstalled()) {
                        showInfo("请您先安装微信客户端！");
                        return;
                    }
                    presenter.buyNetWork(netid, addressId, flag, payment, wxText, "");
                }
//                }
            } else if (isbuy == 0) {
                showInfo("名额已满");
                presenter.getNetSuggest(netid);
            } else if (isbuy == 1) {
                showInfo("已购买该课程");
                presenter.getNetSuggest(netid);
                JTMessage message1 = new JTMessage();
                message1.what = MethodCode.EVENT_PAY;
                message1.obj = 3;
                EventBus.getDefault().post(message1);
            } else if (isbuy == 3) {
                showInfo("已购买过该类课程");
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (payment == 1 && wxout_trade_no.length() > 0) {
            if (wx_status == 0) {
                presenter.OrderQuery("", wxout_trade_no, payment, tag);
            } else if (wx_status == 2) {
                Toast.makeText(this, "支付取消", Toast.LENGTH_SHORT).show();
            } else if (wx_status == 1) {//支付失败
                Intent intent = new Intent(ConfirmOrderActivity.this, PayFailActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (resultCode == 2) {
//            if (data != null) {
//                Bundle bundle = data.getExtras();
//                selectList.clear();
//                lists.clear();
//                selectList.addAll((List<Gift>) bundle.getSerializable("select"));
//                lists.addAll((List<Gift>) bundle.getSerializable("list"));
//            }
//        }
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
                                    payment, tag);
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
                        Toast.makeText(ConfirmOrderActivity.this, "支付取消", Toast.LENGTH_SHORT).show();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Intent intent = new Intent(ConfirmOrderActivity.this, PayFailActivity.class);
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
                PayTask alipay = new PayTask(ConfirmOrderActivity.this);
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

    private void refresh() {
        if (netSuggest != null) {
            if (netSuggest.getIsBuy() == 2) {
                canBuy = false;
            } else {
                canBuy = true;
            }
            product_name.setText(netSuggest.getNetName());
            confirm_price.setText(netSuggest.getPrice() + "元");
            confirmWechat.setText(netSuggest.getWxnumber() != null ? netSuggest.getWxnumber() : "");
//            if (netSuggest.getMaterial() != null && netSuggest.getMaterial().length() != 0) {
//                material.setText(netSuggest.getMaterial());
//            } else {
//                material.setText("无");
//            }

//            if (netSuggest.getGift() != null) {
//                count = netSuggest.getCount();
//                lists = netSuggest.getGift();
//                if (application.getSystemUtils().getUserInfo() != null) {
//                    if (application.getSystemUtils().getUserInfo().getIsfirstbuy() == 1) {
//                        selectList.clear();
//                        for (int i = 0; i < lists.size(); i++) {
//                            if (lists.get(i).getIsmust() == 1) {
//                                Gift gift = lists.get(i);
//                                selectList.add(gift);
//                                lists.get(i).setSelect(true);
//                            }
//                        }
//                    }
//                }
//            }

            if (netSuggest.getMaterialPrice() == null) {
                materialPrice.setVisibility(View.GONE);
            } else {
                materialPrice.setVisibility(View.VISIBLE);
                materialPrice.setText("￥" + netSuggest.getMaterialPrice());
                matPrice = Double.parseDouble(netSuggest.getMaterialPrice());
            }
            if (netSuggest.getAddress() != null && netSuggest.getAddress().size() != 0) {
                if (isAddress) {
                    for (int i = 0; i < netSuggest.getAddress().size(); i++) {
                        if (netSuggest.getAddress().get(i).getAddressId() == addressId) {
                            name.setText(netSuggest.getAddress().get(i).getName());
                            phone.setText(netSuggest.getAddress().get(i).getPhone());
                            address.setText(netSuggest.getAddress().get(i).getProvinces()+netSuggest.getAddress().get(i).getAddress());
                        }
                    }
                } else {
                    addressId = netSuggest.getAddress().get(0).getAddressId();
                    name.setText(netSuggest.getAddress().get(0).getName());
                    phone.setText(netSuggest.getAddress().get(0).getPhone());
                    address.setText(netSuggest.getAddress().get(0).getAddress());
                }
            }
            netPrice = Double.parseDouble(netSuggest.getPrice());
            if (flag == 0) {
                totalPrice.setText("共计：" + netPrice + "元");
                buyPrice = netPrice + "";
            } else {
                totalPrice.setText("共计：" + (netPrice + matPrice) + "元");
                buyPrice = (netPrice + matPrice) + "";
            }
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
