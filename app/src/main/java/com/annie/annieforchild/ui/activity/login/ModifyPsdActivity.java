package com.annie.annieforchild.ui.activity.login;

import android.app.Dialog;
import android.content.Intent;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.BaseAdapter;
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
import com.annie.annieforchild.view.RegisterView;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;

import org.greenrobot.eventbus.Subscribe;

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
    }

    @Override
    protected void initData() {
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
        }
    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.modify_psd_back:
//                finish();
//                break;
//            case R.id.get_test_code2:
//                if (!phoneNumber2.getText().toString().equals("") && phoneNumber2.getText().toString().length() == 11 && !phoneNumber2.getText().toString().contains(" ") && phoneNumber2.getText().toString().matches("[0-9]+")) {
//                    getTestCode2.setClickable(false);
//                    countDownTimer.start();
//                    String phone = phoneNumber2.getText().toString();
//                    presenter.getVerificationCode(phone, 2);
//                } else {
//                    SystemUtils.show(this, "请重新输入手机号");
//                }
//                break;
//            case R.id.next_btn2:
//                if (ifNext()) {
//                    presenter.resetPassword(phoneNumber2.getText().toString(), testCode2.getText().toString(), modifyPsd.getText().toString(), presenter.getSerial_number());
//                } else {
//                    showInfo("输入有误,请重新检查");
//                }
//                break;
//        }
//    }

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
                if (ifNext()) {
                    presenter.resetPassword(phoneNumber2.getText().toString(), testCode2.getText().toString(), modifyPsd.getText().toString(), presenter.getSerial_number());
                } else {
                    showInfo("输入有误,请重新检查");
                }
                break;
        }
    }
}
