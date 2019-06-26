package com.annie.annieforchild.presenter.imp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.annie.annieforchild.Utils.ActivityCollector;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.Utils.service.MusicService;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.SerialBean;
import com.annie.annieforchild.interactor.RegisterInteractor;
import com.annie.annieforchild.interactor.imp.RegisterInteractorImp;
import com.annie.annieforchild.presenter.RegisterPresenter;
import com.annie.annieforchild.ui.activity.login.LoginActivity;
import com.annie.annieforchild.ui.application.MyApplication;
import com.annie.annieforchild.view.RegisterView;
import com.annie.baselibrary.base.BasePresenterImp;
import com.yanzhenjie.nohttp.rest.Response;

import org.greenrobot.eventbus.EventBus;

import static android.content.Context.MODE_MULTI_PROCESS;
import static android.content.Context.MODE_PRIVATE;

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
    public void getBindVerificationCode(String username) {
        registerView.showLoad();
        interactor.getBindVerificationCode(username);
    }

    @Override
    public void bindStudent(String username, String code, String serialNumber, String phone) {
        registerView.showLoad();
        interactor.bindStudent(username, code, serialNumber, phone);
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
            } else if (what == MethodCode.EVENT_GETBINDVERIFICATIONCODE) {
                SerialBean serialBean = (SerialBean) result;
                /**
                 * {@link com.annie.annieforchild.ui.activity.child.BindStudentActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = serialBean;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_BINDSTUDENT) {
                /**
                 * {@link com.annie.annieforchild.ui.activity.child.BindStudentActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = result;
                EventBus.getDefault().post(message);
            }
        }
    }

    @Override
    public void Error(int what, int status, String error) {
        registerView.dismissLoad();
//        registerView.showInfo(error);
        if (status == 1) {
            //该账号已在别处登陆
            if (!application.getSystemUtils().isReLogin()) {
                application.getSystemUtils().setReLogin(true);
                registerView.showInfo(error);
                if (MusicService.isPlay) {
                    MusicService.stop();
                }
                MusicService.musicTitle = null;
                MusicService.musicImageUrl = null;
                SharedPreferences preferences = context.getSharedPreferences("userInfo", MODE_PRIVATE | MODE_MULTI_PROCESS);
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
                ActivityCollector.finishAll();
                Intent intent2 = new Intent(context, LoginActivity.class);
                context.startActivity(intent2);
                return;
            } else {
                return;
            }
        } else if (status == 2) {
            //升级

        } else if (status == 3) {
            //参数错误
            Toast.makeText(application.getApplicationContext(), error, Toast.LENGTH_SHORT).show();
        } else if (status == 4) {
            //服务器错误

        } else if (status == 5) {
            //账号或密码错误

        } else if (status == 6) {
            //获取验证码失败

        } else if (status == 7) {
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
            } else if (what == MethodCode.EVENT_BINDSTUDENT) {
                registerView.showInfo(error);
            } else if (what == MethodCode.EVENT_GETBINDVERIFICATIONCODE) {
                registerView.showInfo(error);
            } else if (what == MethodCode.EVENT_CHANGEPHONE) {
                registerView.showInfo(error);
            }
        }

    }

    @Override
    public void Fail(int what, String error) {
        if (registerView != null) {
            registerView.dismissLoad();
        }
        Toast.makeText(application.getApplicationContext(), error, Toast.LENGTH_SHORT).show();
    }
}
