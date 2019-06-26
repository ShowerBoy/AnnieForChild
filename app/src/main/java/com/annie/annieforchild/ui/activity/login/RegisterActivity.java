package com.annie.annieforchild.ui.activity.login;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.annie.annieforchild.presenter.RegisterPresenter;
import com.annie.annieforchild.presenter.imp.RegisterPresenterImp;
import com.annie.annieforchild.ui.activity.child.AddChildActivity;
import com.annie.annieforchild.ui.activity.child.AddStudentActivity;
import com.annie.annieforchild.ui.activity.my.WebActivity;
import com.annie.annieforchild.view.RegisterView;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;
import com.ashokvarma.bottomnavigation.utils.Utils;
import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionDenied;
import com.zhy.m.permission.PermissionGrant;

import org.greenrobot.eventbus.Subscribe;

/**
 * 注册
 * Created by WangLei on 2018/1/22 0022
 */

public class RegisterActivity extends BaseActivity implements RegisterView, OnCheckDoubleClick {
    private EditText phone_number, test_code, password, confirm_password;
    private TextView getTestCode, userProtocol;
    private ImageView registerBack;
    private Button nextBtn;
    private CountDownTimer countDownTimer;
    private RegisterPresenter presenter;
    private AlertHelper helper;
    private Dialog dialog;
    private String phone;
    private boolean isClick = false;
    private CheckDoubleClickListener listener;

    {
        setRegister(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {
        phone_number = findViewById(R.id.phone_number);
        test_code = findViewById(R.id.test_code);
        getTestCode = findViewById(R.id.get_test_code);
        userProtocol = findViewById(R.id.user_protocol);
        nextBtn = findViewById(R.id.next_btn);
        registerBack = findViewById(R.id.register_back);
        password = findViewById(R.id.register_password);
        confirm_password = findViewById(R.id.confirm_password);
        listener = new CheckDoubleClickListener(this);
        getTestCode.setOnClickListener(listener);
        userProtocol.setOnClickListener(listener);
        nextBtn.setOnClickListener(listener);
        registerBack.setOnClickListener(listener);
        helper = new AlertHelper(this);
        dialog = helper.LoadingDialog();
        phone_number.addTextChangedListener(textWatcher);
        test_code.addTextChangedListener(textWatcher);
        password.addTextChangedListener(textWatcher);
        confirm_password.addTextChangedListener(textWatcher);
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (phone_number.getText().toString() != null && phone_number.getText().toString().length() > 0
                    && test_code.getText().toString() != null && test_code.getText().toString().length() > 0
                    && password.getText().toString() != null && password.getText().toString().length() > 0
                    && confirm_password.getText().toString() != null && confirm_password.getText().toString().length() > 0) {
                isClick = true;
                nextBtn.setBackground(ContextCompat.getDrawable(RegisterActivity.this, R.drawable.login_btn_t));
            } else {
                isClick = false;
                nextBtn.setBackground(ContextCompat.getDrawable(RegisterActivity.this, R.drawable.login_btn_f));
            }
        }
    };

    @Override
    protected void initData() {
        presenter = new RegisterPresenterImp(this, this);
        presenter.initViewAndData();
        countDownTimer = new CountDownTimer(60 * 1000, 1000) {
            @Override
            public void onTick(long l) {
//                getTestCode.setTextSize(12);
                getTestCode.setText(l / 1000 + "s后重新发送");
            }

            @Override
            public void onFinish() {
//                getTestCode.setTextSize(14);
                getTestCode.setText("获取验证码");
                getTestCode.setClickable(true);
            }
        };
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    private boolean ifNext() {
        if (phone_number.getText().toString().equals("") || phone_number.getText().toString().length() != 11 || phone_number.getText().toString().contains(" ") || test_code.getText().toString().contains(" ") || test_code.getText().toString().equals("")
                || password.getText().toString().equals("") || confirm_password.getText().toString().equals("") || password.getText().toString().length() != confirm_password.getText().toString().length() || !password.getText().toString().equals(confirm_password.getText().toString())) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * {@link RegisterPresenterImp#Success(int, Object)}
     *
     * @param message
     */
    @Subscribe
    public void onEventMainThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_RGISTER) {
            if (message.obj instanceof String) {
                application.getSystemUtils().setPhone(phone_number.getText().toString());
                application.getSystemUtils().setPassword(password.getText().toString());
                Intent intent = new Intent(this, AddStudentActivity.class);
                intent.putExtra("from", "register");
                startActivity(intent);
                finish();
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
    protected void onResume() {
        super.onResume();
        phone_number.requestFocus();
    }

    @Override
    public void onCheckDoubleClick(View view) {
        switch (view.getId()) {
            case R.id.get_test_code:
                //获取验证码
                if (!phone_number.getText().toString().equals("") && phone_number.getText().toString().length() == 11 && !phone_number.getText().toString().contains(" ") && phone_number.getText().toString().matches("[0-9]+")) {
                    getTestCode.setClickable(false);
                    countDownTimer.start();
                    phone = phone_number.getText().toString();
                    presenter.getVerificationCode(phone, 1);
                } else {
                    SystemUtils.show(this, "请重新输入手机号");
                }
                break;
            case R.id.user_protocol:
                //用户协议
                Intent intent = new Intent(this, WebActivity.class);
                intent.putExtra("title", "用户协议");
                intent.putExtra("url", "https://testapici.anniekids.com/Api/index.php/v1//Share/UserRegistrationProtocol");
                startActivity(intent);
                break;
            case R.id.next_btn:
                if (isClick) {
                    if (ifNext()) {
                        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                                MPermissions.requestPermissions(this, 3, new String[]{
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                                });
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
                            presenter.register(phone_number.getText().toString(), test_code.getText().toString(), password.getText().toString());
                        }
                    } else {
                        showInfo("输入有误,请重新检查");
                    }
                }
                break;
            case R.id.register_back:
                finish();
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        MPermissions.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @PermissionGrant(3)
    public void requsetSuccess() {
    }

    @PermissionDenied(3)
    public void requestDenied() {
        Toast.makeText(this, "缺少权限！", Toast.LENGTH_SHORT).show();
    }
}
