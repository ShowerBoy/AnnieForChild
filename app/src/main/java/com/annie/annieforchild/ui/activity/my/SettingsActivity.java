package com.annie.annieforchild.ui.activity.my;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.ui.activity.login.ModifyPsdActivity;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;
import com.annie.baselibrary.utils.CleanUtils;

/**
 * 设置
 * Created by WangLei on 2018/1/17 0017
 */

public class SettingsActivity extends BaseActivity implements View.OnClickListener {
    private ImageView settingsBack;
    private TextView cacheSize;
    private RelativeLayout changePsdLayout, changePhoneLayout, clearCacheLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_settings;
    }

    @Override
    protected void initView() {
        settingsBack = findViewById(R.id.settings_back);
        cacheSize = findViewById(R.id.cache_size);
        changePsdLayout = findViewById(R.id.change_psd_layout);
        changePhoneLayout = findViewById(R.id.change_phone_layout);
        clearCacheLayout = findViewById(R.id.clear_cache_layout);
        settingsBack.setOnClickListener(this);
        changePsdLayout.setOnClickListener(this);
        changePhoneLayout.setOnClickListener(this);
        clearCacheLayout.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        try {
            String size = CleanUtils.getTotalCacheSize(this);
            cacheSize.setText(size);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.settings_back:
                finish();
                break;
            case R.id.change_psd_layout:
                //修改密码
                Intent intent1 = new Intent(this, ModifyPsdActivity.class);
                intent1.putExtra("title", "修改密码");
                startActivity(intent1);
                break;
            case R.id.change_phone_layout:
                //修改手机号
                Intent intent = new Intent(this, ChangePhoneActivity.class);
                startActivity(intent);
                break;
            case R.id.clear_cache_layout:
                //清除缓存
                SystemUtils.GeneralDialog(this, "清除缓存")
                        .setMessage("是否清除缓存?")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                CleanUtils.cleanInternalCache();
                                initData();
                                dialogInterface.dismiss();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .create().show();
                break;
        }
    }
}
