package com.annie.annieforchild.presenter.imp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.annie.annieforchild.Utils.ActivityCollector;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.service.MusicService;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.interactor.NectarInteractor;
import com.annie.annieforchild.interactor.imp.NectarInteractorImp;
import com.annie.annieforchild.presenter.NectarPresenter;
import com.annie.annieforchild.ui.activity.login.LoginActivity;
import com.annie.annieforchild.ui.application.MyApplication;
import com.annie.annieforchild.view.info.ViewInfo;
import com.annie.baselibrary.base.BasePresenterImp;

import org.greenrobot.eventbus.EventBus;

import static android.content.Context.MODE_MULTI_PROCESS;
import static android.content.Context.MODE_PRIVATE;

/**
 * 花蜜
 * Created by WangLei on 2018/3/7 0007
 */

public class NectarPresenterImp extends BasePresenterImp implements NectarPresenter {
    private Context context;
    private NectarInteractor interactor;
    private ViewInfo viewInfo;
    private MyApplication application;

    public NectarPresenterImp(Context context, ViewInfo viewInfo) {
        this.context = context;
        this.viewInfo = viewInfo;
        application = (MyApplication) context.getApplicationContext();
    }

    @Override
    public void initViewAndData() {
        interactor = new NectarInteractorImp(context, this);
    }

    /**
     * 我的花蜜
     */
    @Override
    public void getNectar() {
        interactor.getNectar();
    }

    @Override
    public void Success(int what, Object result) {
        if (result != null) {
            if (what == MethodCode.EVENT_GETNECTAR) {
                /**
                 * {@link com.annie.annieforchild.ui.fragment.nectar.IncomeFragment#onMainEventThread(JTMessage)}
                 * {@link com.annie.annieforchild.ui.fragment.nectar.OutcomeFragment#onMainEventThread(JTMessage)}
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
        if (status == 1) {
            //该账号已在别处登陆
            if (!application.getSystemUtils().isReLogin()) {
                application.getSystemUtils().setReLogin(true);
                viewInfo.showInfo(error);
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

        } else if (status == 4) {
            //服务器错误

        } else if (status == 5) {
            //账号或密码错误

        } else if (status == 6) {
            //获取验证码失败

        } else if (status == 7) {
            viewInfo.showInfo(error);
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
