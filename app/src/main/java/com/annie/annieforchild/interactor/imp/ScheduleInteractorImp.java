package com.annie.annieforchild.interactor.imp;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.MethodType;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.ClassList;
import com.annie.annieforchild.bean.course.Course;
import com.annie.annieforchild.bean.course.OnlineCourse;
import com.annie.annieforchild.bean.grindear.GrindEarData;
import com.annie.annieforchild.bean.material.Material;
import com.annie.annieforchild.bean.schedule.Schedule;
import com.annie.annieforchild.bean.schedule.TotalSchedule;
import com.annie.annieforchild.bean.song.SongClassify;
import com.annie.annieforchild.interactor.ScheduleInteractor;
import com.annie.baselibrary.utils.NetUtils.NetWorkImp;
import com.annie.baselibrary.utils.NetUtils.RequestListener;
import com.annie.baselibrary.utils.NetUtils.request.FastJsonRequest;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.List;

/**
 * Created by wanglei on 2018/3/17.
 */

public class ScheduleInteractorImp extends NetWorkImp implements ScheduleInteractor {
    private Context context;
    private RequestListener listener;

    public ScheduleInteractorImp(Context context, RequestListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    public void mySchedule(String date) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.HOMEWORKAPI + MethodType.MYSCHEDULE, RequestMethod.POST);
        request.add("username", SystemUtils.defaultUsername);
        request.add("token", SystemUtils.token);
        request.add("date", date);
        addQueue(MethodCode.EVENT_MYSCHEDULE, request);
        startQueue();
    }

    @Override
    public void getMaterialClass(int type) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.HOMEWORKAPI + MethodType.GETALLMATERIALLIST, RequestMethod.POST);
        request.add("username", SystemUtils.defaultUsername);
        request.add("token", SystemUtils.token);
        request.add("type", type);
        if (type == 0) {
            addQueue(MethodCode.EVENT_GETALLMATERIALLIST1, request);
        } else if (type == 1) {
            addQueue(MethodCode.EVENT_GETALLMATERIALLIST2, request);
        } else if (type == 2) {
            addQueue(MethodCode.EVENT_GETALLMATERIALLIST3, request);
        }
        startQueue();
    }

    @Override
    public void addSchedule(int materialId, String startDate, int totalDays, String start, String end) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.HOMEWORKAPI + MethodType.ADDSCHEDULE, RequestMethod.POST);
        request.add("token", SystemUtils.token);
        request.add("username", SystemUtils.defaultUsername);
        request.add("materialId", materialId);
        request.add("startDate", startDate);
        request.add("totalDays", totalDays);
        request.add("start", start);
        request.add("stop", end);
        addQueue(MethodCode.EVENT_ADDSCHEDULE, request);
        startQueue();
    }

    @Override
    public void totalSchedule(String startDate, String endDate) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.HOMEWORKAPI + MethodType.TOTALSCHEDULE, RequestMethod.POST);
        request.add("token", SystemUtils.token);
        request.add("username", SystemUtils.defaultUsername);
        request.add("startDate", startDate);
        request.add("endDate", endDate);
        addQueue(MethodCode.EVENT_TOTALSCHEDULE, request);
        startQueue();
    }

    @Override
    public void editSchedule(int scheduleId, int materialId, String startDate, int totalDays, String start, String end) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.HOMEWORKAPI + MethodType.EDITSCHEDULE, RequestMethod.POST);
        request.add("token", SystemUtils.token);
        request.add("username", SystemUtils.defaultUsername);
        request.add("scheduleId", scheduleId);
        request.add("materialId", materialId);
        request.add("startDate", startDate);
        request.add("totalDays", totalDays);
        request.add("start", start);
        request.add("stop", end);
        addQueue(MethodCode.EVENT_EDITSCHEDULE, request);
        startQueue();
    }

    @Override
    public void deleteSchedule(int scheduleId) {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.HOMEWORKAPI + MethodType.DELETESCHEDULE, RequestMethod.POST);
        request.add("token", SystemUtils.token);
        request.add("username", SystemUtils.defaultUsername);
        request.add("scheduleId", scheduleId);
        addQueue(MethodCode.EVENT_DELETESCHEDULE, request);
        startQueue();
    }

    @Override
    public void myCoursesOnline() {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.HOMEWORKAPI + MethodType.MYCOURSESONLINE, RequestMethod.POST);
        request.add("token", SystemUtils.token);
        request.add("username", SystemUtils.defaultUsername);
        addQueue(MethodCode.EVENT_MYCOURSESONLINE, request);
        startQueue();
    }

    @Override
    public void myCoursesOffline() {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.HOMEWORKAPI + MethodType.MYCOURSESOFFLINE, RequestMethod.POST);
        request.add("token", SystemUtils.token);
        request.add("username", SystemUtils.defaultUsername);
        addQueue(MethodCode.EVENT_MYCOURSESOFFLINE, request);
        startQueue();
    }

    @Override
    public void myTeachingMaterials() {
        FastJsonRequest request = new FastJsonRequest(SystemUtils.mainUrl + MethodCode.HOMEWORKAPI + MethodType.MYTEACHINGMATERIALS, RequestMethod.POST);
        request.add("token", SystemUtils.token);
        request.add("username", SystemUtils.defaultUsername);
        addQueue(MethodCode.EVENT_MYTEACHINGMATERIALS, request);
        startQueue();
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
        int errorType = jsonObject.getInteger(MethodCode.ERRTYPE);
        String errorInfo = jsonObject.getString(MethodCode.ERRINFO);
        String data = jsonObject.getString(MethodCode.DATA);
        if (errorType == 3) {
            listener.Error(what, errorInfo);
        } else {
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
                JSONObject dataObj = jsonObject.getJSONObject(MethodCode.DATA);
                String optional = dataObj.getString("optional");
                List<Material> lists = JSON.parseArray(optional, Material.class);
                listener.Success(what, lists);
            }
        }
    }

    @Override
    protected void onFail(int what, Response response) {
        listener.Error(what, response.getException().getMessage());
    }
}
