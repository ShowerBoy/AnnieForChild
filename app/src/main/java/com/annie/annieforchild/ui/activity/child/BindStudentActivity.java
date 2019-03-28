package com.annie.annieforchild.ui.activity.child;

import android.app.Dialog;
import android.os.CountDownTimer;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.CheckDoubleClickListener;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.OnCheckDoubleClick;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.SerialBean;
import com.annie.annieforchild.presenter.RegisterPresenter;
import com.annie.annieforchild.presenter.imp.RegisterPresenterImp;
import com.annie.annieforchild.view.BindStudentView;
import com.annie.annieforchild.view.RegisterView;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;

import org.greenrobot.eventbus.Subscribe;

/**
 * 绑定已有学员
 * Created by WangLei on 2018/2/7 0007
 */

public class BindStudentActivity extends BaseActivity implements RegisterView, OnCheckDoubleClick {
    private ImageView bindBack;
    private EditText username, code;
    private TextView testCode, bindPhone;
    private Button bindBtn;
    private CheckDoubleClickListener listener;
    private AlertHelper helper;
    private Dialog dialog;
    private CountDownTimer countDownTimer;
    private RegisterPresenter presenter;
    private String usernameText, serialNumber;
    private SerialBean serialBean;

    {
        setRegister(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bind_student;
    }

    @Override
    protected void initView() {
        listener = new CheckDoubleClickListener(this);
        bindBack = findViewById(R.id.bind_student_back);
        testCode = findViewById(R.id.get_test_code);
        username = findViewById(R.id.username_edit);
        code = findViewById(R.id.test_code);
        bindBtn = findViewById(R.id.bind_btn);
        bindPhone = findViewById(R.id.bind_student_phone);
        bindBack.setOnClickListener(listener);
        testCode.setOnClickListener(listener);
        bindBtn.setOnClickListener(listener);
    }

    @Override
    protected void initData() {
        helper = new AlertHelper(this);
        dialog = helper.LoadingDialog();
        presenter = new RegisterPresenterImp(this, this);
        presenter.initViewAndData();
        countDownTimer = new CountDownTimer(60 * 1000, 1000) {
            @Override
            public void onTick(long l) {
//                getTestCode.setTextSize(12);
                testCode.setText(l / 1000 + "s后重新发送");
            }

            @Override
            public void onFinish() {
//                getTestCode.setTextSize(14);
                testCode.setText("获取验证码");
                testCode.setClickable(true);
            }
        };
    }

    @Override
    public void onCheckDoubleClick(View view) {
        switch (view.getId()) {
            case R.id.bind_student_back:
                finish();
                break;
            case R.id.get_test_code:
                if (username.getText() != null && !username.getText().toString().equals("") && username.getText().toString().length() != 0 && username.getText().toString().matches("[0-9]+")) {
                    testCode.setClickable(false);
                    countDownTimer.start();
                    usernameText = username.getText().toString().trim();
                    presenter.getBindVerificationCode(usernameText);
                } else {
                    showInfo("请输入学员ID");
                }
                break;
            case R.id.bind_btn:
                if (ifNext()) {
                    presenter.bindStudent(username.getText().toString().trim(), code.getText().toString().trim(), serialNumber);
                } else {
                    showInfo("输入有误,请重新检查");
                }
                break;
        }
    }

    private boolean ifNext() {
        if (username.getText() == null || username.getText().toString().equals("") || username.getText().toString().length() == 0
                || code.getText() == null || code.getText().toString().equals("") || code.getText().toString().length() == 0) {
            return false;
        } else {
            return true;
        }
    }

    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_GETBINDVERIFICATIONCODE) {
            serialBean = (SerialBean) message.obj;
            if (serialBean != null) {
                bindPhone.setText("已发送到" + serialBean.getPhone() != null ? serialBean.getPhone() : "");
                serialNumber = serialBean.getSerialNumber();
            }
        } else if (message.what == MethodCode.EVENT_BINDSTUDENT) {
            String result = (String) message.obj;
            if (result != null) {
                if (result.equals("0")) {
                    showInfo("绑定失败");
                } else {
                    showInfo("绑定成功");
                }
            }
        }
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
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
        username.requestFocus();
    }
}
