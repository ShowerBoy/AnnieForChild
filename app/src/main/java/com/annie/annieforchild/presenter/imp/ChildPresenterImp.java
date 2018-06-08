package com.annie.annieforchild.presenter.imp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.test.ActivityTestCase;
import android.view.View;

import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.interactor.ChildInteractor;
import com.annie.annieforchild.interactor.imp.ChildInteractorImp;
import com.annie.annieforchild.presenter.ChildPresenter;
import com.annie.annieforchild.ui.activity.login.LoginActivity;
import com.annie.annieforchild.view.AddChildView;
import com.annie.annieforchild.view.info.ViewInfo;
import com.annie.baselibrary.base.BasePresenterImp;

import org.greenrobot.eventbus.EventBus;

import java.lang.invoke.MethodHandle;

/**
 * Created by WangLei on 2018/3/2 0002
 */

public class ChildPresenterImp extends BasePresenterImp implements ChildPresenter {
    private Context context;
    private ChildInteractor interactor;
    private AddChildView viewInfo;
    private int tag;

    public ChildPresenterImp(Context context, AddChildView viewInfo) {
        this.context = context;
        this.viewInfo = viewInfo;
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
    public void addChild(String headpic, String name, String sex, String birthday) {
        viewInfo.showLoad();
        interactor.addChild(headpic, name, sex, birthday);
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
    public void motifyChild(String avatar, String name, String sex, String birthday) {
        viewInfo.showLoad();
        interactor.motifyChild(avatar, name, sex, birthday);
    }


    @Override
    public void Success(int what, Object result) {
        viewInfo.dismissLoad();
        if (result != null) {
            if (what == MethodCode.EVENT_ADDCHILD) {
                if (viewInfo.getTag() == 0) {
                    viewInfo.showInfo((String) result);
                    /**
                     * {@link com.annie.annieforchild.ui.activity.child.AddChildActivity#onEventMainThread(JTMessage)}
                     */
                    JTMessage message = new JTMessage();
                    message.what = what;
                    message.obj = result;
                    EventBus.getDefault().post(message);
                } else {
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
    public void Error(int what, String error) {
        viewInfo.dismissLoad();
        viewInfo.showInfo(error);
        if (what == MethodCode.EVENT_UPDATEUSER) {
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
}
