package com.annie.annieforchild.ui.activity.my;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.Utils.service.MusicService;
import com.annie.annieforchild.bean.login.LoginBean;
import com.annie.annieforchild.ui.activity.login.LoginActivity;
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
    private RelativeLayout changePsdLayout, changePhoneLayout, clearCacheLayout, logout;

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
        logout = findViewById(R.id.logout);
        settingsBack.setOnClickListener(this);
        changePsdLayout.setOnClickListener(this);
        changePhoneLayout.setOnClickListener(this);
        clearCacheLayout.setOnClickListener(this);
        logout.setOnClickListener(this);
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
            case R.id.logout:
                SystemUtils.GeneralDialog(this, "退出登陆")
                        .setMessage("是否退出登录?")
                        .setPositiveButton("确定", new DialogInterface
                                .OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (MusicService.isPlay) {
                                    if (musicService != null) {
                                        musicService.stop();
                                    }
                                }
                                MusicService.musicTitle = null;
                                MusicService.musicImageUrl = null;
                                SharedPreferences preferences = getSharedPreferences("userInfo", MODE_PRIVATE | MODE_MULTI_PROCESS);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.remove("phone");
                                editor.remove("psd");
                                editor.commit();

                                application.getSystemUtils().getPhoneSN().setUsername(null);
                                application.getSystemUtils().getPhoneSN().setLastlogintime(null);
                                application.getSystemUtils().getPhoneSN().setSystem(null);
                                application.getSystemUtils().getPhoneSN().setBitcode(null);
                                application.getSystemUtils().setDefaultUsername(null);
                                application.getSystemUtils().setToken(null);
                                application.getSystemUtils().getPhoneSN().save();
                                application.getSystemUtils().setOnline(false);
                                dialog.dismiss();
                                Intent intent2 = new Intent(SettingsActivity.this, LoginActivity.class);
                                startActivity(intent2);
                                finishAffinity();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create().show();
                break;
        }
    }
}
