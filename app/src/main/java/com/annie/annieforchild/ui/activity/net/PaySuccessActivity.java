package com.annie.annieforchild.ui.activity.net;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.ActivityCollector;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.CheckDoubleClickListener;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.OnCheckDoubleClick;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.net.Address;
import com.annie.annieforchild.bean.net.NetClass;
import com.annie.annieforchild.bean.order.SecretaryInfo;
import com.annie.annieforchild.presenter.imp.NetWorkPresenterImp;
import com.annie.annieforchild.ui.activity.my.MyCourseActivity;
import com.annie.annieforchild.ui.activity.my.MyOrderActivity;
import com.annie.annieforchild.ui.activity.my.WebActivity;
import com.annie.annieforchild.ui.adapter.MyCourseAdapter;
import com.annie.annieforchild.ui.interfaces.OnRecyclerItemClickListener;
import com.annie.annieforchild.view.info.ViewInfo;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class PaySuccessActivity extends BaseActivity implements ViewInfo, OnCheckDoubleClick {
    private ImageView back;
    CheckDoubleClickListener listener;
    private TextView ok, price;
    LinearLayout to_network, to_mylesson, xiaomishuLayout;
    NetWorkPresenterImp presenter;
    private AlertHelper helper;
    private Dialog dialog;
    private String buyPrice;
    private TextView title, name, teacher, tips;
    private Button copy;
    private SecretaryInfo secretaryInfo;

    {
        setRegister(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pay_success;
    }

    @Override
    protected void initView() {
        listener = new CheckDoubleClickListener(this);
        price = findViewById(R.id.pay_price);
        back = findViewById(R.id.back);
        ok = findViewById(R.id.ok);
        to_mylesson = findViewById(R.id.to_mylesson);
        to_network = findViewById(R.id.to_network);
        title = findViewById(R.id.xiaomishu_title);
        name = findViewById(R.id.xiaomishu_name);
        teacher = findViewById(R.id.xiaomishu_teacher);
        tips = findViewById(R.id.xiaomishu_tips);
        copy = findViewById(R.id.xiaomishu_copy);
        xiaomishuLayout = findViewById(R.id.xiaomishu_layout);

        back.setOnClickListener(listener);
        ok.setOnClickListener(listener);
        to_network.setOnClickListener(listener);
        to_mylesson.setOnClickListener(listener);
        copy.setOnClickListener(listener);
        if (getIntent() != null) {
            buyPrice = getIntent().getStringExtra("price");
            secretaryInfo = (SecretaryInfo) getIntent().getSerializableExtra("info");
        }
        if (buyPrice != null) {
            if (buyPrice.equals("ConfirmOrderActivity")) {
                buyPrice = ConfirmOrderActivity.buyPrice;
            } else if (buyPrice.equals("ConfirmOrderActivity2")) {
                buyPrice = ConfirmOrderActivity2.buyPrice;
            } else {
                buyPrice = MyOrderActivity.buyPrice;
            }
            price.setText("￥" + buyPrice);
        }
        if (secretaryInfo != null) {
            xiaomishuLayout.setVisibility(View.VISIBLE);
            title.setText(secretaryInfo.getTitle());
            name.setText(secretaryInfo.getNikename());
            teacher.setText(secretaryInfo.getTeacher());
            tips.setText(secretaryInfo.getTips());
        } else {
            xiaomishuLayout.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initData() {
        helper = new AlertHelper(this);
        dialog = helper.LoadingDialog();
        presenter = new NetWorkPresenterImp(this, this);
        presenter.initViewAndData();
//        presenter.buySuccess();
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Subscribe
    public void onMainEventThread(JTMessage message) {

    }

    @Override
    public void onCheckDoubleClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.ok:
                finish();
                break;
            case R.id.to_mylesson:
                Intent intent = new Intent(this, MyCourseActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.to_network:
                NetSuggestActivity.netSuggestActivity.finish();
                finish();
                break;
            case R.id.xiaomishu_copy:
                //获取剪贴板管理器：
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                // 创建普通字符型ClipData
                ClipData mClipData = ClipData.newPlainText("Label", teacher.getText());
                // 将ClipData内容放到系统剪贴板里。
                cm.setPrimaryClip(mClipData);
                Toast.makeText(PaySuccessActivity.this, "复制成功", Toast.LENGTH_SHORT).show();
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
