package com.annie.annieforchild.ui.activity.my;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.Utils.service.MusicService;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.UserInfo;
import com.annie.annieforchild.presenter.FourthPresenter;
import com.annie.annieforchild.presenter.imp.FourthPresenterImp;
import com.annie.annieforchild.ui.activity.login.LoginActivity;
import com.annie.annieforchild.ui.activity.login.ModifyPsdActivity;
import com.annie.annieforchild.view.FourthView;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;
import com.annie.baselibrary.utils.CleanUtils;

import org.greenrobot.eventbus.Subscribe;

/**
 * 设置
 * Created by WangLei on 2018/1/17 0017
 */

public class SettingsActivity extends BaseActivity implements FourthView, View.OnClickListener {
    private ImageView settingsBack;
    private TextView cacheSize, weixinText;
    private RelativeLayout changePsdLayout, changePhoneLayout, clearCacheLayout, bindWeixinLayout, logout;
    private FourthPresenter presenter;
    private UserInfo userInfo;
    private AlertHelper helper;
    private Dialog dialog;
    private String tag;

    {
        setRegister(true);
    }

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
        bindWeixinLayout = findViewById(R.id.bind_wechat_layout);
        weixinText = findViewById(R.id.bind_wechat_text);
        logout = findViewById(R.id.logout);
        settingsBack.setOnClickListener(this);
        changePsdLayout.setOnClickListener(this);
        changePhoneLayout.setOnClickListener(this);
        clearCacheLayout.setOnClickListener(this);
        bindWeixinLayout.setOnClickListener(this);
        logout.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        helper = new AlertHelper(this);
        dialog = helper.LoadingDialog();
        tag = getIntent().getStringExtra("tag");
        presenter = new FourthPresenterImp(this, this, tag);
        presenter.initViewAndData(1);
        try {
            String size = CleanUtils.getTotalCacheSize(this);
            cacheSize.setText(size);
        } catch (Exception e) {
            e.printStackTrace();
        }
        weixinText.setText(application.getSystemUtils().getUserInfo().getWeixinNum() != null ? application.getSystemUtils().getUserInfo().getWeixinNum() : "");
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
//                                    if (musicService != null) {
//                                        musicService.stop();
//                                    }
                                    MusicService.stop();
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
            case R.id.bind_wechat_layout:
                SystemUtils.setBackGray(this, true);
                SystemUtils.getBindWeixin(this, presenter).showAtLocation(SystemUtils.popupView, Gravity.CENTER, 0, 0);
                break;
        }
    }

    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_BINDWEIXIN) {
            int result = (int) message.obj;
            if (result == 0) {
                showInfo("绑定失败");
            } else {
                weixinText.setText(SystemUtils.weixinNum);
                application.getSystemUtils().getUserInfo().setWeixinNum(SystemUtils.weixinNum);
                showInfo("绑定成功");
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

    @Override
    public RecyclerView getMemberRecycler() {
        return null;
    }
}
