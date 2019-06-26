package com.annie.annieforchild.interactor.imp;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.MethodType;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.ClassList;
import com.annie.annieforchild.bean.course.OnlineCourse;
import com.annie.annieforchild.bean.material.Material;
import com.annie.annieforchild.bean.schedule.TotalSchedule;
import com.annie.annieforchild.interactor.ScheduleInteractor;
import com.annie.annieforchild.ui.application.MyApplication;
import com.annie.baselibrary.utils.NetUtils.NetWorkImp;
import com.annie.baselibrary.utils.NetUtils.RequestListener;
import com.annie.baselibrary.utils.NetUtils.request.FastJsonRequest;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanglei on 2018/3/17.
 */

public class ScheduleInteractorImp extends NetWorkImp implements ScheduleInteractor {
    private Context context;
    private RequestListener listener;
    private MyApplication application;

    public ScheduleInteractorImp(Context context, RequestListener listener) {
        this.context = context;
        this.listener = listener;
        application = (MyApplication) context.getApplicationContext();
    }

    @Override
    public void mySchedule(String date) {
//        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.CLASSSCHEDULE + MethodType.MYSCHEDULE, RequestMethod.POST);
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.CLASSSCHEDULE + MethodType.MYSCHEDULEV2, RequestMethod.POST);
        request.add("username", application.getSystemUtils().getDefaultUsername());
        request.add("token", application.getSystemUtils().getToken());
        request.add("date", date);
        request.add(MethodCode.DEVICEID, application.getSystemUtils().getSn());
        request.add(MethodCode.DEVICETYPE, SystemUtils.deviceType);
        request.add(MethodCode.APPVERSION, SystemUtils.getVersionName(context));
        addQueue(MethodCode.EVENT_MYSCHEDULE, request);


//        startQueue();
    }

    @Override
    public void getMaterialClass(int type) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.PERSONAPI + MethodType.GETALLMATERIALLIST, RequestMethod.POST);
        request.add("username", application.getSystemUtils().getDefaultUsername());
        request.add("token", application.getSystemUtils().getToken());
        request.add("type", type);
        request.add(MethodCode.DEVICEID, application.getSystemUtils().getSn());
        request.add(MethodCode.DEVICETYPE, SystemUtils.deviceType);
        request.add(MethodCode.APPVERSION, SystemUtils.getVersionName(context));
        if (type == 0) {
            addQueue(MethodCode.EVENT_GETALLMATERIALLIST1, request);
        } else if (type == 1) {
            addQueue(MethodCode.EVENT_GETALLMATERIALLIST2, request);
        } else if (type == 2) {
            addQueue(MethodCode.EVENT_GETALLMATERIALLIST3, request);
        }
//        startQueue();
    }

    @Override
    public void addSchedule(int materialId, String startDate, int totalDays, String start, String end, int audioType, int audioSource) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.CLASSSCHEDULE + MethodType.ADDSCHEDULE, RequestMethod.POST);
        request.add("token", application.getSystemUtils().getToken());
        request.add("username", application.getSystemUtils().getDefaultUsername());
        request.add("materialId", materialId);
        request.add("startDate", startDate);
        request.add("totalDays", totalDays);
        request.add("start", start);
        request.add("stop", end);
        request.add("audioType", audioType);
        request.add("audioSource", audioSource);
        request.add(MethodCode.DEVICEID, application.getSystemUtils().getSn());
        request.add(MethodCode.DEVICETYPE, SystemUtils.deviceType);
        request.add(MethodCode.APPVERSION, SystemUtils.getVersionName(context));
        addQueue(MethodCode.EVENT_ADDSCHEDULE, request);
//        startQueue();
    }

    @Override
    public void totalSchedule(String startDate, String endDate) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.CLASSSCHEDULE + MethodType.TOTALSCHEDULE, RequestMethod.POST);
        request.add("token", application.getSystemUtils().getToken());
        request.add("username", application.getSystemUtils().getDefaultUsername());
        request.add("startDate", startDate);
        request.add("endDate", endDate);
        request.add(MethodCode.DEVICEID, application.getSystemUtils().getSn());
        request.add(MethodCode.DEVICETYPE, SystemUtils.deviceType);
        request.add(MethodCode.APPVERSION, SystemUtils.getVersionName(context));
        addQueue(MethodCode.EVENT_TOTALSCHEDULE, request);
//        startQueue();
    }

    @Override
    public void editSchedule(int scheduleId, int materialId, String startDate, int totalDays, String start, String end) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.CLASSSCHEDULE + MethodType.EDITSCHEDULE, RequestMethod.POST);
        request.add("token", application.getSystemUtils().getToken());
        request.add("username", application.getSystemUtils().getDefaultUsername());
        request.add("scheduleId", scheduleId);
        request.add("materialId", materialId);
        request.add("startDate", startDate);
        request.add("totalDays", totalDays);
        request.add("start", start);
        request.add("stop", end);
        request.add(MethodCode.DEVICEID, application.getSystemUtils().getSn());
        request.add(MethodCode.DEVICETYPE, SystemUtils.deviceType);
        request.add(MethodCode.APPVERSION, SystemUtils.getVersionName(context));
        addQueue(MethodCode.EVENT_EDITSCHEDULE, request);
//        startQueue();
    }

    @Override
    public void deleteSchedule(int scheduleId) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.CLASSSCHEDULE + MethodType.DELETESCHEDULE, RequestMethod.POST);
        request.add("token", application.getSystemUtils().getToken());
        request.add("username", application.getSystemUtils().getDefaultUsername());
        request.add("scheduleId", scheduleId);
        request.add(MethodCode.DEVICEID, application.getSystemUtils().getSn());
        request.add(MethodCode.DEVICETYPE, SystemUtils.deviceType);
        request.add(MethodCode.APPVERSION, SystemUtils.getVersionName(context));
        addQueue(MethodCode.EVENT_DELETESCHEDULE, request);
//        startQueue();
    }

    @Override
    public void myCoursesOnline() {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.CLASSSCHEDULE + MethodType.MYCOURSESONLINE, RequestMethod.POST);
        request.add("token", application.getSystemUtils().getToken());
        request.add("username", application.getSystemUtils().getDefaultUsername());
        request.add(MethodCode.DEVICEID, application.getSystemUtils().getSn());
        request.add(MethodCode.DEVICETYPE, SystemUtils.deviceType);
        request.add(MethodCode.APPVERSION, SystemUtils.getVersionName(context));
        addQueue(MethodCode.EVENT_MYCOURSESONLINE, request);
//        startQueue();
    }

    @Override
    public void myCoursesOffline() {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.CLASSSCHEDULE + MethodType.MYCOURSESOFFLINE, RequestMethod.POST);
        request.add("token", application.getSystemUtils().getToken());
        request.add("username", application.getSystemUtils().getDefaultUsername());
        request.add(MethodCode.DEVICEID, application.getSystemUtils().getSn());
        request.add(MethodCode.DEVICETYPE, SystemUtils.deviceType);
        request.add(MethodCode.APPVERSION, SystemUtils.getVersionName(context));
        addQueue(MethodCode.EVENT_MYCOURSESOFFLINE, request);
        startQueue();
    }

    @Override
    public void myTeachingMaterials() {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.CLASSSCHEDULE + MethodType.MYTEACHINGMATERIALS, RequestMethod.POST);
        request.add("token", application.getSystemUtils().getToken());
        request.add("username", application.getSystemUtils().getDefaultUsername());
        request.add(MethodCode.DEVICEID, application.getSystemUtils().getSn());
        request.add(MethodCode.DEVICETYPE, SystemUtils.deviceType);
        request.add(MethodCode.APPVERSION, SystemUtils.getVersionName(context));
        addQueue(MethodCode.EVENT_MYTEACHINGMATERIALS, request);
//        startQueue();
    }

    @Override
    public void myCalendar(String date) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.CLASSSCHEDULE + MethodType.MYCALENDAR, RequestMethod.POST);
        request.add("token", application.getSystemUtils().getToken());
        request.add("username", application.getSystemUtils().getDefaultUsername());
        request.add("date", date);
        request.add(MethodCode.DEVICEID, application.getSystemUtils().getSn());
        request.add(MethodCode.DEVICETYPE, SystemUtils.deviceType);
        request.add(MethodCode.APPVERSION, SystemUtils.getVersionName(context));
        addQueue(MethodCode.EVENT_MYCALENDAR, request);
//        startQueue();
    }

    @Override
    public void monthCalendar(String date) {
//        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.CLASSSCHEDULE + MethodType.MONTHCALENDAR, RequestMethod.POST);
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.CLASSSCHEDULE + MethodType.MONTHCALENDARV2, RequestMethod.POST);
        request.add("token", application.getSystemUtils().getToken());
        request.add("username", application.getSystemUtils().getDefaultUsername());
        request.add("date", date);
        request.add(MethodCode.DEVICEID, application.getSystemUtils().getSn());
        request.add(MethodCode.DEVICETYPE, SystemUtils.deviceType);
        request.add(MethodCode.APPVERSION, SystemUtils.getVersionName(context));
        addQueue(MethodCode.EVENT_MONTHCALENDAR, request);
//        startQueue();
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
        int status = jsonObject.getInteger(MethodCode.STATUS);
        String msg = jsonObject.getString(MethodCode.MSG);
        String data = jsonObject.getString(MethodCode.DATA);
        if (status == 0) {
            if (what == MethodCode.EVENT_MYSCHEDULE) {
                TotalSchedule totalSchedule = JSON.parseObject(data, TotalSchedule.class);
                listener.Success(what, totalSchedule);
            } else if (what == MethodCode.EVENT_GETALLMATERIALLIST1) {
                List<ClassList> lists = JSON.parseArray(data, ClassList.class);
                listener.Success(what, lists);
            } else if (what == MethodCode.EVENT_GETALLMATERIALLIST2) {
                List<ClassList> lists = JSON.parseArray(data, ClassList.class);
                listener.Success(what, lists);
            } else if (what == MethodCode.EVENT_GETALLMATERIALLIST3) {
                List<ClassList> lists = JSON.parseArray(data, ClassList.class);
                listener.Success(what, lists);
            } else if (what == MethodCode.EVENT_ADDSCHEDULE) {
                listener.Success(what, "添加成功");
            } else if (what == MethodCode.EVENT_TOTALSCHEDULE) {
                TotalSchedule totalSchedule = JSON.parseObject(data, TotalSchedule.class);
                listener.Success(what, totalSchedule);
            } else if (what == MethodCode.EVENT_EDITSCHEDULE) {
                listener.Success(what, "修改成功");
            } else if (what == MethodCode.EVENT_DELETESCHEDULE) {
                listener.Success(what, "删除成功");
            } else if (what == MethodCode.EVENT_MYCOURSESONLINE) {
                List<OnlineCourse> lists = JSON.parseArray(data, OnlineCourse.class);
                listener.Success(what, lists);
            } else if (what == MethodCode.EVENT_MYCOURSESOFFLINE) {

            } else if (what == MethodCode.EVENT_MYTEACHINGMATERIALS) {
//                JSONObject dataObj = jsonObject.getJSONObject(MethodCode.DATA);
//                String optional = dataObj.getString("optional");
                List<Material> lists = JSON.parseArray(data, Material.class);
                listener.Success(what, lists);
            } else if (what == MethodCode.EVENT_MYCALENDAR) {
                List<String> lists = JSON.parseArray(data, String.class);
                listener.Success(what, lists);
            } else if (what == MethodCode.EVENT_MONTHCALENDAR) {
                List<TotalSchedule> lists;
                if (data == null) {
                    lists = new ArrayList<>();
                } else {
                    lists = JSON.parseArray(data, TotalSchedule.class);
                }
                listener.Success(what, lists);
            }
        } else {
            listener.Error(what, status, msg);
        }
    }

    @Override
    protected void onFail(int what, Response response) {
        listener.Fail(what, "系统发生错误");
    }
}
