package com.annie.annieforchild.ui.activity.my;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.UserInfo;
import com.annie.annieforchild.presenter.MessagePresenter;
import com.annie.annieforchild.presenter.imp.MessagePresenterImp;
import com.annie.annieforchild.view.info.ViewInfo;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;
import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.Subscribe;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 兑换
 * Created by WangLei on 2018/1/17 0017
 */

public class ExchangeActivity extends BaseActivity implements ViewInfo, View.OnClickListener {
    private ImageView myExchangeBack;
    private CircleImageView headpic;
    private TextView name, nectar, coin, exchange_btn;
    private EditText exchange_nectar;
    private MessagePresenter presenter;
    private UserInfo userInfo;
    private AlertHelper helper;
    private Dialog dialog;

    {
        setRegister(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_exchange;
    }

    @Override
    protected void initView() {
        myExchangeBack = findViewById(R.id.exchange_back);
        headpic = findViewById(R.id.exchange_headpic);
        name = findViewById(R.id.exchange_name);
        nectar = findViewById(R.id.my_exchange_nectar);
        coin = findViewById(R.id.exchange_coin);
        exchange_nectar = findViewById(R.id.exchange_nectar);
        exchange_btn = findViewById(R.id.exchange_btn);
        myExchangeBack.setOnClickListener(this);
        exchange_btn.setOnClickListener(this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        if (getIntent() != null) {
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                userInfo = (UserInfo) bundle.getSerializable("userinfo");
            }
        }
    }

    @Override
    protected void initData() {
        initialize();
        helper = new AlertHelper(this);
        dialog = helper.LoadingDialog();
        presenter = new MessagePresenterImp(this, this);
        presenter.initViewAndData();
    }

    private void initialize() {
        Glide.with(this).load(userInfo.getAvatar()).into(headpic);
        name.setText(userInfo.getName());
        nectar.setText(userInfo.getNectar() != null ? userInfo.getNectar() + "花蜜" : "0花蜜");
        coin.setText(userInfo.getGold() != null ? userInfo.getGold() + "金币" : "0金币");
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.exchange_back:
                finish();
                break;
            case R.id.exchange_btn:
                //兑换
                if (exchange_nectar.getText() != null && exchange_nectar.getText().length() != 0) {
                    int ori = Integer.parseInt(userInfo.getNectar());
                    int now = Integer.parseInt(exchange_nectar.getText().toString());
                    if (now == 0) {
                        showInfo("请输入兑换花蜜数量");
                    } else {
                        if (now <= ori) {
                            if (now % 10 == 0) {
                                presenter.exchangeGold(Integer.parseInt(exchange_nectar.getText().toString()));
                            } else {
                                showInfo("请输入10的倍数");
                            }
                        } else {
                            showInfo("输入金额不能大于总花蜜数");
                        }
                    }
                }
                break;
        }
    }

    /**
     * {@link MessagePresenterImp#Success(int, Object)}
     *
     * @param message
     */
    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_EXCHANGEGOLD) {
            showInfo((String) message.obj);
            finish();
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
