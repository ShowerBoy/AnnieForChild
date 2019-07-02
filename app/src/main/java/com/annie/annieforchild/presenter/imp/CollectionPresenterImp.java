package com.annie.annieforchild.presenter.imp;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.annie.annieforchild.Utils.ActivityCollector;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.Collection;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.interactor.CollectionInteractor;
import com.annie.annieforchild.interactor.imp.CollectionInteractorImp;
import com.annie.annieforchild.presenter.CollectionPresenter;
import com.annie.annieforchild.ui.activity.login.LoginActivity;
import com.annie.annieforchild.ui.application.MyApplication;
import com.annie.annieforchild.view.info.ViewInfo;
import com.annie.baselibrary.base.BasePresenterImp;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_MULTI_PROCESS;
import static android.content.Context.MODE_PRIVATE;

/**
 * 收藏
 * Created by WangLei on 2018/3/6 0006
 */

public class CollectionPresenterImp extends BasePresenterImp implements CollectionPresenter {
    private Context context;
    private ViewInfo collectionView;
    private int classId;
    private MyApplication application;
    private CollectionInteractor interactor;

    public CollectionPresenterImp(Context context, ViewInfo collectionView) {
        this.context = context;
        this.collectionView = collectionView;
        application = (MyApplication) context.getApplicationContext();
    }

    @Override
    public void initViewAndData() {
        interactor = new CollectionInteractorImp(context, this);
    }

    /**
     * 我的收藏  1:磨耳朵 2:阅读 3:口语
     *
     * @param type
     */
    @Override
    public void getMyCollections(int type) {
        interactor.getMyCollections(type);
    }

    /**
     * 取消收藏
     *
     * @param type
     * @param courseId
     */
    @Override
    public void cancelCollection(int type, int audioSource, int courseId) {
        collectionView.showLoad();
        interactor.cancelCollection(type, audioSource, courseId);
    }

    @Override
    public void Success(int what, Object result) {
        collectionView.dismissLoad();
        if (result != null) {
            if (what == MethodCode.EVENT_MYCOLLECTIONS1) {
                /**
                 * {@link com.annie.annieforchild.ui.fragment.collection.GrindEarFragment#onMainEventThread(JTMessage)}
                 * {@link com.annie.annieforchild.ui.activity.grindEar.GrindEarActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = result;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_MYCOLLECTIONS2) {
                /**
                 * {@link com.annie.annieforchild.ui.fragment.collection.ReadingFragment#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = result;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_MYCOLLECTIONS3) {
                /**
                 * {@link com.annie.annieforchild.ui.fragment.collection.SpokenFragment#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = result;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_MYCOLLECTIONS0) {
                /**
                 * {@link com.annie.annieforchild.ui.fragment.collection.OtherFragment#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = result;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_CANCELCOLLECTION1) {
                collectionView.dismissLoad();
                /**
                 * {@link com.annie.annieforchild.ui.fragment.collection.GrindEarFragment#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = result;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_CANCELCOLLECTION2) {
                collectionView.dismissLoad();
                /**
                 * {@link com.annie.annieforchild.ui.fragment.collection.ReadingFragment#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = result;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_CANCELCOLLECTION3) {
                collectionView.dismissLoad();
                /**
                 * {@link com.annie.annieforchild.ui.fragment.collection.SpokenFragment#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = result;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_CANCELCOLLECTION0) {
                collectionView.dismissLoad();
                /**
                 * {@link com.annie.annieforchild.ui.fragment.collection.OtherFragment#onMainEventThread(JTMessage)}
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
        collectionView.dismissLoad();
        if (status == 1) {
            //该账号已在别处登陆
            if (!application.getSystemUtils().isReLogin()) {
                application.getSystemUtils().setReLogin(true);
                collectionView.showInfo(error);

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
            Toast.makeText(application.getApplicationContext(), error, Toast.LENGTH_SHORT).show();
            SystemUtils.setDefaltSn(context, application);
        } else if (status == 4) {
            //服务器错误

        } else if (status == 5) {
            //账号或密码错误

        } else if (status == 6) {
            //获取验证码失败

        } else if (status == 7) {
            //通用错误
            Toast.makeText(application.getApplicationContext(), error, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void Fail(int what, String error) {
        if (collectionView != null) {
            collectionView.dismissLoad();
        }
        Toast.makeText(application.getApplicationContext(), error, Toast.LENGTH_SHORT).show();
    }
}
