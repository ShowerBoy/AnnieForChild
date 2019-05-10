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
import com.annie.annieforchild.bean.net.NetGift;
import com.annie.annieforchild.bean.search.BookClassify;
import com.annie.annieforchild.bean.search.Books;
import com.annie.annieforchild.bean.search.SearchContent;
import com.annie.annieforchild.interactor.LoginInteractor;
import com.annie.annieforchild.interactor.imp.LoginInteractorImp;
import com.annie.annieforchild.presenter.LoginPresenter;
import com.annie.annieforchild.ui.activity.login.LoginActivity;
import com.annie.annieforchild.ui.application.MyApplication;
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
import org.litepal.crud.LitePalSupport;

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
    private MyApplication application;
    private int origin;
//    private Timer timer;
//    private TimerTask task;

    public LoginPresenterImp(Context context, LoginView loginView) {
        this.context = context;
        this.loginView = loginView;
        application = (MyApplication) context.getApplicationContext();
    }

    public LoginPresenterImp(Context context, ViewInfo viewInfo) {
        this.context = context;
        this.viewInfo = viewInfo;
        application = (MyApplication) context.getApplicationContext();
    }

    @Override
    public void initViewAndData() {
        interactor = new LoginInteractorImp(context, this);
        ageList = new ArrayList<>();
        functionList = new ArrayList<>();
        themeList = new ArrayList<>();
        typeList = new ArrayList<>();
        seriesList = new ArrayList<>();
        application.getSystemUtils().setTimer(new Timer());
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
    public void showGifts(int origin, int giftRecordId) {
        this.origin = origin;
        interactor.showGifts(origin, giftRecordId);
    }

    @Override
    public void chooseGift(int giftId, int giftRecordId) {
        interactor.chooseGift(giftId, giftRecordId);
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
                } else if (bean.getErrType() == 2) {
                    //升级
                    bean.save();
                } else if (bean.getErrType() == 4) {
                    //更新接口地址
                    List<MainBean> lists = LitePal.findAll(MainBean.class);
                    if (lists != null && lists.size() != 0) {
                        MainBean bean1 = lists.get(lists.size() - 1);
                        bean1.setData(bean.getData());
                        bean1.save();
                        application.getSystemUtils().setMainBean(bean1);
                    }
                } else {
                    //成功
                    SQLiteDatabase db = LitePal.getDatabase();
//                      DataSupport.deleteAll(SigninBean.class);
                    try {
                        LoginBean bean1 = JSON.parseObject(bean.getData(), LoginBean.class);
                        if (bean1 == null) {
                            SystemUtils.show(context, "无数据");
                            return;
                        }
                        if (bean1.getDefaultUsername() == null) {
                            SystemUtils.show(context, "没有学员");
                            return;
                        }
                        List<LoginBean> loginList = LitePal.findAll(LoginBean.class);
                        if (loginList != null && loginList.size() != 0) {
                            boolean flag = true;
                            for (int i = 0; i < loginList.size(); i++) {
                                if (loginList.get(i).getDefaultUsername().equals(bean1.getDefaultUsername())) {
                                    flag = false;
                                }
                            }
                            if (flag) {
                                bean1.save();
                            }
                        } else {
                            bean1.save();
                        }
                        application.getSystemUtils().setToken(bean1.getToken());
                        application.getSystemUtils().setDefaultUsername(bean1.getDefaultUsername());
                        if (bean1.getDefaultUsername().equals("")) {
                            application.getSystemUtils().setChildTag(0);
                            application.getSystemUtils().setOnline(false);
                        } else {
                            application.getSystemUtils().setChildTag(1);
                            application.getSystemUtils().setOnline(true);
                            //在线得花蜜

                            List<SigninBean> list = LitePal.where("username = ?", application.getSystemUtils().getDefaultUsername()).find(SigninBean.class);
//                            DataSupport.findBySQL("select * from Signin ")
                            if (list != null && list.size() != 0) {
                                SigninBean signinBean = list.get(list.size() - 1);
                                String date = application.getSystemUtils().getNetDate();
                                if (date != null) {
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
                                }
                            } else {
                                application.getSystemUtils().setSigninBean(new SigninBean());
                                String date = application.getSystemUtils().getNetDate();
                                application.getSystemUtils().getSigninBean().setDate(date != null ? date : "");
                                application.getSystemUtils().getSigninBean().setUsername(application.getSystemUtils().getDefaultUsername());
                                application.getSystemUtils().getSigninBean().setNectar(false);
                                application.getSystemUtils().getSigninBean().save();
                            }

                            //获取网课礼包
//                            showGifts(1, 0);
                        }
                        /**
                         * {@link LoginActivity#onEventMainThread(JTMessage)}
                         */
                        JTMessage jtMessage = new JTMessage();
                        jtMessage.setWhat(MethodCode.EVENT_LOGIN);
                        jtMessage.setObj(bean1);
                        EventBus.getDefault().post(jtMessage);

                        if (application.getSystemUtils().isOnline()) {
                            if (application.getSystemUtils().getSigninBean() == null) {
                                application.getSystemUtils().setSigninBean(new SigninBean());
                                String date = application.getSystemUtils().getNetDate();
                                application.getSystemUtils().getSigninBean().setDate(date != null ? date : "");
                                application.getSystemUtils().getSigninBean().setUsername(application.getSystemUtils().getDefaultUsername());
                                application.getSystemUtils().getSigninBean().setNectar(false);
                                application.getSystemUtils().getSigninBean().save();
                            }
                            if (!application.getSystemUtils().getSigninBean().isNectar()) {
                                application.getSystemUtils().setTask(new TimerTask() {
                                    @Override
                                    public void run() {
                                        Intent intent = new Intent();
                                        intent.setAction("countdown");
                                        context.sendBroadcast(intent);
                                    }
                                });
                                Runnable runnable = new Runnable() {
                                    @Override
                                    public void run() {
                                        if (!application.getSystemUtils().getSigninBean().isNectar()) {
                                            if (application.getSystemUtils().getTask() == null) {
                                                application.getSystemUtils().setTask(new TimerTask() {
                                                    @Override
                                                    public void run() {
                                                        Intent intent = new Intent();
                                                        intent.setAction("countdown");
                                                        context.sendBroadcast(intent);
                                                    }
                                                });
                                            }
                                            if (application.getSystemUtils().getTimer() == null) {
                                                application.getSystemUtils().setTimer(new Timer());
                                            }
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
            } else if (what == MethodCode.EVENT_SHOWGIFTS + 110000 + origin) {
                NetGift netGift = (NetGift) result;
                /**
                 * {@link com.annie.annieforchild.ui.activity.MainActivity#onMainEventThread(JTMessage)}
                 * {@link com.annie.annieforchild.ui.fragment.message.NoticeFragment#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = netGift;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_CHOOSEGIFT) {
                /**
                 * {@link com.annie.annieforchild.ui.activity.MainActivity#onMainEventThread(JTMessage)}
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
            application.getSystemUtils().setOnline(false);
        }
    }
}
