package com.annie.annieforchild.ui.activity.login;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.presenter.RegisterPresenter;
import com.annie.annieforchild.presenter.imp.RegisterPresenterImp;
import com.annie.annieforchild.ui.activity.child.AddChildActivity;
import com.annie.annieforchild.view.RegisterView;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;
import com.ashokvarma.bottomnavigation.utils.Utils;

import org.greenrobot.eventbus.Subscribe;

/**
 * 注册
 * Created by WangLei on 2018/1/22 0022
 */

public class RegisterActivity extends BaseActivity implements RegisterView, View.OnClickListener {
    private EditText phone_number, test_code, password, confirm_password;
    private TextView getTestCode, userProtocol;
    private ImageView registerBack;
    private Button nextBtn;
    private CountDownTimer countDownTimer;
    private RegisterPresenter presenter;
    private AlertHelper helper;
    private Dialog dialog;

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
        getTestCode.setOnClickListener(this);
        userProtocol.setOnClickListener(this);
        nextBtn.setOnClickListener(this);
        registerBack.setOnClickListener(this);
        helper = new AlertHelper(this);
        dialog = helper.LoadingDialog();
    }

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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.get_test_code:
                //获取验证码
                if (!phone_number.getText().toString().equals("") && phone_number.getText().toString().length() == 11 && !phone_number.getText().toString().contains(" ") && phone_number.getText().toString().matches("[0-9]+")) {
                    getTestCode.setClickable(false);
                    countDownTimer.start();
                    String phone = phone_number.getText().toString();
                    presenter.getVerificationCode(phone, 1);
                } else {
                    SystemUtils.show(this, "请重新输入手机号");
                }
                break;
            case R.id.user_protocol:
                //用户协议

                break;
            case R.id.next_btn:
//                Intent intent = new Intent(this, AddChildActivity.class);
//                startActivity(intent);
                if (ifNext()) {
                    presenter.register(phone_number.getText().toString(), test_code.getText().toString(), password.getText().toString());
                } else {
                    showInfo("输入有误,请重新检查");
                }
                break;
            case R.id.register_back:
                finish();
                break;
        }
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
                Intent intent = new Intent(this, AddChildActivity.class);
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
}
