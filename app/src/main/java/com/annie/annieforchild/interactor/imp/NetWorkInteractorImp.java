package com.annie.annieforchild.interactor.imp;

import android.content.Context;
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
import com.annie.annieforchild.bean.net.NetSuggest;
import com.annie.annieforchild.bean.net.NetWork;
import com.annie.annieforchild.bean.net.PreheatConsult;
import com.annie.annieforchild.bean.net.PreheatConsultList;
import com.annie.annieforchild.bean.net.WechatBean;
import com.annie.annieforchild.bean.net.netexpclass.NetExpClass;
import com.annie.annieforchild.bean.net.netexpclass.NetExp_new;
import com.annie.annieforchild.bean.net.netexpclass.Video_first;
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
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.NETCLASSAPI + MethodType.GETNETHOMEDATA, RequestMethod.POST);
        request.add("token", application.getSystemUtils().getToken());
//        request.add("username", "100506478");
        request.add("username", application.getSystemUtils().getDefaultUsername());
        addQueue(MethodCode.EVENT_GETNETHOMEDATA, request);
//        startQueue();
    }

    @Override
    public void getNetSuggest(int netid) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.NETCLASSAPI + MethodType.GETNETSUGGEST, RequestMethod.POST);
        request.add("token", application.getSystemUtils().getToken());
        request.add("username", application.getSystemUtils().getDefaultUsername());
        request.add("netid", netid);
        addQueue(MethodCode.EVENT_GETNETSUGGEST, request);
//        startQueue();
    }

    @Override
    public void getMyNetClass() {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.NETCLASSAPI + MethodType.GETMYNETCLASS, RequestMethod.POST);
        request.add("token", application.getSystemUtils().getToken());
        request.add("username", application.getSystemUtils().getDefaultUsername());
        addQueue(MethodCode.EVENT_GETMYNETCLASS, request);
//        startQueue();
    }

    @Override
    public void confirmOrder(int netid) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.NETCLASSAPI + MethodType.CONFIRMORDER, RequestMethod.POST);
        request.add("token", application.getSystemUtils().getToken());
        request.add("username", application.getSystemUtils().getDefaultUsername());
        request.add("netid", netid);
        addQueue(MethodCode.EVENT_CONFIRMORDER, request);
//        startQueue();
    }

    @Override
    public void getMyAddress() {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.NETCLASSAPI + MethodType.GETMYADDRESS, RequestMethod.POST);
        request.add("token", application.getSystemUtils().getToken());
        request.add("username", application.getSystemUtils().getDefaultUsername());
        addQueue(MethodCode.EVENT_GETMYADDRESS, request);
//        startQueue();
    }

    @Override
    public void addAddress(String name, String phone, String address) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.NETCLASSAPI + MethodType.ADDADDRESS, RequestMethod.POST);
        request.add("token", application.getSystemUtils().getToken());
        request.add("username", application.getSystemUtils().getDefaultUsername());
        request.add("name", name);
        request.add("phone", phone);
        request.add("address", address);
        addQueue(MethodCode.EVENT_ADDADDRESS, request);
//        startQueue();
    }

    @Override
    public void editAddress(int addressid, String name, String phone, String address) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.NETCLASSAPI + MethodType.EDITADDRESS, RequestMethod.POST);
        request.add("token", application.getSystemUtils().getToken());
        request.add("username", application.getSystemUtils().getDefaultUsername());
        request.add("addressid", addressid);
        request.add("name", name);
        request.add("phone", phone);
        request.add("address", address);
        addQueue(MethodCode.EVENT_EDITADDRESS, request);
//        startQueue();
    }

    @Override
    public void deleteAddress(int addressid) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.NETCLASSAPI + MethodType.DELETEADDRESS, RequestMethod.POST);
        request.add("token", application.getSystemUtils().getToken());
        request.add("username", application.getSystemUtils().getDefaultUsername());
        request.add("addressid", addressid);
        addQueue(MethodCode.EVENT_DELETEADDRESS, request);
//        startQueue();
    }

    @Override
    public void buyNetWork(int netid, int addressid, int ismaterial, int payment, String giftid) {
        this.payment = payment;
//        JavaBeanRequest request = new JavaBeanRequest(SystemUtils.mainUrl + MethodCode.NETCLASSAPI + MethodType.BUYNETWORK, String.class);
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.NETCLASSAPI + MethodType.BUYNETWORK, RequestMethod.POST);
//        WLStringRequest request = new WLStringRequest(SystemUtils.mainUrl + MethodCode.NETCLASSAPI + MethodType.BUYNETWORK, RequestMethod.POST);
        request.add("token", application.getSystemUtils().getToken());
        request.add("username", application.getSystemUtils().getDefaultUsername());
        request.add("netid", netid);
        request.add("addressid", addressid);
        request.add("ismaterial", ismaterial);
        request.add("payment", payment);
//        request.add("giftid", giftid);
        addQueue(MethodCode.EVENT_BUYNETWORK, request);
//        startQueue();
    }

    @Override
    public void getNetDetails(int netid) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.NETCLASSAPI + MethodType.GETNETDETAILS, RequestMethod.POST);
        request.add("token", application.getSystemUtils().getToken());
        request.add("username", application.getSystemUtils().getDefaultUsername());
        request.add("netid", netid);
        addQueue(MethodCode.EVENT_GETNETDETAILS, request);
//        startQueue();
    }

    @Override
    public void getNetExpDetails(int netid) {
//        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.NETCLASSAPI + MethodType.GETNETEXPDETAILS, RequestMethod.POST);
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.NETCLASSAPI + MethodType.GETNETEXPDETAILS, RequestMethod.POST);
        request.add("token", application.getSystemUtils().getToken());
        request.add("username", application.getSystemUtils().getDefaultUsername());
        request.add("netid", netid);
        addQueue(MethodCode.EVENT_GETNETEXPDETAILS, request);
//        startQueue();
    }
    @Override
    public void getNetExpDetails_new(int netid) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.NETCLASSAPI + MethodType.GETNETEXPDETAILS_NEW, RequestMethod.POST);
        request.add("token", application.getSystemUtils().getToken());
        request.add("username", application.getSystemUtils().getDefaultUsername());
        request.add("netid", netid);
//        request.add("netid", 22);
        addQueue(MethodCode.EVENT_GETNETEXPDETAILS_NEW, request);
//        startQueue();
    }

    @Override
    public void getLesson(String lessonid,int type) {
        FastJsonRequest request;
        if(type==4){//彩虹条新版本
            request= new FastJsonRequest(SystemUtils.mainUrl + MethodCode.NETCLASSAPI + MethodType.GETSPECIALLESSON_NEW, RequestMethod.POST);
            request.add("fid", lessonid);
        }else{
            request= new FastJsonRequest(SystemUtils.mainUrl + MethodCode.NETCLASSAPI + MethodType.GETLESSON, RequestMethod.POST);
            request.add("lessonid", lessonid);
        }
        request.add("token", application.getSystemUtils().getToken());
        request.add("username", application.getSystemUtils().getDefaultUsername());

        addQueue(MethodCode.EVENT_GETLESSON, request);
//        startQueue();
    }

    @Override
    public void buySuccess() {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.NETCLASSAPI + MethodType.BUYSUCCESS, RequestMethod.POST);
        request.add("token", application.getSystemUtils().getToken());
        request.add("username", application.getSystemUtils().getDefaultUsername());
        addQueue(MethodCode.EVENT_BUYSUCCESS, request);
//        startQueue();
    }

    @Override
    public void getNetPreheatConsult(String lessonid) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.NETCLASSAPI + MethodType.GETPREHEATCONSULT, RequestMethod.POST);
        request.add("token", application.getSystemUtils().getToken());
        request.add("username", application.getSystemUtils().getDefaultUsername());
        request.add("lessonid", Integer.parseInt(lessonid));
        addQueue(MethodCode.EVENT_GETPREHEATCONSULT, request);
//        startQueue();
    }

    @Override
    public void getListeningAndReading(String week, String classid, int tag, int classify) {
        this.tag = tag;
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.NETCLASSAPI + MethodType.GETLISTENANDREAD, RequestMethod.POST);
        request.add("token", application.getSystemUtils().getToken());
        request.add("username", application.getSystemUtils().getDefaultUsername());
        request.add("classid", classid);
        request.add("week", week);
        request.add("classify", classify);
        addQueue(MethodCode.EVENT_GETLISTENANDREAD + 80000 + tag, request);
//        startQueue();
    }

    @Override
    public void buynum(int netid, int type) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.NETCLASSAPI + MethodType.BUYNUM, RequestMethod.POST);
        request.add("token", application.getSystemUtils().getToken());
        request.add("username", application.getSystemUtils().getDefaultUsername());
        request.add("netid", netid);
        if (type == 1) {//1:netsuggest请求 2：confirmorder请求
            addQueue(MethodCode.EVENT_BUYNUM, request);
        } else {
            addQueue(MethodCode.EVENT_BUYNUM1, request);
        }
//        startQueue();
    }

    @Override
    public void OrderQuery(String tradeno, String outtradeno, int type) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.NETCLASSAPI + MethodType.ORDERQUERY, RequestMethod.POST);
        request.add("token", application.getSystemUtils().getToken());
        request.add("username", application.getSystemUtils().getDefaultUsername());
        request.add("tradeno", tradeno);
        request.add("outtradeno", outtradeno);
        request.add("type", type);
        addQueue(MethodCode.EVENT_ORDERQUERY, request);
//        startQueue();
    }

    @Override
    public void getWeiClass(String fid,int type) {
        FastJsonRequest request;
        if(type==1){//微课堂
            request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.NETCLASSAPI + MethodType.GETWEICLASS, RequestMethod.POST);
         }else if(type==4){//新版本的开班活动等
            request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.NETCLASSAPI + MethodType.GETSPECIAL_NEW, RequestMethod.POST);
        } else{
            request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.NETCLASSAPI + MethodType.GETCLASSANALYSIS, RequestMethod.POST);
        }
        request.add("token", application.getSystemUtils().getToken());
        request.add("username", application.getSystemUtils().getDefaultUsername());
        request.add("fid", fid);
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
                MyNetClass myNetClass = JSON.parseObject(data, MyNetClass.class);
                listener.Success(what, myNetClass);
//                JSONObject dataobj = jsonObject.getJSONObject(MethodCode.DATA);
//                if (dataobj != null) {
//                    String myList = dataobj.getString("myList");
//                    if (myList != null) {
//                        List<NetClass> lists = JSON.parseArray(myList, NetClass.class);
//                        if (lists != null) {
//                            listener.Success(what, lists);
//                        } else {
//                            listener.Success(what, new ArrayList<NetClass>());
//                        }
//                    } else {
//                        listener.Success(what, new ArrayList<NetClass>());
//                    }
//                } else {
//                    listener.Success(what, new ArrayList<NetClass>());
//                }
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
                Log.e("111",data+"");
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
            }else if (what == MethodCode.EVENT_GETNETEXPDETAILS_NEW) {
                NetExp_new netExpClass = JSON.parseObject(data, NetExp_new.class);
                listener.Success(what, netExpClass);
            } else if (what == MethodCode.EVENT_GETPREHEATCONSULT) {
//                JSONObject jsonObject1=JSON.parseObject(data);
//                HashMap<String,List<PreheatConsultList>> map=new HashMap<>();
//                for (Map.Entry<String, Object> entry : jsonObject1.entrySet()){
//                   Log.e("----",entry.getKey() + ":" + entry.getValue());
//                }
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
            } else if (what == MethodCode.EVENT_ORDERQUERY) {
                String trade_status = "";
                if (payment == 0) {
                    JSONObject jsonObject1 = JSON.parseObject(data);
                    String response1 = jsonObject1.getString("alipay_trade_query_response");
                    JSONObject jsonObject2 = JSON.parseObject(response1);
                    trade_status = jsonObject2.getString("trade_status");
                    listener.Success(what, trade_status);
                } else {
                    JSONObject jsonObject1 = JSON.parseObject(data);
                    trade_status = jsonObject1.getString("trade_state");
                    listener.Success(what, trade_status);
                }
            }else if(what ==MethodCode.EVENT_GETWEICLASS){
                List<Video_first> lists;
                if (data != null) {
                    lists = JSON.parseArray(data, Video_first.class);
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
