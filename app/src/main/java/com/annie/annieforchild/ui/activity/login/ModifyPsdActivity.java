package com.annie.annieforchild.ui.activity.login;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.CheckDoubleClickListener;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.OnCheckDoubleClick;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.login.PhoneSN;
import com.annie.annieforchild.presenter.RegisterPresenter;
import com.annie.annieforchild.presenter.imp.RegisterPresenterImp;
import com.annie.annieforchild.view.RegisterView;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;
import com.zhy.m.permission.MPermissions;

import org.greenrobot.eventbus.Subscribe;
import org.litepal.LitePal;

import java.util.List;
import java.util.UUID;

import cn.jiguang.analytics.android.api.CountEvent;
import cn.jiguang.analytics.android.api.JAnalyticsInterface;

//import com.zhy.m.permission.PermissionDenied;
//import com.zhy.m.permission.PermissionGrant;

/**
 * 忘记密码/修改密码
 * Created by wanglei on 2018/3/26.
 */

public class ModifyPsdActivity extends BaseActivity implements RegisterView, OnCheckDoubleClick {
    private Button confirm;
    private ImageView back;
    private TextView title, getTestCode2;
    private CountDownTimer countDownTimer;
    private EditText confirmPsd2, modifyPsd, testCode2, phoneNumber2;
    private RegisterPresenter presenter;
    private AlertHelper helper;
    private Dialog dialog;
    private Intent intent;
    private CheckDoubleClickListener listener;
    private TelephonyManager tm;
    private SQLiteDatabase db;

    {
        setRegister(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_modify_psd;
    }

    @Override
    protected void initView() {
        confirm = findViewById(R.id.next_btn2);
        back = findViewById(R.id.modify_psd_back);
        title = findViewById(R.id.modify_psd_title);
        confirmPsd2 = findViewById(R.id.confirm_password2);
        modifyPsd = findViewById(R.id.modify_password);
        testCode2 = findViewById(R.id.test_code2);
        getTestCode2 = findViewById(R.id.get_test_code2);
        phoneNumber2 = findViewById(R.id.phone_number2);
        listener = new CheckDoubleClickListener(this);
        getTestCode2.setOnClickListener(listener);
        confirm.setOnClickListener(listener);
        back.setOnClickListener(listener);
        helper = new AlertHelper(this);
        dialog = helper.LoadingDialog();
        SystemUtils.setEditTextInhibitInputSpace(modifyPsd);
        SystemUtils.setEditTextInhibitInputSpace(confirmPsd2);
        SystemUtils.setEditTextInhibitInputSpace(testCode2);
        SystemUtils.setEditTextInhibitInputSpace(phoneNumber2);
    }

    @Override
    protected void initData() {
        tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        intent = getIntent();
        if (intent != null) {
            String text = intent.getStringExtra("title");
            title.setText(text);
        }
        presenter = new RegisterPresenterImp(this, this);
        presenter.initViewAndData();
        countDownTimer = new CountDownTimer(60 * 1000, 1000) {
            @Override
            public void onTick(long l) {
                getTestCode2.setText(l / 1000 + "s后重新发送");
            }

            @Override
            public void onFinish() {
                getTestCode2.setText("获取验证码");
                getTestCode2.setClickable(true);
            }
        };
        db = LitePal.getDatabase();
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    /**
     * {@link RegisterPresenterImp#Success(int, Object)}
     *
     * @param message
     */
    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_RESETPASSWORD) {
            showInfo((String) message.obj);
            finish();
        }else if(message.what==MethodCode.EVENT_RESETPASSWORD+10000){//验证码错误或者其他错误
            showInfo((String) message.obj);
        }
    }

    private boolean ifNext() {
        if (phoneNumber2.getText().toString().equals("") || phoneNumber2.getText().toString().length() != 11 || phoneNumber2.getText().toString().contains(" ") || testCode2.getText().toString().contains(" ") || testCode2.getText().toString().equals("")
                || modifyPsd.getText().toString().equals("") || confirmPsd2.getText().toString().equals("") || modifyPsd.getText().toString().length() != confirmPsd2.getText().toString().length()) {
            return false;
        } else {
            return true;
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
    protected void onResume() {
        super.onResume();
        phoneNumber2.requestFocus();
    }

    @Override
    public void onCheckDoubleClick(View view) {
        switch (view.getId()) {
            case R.id.modify_psd_back:
                finish();
                break;
            case R.id.get_test_code2:
                if (!phoneNumber2.getText().toString().equals("") && phoneNumber2.getText().toString().length() == 11 && !phoneNumber2.getText().toString().contains(" ") && phoneNumber2.getText().toString().matches("[0-9]+")) {
                    getTestCode2.setClickable(false);
                    countDownTimer.start();
                    String phone = phoneNumber2.getText().toString();
                    presenter.getVerificationCode(phone, 2);
                } else {
                    SystemUtils.show(this, "请重新输入手机号");
                }
                break;
            case R.id.next_btn2:
                if (phoneNumber2.getText().toString().equals("") || phoneNumber2.getText().toString().length() != 11 || phoneNumber2.getText().toString().contains(" ")) {
                    showInfo("手机号输入有误");
                    return;
                }
                if (testCode2.getText().toString().contains(" ") || testCode2.getText().toString().equals("")) {
                    showInfo("验证码输入有误");
                    return;
                }
                if (modifyPsd.getText().toString().equals("") || modifyPsd.getText().toString().length() == 0) {
                    showInfo("请输入密码");
                    return;
                }
                if (confirmPsd2.getText().toString().equals("") || confirmPsd2.getText().toString().length() == 0) {
                    showInfo("请输入确认密码");
                    return;
                }
                if (modifyPsd.getText().toString().length() != confirmPsd2.getText().toString().length() || !modifyPsd.getText().toString().equals(confirmPsd2.getText().toString())) {
                    showInfo("输入密码不一致");
                    return;
                }

                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//                        MPermissions.requestPermissions(this, 4, new String[]{
//                                Manifest.permission.WRITE_EXTERNAL_STORAGE
//                        });
                        SystemUtils.hasPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    } else {
                        showInfo("无法正常使用安妮花，请开通存储权限！请设置");
                        Intent localIntent = new Intent();
                        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        if (Build.VERSION.SDK_INT >= 9) {
                            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                            localIntent.setData(Uri.fromParts("package", getPackageName(), null));
                        } else if (Build.VERSION.SDK_INT <= 8) {
                            localIntent.setAction(Intent.ACTION_VIEW);
                            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
                            localIntent.putExtra("com.android.settings.ApplicationPkgName", getPackageName());
                        }
                        startActivity(localIntent);
                    }
                } else {
                    CountEvent Event_040401 = new CountEvent(MethodCode.A040401);
                    JAnalyticsInterface.onEvent(this, Event_040401);
                    doit();
                    presenter.resetPassword(phoneNumber2.getText().toString(), testCode2.getText().toString(), modifyPsd.getText().toString(), presenter.getSerial_number());
                }
                break;
        }
    }

    @SuppressLint("MissingPermission")
    private void doit() {
        List<PhoneSN> list = LitePal.findAll(PhoneSN.class);
        if (list != null && list.size() != 0) {
            PhoneSN phoneSN = list.get(list.size() - 1);
            if (phoneSN.getSn() == null) {
                if (tm.getSimSerialNumber() != null) {
                    phoneSN.setSn(tm.getSimSerialNumber());
                } else {
                    phoneSN.setSn(UUID.randomUUID().toString());
                }
                phoneSN.save();
            }
            application.getSystemUtils().setPhoneSN(phoneSN);
            application.getSystemUtils().setSn(phoneSN.getSn());
        } else {
            PhoneSN phoneSN = new PhoneSN();
            if (tm.getSimSerialNumber() != null) {
                phoneSN.setSn(tm.getSimSerialNumber());
            } else {
                phoneSN.setSn(UUID.randomUUID().toString());
            }
            phoneSN.save();
            application.getSystemUtils().setPhoneSN(phoneSN);
            application.getSystemUtils().setSn(phoneSN.getSn());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        MPermissions.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

//    @PermissionGrant(4)
//    public void requsetSuccess() {
//        doit();
//        presenter.resetPassword(phoneNumber2.getText().toString(), testCode2.getText().toString(), modifyPsd.getText().toString(), presenter.getSerial_number());
//    }
//
//    @PermissionDenied(4)
//    public void requestDenied() {
//        Toast.makeText(this, "缺少权限！", Toast.LENGTH_SHORT).show();
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (db != null) {
            db.close();
        }
    }

}
