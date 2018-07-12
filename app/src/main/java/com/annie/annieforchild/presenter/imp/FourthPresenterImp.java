package com.annie.annieforchild.presenter.imp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;

import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.UserInfo2;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.UserInfo;
import com.annie.annieforchild.bean.login.SigninBean;
import com.annie.annieforchild.interactor.FourthInteractor;
import com.annie.annieforchild.interactor.imp.FourthInteractorImp;
import com.annie.annieforchild.presenter.FourthPresenter;
import com.annie.annieforchild.ui.adapter.MemberAdapter;
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

    public FourthPresenterImp(Context context, FourthView fourthView, String tag) {
        this.context = context;
        this.fourthView = fourthView;
        this.tag = tag;
    }

    @Override
    public void initViewAndData() {
        interactor = new FourthInteractorImp(context, this);
        lists = new ArrayList<>();
        adapter = new MemberAdapter(context, lists, tag, new OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view) {
                position = fourthView.getMemberRecycler().getChildAdapterPosition(view);
                if (lists.get(position).getUsername().equals(SystemUtils.defaultUsername)) {
                    return;
                }
                SystemUtils.GeneralDialog(context, "切换默认学员")
                        .setMessage("切换当前学员为默认学员？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                setDefaultUser(lists.get(position).getUsername());
                                SystemUtils.getNetTime();
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
                position = fourthView.getMemberRecycler().getChildAdapterPosition(view);
                if (lists.get(position).getUsername().equals(SystemUtils.defaultUsername)) {
                    fourthView.showInfo("默认学员不能删除！");
                    return;
                }
                SystemUtils.GeneralDialog(context, "删除学员")
                        .setMessage("是否删除当前选中学员？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
//                                setDefaultUser(lists.get(position).getUsername());
                                deleteUsername(lists.get(position).getUsername());
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
        });
        fourthView.getMemberRecycler().setAdapter(adapter);
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
    public void Success(int what, Object result) {
        fourthView.dismissLoad();
        if (result != null) {
            if (what == MethodCode.EVENT_USERINFO) {
                UserInfo info = (UserInfo) result;
                SystemUtils.userInfo = info;
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
                SystemUtils.defaultUsername = lists.get(position).getUsername();
                getUserInfo();
                fourthView.showInfo((String) result);
                //切换用户重置在线
                List<SigninBean> list = DataSupport.where("username = ?", SystemUtils.defaultUsername).find(SigninBean.class);
//                List<SigninBean> list = DataSupport.findAll(SigninBean.class);
                if (list != null && list.size() != 0) {
                    SigninBean signinBean = list.get(list.size() - 1);
                    String date = SystemUtils.netDate;
                    if (!date.equals(signinBean.getDate())) {
                        if (SystemUtils.signinBean == null) {
                            SystemUtils.signinBean = new SigninBean();
                        }
                        SystemUtils.signinBean.setDate(date);
                        SystemUtils.signinBean.setUsername(SystemUtils.defaultUsername);
                        SystemUtils.signinBean.setNectar(false);
                        SystemUtils.signinBean.save();
                    } else {
                        SystemUtils.signinBean = signinBean;
                    }
                } else {
                    SystemUtils.signinBean = new SigninBean();
                    String date = SystemUtils.netDate;
                    SystemUtils.signinBean.setDate(date != null ? date : "");
                    SystemUtils.signinBean.setUsername(SystemUtils.defaultUsername);
                    SystemUtils.signinBean.setNectar(false);
                    SystemUtils.signinBean.save();
                }
                if (SystemUtils.timer != null) {
                    SystemUtils.timer.cancel();
                    SystemUtils.timer = null;
                }
                if (SystemUtils.task != null) {
                    SystemUtils.task.cancel();
                    SystemUtils.task = null;
                }
                SystemUtils.timer = new Timer();
                if (SystemUtils.isOnline) {
                    if (!SystemUtils.signinBean.isNectar()) {
                        SystemUtils.task = new TimerTask() {
                            @Override
                            public void run() {
                                if (!SystemUtils.signinBean.isNectar()) {
                                    Intent intent = new Intent();
                                    intent.setAction("countdown");
                                    context.sendBroadcast(intent);
                                }
                            }
                        };
                        Runnable runnable = new Runnable() {
                            @Override
                            public void run() {
                                if (!SystemUtils.signinBean.isNectar()) {
                                    SystemUtils.timer.schedule(SystemUtils.task, 120 * 1000);
                                }
                            }
                        };
                        SystemUtils.countDownThread = new Thread(runnable);
                        SystemUtils.countDownThread.start();
                    }
                }

            } else if (what == MethodCode.EVENT_DELETEUSERNAME) {
                fourthView.showInfo((String) result);
                /**
                 * {@link com.annie.annieforchild.ui.fragment.FourthFragment#onMainEventThread(JTMessage)}
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
