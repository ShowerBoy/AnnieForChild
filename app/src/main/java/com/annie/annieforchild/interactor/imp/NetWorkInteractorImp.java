package com.annie.annieforchild.interactor.imp;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.MethodType;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.net.Address;
import com.annie.annieforchild.bean.net.Game;
import com.annie.annieforchild.bean.net.ListenAndRead;
import com.annie.annieforchild.bean.net.MyNetClass;
import com.annie.annieforchild.bean.net.NetClass;
import com.annie.annieforchild.bean.net.NetDetails;
import com.annie.annieforchild.bean.net.NetExpDetails;
import com.annie.annieforchild.bean.net.NetSpecialDetail;
import com.annie.annieforchild.bean.net.NetSuggest;
import com.annie.annieforchild.bean.net.NetWork;
import com.annie.annieforchild.bean.net.PreheatConsult;
import com.annie.annieforchild.bean.net.PreheatConsultList;
import com.annie.annieforchild.bean.net.SpecialPreHeat;
import com.annie.annieforchild.bean.net.WechatBean;
import com.annie.annieforchild.bean.net.experience.EveryDetail;
import com.annie.annieforchild.bean.net.experience.EveryTaskList;
import com.annie.annieforchild.bean.net.experience.EveryTasks;
import com.annie.annieforchild.bean.net.experience.ExperienceV2;
import com.annie.annieforchild.bean.net.experience.VideoFinishBean;
import com.annie.annieforchild.bean.net.netexpclass.NetExpClass;
import com.annie.annieforchild.bean.net.netexpclass.NetExp_new;
import com.annie.annieforchild.bean.net.netexpclass.Video_first;
import com.annie.annieforchild.bean.net.netexpclass.Video_second;
import com.annie.annieforchild.bean.order.AliOrderBean;
import com.annie.annieforchild.bean.order.MyOrder;
import com.annie.annieforchild.bean.order.OrderDetail;
import com.annie.annieforchild.bean.order.WechatOrderBean;
import com.annie.annieforchild.interactor.NetWorkInteractor;
import com.annie.annieforchild.ui.application.MyApplication;
import com.annie.baselibrary.utils.NetUtils.NetWorkImp;
import com.annie.baselibrary.utils.NetUtils.RequestListener;
import com.annie.baselibrary.utils.NetUtils.request.FastJsonRequest;
import com.annie.baselibrary.utils.NetUtils.request.JavaBeanRequest;
import com.annie.baselibrary.utils.NetUtils.request.WLStringRequest;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by wanglei on 2018/9/22.
 */

public class NetWorkInteractorImp extends NetWorkImp implements NetWorkInteractor {
    private Context context;
    private RequestListener listener;
    private int payment, tag;
    private MyApplication application;

    public NetWorkInteractorImp(Context context, RequestListener listener) {
        this.context = context;
        this.listener = listener;
        application = (MyApplication) context.getApplicationContext();
    }

    @Override
    protected void onNetWorkStart(int what) {

    }

    @Override
    protected void onNetWorkFinish(int what) {

    }

    @Override
    public void getNetHomeData() {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.netMainUrl + MethodCode.NETCLASSAPI + MethodType.GETNETHOMEDATA, RequestMethod.POST);
        request.add("token", application.getSystemUtils().getToken());
        request.add("username", application.getSystemUtils().getDefaultUsername());
        request.add(MethodCode.DEVICEID, application.getSystemUtils().getSn());
        request.add(MethodCode.DEVICETYPE, SystemUtils.deviceType);
        request.add(MethodCode.APPVERSION, SystemUtils.getVersionName(context));
        addQueue(MethodCode.EVENT_GETNETHOMEDATA, request);
//        startQueue();
    }

    @Override
    public void getNetSuggest(int netid) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.netMainUrl + MethodCode.NETCLASSAPI + MethodType.GETNETSUGGEST, RequestMethod.POST);
        request.add("token", application.getSystemUtils().getToken());
        request.add("username", application.getSystemUtils().getDefaultUsername());
        request.add(MethodCode.DEVICEID, application.getSystemUtils().getSn());
        request.add(MethodCode.DEVICETYPE, SystemUtils.deviceType);
        request.add(MethodCode.APPVERSION, SystemUtils.getVersionName(context));
        request.add("netid", netid);
        addQueue(MethodCode.EVENT_GETNETSUGGEST, request);
//        startQueue();
    }

    @Override
    public void getMyNetClass() {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.netMainUrl + MethodCode.NETCLASSAPI + MethodType.GETMYNETCLASS, RequestMethod.POST);
        request.add("token", application.getSystemUtils().getToken());
        request.add("username", application.getSystemUtils().getDefaultUsername());
        request.add(MethodCode.DEVICEID, application.getSystemUtils().getSn());
        request.add(MethodCode.DEVICETYPE, SystemUtils.deviceType);
        request.add(MethodCode.APPVERSION, SystemUtils.getVersionName(context));
        addQueue(MethodCode.EVENT_GETMYNETCLASS, request);
//        startQueue();
    }

    @Override
    public void confirmOrder(int netid) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.netMainUrl + MethodCode.NETCLASSAPI + MethodType.CONFIRMORDER, RequestMethod.POST);
//        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + "NetclassTestApi/" + MethodType.CONFIRMORDER, RequestMethod.POST);
        request.add("token", application.getSystemUtils().getToken());
        request.add("username", application.getSystemUtils().getDefaultUsername());
        request.add("netid", netid);
        request.add(MethodCode.DEVICEID, application.getSystemUtils().getSn());
        request.add(MethodCode.DEVICETYPE, SystemUtils.deviceType);
        request.add(MethodCode.APPVERSION, SystemUtils.getVersionName(context));
        addQueue(MethodCode.EVENT_CONFIRMORDER, request);
//        startQueue();
    }

    @Override
    public void getMyAddress() {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.ADDRESSAPI + MethodType.GETMYADDRESS, RequestMethod.POST);
//        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl +"NetclassTestApi/"+ MethodType.GETMYADDRESS, RequestMethod.POST);
        request.add("token", application.getSystemUtils().getToken());
        request.add("username", application.getSystemUtils().getDefaultUsername());
        request.add(MethodCode.DEVICEID, application.getSystemUtils().getSn());
        request.add(MethodCode.DEVICETYPE, SystemUtils.deviceType);
        request.add(MethodCode.APPVERSION, SystemUtils.getVersionName(context));
        addQueue(MethodCode.EVENT_GETMYADDRESS, request);
//        startQueue();
    }


    @Override
    public void addOrUpdateAddress(int addressid, String name, String phone, String address, String provinces) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.ADDRESSAPI + MethodType.EDITADDRESS, RequestMethod.POST);
        request.add("token", application.getSystemUtils().getToken());
        request.add("username", application.getSystemUtils().getDefaultUsername());
        if (addressid != -1) {
            request.add("addressid", addressid);
        }
        request.add("name", name);
        request.add("phone", phone);
        request.add("address", address);
        request.add("provinces", provinces);
        request.add(MethodCode.DEVICEID, application.getSystemUtils().getSn());
        request.add(MethodCode.DEVICETYPE, SystemUtils.deviceType);
        request.add(MethodCode.APPVERSION, SystemUtils.getVersionName(context));
        if (addressid != -1) {
            addQueue(MethodCode.EVENT_EDITADDRESS, request);
        } else {
            addQueue(MethodCode.EVENT_ADDADDRESS, request);
        }
//        startQueue();
    }

    @Override
    public void deleteAddress(int addressid) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.ADDRESSAPI + MethodType.DELETEADDRESS, RequestMethod.POST);
        request.add("token", application.getSystemUtils().getToken());
        request.add("username", application.getSystemUtils().getDefaultUsername());
        request.add("addressid", addressid);
        request.add(MethodCode.DEVICEID, application.getSystemUtils().getSn());
        request.add(MethodCode.DEVICETYPE, SystemUtils.deviceType);
        request.add(MethodCode.APPVERSION, SystemUtils.getVersionName(context));
        addQueue(MethodCode.EVENT_DELETEADDRESS, request);
//        startQueue();
    }

    @Override
    public void buyNetWork(int netid, int addressid, int ismaterial, int payment, String wxnumber, String giftid) {
        this.payment = payment;
//        JavaBeanRequest request = new JavaBeanRequest(SystemUtils.mainUrl + MethodCode.NETCLASSAPI + MethodType.BUYNETWORK, String.class);
        FastJsonRequest request = new FastJsonRequest(SystemUtils.netMainUrl + MethodCode.NETCLASSAPI + MethodType.BUYNETWORK, RequestMethod.POST);
//        WLStringRequest request = new WLStringRequest(SystemUtils.mainUrl + MethodCode.NETCLASSAPI + MethodType.BUYNETWORK, RequestMethod.POST);
        request.add("token", application.getSystemUtils().getToken());
        request.add("username", application.getSystemUtils().getDefaultUsername());
        request.add("netid", netid);
        request.add("addressid", addressid);
        request.add("ismaterial", ismaterial);
        request.add("payment", payment);
        request.add("wxnumber", wxnumber);
        request.add(MethodCode.DEVICEID, application.getSystemUtils().getSn());
        request.add(MethodCode.DEVICETYPE, SystemUtils.deviceType);
        request.add(MethodCode.APPVERSION, SystemUtils.getVersionName(context));
//        request.add("giftid", giftid);
        addQueue(MethodCode.EVENT_BUYNETWORK, request);
//        startQueue();
    }

    @Override
    public void getNetDetails(int netid) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.netMainUrl + MethodCode.NETCLASSAPI + MethodType.GETNETDETAILS, RequestMethod.POST);
        request.add("token", application.getSystemUtils().getToken());
        request.add("username", application.getSystemUtils().getDefaultUsername());
        request.add("netid", netid);
        request.add(MethodCode.DEVICEID, application.getSystemUtils().getSn());
        request.add(MethodCode.DEVICETYPE, SystemUtils.deviceType);
        request.add(MethodCode.APPVERSION, SystemUtils.getVersionName(context));
        addQueue(MethodCode.EVENT_GETNETDETAILS, request);
//        startQueue();
    }

    @Override
    public void getNetExpDetails(int netid) {
//        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.NETCLASSAPI + MethodType.GETNETEXPDETAILS, RequestMethod.POST);
        FastJsonRequest request = new FastJsonRequest(SystemUtils.netMainUrl + MethodCode.NETCLASSAPI + MethodType.GETNETEXPDETAILS, RequestMethod.POST);
        request.add("token", application.getSystemUtils().getToken());
        request.add("username", application.getSystemUtils().getDefaultUsername());
        request.add("netid", netid);
        request.add(MethodCode.DEVICEID, application.getSystemUtils().getSn());
        request.add(MethodCode.DEVICETYPE, SystemUtils.deviceType);
        request.add(MethodCode.APPVERSION, SystemUtils.getVersionName(context));
        addQueue(MethodCode.EVENT_GETNETEXPDETAILS, request);
//        startQueue();
    }

    @Override
    public void getNetExpDetails_new(int netid) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.netMainUrl + MethodCode.NETCLASSAPI + MethodType.GETNETEXPDETAILS_NEW, RequestMethod.POST);
        request.add("token", application.getSystemUtils().getToken());
        request.add("username", application.getSystemUtils().getDefaultUsername());
        request.add("netid", netid);
        request.add(MethodCode.DEVICEID, application.getSystemUtils().getSn());
        request.add(MethodCode.DEVICETYPE, SystemUtils.deviceType);
        request.add(MethodCode.APPVERSION, SystemUtils.getVersionName(context));
//        request.add("netid", 22);
        addQueue(MethodCode.EVENT_GETNETEXPDETAILS_NEW, request);
//        startQueue();
    }

    @Override
    public void getNetSpecialDetail(int netid) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.netMainUrl + MethodCode.NETCLASSAPI + MethodType.SPECIALCLASS, RequestMethod.POST);
        request.add("token", application.getSystemUtils().getToken());
        request.add("username", application.getSystemUtils().getDefaultUsername());
        request.add("netid", netid);
        request.add(MethodCode.DEVICEID, application.getSystemUtils().getSn());
        request.add(MethodCode.DEVICETYPE, SystemUtils.deviceType);
        request.add(MethodCode.APPVERSION, SystemUtils.getVersionName(context));
        addQueue(MethodCode.EVENT_SPECIALCLASS, request);
    }

    @Override
    public void getMyOrderList() {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.netMainUrl + MethodCode.PERSONAPI2 + MethodType.GETMYORDERLIST, RequestMethod.POST);
        request.add("token", application.getSystemUtils().getToken());
        request.add("username", application.getSystemUtils().getDefaultUsername());
        request.add(MethodCode.DEVICEID, application.getSystemUtils().getSn());
        request.add(MethodCode.DEVICETYPE, SystemUtils.deviceType);
        request.add(MethodCode.APPVERSION, SystemUtils.getVersionName(context));
        addQueue(MethodCode.EVENT_GETMYORDERLIST, request);
    }

    @Override
    public void getMyOrderDetail(int orderIncrId) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.netMainUrl + MethodCode.PERSONAPI2 + MethodType.GETMYORDERDETAIL, RequestMethod.POST);
        request.add("token", application.getSystemUtils().getToken());
        request.add("username", application.getSystemUtils().getDefaultUsername());
        request.add("orderIncrId", orderIncrId);
        request.add(MethodCode.DEVICEID, application.getSystemUtils().getSn());
        request.add(MethodCode.DEVICETYPE, SystemUtils.deviceType);
        request.add(MethodCode.APPVERSION, SystemUtils.getVersionName(context));
        addQueue(MethodCode.EVENT_GETMYORDERDETAIL, request);
    }

    @Override
    public void continuePay(int orderIncrId, int payment, int tag) {
        this.payment = payment;
        this.tag = tag;
        FastJsonRequest request = new FastJsonRequest(SystemUtils.netMainUrl + MethodCode.PERSONAPI2 + MethodType.CONTINUEPAY, RequestMethod.POST);
        request.add("token", application.getSystemUtils().getToken());
        request.add("username", application.getSystemUtils().getDefaultUsername());
        request.add("orderIncrId", orderIncrId);
        request.add(MethodCode.DEVICEID, application.getSystemUtils().getSn());
        request.add(MethodCode.DEVICETYPE, SystemUtils.deviceType);
        request.add(MethodCode.APPVERSION, SystemUtils.getVersionName(context));
        addQueue(MethodCode.EVENT_CONTINUEPAY + 90000 + tag, request);
    }

    @Override
    public void cancelOrder(int orderIncrId, int payment, int tag) {
        this.payment = payment;
        this.tag = tag;
        FastJsonRequest request = new FastJsonRequest(SystemUtils.netMainUrl + MethodCode.PERSONAPI2 + MethodType.CANCELORDER, RequestMethod.POST);
        request.add("token", application.getSystemUtils().getToken());
        request.add("username", application.getSystemUtils().getDefaultUsername());
        request.add("orderIncrId", orderIncrId);
        request.add(MethodCode.DEVICEID, application.getSystemUtils().getSn());
        request.add(MethodCode.DEVICETYPE, SystemUtils.deviceType);
        request.add(MethodCode.APPVERSION, SystemUtils.getVersionName(context));
        addQueue(MethodCode.EVENT_CANCELORDER, request);
    }

    @Override
    public void experienceDetailsV2(int netid) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.netMainUrl + MethodCode.NETCLASSAPI + MethodType.EXPERIENCEDETAILSV2, RequestMethod.POST);
        request.add("token", application.getSystemUtils().getToken());
        request.add("username", application.getSystemUtils().getDefaultUsername());
        request.add("netid", netid);
        request.add(MethodCode.DEVICEID, application.getSystemUtils().getSn());
        request.add(MethodCode.DEVICETYPE, SystemUtils.deviceType);
        request.add(MethodCode.APPVERSION, SystemUtils.getVersionName(context));
        addQueue(MethodCode.EVENT_EXPERIENCEDETAILSV2, request);
    }

    @Override
    public void videoPayRecord(String netid, String stageid, String unitid, String chaptercontent_id, int isFinish, String classcode) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.netMainUrl + MethodCode.NETCLASSAPI + MethodType.VIDEOPAYRECORD, RequestMethod.POST);
        request.add("token", application.getSystemUtils().getToken());
        request.add("username", application.getSystemUtils().getDefaultUsername());
        request.add("netid", netid);
        request.add("stageid", stageid);
        request.add("unitid", unitid);
        request.add("chaptercontent_id", chaptercontent_id);
        request.add("isfinish", isFinish);
        request.add("classcode", classcode);
        request.add(MethodCode.DEVICEID, application.getSystemUtils().getSn());
        request.add(MethodCode.DEVICETYPE, SystemUtils.deviceType);
        request.add(MethodCode.APPVERSION, SystemUtils.getVersionName(context));
        addQueue(MethodCode.EVENT_VIDEOPAYRECORD, request);
    }

    @Override
    public void videoList(String fid, String netid, String stageid, String unitid) {
//        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.NETCLASSAPI + MethodType.VIDEOLIST, RequestMethod.POST);
        FastJsonRequest request = new FastJsonRequest(SystemUtils.netMainUrl + MethodCode.NETCLASSAPI + MethodType.VIDEOLISTV2, RequestMethod.POST);
        request.add("token", application.getSystemUtils().getToken());
        request.add("username", application.getSystemUtils().getDefaultUsername());
        request.add("fid", fid);
        request.add("netid", netid);
        request.add("stageid", stageid);
        request.add("unitid", unitid);
        request.add(MethodCode.DEVICEID, application.getSystemUtils().getSn());
        request.add(MethodCode.DEVICETYPE, SystemUtils.deviceType);
        request.add(MethodCode.APPVERSION, SystemUtils.getVersionName(context));
        addQueue(MethodCode.EVENT_VIDEOLIST, request);
    }

    @Override
    public void SpecialClassV2(int netid) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.netMainUrl + MethodCode.NETCLASSAPI + MethodType.SPECIALCLASSV2, RequestMethod.POST);
        request.add("token", application.getSystemUtils().getToken());
        request.add("username", application.getSystemUtils().getDefaultUsername());
        request.add("netid", netid);
        request.add(MethodCode.DEVICEID, application.getSystemUtils().getSn());
        request.add(MethodCode.DEVICETYPE, SystemUtils.deviceType);
        request.add(MethodCode.APPVERSION, SystemUtils.getVersionName(context));
        addQueue(MethodCode.EVENT_SPECIALCLASSV2, request);
    }

    @Override
    public void taskList(int netid) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.netMainUrl + MethodCode.NETCLASSAPI + MethodType.TASKLIST, RequestMethod.POST);
        request.add("token", application.getSystemUtils().getToken());
        request.add("username", application.getSystemUtils().getDefaultUsername());
        request.add("netid", netid);
        request.add(MethodCode.DEVICEID, application.getSystemUtils().getSn());
        request.add(MethodCode.DEVICETYPE, SystemUtils.deviceType);
        request.add(MethodCode.APPVERSION, SystemUtils.getVersionName(context));
        addQueue(MethodCode.EVENT_TASKLIST, request);
    }

    @Override
    public void taskDetail(int netid, int num) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.netMainUrl + MethodCode.NETCLASSAPI + MethodType.TASKDETAIL, RequestMethod.POST);
        request.add("token", application.getSystemUtils().getToken());
        request.add("username", application.getSystemUtils().getDefaultUsername());
        request.add("netid", netid);
        request.add("num", num);
        request.add(MethodCode.DEVICEID, application.getSystemUtils().getSn());
        request.add(MethodCode.DEVICETYPE, SystemUtils.deviceType);
        request.add(MethodCode.APPVERSION, SystemUtils.getVersionName(context));
        addQueue(MethodCode.EVENT_TASKDETAIL, request);
    }

    @Override
    public void getLesson(String lessonid, int type) {
        FastJsonRequest request;
        if (type == 4) {//彩虹条新版本
            request = new FastJsonRequest(SystemUtils.netMainUrl + MethodCode.NETCLASSAPI + MethodType.GETSPECIALLESSON_NEW, RequestMethod.POST);
            request.add("fid", lessonid);
        } else {
            request = new FastJsonRequest(SystemUtils.netMainUrl + MethodCode.NETCLASSAPI + MethodType.GETLESSON, RequestMethod.POST);
            request.add("lessonid", lessonid);
        }
        request.add("token", application.getSystemUtils().getToken());
        request.add("username", application.getSystemUtils().getDefaultUsername());
        request.add(MethodCode.DEVICEID, application.getSystemUtils().getSn());
        request.add(MethodCode.DEVICETYPE, SystemUtils.deviceType);
        request.add(MethodCode.APPVERSION, SystemUtils.getVersionName(context));

        addQueue(MethodCode.EVENT_GETLESSON, request);
//        startQueue();
    }

    @Override
    public void buySuccess(String tradeno, String outtradeno, int type) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.netMainUrl + MethodCode.NETCLASSAPI + MethodType.BUYSUCCESS, RequestMethod.POST);
        request.add("token", application.getSystemUtils().getToken());
        request.add("username", application.getSystemUtils().getDefaultUsername());
        request.add("tradeno", tradeno);
        request.add("outtradeno", outtradeno);
        request.add("type", type);
        request.add(MethodCode.DEVICEID, application.getSystemUtils().getSn());
        request.add(MethodCode.DEVICETYPE, SystemUtils.deviceType);
        request.add(MethodCode.APPVERSION, SystemUtils.getVersionName(context));
        addQueue(MethodCode.EVENT_BUYSUCCESS, request);
//        startQueue();
    }

    @Override
    public void getNetPreheatConsult(String lessonid, int type) {
        if (type == 1) {
            //体验课预热课
            FastJsonRequest request = new FastJsonRequest(SystemUtils.netMainUrl + MethodCode.NETCLASSAPI + MethodType.GETPREHEATCONSULT, RequestMethod.POST);
            request.add("token", application.getSystemUtils().getToken());
            request.add("username", application.getSystemUtils().getDefaultUsername());
            request.add("lessonid", Integer.parseInt(lessonid));
            request.add(MethodCode.DEVICEID, application.getSystemUtils().getSn());
            request.add(MethodCode.DEVICETYPE, SystemUtils.deviceType);
            request.add(MethodCode.APPVERSION, SystemUtils.getVersionName(context));
            addQueue(MethodCode.EVENT_GETPREHEATCONSULT, request);
        } else if (type == 2) {
            //综合课预热课
//            FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.NETCLASSAPI + MethodType.SPECIALPREHEATING, RequestMethod.POST);
            FastJsonRequest request = new FastJsonRequest(SystemUtils.netMainUrl + MethodCode.NETCLASSAPI + MethodType.SPECIALPREHEATINGV2, RequestMethod.POST);
            request.add("token", application.getSystemUtils().getToken());
            request.add("username", application.getSystemUtils().getDefaultUsername());
            request.add("lessonid", Integer.parseInt(lessonid));
            request.add(MethodCode.DEVICEID, application.getSystemUtils().getSn());
            request.add(MethodCode.DEVICETYPE, SystemUtils.deviceType);
            request.add(MethodCode.APPVERSION, SystemUtils.getVersionName(context));
            addQueue(MethodCode.EVENT_SPECIALPREHEATING, request);
        }
//        startQueue();
    }

    @Override
    public void getListeningAndReading(String week, String classid, int tag, int classify) {
        this.tag = tag;
        FastJsonRequest request = new FastJsonRequest(SystemUtils.netMainUrl + MethodCode.NETCLASSAPI + MethodType.GETLISTENANDREAD, RequestMethod.POST);
        request.add("token", application.getSystemUtils().getToken());
        request.add("username", application.getSystemUtils().getDefaultUsername());
        request.add("classid", classid);
        request.add("week", week);
        request.add("classify", classify);
        request.add(MethodCode.DEVICEID, application.getSystemUtils().getSn());
        request.add(MethodCode.DEVICETYPE, SystemUtils.deviceType);
        request.add(MethodCode.APPVERSION, SystemUtils.getVersionName(context));
        addQueue(MethodCode.EVENT_GETLISTENANDREAD + 80000 + tag, request);
//        startQueue();
    }

    @Override
    public void buynum(int netid, int type) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.netMainUrl + MethodCode.NETCLASSAPI + MethodType.BUYNUM, RequestMethod.POST);
        request.add("token", application.getSystemUtils().getToken());
        request.add("username", application.getSystemUtils().getDefaultUsername());
        request.add("netid", netid);
        request.add("checktype", type);
        request.add(MethodCode.DEVICEID, application.getSystemUtils().getSn());
        request.add(MethodCode.DEVICETYPE, SystemUtils.deviceType);
        request.add(MethodCode.APPVERSION, SystemUtils.getVersionName(context));
        if (type == 1) {//1:netsuggest请求 2：confirmorder请求
            addQueue(MethodCode.EVENT_BUYNUM, request);
        } else {
            addQueue(MethodCode.EVENT_BUYNUM1, request);
        }
//        startQueue();
    }

    @Override
    public void OrderQuery(String tradeno, String outtradeno, int type, int tag) {
        this.tag = tag;
        FastJsonRequest request = new FastJsonRequest(SystemUtils.netMainUrl + MethodCode.NETCLASSAPI + MethodType.ORDERQUERY, RequestMethod.POST);
        request.add("token", application.getSystemUtils().getToken());
        request.add("username", application.getSystemUtils().getDefaultUsername());
        request.add("tradeno", tradeno);
        request.add("outtradeno", outtradeno);
        request.add("type", type);
        request.add(MethodCode.DEVICEID, application.getSystemUtils().getSn());
        request.add(MethodCode.DEVICETYPE, SystemUtils.deviceType);
        request.add(MethodCode.APPVERSION, SystemUtils.getVersionName(context));
        addQueue(MethodCode.EVENT_ORDERQUERY + 11000 + tag, request);
//        startQueue();
    }

    @Override
    public void getWeiClass(String fid, int type) {
        FastJsonRequest request;
        if (type == 1) {//微课堂
//            request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.NETCLASSAPI + MethodType.GETWEICLASS, RequestMethod.POST);
            request = new FastJsonRequest(SystemUtils.netMainUrl + MethodCode.NETCLASSAPI + MethodType.GETWEICLASSV2, RequestMethod.POST);
            request.add("fid", fid);
        } else if (type == 4) {//新版本的开班活动等
//            request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.NETCLASSAPI + MethodType.GETSPECIAL_NEW, RequestMethod.POST);
            request = new FastJsonRequest(SystemUtils.netMainUrl + MethodCode.NETCLASSAPI + MethodType.GETSPECIAL_NEWV2, RequestMethod.POST);
            request.add("fid", fid);
        } else {
//            request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.NETCLASSAPI + MethodType.GETCLASSANALYSIS, RequestMethod.POST);
            request = new FastJsonRequest(SystemUtils.netMainUrl + MethodCode.NETCLASSAPI + MethodType.GETCLASSANALYSISV2, RequestMethod.POST);
            request.add("fid", fid);
        }
        request.add("token", application.getSystemUtils().getToken());
        request.add("username", application.getSystemUtils().getDefaultUsername());
        request.add(MethodCode.DEVICEID, application.getSystemUtils().getSn());
        request.add(MethodCode.DEVICETYPE, SystemUtils.deviceType);
        request.add(MethodCode.APPVERSION, SystemUtils.getVersionName(context));

        addQueue(MethodCode.EVENT_GETWEICLASS, request);
    }

    @Override
    protected void onSuccess(int what, Object response) {
        String jsonString = response.toString();
//        if (what == MethodCode.EVENT_BUYNETWORK) {
//            listener.Success(what, jsonString);
//            return;
//        }
        JSONObject jsonObject = JSON.parseObject(jsonString);

        if (what == MethodCode.EVENT_GETMYADDRESS) {
            int status = jsonObject.getInteger(MethodCode.STATUS);
            String msg = jsonObject.getString(MethodCode.MSG);
            String data = jsonObject.getString(MethodCode.DATA);
            if (status == 0) {
                if (data != null) {
                    List<Address> lists = JSON.parseArray(data, Address.class);
                    listener.Success(what, lists);
                } else {
                    List<Address> lists = new ArrayList<>();
                    listener.Success(what, lists);
                }
            } else {
                listener.Error(what, status, msg);
            }
        } else if (what == MethodCode.EVENT_ADDADDRESS) {
            int status = jsonObject.getInteger(MethodCode.STATUS);
            String msg = jsonObject.getString(MethodCode.MSG);
            String data = jsonObject.getString(MethodCode.DATA);
            if (status == 0) {
                listener.Success(what, "添加成功");
            } else {
                listener.Error(what, status, msg);
            }
        } else if (what == MethodCode.EVENT_EDITADDRESS) {
            int status = jsonObject.getInteger(MethodCode.STATUS);
            String msg = jsonObject.getString(MethodCode.MSG);
            String data = jsonObject.getString(MethodCode.DATA);
            if (status == 0) {
                listener.Success(what, "修改成功");
            } else {
                listener.Error(what, status, msg);
            }
        } else if (what == MethodCode.EVENT_DELETEADDRESS) {
            int status = jsonObject.getInteger(MethodCode.STATUS);
            String msg = jsonObject.getString(MethodCode.MSG);
            String data = jsonObject.getString(MethodCode.DATA);
            if (status == 0) {
                listener.Success(what, "删除成功");
            } else {
                listener.Error(what, status, msg);
            }
        } else {
            int errorType = jsonObject.getInteger(MethodCode.ERRTYPE);
            String errorInfo = jsonObject.getString(MethodCode.ERRINFO);
            String data = jsonObject.getString(MethodCode.DATA);
            if (errorType == 0) {
                if (what == MethodCode.EVENT_GETNETHOMEDATA) {
                    NetWork netWork = JSON.parseObject(data, NetWork.class);
                    listener.Success(what, netWork);
                } else if (what == MethodCode.EVENT_GETNETSUGGEST) {
                    NetSuggest netSuggest = JSON.parseObject(data, NetSuggest.class);
                    listener.Success(what, netSuggest);
                } else if (what == MethodCode.EVENT_GETMYNETCLASS) {
                    MyNetClass myNetClass = JSON.parseObject(data, MyNetClass.class);
                    listener.Success(what, myNetClass);
                } else if (what == MethodCode.EVENT_CONFIRMORDER) {
                    NetSuggest netSuggest = JSON.parseObject(data, NetSuggest.class);
                    listener.Success(what, netSuggest);
                } else if (what == MethodCode.EVENT_GETNETDETAILS) {
                    List<NetDetails> lists;
                    if (data != null) {
                        lists = JSON.parseArray(data, NetDetails.class);
                    } else {
                        lists = new ArrayList<>();
                    }
                    listener.Success(what, lists);
                } else if (what == MethodCode.EVENT_BUYNETWORK) {
                    if (data != null) {
                        if (payment == 0) {
                            listener.Success(what, data);
                        } else {
                            WechatBean wechatBean = JSON.parseObject(data, WechatBean.class);
                            listener.Success(what, wechatBean);
                        }
                    }
                } else if (what == MethodCode.EVENT_GETLESSON) {
                    List<Game> lists;
                    if (data != null) {
                        lists = JSON.parseArray(data, Game.class);
                    } else {
                        lists = new ArrayList<>();
                    }
                    listener.Success(what, lists);
                } else if (what == MethodCode.EVENT_BUYSUCCESS) {
                    List<NetClass> lists;
                    if (data != null) {
                        lists = JSON.parseArray(data, NetClass.class);
                    } else {
                        lists = new ArrayList<>();
                    }
                    listener.Success(what, lists);
                } else if (what == MethodCode.EVENT_GETNETEXPDETAILS) {
                    NetExpClass netExpClass = JSON.parseObject(data, NetExpClass.class);
                    listener.Success(what, netExpClass);
                } else if (what == MethodCode.EVENT_GETNETEXPDETAILS_NEW) {
                    NetExp_new netExpClass = JSON.parseObject(data, NetExp_new.class);
                    listener.Success(what, netExpClass);
                } else if (what == MethodCode.EVENT_GETPREHEATCONSULT) {
                    PreheatConsult preheatConsult = JSON.parseObject(data, PreheatConsult.class);
                    listener.Success(what, preheatConsult);
                } else if (what == MethodCode.EVENT_GETLISTENANDREAD + 80000 + tag) {
                    ListenAndRead listenAndRead = JSON.parseObject(data, ListenAndRead.class);
                    listener.Success(what, listenAndRead);
                } else if (what == MethodCode.EVENT_BUYNUM) {
                    JSONObject dataobj = jsonObject.getJSONObject(MethodCode.DATA);
                    int canbuy = dataobj.getInteger("canbuy");
                    listener.Success(what, canbuy);
                } else if (what == MethodCode.EVENT_BUYNUM1) {
                    JSONObject dataobj = jsonObject.getJSONObject(MethodCode.DATA);
                    int canbuy = dataobj.getInteger("canbuy");
                    listener.Success(what, canbuy);
                } else if (what == MethodCode.EVENT_ORDERQUERY + 11000 + tag) {
                    String trade_status = "";
                    if (payment == 0) {
                        AliOrderBean aliOrderBean = JSON.parseObject(data, AliOrderBean.class);
//                    JSONObject jsonObject1 = JSON.parseObject(data);
//                    String response1 = jsonObject1.getString("alipay_trade_query_response");
//                    JSONObject jsonObject2 = JSON.parseObject(response1);
//                    trade_status = jsonObject2.getString("trade_status");
                        listener.Success(what, aliOrderBean);
                    } else {
                        WechatOrderBean wechatOrderBean = JSON.parseObject(data, WechatOrderBean.class);
//                    JSONObject jsonObject1 = JSON.parseObject(data);
//                    trade_status = jsonObject1.getString("trade_state");
                        listener.Success(what, wechatOrderBean);
                    }
                } else if (what == MethodCode.EVENT_GETWEICLASS) {
                    List<Video_second> lists;
                    if (data != null) {
                        lists = JSON.parseArray(data, Video_second.class);
                    } else {
                        lists = new ArrayList<>();
                    }
                    listener.Success(what, lists);
                } else if (what == MethodCode.EVENT_SPECIALCLASS) {
                    NetSpecialDetail netSpecialDetail = JSON.parseObject(data, NetSpecialDetail.class);
                    listener.Success(what, netSpecialDetail);
                } else if (what == MethodCode.EVENT_SPECIALPREHEATING) {
                    List<SpecialPreHeat> lists = JSON.parseArray(data, SpecialPreHeat.class);
                    if (lists == null) {
                        lists = new ArrayList<>();
                    }
                    listener.Success(what, lists);
                } else if (what == MethodCode.EVENT_GETMYORDERLIST) {
                    List<MyOrder> lists = JSON.parseArray(data, MyOrder.class);
                    if(lists==null){
                        lists = new ArrayList<>();
                    }
                    listener.Success(what, lists);
                } else if (what == MethodCode.EVENT_GETMYORDERDETAIL) {
                    OrderDetail orderDetail = JSON.parseObject(data, OrderDetail.class);
                    listener.Success(what, orderDetail);
                } else if (what == MethodCode.EVENT_CONTINUEPAY + 90000 + tag) {
                    if (data != null) {
                        if (payment == 0) {
                            listener.Success(what, data);
                        } else {
                            WechatBean wechatBean = JSON.parseObject(data, WechatBean.class);
                            listener.Success(what, wechatBean);
                        }

                    }
                } else if (what == MethodCode.EVENT_CANCELORDER) {
                    listener.Success(what, "取消订单成功");
                } else if (what == MethodCode.EVENT_EXPERIENCEDETAILSV2) {
                    ExperienceV2 experienceV2 = JSON.parseObject(data, ExperienceV2.class);
                    listener.Success(what, experienceV2);
                } else if (what == MethodCode.EVENT_VIDEOPAYRECORD) {
                    VideoFinishBean videoFinishBean = JSON.parseObject(data, VideoFinishBean.class);
                    listener.Success(what, videoFinishBean);
                } else if (what == MethodCode.EVENT_VIDEOLIST) {
                    List<Video_second> lists;
                    if (data != null) {
                        lists = JSON.parseArray(data, Video_second.class);
                    } else {
                        lists = new ArrayList<>();
                    }
                    listener.Success(what, lists);
                } else if (what == MethodCode.EVENT_SPECIALCLASSV2) {
                    ExperienceV2 experienceV2 = JSON.parseObject(data, ExperienceV2.class);
                    listener.Success(what, experienceV2);
                } else if (what == MethodCode.EVENT_TASKLIST) {
                    EveryTaskList everyTaskList = JSON.parseObject(data, EveryTaskList.class);
                    listener.Success(what, everyTaskList);
                } else if (what == MethodCode.EVENT_TASKDETAIL) {
                    EveryDetail everyDetail = JSON.parseObject(data, EveryDetail.class);
                    listener.Success(what, everyDetail);
                }
            } else {
                Log.e("222",jsonString+"");
                listener.Error(what, errorType, errorInfo);
            }
        }
    }

    @Override
    protected void onFail(int what, Response response) {
        listener.Fail(what, "系统发生错误");
    }

}
