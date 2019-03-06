package com.annie.annieforchild.presenter.imp;

import android.content.Context;
import android.util.Log;

import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.interactor.RegisterInteractor;
import com.annie.annieforchild.interactor.imp.RegisterInteractorImp;
import com.annie.annieforchild.presenter.RegisterPresenter;
import com.annie.annieforchild.ui.application.MyApplication;
import com.annie.annieforchild.view.RegisterView;
import com.annie.baselibrary.base.BasePresenterImp;
import com.yanzhenjie.nohttp.rest.Response;

import org.greenrobot.eventbus.EventBus;

/**
 * 注册
 * Created by WangLei on 2018/1/29 0029
 */

public class RegisterPresenterImp extends BasePresenterImp implements RegisterPresenter {
    private Context context;
    private RegisterView registerView;
    private RegisterInteractor interactor;
    private String serial_number; //流水号
    private MyApplication application;

    public RegisterPresenterImp(Context context) {
        this.context = context;
        application = (MyApplication) context.getApplicationContext();
    }

    public RegisterPresenterImp(Context context, RegisterView registerView) {
        this.context = context;
        this.registerView = registerView;
        application = (MyApplication) context.getApplicationContext();
    }

    @Override
    public void initViewAndData() {
        interactor = new RegisterInteractorImp(context, this);
    }

    @Override
    public void getVerificationCode(String phone, int type) {
        interactor.getVerificationCode(phone, type);
    }

    @Override
    public void register(String phone, String code, String password) {
        if (serial_number == null) {
            registerView.showInfo("请输入正确的验证码");
        } else {
            registerView.showLoad();
            interactor.register(phone, code, password, serial_number);
        }

    }

    @Override
    public void changePhone(String serialNumber, String code, String newPhone) {
        registerView.showLoad();
        interactor.changePhone(serialNumber, code, newPhone);
    }

    @Override
    public void resetPassword(String phone, String code, String password, String serialNumber) {
        registerView.showLoad();
        interactor.resetPassword(phone, code, password, serialNumber);
    }

    @Override
    public String getSerial_number() {
        return serial_number;
    }


    @Override
    public void Success(int what, Object result) {
        registerView.dismissLoad();
        if (result != null) {
            if (what == MethodCode.EVENT_VERIFICATION_CODE) {
                serial_number = (String) result;
            } else if (what == MethodCode.EVENT_RGISTER) {
                registerView.showInfo("注册成功");
                application.getSystemUtils().setToken((String) result);
                /**
                 * {@link com.annie.annieforchild.ui.activity.login.RegisterActivity#onEventMainThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.setWhat(what);
                message.setObj("注册成功");
                EventBus.getDefault().post(message);

            } else if (what == MethodCode.EVENT_CHANGEPHONE) {
                registerView.showInfo((String) result);
                /**
                 * {@link com.annie.annieforchild.ui.activity.my.ChangePhoneActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.setWhat(what);
                message.setObj("修改成功");
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_RESETPASSWORD) {
                registerView.dismissLoad();
                /**
                 * {@link com.annie.annieforchild.ui.activity.login.ModifyPsdActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = result;
                EventBus.getDefault().post(message);
            }
        }
    }

    @Override
    public void Error(int what, String error) {
        registerView.dismissLoad();
//        registerView.showInfo(error);
        if (what == MethodCode.EVENT_RESETPASSWORD) {
            /**
             * {@link com.annie.annieforchild.ui.activity.login.ModifyPsdActivity#onMainEventThread(JTMessage)}
             */
            JTMessage message = new JTMessage();
            message.what = what;
            message.obj = error;
            EventBus.getDefault().post(message);
        } else if (what == MethodCode.EVENT_RGISTER) {
            registerView.showInfo(error);
        }
    }
}
