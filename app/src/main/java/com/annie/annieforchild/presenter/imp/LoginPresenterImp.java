package com.annie.annieforchild.presenter.imp;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.alibaba.fastjson.JSON;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.SearchTag;
import com.annie.annieforchild.bean.Tags;
import com.annie.annieforchild.bean.UpdateBean;
import com.annie.annieforchild.bean.login.LoginBean;
import com.annie.annieforchild.bean.login.MainBean;
import com.annie.annieforchild.bean.login.SigninBean;
import com.annie.annieforchild.bean.search.BookClassify;
import com.annie.annieforchild.bean.search.Books;
import com.annie.annieforchild.bean.search.SearchContent;
import com.annie.annieforchild.interactor.LoginInteractor;
import com.annie.annieforchild.interactor.imp.LoginInteractorImp;
import com.annie.annieforchild.presenter.LoginPresenter;
import com.annie.annieforchild.ui.activity.login.LoginActivity;
import com.annie.annieforchild.view.LoginView;
import com.annie.annieforchild.view.info.ViewInfo;
import com.annie.baselibrary.base.BasePresenterImp;
import com.yanzhenjie.nohttp.Headers;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.download.DownloadListener;
import com.yanzhenjie.nohttp.download.DownloadQueue;
import com.yanzhenjie.nohttp.download.DownloadRequest;

import org.greenrobot.eventbus.EventBus;
import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 登陆
 * Created by WangLei on 2018/1/29 0029
 */

public class LoginPresenterImp extends BasePresenterImp implements LoginPresenter {
    Activity activity;
    private Context context;
    private LoginView loginView;
    private ViewInfo viewInfo;
    private LoginInteractor interactor;
    private List<Tags> ageList, functionList, themeList, typeList, seriesList;
//    private Timer timer;
//    private TimerTask task;

    public LoginPresenterImp(Context context, LoginView loginView) {
        this.context = context;
        this.loginView = loginView;
    }

    public LoginPresenterImp(Context context, ViewInfo viewInfo) {
        this.context = context;
        this.viewInfo = viewInfo;
    }

    @Override
    public void initViewAndData() {
        interactor = new LoginInteractorImp(context, this);
        ageList = new ArrayList<>();
        functionList = new ArrayList<>();
        themeList = new ArrayList<>();
        typeList = new ArrayList<>();
        seriesList = new ArrayList<>();
        SystemUtils.timer = new Timer();
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
    public void globalSearch(String keyword, int page) {
        loginView.showLoad();
        interactor.globalSearch(keyword, page);
    }

    @Override
    public void checkUpdate(int versionCode, String versionName) {
        interactor.checkUpdate(versionCode, versionName);
    }

    @Override
    public void getTags() {
        interactor.getTags();
    }

    @Override
    public void getTagBook(List<Tags> ageList, List<Tags> functionList, List<Tags> themeList, List<Tags> typeList, List<Tags> seriesList, int page) {
        loginView.showLoad();
        interactor.getTagBook(ageList, functionList, themeList, typeList, seriesList, page);
    }

    @Override
    public void addTags(int i, Tags tags) {
        if (i == 0) {
            if (ageList.contains(tags)) {
                ageList.remove(tags);
            } else {
                ageList.add(tags);
            }
        } else if (i == 1) {
            if (functionList.contains(tags)) {
                functionList.remove(tags);
            } else {
                functionList.add(tags);
            }
        } else if (i == 2) {
            if (themeList.contains(tags)) {
                themeList.remove(tags);
            } else {
                themeList.add(tags);
            }
        } else if (i == 3) {
            if (typeList.contains(tags)) {
                typeList.remove(tags);
            } else {
                typeList.add(tags);
            }
        } else if (i == 4) {
            if (seriesList.contains(tags)) {
                seriesList.remove(tags);
            } else {
                seriesList.add(tags);
            }
        }
    }

    @Override
    public void clearTags() {
        ageList.clear();
        functionList.clear();
        themeList.clear();
        typeList.clear();
        seriesList.clear();
    }

    @Override
    public List<Tags> getAgeList() {
        return ageList;
    }

    @Override
    public List<Tags> getFunctionList() {
        return functionList;
    }

    @Override
    public List<Tags> getThemeList() {
        return themeList;
    }

    @Override
    public List<Tags> getTypeList() {
        return typeList;
    }

    @Override
    public List<Tags> getSeriesList() {
        return seriesList;
    }

    @Override
    public void Success(int what, Object result) {
        if (loginView != null) {
            loginView.dismissLoad();
        }
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
//                    SystemUtils.mainBean = bean;
//                    SystemUtils.GeneralDialog(context, "升级")
//                            .setMessage("检测到更新，升级吗？")
//                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialogInterface, int i) {
//
//                                }
//                            })
//                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialogInterface, int i) {
//                                    dialogInterface.dismiss();
//                                }
//                            }).create().show();
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
                        if (bean1.getDefaultUsername().equals("")) {
                            SystemUtils.childTag = 0;
                            SystemUtils.isOnline = false;
                        } else {
                            SystemUtils.childTag = 1;
                            SystemUtils.isOnline = true;
                            //在线得花蜜
                            SQLiteDatabase db = LitePal.getDatabase();
//                            DataSupport.deleteAll(SigninBean.class);
                            List<SigninBean> list = DataSupport.where("username = ?", SystemUtils.defaultUsername).find(SigninBean.class);
//                            DataSupport.findBySQL("select * from Signin ")
                            if (list != null && list.size() != 0) {
                                SigninBean signinBean = list.get(list.size() - 1);
                                String date = SystemUtils.netDate;
                                if (date != null) {

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
                                }
                            } else {
                                SystemUtils.signinBean = new SigninBean();
                                String date = SystemUtils.netDate;
                                SystemUtils.signinBean.setDate(date != null ? date : "");
                                SystemUtils.signinBean.setUsername(SystemUtils.defaultUsername);
                                SystemUtils.signinBean.setNectar(false);
                                SystemUtils.signinBean.save();
                            }
                        }
                        /**
                         * {@link LoginActivity#onEventMainThread(JTMessage)}
                         */
                        JTMessage jtMessage = new JTMessage();
                        jtMessage.setWhat(MethodCode.EVENT_LOGIN);
                        jtMessage.setObj(bean1);
                        EventBus.getDefault().post(jtMessage);

                        if (SystemUtils.isOnline) {
                            if (SystemUtils.signinBean == null) {
                                SystemUtils.signinBean = new SigninBean();
                                String date = SystemUtils.netDate;
                                SystemUtils.signinBean.setDate(date != null ? date : "");
                                SystemUtils.signinBean.setUsername(SystemUtils.defaultUsername);
                                SystemUtils.signinBean.setNectar(false);
                                SystemUtils.signinBean.save();
                            }
                            if (!SystemUtils.signinBean.isNectar()) {
                                SystemUtils.task = new TimerTask() {
                                    @Override
                                    public void run() {
                                        Intent intent = new Intent();
                                        intent.setAction("countdown");
                                        context.sendBroadcast(intent);
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
                    }
                }
            } else if (what == MethodCode.EVENT_GLOBALSEARCH) {
                SearchContent searchContent = (SearchContent) result;
                /**
                 * {@link com.annie.annieforchild.ui.activity.GlobalSearchActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = searchContent;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_CHECKUPDATE) {
                UpdateBean updateBean = (UpdateBean) result;
                /**
                 * {@link com.annie.annieforchild.ui.activity.MainActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = updateBean;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_GETTAGS) {
                List<SearchTag> lists = (List<SearchTag>) result;
                /**
                 * {@link com.annie.annieforchild.ui.activity.GlobalSearchActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = lists;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_GETTAGBOOK) {
                SearchContent searchContent = (SearchContent) result;
                /**
                 * {@link com.annie.annieforchild.ui.activity.GlobalSearchActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = searchContent;
                EventBus.getDefault().post(message);
            }
        }
    }

    @Override
    public void Error(int what, String error) {
        if (loginView != null) {
            loginView.dismissLoad();
            loginView.showInfo(error);
        }
        if (what == MethodCode.EVENT_LOGIN) {
            /**
             * {@link com.annie.annieforchild.ui.activity.GuideActivity#onMainEventThread(JTMessage)}
             */
            JTMessage message = new JTMessage();
            message.what = MethodCode.EVENT_ERROR;
            message.obj = error;
            EventBus.getDefault().post(message);
            SystemUtils.isOnline = false;
        }
    }
}
