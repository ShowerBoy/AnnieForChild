package com.annie.annieforchild.presenter.imp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.annie.annieforchild.Utils.ActivityCollector;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.interactor.ChildInteractor;
import com.annie.annieforchild.interactor.imp.ChildInteractorImp;
import com.annie.annieforchild.presenter.ChildPresenter;
import com.annie.annieforchild.ui.activity.MainActivity;
import com.annie.annieforchild.ui.activity.login.LoginActivity;
import com.annie.annieforchild.ui.activity.my.SettingsActivity;
import com.annie.annieforchild.ui.application.MyApplication;
import com.annie.annieforchild.view.AddChildView;
import com.annie.annieforchild.view.info.ViewInfo;
import com.annie.baselibrary.base.BasePresenterImp;

import org.greenrobot.eventbus.EventBus;

import java.lang.invoke.MethodHandle;

import static android.content.Context.MODE_MULTI_PROCESS;
import static android.content.Context.MODE_PRIVATE;

/**
 * Created by WangLei on 2018/3/2 0002
 */

public class ChildPresenterImp extends BasePresenterImp implements ChildPresenter {
    private Context context;
    private ChildInteractor interactor;
    private AddChildView viewInfo;
    private int tag;
    private MyApplication application;

    public ChildPresenterImp(Context context, AddChildView viewInfo) {
        this.context = context;
        this.viewInfo = viewInfo;
        application = (MyApplication) context.getApplicationContext();
    }

    @Override
    public void initViewAndData() {
        interactor = new ChildInteractorImp(context, this);
    }

    /**
     * 上传头像
     *
     * @param tag  0:AddChildActivity 1:ModifyChildActivity
     * @param path
     */
    @Override
    public void uploadHeadpic(int tag, String path) {
        this.tag = tag;
        viewInfo.showLoad();
        interactor.uploadHeadpic(path);
    }

    /**
     * 添加学员
     *
     * @param headpic
     * @param name
     * @param sex
     * @param birthday
     */
    @Override
    public void addChild(String headpic, String name, String sex, String birthday, String phone) {
        viewInfo.showLoad();
        interactor.addChild(headpic, name, sex, birthday, phone);
    }

    /**
     * 修改学员信息
     *
     * @param avatar
     * @param name
     * @param sex
     * @param birthday
     */
    @Override
    public void motifyChild(String avatar, String name, String sex, String birthday, String WechatNickname, String wechatNum, String BusinessCard) {
        viewInfo.showLoad();
        interactor.motifyChild(avatar, name, sex, birthday, WechatNickname, wechatNum, BusinessCard);
    }


    @Override
    public void Success(int what, Object result) {
        viewInfo.dismissLoad();
        if (result != null) {
            if (what == MethodCode.EVENT_ADDCHILD) {
                if (viewInfo.getTag() == 0) {
//                    viewInfo.showInfo((String) result);
                    /**
                     * {@link com.annie.annieforchild.ui.activity.child.AddChildActivity#onEventMainThread(JTMessage)}
                     */
                    JTMessage message = new JTMessage();
                    message.what = what;
                    message.obj = result;
                    EventBus.getDefault().post(message);
                } else {
                    if (application.getSystemUtils().getChildTag() == 0) {
                        application.getSystemUtils().setDefaultUsername((String) result);
                        application.getSystemUtils().setChildTag(1);
                    }
                    /**
                     * {@link com.annie.annieforchild.ui.activity.child.AddChildActivity#onEventMainThread(JTMessage)}
                     * {@link com.annie.annieforchild.ui.fragment.FourthFragment#onMainEventThread(JTMessage)}
                     */
                    JTMessage message1 = new JTMessage();
                    message1.what = MethodCode.EVENT_ADDCHILD2;
                    message1.obj = result;
                    EventBus.getDefault().post(message1);
                }
            } else if (what == MethodCode.EVENT_UPDATEUSER) {
                viewInfo.showInfo((String) result);
                /**
                 * {@link com.annie.annieforchild.ui.fragment.FourthFragment#onMainEventThread(JTMessage)}
                 * {@link com.annie.annieforchild.ui.activity.child.ModifyChildActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage message2 = new JTMessage();
                message2.what = what;
                message2.obj = result;
                EventBus.getDefault().post(message2);
            } else if (what == MethodCode.EVENT_UPLOADAVATAR) {
                /**
                 * {@link com.annie.annieforchild.ui.activity.child.AddChildActivity#onEventMainThread(JTMessage)}
                 * {@link com.annie.annieforchild.ui.activity.child.ModifyChildActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage message2 = new JTMessage();
                message2.what = what + tag;
                message2.obj = result;
                EventBus.getDefault().post(message2);
            }
        }
    }

    @Override
    public void Error(int what, int status, String error) {
        viewInfo.dismissLoad();
        if (status == 1) {
            //该账号已在别处登陆
            if (!application.getSystemUtils().isReLogin()) {
                application.getSystemUtils().setReLogin(true);
                viewInfo.showInfo(error);

                JTMessage message = new JTMessage();
                message.what = MethodCode.EVENT_RELOGIN;
                message.obj = 1;
                EventBus.getDefault().post(message);

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
            SystemUtils.setDefaltSn(context, application);
        } else if (status == 4) {
            //服务器错误

        } else if (status == 5) {
            //账号或密码错误

        } else if (status == 6) {
            //获取验证码失败

        } else if (status == 7) {
            //通用错误
            viewInfo.showInfo(error);
            /**
             * {@link com.annie.annieforchild.ui.fragment.FourthFragment#onMainEventThread(JTMessage)}
             *  {@link com.annie.annieforchild.ui.activity.child.ModifyChildActivity#onMainEventThread(JTMessage)}
             */
            JTMessage message2 = new JTMessage();
            message2.what = what;
            message2.obj = error;
            EventBus.getDefault().post(message2);
        }

    }

    @Override
    public void Fail(int what, String error) {
        if (viewInfo != null) {
            viewInfo.dismissLoad();
        }
        Toast.makeText(application.getApplicationContext(), error, Toast.LENGTH_SHORT).show();
    }
}
