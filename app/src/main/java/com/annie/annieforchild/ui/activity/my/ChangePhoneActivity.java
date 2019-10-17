package com.annie.annieforchild.ui.activity.my;

import android.app.Dialog;
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
import com.annie.annieforchild.view.RegisterView;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;

import org.greenrobot.eventbus.Subscribe;

import cn.jiguang.analytics.android.api.CountEvent;
import cn.jiguang.analytics.android.api.JAnalyticsInterface;

/**
 * 修改手机号
 * Created by WangLei on 2018/2/27 0027
 */

public class ChangePhoneActivity extends BaseActivity implements RegisterView, View.OnClickListener {
    private ImageView changePhoneBack;
    private EditText newPhoneNumber, testCodeCp;
    private TextView getTestCodeCp, currentPhone;
    private Button commitNewPhone;
    private CountDownTimer countDownTimer;
    private RegisterPresenter presenter;
    private AlertHelper helper;
    private Dialog dialog;

    {
        setRegister(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_change_phone;
    }

    @Override
    protected void initView() {
        changePhoneBack = findViewById(R.id.change_phone_back);
        newPhoneNumber = findViewById(R.id.new_phoneNumber);
        testCodeCp = findViewById(R.id.test_code_cp);
        getTestCodeCp = findViewById(R.id.get_test_code_cp);
        commitNewPhone = findViewById(R.id.commit_new_phone);
        currentPhone = findViewById(R.id.current_phone);
        changePhoneBack.setOnClickListener(this);
        getTestCodeCp.setOnClickListener(this);
        commitNewPhone.setOnClickListener(this);
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
                getTestCodeCp.setText(l / 1000 + "s后重新发送");
            }

            @Override
            public void onFinish() {
                getTestCodeCp.setText("获取验证码");
                getTestCodeCp.setClickable(true);
            }
        };
        currentPhone.setText("当前手机号：" + application.getSystemUtils().getPhone());
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.change_phone_back:
                finish();
                break;
            case R.id.get_test_code_cp:
                if (!newPhoneNumber.getText().toString().equals("") && newPhoneNumber.getText().toString().length() == 11 && !newPhoneNumber.getText().toString().contains(" ") && newPhoneNumber.getText().toString().matches("[0-9]+")) {
                    getTestCodeCp.setClickable(false);
                    countDownTimer.start();
                    String phone = newPhoneNumber.getText().toString();
                    presenter.getVerificationCode(phone, 3);
                } else {
                    SystemUtils.show(this, "请重新输入手机号");
                }
                break;
            case R.id.commit_new_phone:
                if (ifNext()) {
                    //提交新手机号
                    CountEvent Event_040402 = new CountEvent(MethodCode.A040402);
                    JAnalyticsInterface.onEvent(this, Event_040402);
                    presenter.changePhone(presenter.getSerial_number(), testCodeCp.getText().toString(), newPhoneNumber.getText().toString());
                } else {
                    SystemUtils.show(this, "输入有误,请重新检查");
                }
                break;
        }
    }

    private boolean ifNext() {
        if (newPhoneNumber.getText().toString().equals("") || newPhoneNumber.getText().toString().length() != 11 || newPhoneNumber.getText().toString().contains(" ") || testCodeCp.getText().toString().contains(" ") || testCodeCp.getText().toString().equals("")) {
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
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_CHANGEPHONE) {
            application.getSystemUtils().setPhone(newPhoneNumber.getText().toString());
            finish();
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
}
