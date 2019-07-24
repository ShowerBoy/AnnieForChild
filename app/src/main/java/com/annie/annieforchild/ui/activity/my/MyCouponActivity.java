package com.annie.annieforchild.ui.activity.my;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
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
import com.annie.annieforchild.bean.net.DiscountRecord;
import com.annie.annieforchild.bean.net.Payresulrinfo;
import com.annie.annieforchild.bean.net.WechatBean;
import com.annie.annieforchild.bean.order.AliOrderBean;
import com.annie.annieforchild.bean.order.MyOrder;
import com.annie.annieforchild.bean.order.WechatOrderBean;
import com.annie.annieforchild.presenter.NetWorkPresenter;
import com.annie.annieforchild.presenter.imp.NetWorkPresenterImp;
import com.annie.annieforchild.ui.activity.net.ConfirmOrderActivity2;
import com.annie.annieforchild.ui.activity.net.NetWorkActivity;
import com.annie.annieforchild.ui.activity.net.PayFailActivity;
import com.annie.annieforchild.ui.activity.net.PaySuccessActivity;
import com.annie.annieforchild.ui.adapter.CouponAdapter;
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
 * 我的优惠券
 */

public class MyCouponActivity extends BaseActivity implements ViewInfo, OnCheckDoubleClick {
    private ImageView back, empty;
    private RecyclerView recycler;
    private NetWorkPresenter presenter;
    private CouponAdapter adapter;
    private List<DiscountRecord> lists;
    private CheckDoubleClickListener listener;
    private AlertHelper helper;
    private Dialog dialog;
    private int tag = 3;
    private  Intent intent;
    private Bundle bundle;
    private String to_enter="";//进入方式，从哪个页面进入，我的优惠券或者订单页面选择优惠券
    private int netid=0;

    {
        setRegister(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_coupon;
    }

    @Override
    protected void initView() {
        back = findViewById(R.id.coupon_back);
        empty = findViewById(R.id.my_coupon_empty);
        recycler = findViewById(R.id.coupon_recycler);
        listener = new CheckDoubleClickListener(this);
        back.setOnClickListener(listener);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        recycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recycler.setLayoutManager(layoutManager);
    }

    @Override
    protected void initData() {
        intent = getIntent();
        bundle = intent.getExtras();
        if(bundle!=null){
            to_enter=bundle.getString("to");
            netid=bundle.getInt("netid",0);

        }
        helper = new AlertHelper(this);
        dialog = helper.LoadingDialog();
        presenter = new NetWorkPresenterImp(this, this);
        presenter.initViewAndData();
        lists = new ArrayList<>();
        adapter = new CouponAdapter(this,this, lists, to_enter);
        recycler.setAdapter(adapter);
        if(to_enter.equals("confirm")){//从订单页面进入
            presenter.getDiscountRecordList(netid);
        }else {
            presenter.getDiscountRecordList(0);//我的页面进入传0
        }

    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_DISCOUNTRECORD) {
            List<DiscountRecord> list = (List<DiscountRecord>) message.obj;
            lists.clear();
            lists.addAll(list);
            if (lists.size() != 0) {
                empty.setVisibility(View.GONE);
            } else {
                empty.setVisibility(View.VISIBLE);
            }
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onCheckDoubleClick(View view) {
        switch (view.getId()) {
            case R.id.coupon_back:
                finish();
                break;

        }
    }

    @Override
    public void onResume() {
        super.onResume();

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
