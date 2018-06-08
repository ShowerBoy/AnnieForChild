package com.annie.annieforchild.presenter.imp;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;

import com.alibaba.fastjson.JSON;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.login.LoginBean;
import com.annie.annieforchild.bean.login.MainBean;
import com.annie.annieforchild.bean.search.BookClassify;
import com.annie.annieforchild.interactor.LoginInteractor;
import com.annie.annieforchild.interactor.imp.LoginInteractorImp;
import com.annie.annieforchild.presenter.LoginPresenter;
import com.annie.annieforchild.ui.activity.login.LoginActivity;
import com.annie.annieforchild.view.LoginView;
import com.annie.baselibrary.base.BasePresenterImp;

import org.greenrobot.eventbus.EventBus;
import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * 登陆
 * Created by WangLei on 2018/1/29 0029
 */

public class LoginPresenterImp extends BasePresenterImp implements LoginPresenter {
    Activity activity;
    private Context context;
    private LoginView loginView;
    private LoginInteractor interactor;

    public LoginPresenterImp(Context context, LoginView loginView) {
        this.context = context;
        this.loginView = loginView;
    }

    @Override
    public void initViewAndData() {
        interactor = new LoginInteractorImp(context, this);
    }

//    @Override
//    public void getMainAddress() {
//        interactor.getMainAddress(SystemUtils.sn);
//    }

    @Override
    public void login(String phone, String password, String loginTime) {
        loginView.showLoad();
        interactor.login(phone, password, loginTime);
    }

    @Override
    public void globalSearch(String keyword) {
        loginView.showLoad();
        interactor.globalSearch(keyword);
    }

    @Override
    public void Success(int what, Object result) {
        loginView.dismissLoad();
        if (result != null) {
            if (what == MethodCode.EVENT_LOGIN) {
                MainBean bean = (MainBean) result;
                if (bean.getErrType() == 1) {
                    //重新登录
//                Intent intent = new Intent(context, LoginActivity.class);
//                context.startActivity(intent);
                } else if (bean.getErrType() == 2) {
                    //升级
                    bean.save();
                    SystemUtils.mainBean = bean;
                    SystemUtils.GeneralDialog(context, "升级")
                            .setMessage("检测到更新，升级吗？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            }).create().show();
                } else if (bean.getErrType() == 4) {
                    //更新接口地址
                    List<MainBean> lists = DataSupport.findAll(MainBean.class);
                    if (lists != null && lists.size() != 0) {
                        MainBean bean1 = lists.get(lists.size() - 1);
                        bean1.setData(bean.getData());
                        bean1.save();
                        SystemUtils.mainBean = bean1;
                    }
                } else {
                    //成功
                    if (what == MethodCode.EVENT_MAIN) {
                        bean.save();
                        /**
                         * {@link LoginActivity#onEventMainThread(JTMessage)}
                         */
                        JTMessage jtMessage = new JTMessage();
                        jtMessage.setWhat(MethodCode.EVENT_MAIN);
                        jtMessage.setObj(bean);
                        EventBus.getDefault().post(jtMessage);
                        SystemUtils.mainBean = bean;
                    } else if (what == MethodCode.EVENT_LOGIN) {
                        LoginBean bean1 = JSON.parseObject(bean.getData(), LoginBean.class);
                        bean1.save();
                        SystemUtils.token = bean1.getToken();
                        SystemUtils.defaultUsername = bean1.getDefaultUsername();
                        /**
                         * {@link LoginActivity#onEventMainThread(JTMessage)}
                         */
                        JTMessage jtMessage = new JTMessage();
                        jtMessage.setWhat(MethodCode.EVENT_LOGIN);
                        jtMessage.setObj(bean1);
                        EventBus.getDefault().post(jtMessage);
                    }
                }
            } else if (what == MethodCode.EVENT_GLOBALSEARCH) {
                List<BookClassify> lists = (List<BookClassify>) result;
                /**
                 * {@link com.annie.annieforchild.ui.activity.GlobalSearchActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = lists;
                EventBus.getDefault().post(message);
            }
        }
    }

    @Override
    public void Error(int what, String error) {
        loginView.dismissLoad();
        loginView.showInfo(error);
    }
}
