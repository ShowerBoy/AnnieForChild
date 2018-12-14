package com.annie.annieforchild.interactor.imp;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.MethodType;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.net.Address;
import com.annie.annieforchild.bean.net.Game;
import com.annie.annieforchild.bean.net.NetClass;
import com.annie.annieforchild.bean.net.NetDetails;
import com.annie.annieforchild.bean.net.NetSuggest;
import com.annie.annieforchild.bean.net.NetWork;
import com.annie.annieforchild.bean.net.WechatBean;
import com.annie.annieforchild.interactor.NetWorkInteractor;
import com.annie.baselibrary.utils.NetUtils.NetWorkImp;
import com.annie.baselibrary.utils.NetUtils.RequestListener;
import com.annie.baselibrary.utils.NetUtils.request.FastJsonRequest;
import com.annie.baselibrary.utils.NetUtils.request.JavaBeanRequest;
import com.annie.baselibrary.utils.NetUtils.request.WLStringRequest;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanglei on 2018/9/22.
 */

public class NetWorkInteractorImp extends NetWorkImp implements NetWorkInteractor {
    private Context context;
    private RequestListener listener;
    private int payment;

    public NetWorkInteractorImp(Context context, RequestListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected void onNetWorkStart(int what) {

    }

    @Override
    protected void onNetWorkFinish(int what) {

    }

    @Override
    public void getNetHomeData() {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.NETCLASSAPI + MethodType.GETNETHOMEDATA, RequestMethod.POST);
        request.add("token", SystemUtils.token);
        request.add("username", SystemUtils.defaultUsername);
        addQueue(MethodCode.EVENT_GETNETHOMEDATA, request);
        startQueue();
    }

    @Override
    public void getNetSuggest(int netid) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.NETCLASSAPI + MethodType.GETNETSUGGEST, RequestMethod.POST);
        request.add("token", SystemUtils.token);
        request.add("username", SystemUtils.defaultUsername);
        request.add("netid", netid);
        addQueue(MethodCode.EVENT_GETNETSUGGEST, request);
        startQueue();
    }

    @Override
    public void getMyNetClass() {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.NETCLASSAPI + MethodType.GETMYNETCLASS, RequestMethod.POST);
        request.add("token", SystemUtils.token);
        request.add("username", SystemUtils.defaultUsername);
        addQueue(MethodCode.EVENT_GETMYNETCLASS, request);
        startQueue();
    }

    @Override
    public void confirmOrder(int netid) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.NETCLASSAPI + MethodType.CONFIRMORDER, RequestMethod.POST);
        request.add("token", SystemUtils.token);
        request.add("username", SystemUtils.defaultUsername);
        request.add("netid", netid);
        addQueue(MethodCode.EVENT_CONFIRMORDER, request);
        startQueue();
    }

    @Override
    public void getMyAddress() {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.NETCLASSAPI + MethodType.GETMYADDRESS, RequestMethod.POST);
        request.add("token", SystemUtils.token);
        request.add("username", SystemUtils.defaultUsername);
        addQueue(MethodCode.EVENT_GETMYADDRESS, request);
        startQueue();
    }

    @Override
    public void addAddress(String name, String phone, String address) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.NETCLASSAPI + MethodType.ADDADDRESS, RequestMethod.POST);
        request.add("token", SystemUtils.token);
        request.add("username", SystemUtils.defaultUsername);
        request.add("name", name);
        request.add("phone", phone);
        request.add("address", address);
        addQueue(MethodCode.EVENT_ADDADDRESS, request);
        startQueue();
    }

    @Override
    public void editAddress(int addressid, String name, String phone, String address) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.NETCLASSAPI + MethodType.EDITADDRESS, RequestMethod.POST);
        request.add("token", SystemUtils.token);
        request.add("username", SystemUtils.defaultUsername);
        request.add("addressid", addressid);
        request.add("name", name);
        request.add("phone", phone);
        request.add("address", address);
        addQueue(MethodCode.EVENT_EDITADDRESS, request);
        startQueue();
    }

    @Override
    public void deleteAddress(int addressid) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.NETCLASSAPI + MethodType.DELETEADDRESS, RequestMethod.POST);
        request.add("token", SystemUtils.token);
        request.add("username", SystemUtils.defaultUsername);
        request.add("addressid", addressid);
        addQueue(MethodCode.EVENT_DELETEADDRESS, request);
        startQueue();
    }

    @Override
    public void buyNetWork(int netid, int addressid, int ismaterial, int payment, String giftid) {
        this.payment = payment;
//        JavaBeanRequest request = new JavaBeanRequest(SystemUtils.mainUrl + MethodCode.NETCLASSAPI + MethodType.BUYNETWORK, String.class);
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.NETCLASSAPI + MethodType.BUYNETWORK, RequestMethod.POST);
//        WLStringRequest request = new WLStringRequest(SystemUtils.mainUrl + MethodCode.NETCLASSAPI + MethodType.BUYNETWORK, RequestMethod.POST);
        request.add("token", SystemUtils.token);
        request.add("username", SystemUtils.defaultUsername);
        request.add("netid", netid);
        request.add("addressid", addressid);
        request.add("ismaterial", ismaterial);
        request.add("payment", payment);
        request.add("giftid", giftid);
        addQueue(MethodCode.EVENT_BUYNETWORK, request);
        startQueue();
    }

    @Override
    public void getNetDetails(int netid) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.NETCLASSAPI + MethodType.GETNETDETAILS, RequestMethod.POST);
        request.add("token", SystemUtils.token);
        request.add("username", SystemUtils.defaultUsername);
        request.add("netid", netid);
        addQueue(MethodCode.EVENT_GETNETDETAILS, request);
        startQueue();
    }

    @Override
    public void getLesson(int lessonid) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.NETCLASSAPI + MethodType.GETLESSON, RequestMethod.POST);
        request.add("token", SystemUtils.token);
        request.add("username", SystemUtils.defaultUsername);
        request.add("lessonid", lessonid);
        addQueue(MethodCode.EVENT_GETLESSON, request);
        startQueue();
    }

    @Override
    protected void onSuccess(int what, Object response) {
        String jsonString = response.toString();
//        if (what == MethodCode.EVENT_BUYNETWORK) {
//            listener.Success(what, jsonString);
//            return;
//        }
        JSONObject jsonObject = JSON.parseObject(jsonString);
        int errorType = jsonObject.getInteger(MethodCode.ERRTYPE);
        String errorInfo = jsonObject.getString(MethodCode.ERRINFO);
        String data = jsonObject.getString(MethodCode.DATA);
        if (errorType == 3) {
            listener.Error(what, errorInfo);
        } else {
            if (what == MethodCode.EVENT_GETNETHOMEDATA) {
                NetWork netWork = JSON.parseObject(data, NetWork.class);
                listener.Success(what, netWork);
            } else if (what == MethodCode.EVENT_GETNETSUGGEST) {
                NetSuggest netSuggest = JSON.parseObject(data, NetSuggest.class);
                listener.Success(what, netSuggest);
            } else if (what == MethodCode.EVENT_GETMYNETCLASS) {
                JSONObject dataobj = jsonObject.getJSONObject(MethodCode.DATA);
                if (dataobj != null) {
                    String myList = dataobj.getString("myList");
                    if (myList != null) {
                        List<NetClass> lists = JSON.parseArray(myList, NetClass.class);
                        if (lists != null) {
                            listener.Success(what, lists);
                        } else {
                            listener.Success(what, new ArrayList<NetClass>());
                        }
                    } else {
                        listener.Success(what, new ArrayList<NetClass>());
                    }
                } else {
                    listener.Success(what, new ArrayList<NetClass>());
                }
            } else if (what == MethodCode.EVENT_CONFIRMORDER) {
                NetSuggest netSuggest = JSON.parseObject(data, NetSuggest.class);
                listener.Success(what, netSuggest);
            } else if (what == MethodCode.EVENT_GETMYADDRESS) {
                if (data != null) {
                    List<Address> lists = JSON.parseArray(data, Address.class);
                    listener.Success(what, lists);
                } else {
                    List<Address> lists = new ArrayList<>();
                    listener.Success(what, lists);
                }
            } else if (what == MethodCode.EVENT_ADDADDRESS) {
                listener.Success(what, "添加成功");
            } else if (what == MethodCode.EVENT_EDITADDRESS) {
                listener.Success(what, "修改成功");
            } else if (what == MethodCode.EVENT_DELETEADDRESS) {
                listener.Success(what, "删除成功");
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
            }
        }
    }

    @Override
    protected void onFail(int what, Response response) {
        String error = response.getException().getMessage();
        listener.Error(what, error);
    }

}
