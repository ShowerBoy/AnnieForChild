package com.annie.annieforchild.ui.activity.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.bean.UserInfo;
import com.annie.annieforchild.presenter.ChildPresenter;
import com.annie.annieforchild.presenter.imp.ChildPresenterImp;
import com.annie.annieforchild.view.AddChildView;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;
import com.bumptech.glide.Glide;

/**
 * 我的金币
 * Created by WangLei on 2018/3/7 0007
 */

public class MyCoinActivity extends BaseActivity implements AddChildView, View.OnClickListener {
    private ImageView back, headpic;
    private TextView myCoinName, myCoinNum, duihuan;
    private UserInfo userInfo;
    private ChildPresenter presenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_coin;
    }

    @Override
    protected void initView() {
        back = findViewById(R.id.my_coin_back);
        headpic = findViewById(R.id.my_coin_headpic);
        myCoinName = findViewById(R.id.my_coin_name);
        myCoinNum = findViewById(R.id.my_coin_num);
        duihuan = findViewById(R.id.duihuan);
        back.setOnClickListener(this);
        duihuan.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        if (getIntent() != null) {
            userInfo = (UserInfo) getIntent().getExtras().getSerializable("userinfo");
        }
        initialize();
        presenter = new ChildPresenterImp(this, this);
        presenter.initViewAndData();
    }

    private void initialize() {
        if (userInfo != null) {
            Glide.with(this).load(userInfo.getAvatar()).into(headpic);
            myCoinName.setText(userInfo.getName());
            myCoinNum.setText(userInfo.getGold() != null ? userInfo.getGold() : 0 + "金币");
        }
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_coin_back:
                finish();
                break;
            case R.id.duihuan:
                Intent intent = new Intent(this, ExchangeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("userinfo", userInfo);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void showInfo(String info) {
        Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoad() {

    }

    @Override
    public void dismissLoad() {

    }

    @Override
    public int getTag() {
        return 0;
    }
}
