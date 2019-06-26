package com.annie.annieforchild.ui.activity.my;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.annie.annieforchild.R;
import com.annie.annieforchild.ui.adapter.MyFooterRecyclerAdapter;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;
import com.annie.baselibrary.utils.AppUtils;

/**
 * 关于
 * Created by WangLei on 2018/1/17 0017
 */

public class AboutActivity extends BaseActivity implements View.OnClickListener {
    private ImageView back;
    private TextView appName, appVersion;
    private RelativeLayout gongnengLayout, protocolLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    protected void initView() {
        back = findViewById(R.id.about_back);
        appName = findViewById(R.id.app_name);
        appVersion = findViewById(R.id.app_version);
        gongnengLayout = findViewById(R.id.gongneng_layout);
        protocolLayout = findViewById(R.id.user_protocol_layout);
        back.setOnClickListener(this);
        gongnengLayout.setOnClickListener(this);
        protocolLayout.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        appName.setText(AppUtils.getAppName() + "（学生端）");
        appVersion.setText("V " + AppUtils.getAppVersionName());
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.about_back:
                finish();
                break;
            case R.id.user_protocol_layout:
                Intent intent = new Intent(this, WebActivity.class);
                intent.putExtra("title", "用户协议");
                intent.putExtra("url", "https://testapici.anniekids.com/Api/index.php/v1//Share/UserRegistrationProtocol");
                startActivity(intent);
                break;
            case R.id.gongneng_layout:
                Intent intent1 = new Intent(this, WebActivity.class);
                intent1.putExtra("title", "功能介绍");
                intent1.putExtra("url", "https://testapici.anniekids.com/Api/index.php/v1//Share/FunctionIntroduction");
                startActivity(intent1);
                break;
        }
    }
}
