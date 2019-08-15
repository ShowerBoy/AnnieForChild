package com.annie.annieforchild.ui.activity.my;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.annie.annieforchild.R;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;
import com.annie.baselibrary.utils.AppUtils;

/**
 * 设备检测
 */

public class DevicetestActivity extends BaseActivity implements View.OnClickListener {
    private ImageView back;
    private Button devide_totest;
    private Button devide_topass;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_device_test;
    }

    @Override
    protected void initView() {
        back = findViewById(R.id.about_back);
        devide_topass=findViewById(R.id.devide_topass);
        devide_totest=findViewById(R.id.devide_totest);
        back.setOnClickListener(this);
        devide_totest.setOnClickListener(this);
        devide_topass.setOnClickListener(this);
    }

    @Override
    protected void initData() {

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
            case R.id.devide_totest:
                Intent intent=new Intent();
                intent.setClass(this, DevicetestingActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.devide_topass:
                SharedPreferences preferences = getSharedPreferences("ischeck", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("ischeck","1");
                editor.commit();
                finish();
                break;

        }
    }
}
