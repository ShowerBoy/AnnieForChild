package com.annie.annieforchild.presenter.imp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;

import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.Utils.service.MusicService;
import com.annie.annieforchild.bean.UserInfo2;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.UserInfo;
import com.annie.annieforchild.bean.login.SigninBean;
import com.annie.annieforchild.interactor.FourthInteractor;
import com.annie.annieforchild.interactor.imp.FourthInteractorImp;
import com.annie.annieforchild.presenter.FourthPresenter;
import com.annie.annieforchild.ui.adapter.MemberAdapter;
import com.annie.annieforchild.ui.application.MyApplication;
import com.annie.annieforchild.ui.interfaces.OnRecyclerItemClickListener;
import com.annie.annieforchild.view.FourthView;
import com.annie.baselibrary.base.BasePresenterImp;

import org.greenrobot.eventbus.EventBus;
import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 我的
 * Created by WangLei on 2018/2/8 0008
 */

public class FourthPresenterImp extends BasePresenterImp implements FourthPresenter {
    private Context context;
    private FourthView fourthView;
    private FourthInteractor interactor;
    private List<UserInfo2> lists;
    private MemberAdapter adapter;
    int position;
    private String tag; //身份标示
    private MyApplication application;

    public FourthPresenterImp(Context context, FourthView fourthView, String tag) {
        this.context = context;
        this.fourthView = fourthView;
        this.tag = tag;
        application = (MyApplication) context.getApplicationContext();
    }

    @Override
    public void initViewAndData(int flag) {
        if (flag == 0) {
            interactor = new FourthInteractorImp(context, this);
            lists = new ArrayList<>();
            adapter = new MemberAdapter(context, lists, tag, new OnRecyclerItemClickListener() {
                @Override
                public void onItemClick(View view) {
                    position = fourthView.getMemberRecycler().getChildAdapterPosition(view);
                    if (lists.get(position).getUsername().equals(application.getSystemUtils().getDefaultUsername())) {
                        return;
                    }

                    SystemUtils.GeneralDialog(context, "切换默认学员")
                            .setMessage("切换当前学员为默认学员？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    application.getNetTime();
                                    setDefaultUser(lists.get(position).getUsername());
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            })
                            .show();
                }

                @Override
                public void onItemLongClick(View view) {
//                position = fourthView.getMemberRecycler().getChildAdapterPosition(view);
//                if (lists.get(position).getUsername().equals(SystemUtils.defaultUsername)) {
//                    fourthView.showInfo("默认学员不能删除！");
//                    return;
//                }
//                SystemUtils.GeneralDialog(context, "删除学员")
//                        .setMessage("是否删除当前选中学员？")
//                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                deleteUsername(lists.get(position).getUsername());
//                            }
//                        })
//                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                dialogInterface.dismiss();
//                            }
//                        })
//                        .show();
                }
            });
            fourthView.getMemberRecycler().setAdapter(adapter);
        } else {
            interactor = new FourthInteractorImp(context, this);
        }
    }

    /**
     * 获取学员信息(包含获取学员列表)
     */
    @Override
    public void getUserInfo() {
//        fourthView.showLoad();
        interactor.getUserInfo();
    }

    /**
     * 设置默认学员
     *
     * @param defaultUser
     */
    @Override
    public void setDefaultUser(String defaultUser) {
        fourthView.showLoad();
        interactor.setDefaultUser(defaultUser);
    }

    /**
     * 删除学员
     *
     * @param deleteUsername
     */
    @Override
    public void deleteUsername(String deleteUsername) {
        fourthView.showLoad();
        interactor.deleteUsername(deleteUsername);
    }

    @Override
    public void getUserList() {
//        interactor.getUserList();
    }

    @Override
    public void bindWeixin(String weixinNum) {
        fourthView.showLoad();
        interactor.bindWeixin(weixinNum);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void Success(int what, Object result) {
        fourthView.dismissLoad();
        if (result != null) {
            if (what == MethodCode.EVENT_USERINFO) {
                UserInfo info = (UserInfo) result;
                application.getSystemUtils().setUserInfo(info);
                /**
                 * {@link com.annie.annieforchild.ui.fragment.FourthFragment#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.setWhat(what);
                message.setObj(info);
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_USERLIST) {
                lists.clear();
                lists.addAll((List<UserInfo2>) result);
                adapter.notifyDataSetChanged();
            } else if (what == MethodCode.EVENT_SETDEFAULEUSER) {
                if (MusicService.isPlay) {
//                    fourthView.getBinder().bStop();
                    /**
                     * {@link com.annie.annieforchild.ui.fragment.FourthFragment}
                     */
                    JTMessage message = new JTMessage();
                    message.what = MethodCode.EVENT_MUSICSTOP;
                    message.obj = 0;
                    EventBus.getDefault().post(message);
//                    MusicService.stop();
                }
                application.getSystemUtils().setDefaultUsername(lists.get(position).getUsername());
                getUserInfo();
                fourthView.showInfo((String) result);
                SQLiteDatabase db = LitePal.getDatabase();
                try {
                    //切换用户重置在线
                    List<SigninBean> list = LitePal.where("username = ?", application.getSystemUtils().getDefaultUsername()).find(SigninBean.class);
//                List<SigninBean> list = DataSupport.findAll(SigninBean.class);
                    if (list != null && list.size() != 0) {
                        SigninBean signinBean = list.get(list.size() - 1);
                        String date = application.getSystemUtils().getNetDate();
                        if (!date.equals(signinBean.getDate())) {
                            if (application.getSystemUtils().getSigninBean() == null) {
                                application.getSystemUtils().setSigninBean(new SigninBean());
                            }
                            application.getSystemUtils().getSigninBean().setDate(date);
                            application.getSystemUtils().getSigninBean().setUsername(application.getSystemUtils().getDefaultUsername());
                            application.getSystemUtils().getSigninBean().setNectar(false);
                            application.getSystemUtils().getSigninBean().save();
                        } else {
                            application.getSystemUtils().setSigninBean(signinBean);
                        }
                    } else {
                        application.getSystemUtils().setSigninBean(new SigninBean());
                        String date = application.getSystemUtils().getNetDate();
                        application.getSystemUtils().getSigninBean().setDate(date != null ? date : "");
                        application.getSystemUtils().getSigninBean().setUsername(application.getSystemUtils().getDefaultUsername());
                        application.getSystemUtils().getSigninBean().setNectar(false);
                        application.getSystemUtils().getSigninBean().save();
                    }
                    if (application.getSystemUtils().getTimer() != null) {
                        application.getSystemUtils().getTimer().cancel();
                        application.getSystemUtils().setTimer(null);
                    }
                    if (application.getSystemUtils().getTask() != null) {
                        application.getSystemUtils().getTask().cancel();
                        application.getSystemUtils().setTask(null);
                    }
                    application.getSystemUtils().setTimer(new Timer());
                    if (application.getSystemUtils().isOnline()) {
                        if (!application.getSystemUtils().getSigninBean().isNectar()) {
                            application.getSystemUtils().setTask(new TimerTask() {
                                @Override
                                public void run() {
                                    if (!application.getSystemUtils().getSigninBean().isNectar()) {
                                        Intent intent = new Intent();
                                        intent.setAction("countdown");
                                        context.sendBroadcast(intent);
                                    }
                                }
                            });
                            Runnable runnable = new Runnable() {
                                @Override
                                public void run() {
                                    if (!application.getSystemUtils().getSigninBean().isNectar()) {
                                        application.getSystemUtils().getTimer().schedule(application.getSystemUtils().getTask(), 120 * 1000);
                                    }
                                }
                            };
                            application.getSystemUtils().setCountDownThread(new Thread(runnable));
                            application.getSystemUtils().getCountDownThread().start();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    db.close();
                }
                /**
                 * {@link com.annie.annieforchild.ui.fragment.DakaFragment#onMainEventThread(JTMessage)}
                 * {@link com.annie.annieforchild.ui.fragment.FirstFragment#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = result;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_DELETEUSERNAME) {
                fourthView.showInfo((String) result);
                /**
                 * {@link com.annie.annieforchild.ui.fragment.FourthFragment#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = result;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_BINDWEIXIN) {
                /**
                 * {@link com.annie.annieforchild.ui.activity.my.SettingsActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = result;
                EventBus.getDefault().post(message);
            }
        } else {
            fourthView.dismissLoad();
        }
    }

    @Override
    public void Error(int what, String error) {
        /**
         * {@link com.annie.annieforchild.ui.fragment.FourthFragment#onMainEventThread(JTMessage)}
         */
        JTMessage message = new JTMessage();
        message.setWhat(what);
        message.setObj(error);
        EventBus.getDefault().post(message);
        fourthView.dismissLoad();
        fourthView.showInfo(error);
    }
}
