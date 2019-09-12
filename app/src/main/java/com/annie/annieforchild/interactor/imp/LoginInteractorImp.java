package com.annie.annieforchild.interactor.imp;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.MethodType;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.SearchTag;
import com.annie.annieforchild.bean.Tags;
import com.annie.annieforchild.bean.UpdateBean;
import com.annie.annieforchild.bean.login.MainBean;
import com.annie.annieforchild.bean.net.NetGift;
import com.annie.annieforchild.bean.search.SearchContent;
import com.annie.annieforchild.interactor.LoginInteractor;
import com.annie.annieforchild.ui.application.MyApplication;
import com.annie.baselibrary.utils.NetUtils.NetWorkImp;
import com.annie.baselibrary.utils.NetUtils.RequestListener;
import com.annie.baselibrary.utils.NetUtils.request.FastJsonRequest;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * 登陆
 * Created by WangLei on 2018/1/29 0029
 */

public class LoginInteractorImp extends NetWorkImp implements LoginInteractor {
    private Context context;
    private RequestListener listener;
    private MyApplication application;
    private int origin;

    public LoginInteractorImp(Context context, RequestListener listener) {
        this.context = context;
        this.listener = listener;
        application = (MyApplication) context.getApplicationContext();
    }

    @Override
    public void login(String phone, String password, String loginTime) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.SYSTEMAPI + MethodType.LOGIN, RequestMethod.POST);
        request.add("phone", phone);
        request.add("password", password);
        request.add(MethodCode.DEVICEID, application.getSystemUtils().getSn());
        request.add(MethodCode.DEVICETYPE, SystemUtils.deviceType);
        request.add(MethodCode.APPVERSION, SystemUtils.getVersionName(context));
        addQueue(MethodCode.EVENT_LOGIN, request);
//        startQueue();
    }

    @Override
    public void globalSearch(String keyword, int page) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.SEARCHAPI + MethodType.GLOBALSEARCH, RequestMethod.POST);
//        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.SEARCHAPI + MethodType.GLOBALSEARCHTEST, RequestMethod.POST);
        request.add("token", application.getSystemUtils().getToken());
        request.add("username", application.getSystemUtils().getDefaultUsername());
        request.add("keyword", keyword);
        request.add("page", page);
        request.add(MethodCode.DEVICEID, application.getSystemUtils().getSn());
        request.add(MethodCode.DEVICETYPE, SystemUtils.deviceType);
        request.add(MethodCode.APPVERSION, SystemUtils.getVersionName(context));
        addQueue(MethodCode.EVENT_GLOBALSEARCH, request);
//        startQueue();
    }

    @Override
    public void checkUpdate(int versionCode, String versionName) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.SYSTEMAPI + MethodType.CHECKUPDATE, RequestMethod.POST);
        request.add("versionCode", versionCode);
        request.add("versionName", versionName);
        request.add("type", "android");
        request.add(MethodCode.DEVICEID, application.getSystemUtils().getSn());
        request.add(MethodCode.DEVICETYPE, SystemUtils.deviceType);
        request.add(MethodCode.APPVERSION, SystemUtils.getVersionName(context));
        addQueue(MethodCode.EVENT_CHECKUPDATE, request);
//        startQueue();
    }

    @Override
    public void getTags() {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.SEARCHAPI + MethodType.GETTAGS, RequestMethod.POST);
        request.add("token", application.getSystemUtils().getToken());
        request.add("username", application.getSystemUtils().getDefaultUsername());
        request.add(MethodCode.DEVICEID, application.getSystemUtils().getSn());
        request.add(MethodCode.DEVICETYPE, SystemUtils.deviceType);
        request.add(MethodCode.APPVERSION, SystemUtils.getVersionName(context));
        addQueue(MethodCode.EVENT_GETTAGS, request);
//        startQueue();
    }

    @Override
    public void getTagBook(List<Tags> ageList, List<Tags> functionList, List<Tags> themeList, List<Tags> typeList, List<Tags> seriesList, int page) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.SEARCHAPI + MethodType.GETTAGBOOK, RequestMethod.POST);
//        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.SEARCHAPI + MethodType.GETTAGBOOKTEST, RequestMethod.POST);
        request.add("token", application.getSystemUtils().getToken());
        request.add("username", application.getSystemUtils().getDefaultUsername());
        request.add(MethodCode.DEVICEID, application.getSystemUtils().getSn());
        request.add(MethodCode.DEVICETYPE, SystemUtils.deviceType);
        request.add(MethodCode.APPVERSION, SystemUtils.getVersionName(context));
        request.add("page", page);
        if (ageList.size() != 0) {
            String age = null;
            for (int i = 0; i < ageList.size(); i++) {
                if (i == 0) {
                    age = ageList.get(i).getTid() + "";
                } else {
                    age = age + "," + ageList.get(i).getTid();
                }
            }
            request.add("age", age);
        }
        if (functionList.size() != 0) {
            String function = null;
            for (int i = 0; i < functionList.size(); i++) {
                if (i == 0) {
                    function = functionList.get(i).getTid() + "";
                } else {
                    function = function + "," + functionList.get(i).getTid();
                }
            }
            request.add("function", function);
        }
        if (themeList.size() != 0) {
            String theme = null;
            for (int i = 0; i < themeList.size(); i++) {
                if (i == 0) {
                    theme = themeList.get(i).getTid() + "";
                } else {
                    theme = theme + "," + themeList.get(i).getTid();
                }
            }
            request.add("theme", theme);
        }
        if (typeList.size() != 0) {
            String type = null;
            for (int i = 0; i < typeList.size(); i++) {
                if (i == 0) {
                    type = typeList.get(i).getTid() + "";
                } else {
                    type = type + "," + typeList.get(i).getTid();
                }
            }
            request.add("type", type);
        }
        if (seriesList.size() != 0) {
            String series = null;
            for (int i = 0; i < seriesList.size(); i++) {
                if (i == 0) {
                    series = seriesList.get(i).getTid() + "";
                } else {
                    series = series + "," + seriesList.get(i).getTid();
                }
            }
            request.add("series", series);
        }
        addQueue(MethodCode.EVENT_GETTAGBOOK, request);
//        startQueue();
    }

    @Override
    public void showGifts(int origin, int giftRecordId) {
        this.origin = origin;
        FastJsonRequest request = new FastJsonRequest(SystemUtils.netMainUrl + MethodCode.NETCLASSAPI + MethodType.SHOWGIFTS, RequestMethod.POST);
        request.add("username", application.getSystemUtils().getDefaultUsername());
        request.add("token", application.getSystemUtils().getToken());
        request.add("origin", origin);
        request.add("giftRecordId", giftRecordId);
        request.add(MethodCode.DEVICEID, application.getSystemUtils().getSn());
        request.add(MethodCode.DEVICETYPE, SystemUtils.deviceType);
        request.add(MethodCode.APPVERSION, SystemUtils.getVersionName(context));
        addQueue(MethodCode.EVENT_SHOWGIFTS + 110000 + origin, request);
    }

    @Override
    public void chooseGift(int giftId, int giftRecordId) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.netMainUrl + MethodCode.NETCLASSAPI + MethodType.CHOOSEGIFT, RequestMethod.POST);
        request.add("username", application.getSystemUtils().getDefaultUsername());
        request.add("token", application.getSystemUtils().getToken());
        request.add("giftId", giftId);
        request.add("giftRecordId", giftRecordId);
        request.add(MethodCode.DEVICEID, application.getSystemUtils().getSn());
        request.add(MethodCode.DEVICETYPE, SystemUtils.deviceType);
        request.add(MethodCode.APPVERSION, SystemUtils.getVersionName(context));
        addQueue(MethodCode.EVENT_CHOOSEGIFT, request);
    }

    @Override
    protected void onNetWorkStart(int what) {

    }

    @Override
    protected void onNetWorkFinish(int what) {

    }

    @Override
    protected void onSuccess(int what, Object response) {
        String jsonString = response.toString();
        JSONObject jsonObject = JSON.parseObject(jsonString);
        if (what == MethodCode.EVENT_SHOWGIFTS + 110000 + origin) {
            int errorType = jsonObject.getInteger(MethodCode.ERRTYPE);
            String errorInfo = jsonObject.getString(MethodCode.ERRINFO);
            String data = jsonObject.getString(MethodCode.DATA);
            if (errorType == 0) {
                NetGift netGift = JSON.parseObject(data, NetGift.class);
                listener.Success(what, netGift);
            } else {
                listener.Error(what, 7, errorInfo);
            }
        } else if (what == MethodCode.EVENT_CHOOSEGIFT) {
            int errorType = jsonObject.getInteger(MethodCode.ERRTYPE);
            String errorInfo = jsonObject.getString(MethodCode.ERRINFO);
            String data = jsonObject.getString(MethodCode.DATA);
            if (errorType == 0) {
                listener.Success(what, data);
            }else{
                listener.Error(what, 7, errorInfo);

            }
        } else {
            int status = jsonObject.getInteger(MethodCode.STATUS);
            String msg = jsonObject.getString(MethodCode.MSG);
            if (status == 0) {
                if (what == MethodCode.EVENT_LOGIN) {
                    MainBean bean = JSON.parseObject(jsonString, MainBean.class);
                    if (bean != null) {
                        //请求成功
                        listener.Success(what, bean);
                    } else {
                        listener.Error(what, 7, "没有数据");
                    }
                } else if (what == MethodCode.EVENT_GLOBALSEARCH) {
                    String data = jsonObject.getString(MethodCode.DATA);
                    if (data != null) {
                        SearchContent searchContent = JSON.parseObject(data, SearchContent.class);
                        listener.Success(what, searchContent);
                    } else {
                        listener.Success(what, new SearchContent());
                    }
                } else if (what == MethodCode.EVENT_CHECKUPDATE) {
                    String data = jsonObject.getString(MethodCode.DATA);
                    UpdateBean updateBean = JSON.parseObject(data, UpdateBean.class);
                    listener.Success(what, updateBean);
                } else if (what == MethodCode.EVENT_GETTAGS) {
                    String data = jsonObject.getString(MethodCode.DATA);
                    if (data != null) {
                        List<SearchTag> lists = JSON.parseArray(data, SearchTag.class);
                        listener.Success(what, lists);
                    } else {
                        listener.Success(what, new ArrayList<SearchTag>());
                    }
                } else if (what == MethodCode.EVENT_GETTAGBOOK) {
                    String data = jsonObject.getString(MethodCode.DATA);
                    if (data != null) {
                        SearchContent searchContent = JSON.parseObject(data, SearchContent.class);
                        listener.Success(what, searchContent);
                    } else {
                        listener.Success(what, new SearchContent());
                    }
                }
            } else {
                listener.Error(what, status, msg);
            }
        }

    }

    @Override
    protected void onFail(int what, Response response) {
        listener.Fail(what, "系统发生错误");
    }
}
