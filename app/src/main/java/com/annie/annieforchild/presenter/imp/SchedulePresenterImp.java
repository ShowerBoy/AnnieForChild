package com.annie.annieforchild.presenter.imp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.annie.annieforchild.Utils.ActivityCollector;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.Utils.service.MusicService;
import com.annie.annieforchild.bean.ClassList;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.course.OnlineCourse;
import com.annie.annieforchild.bean.material.Material;
import com.annie.annieforchild.bean.schedule.TotalSchedule;
import com.annie.annieforchild.interactor.ScheduleInteractor;
import com.annie.annieforchild.interactor.imp.ScheduleInteractorImp;
import com.annie.annieforchild.presenter.SchedulePresenter;
import com.annie.annieforchild.ui.activity.login.LoginActivity;
import com.annie.annieforchild.ui.application.MyApplication;
import com.annie.annieforchild.ui.fragment.schedule.OfflineScheduleFragment;
import com.annie.annieforchild.ui.fragment.schedule.OnlineScheduleFragment;
import com.annie.annieforchild.view.ScheduleView;
import com.annie.baselibrary.base.BasePresenterImp;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import static android.content.Context.MODE_MULTI_PROCESS;
import static android.content.Context.MODE_PRIVATE;

/**
 * 课表与课业
 * Created by WangLei on 2018/2/28 0028
 */

public class SchedulePresenterImp extends BasePresenterImp implements SchedulePresenter {
    private Context context;
    private ScheduleView scheduleView;
    private ScheduleInteractor interactor;
    private String date;
    private MyApplication application;

    public SchedulePresenterImp(Context context, ScheduleView scheduleView) {
        this.context = context;
        this.scheduleView = scheduleView;
        application = (MyApplication) context.getApplicationContext();
    }

    @Override
    public void initViewAndData() {
        interactor = new ScheduleInteractorImp(context, this);
    }

    /**
     * 根据日期获取课程详情
     */
    @Override
    public void getScheduleDetails(String date) {
        this.date = date;
        scheduleView.showLoad();
        interactor.mySchedule(date);
    }


    @Override
    public void getMaterialClass(int type) {
        interactor.getMaterialClass(type);
    }

    /**
     * 添加课表任务
     *
     * @param materialId
     * @param startDate
     * @param totalDays
     * @param start
     * @param end
     */
    @Override
    public void addSchedule(int materialId, String startDate, int totalDays, String start, String end, int audioType, int audioSource) {
        scheduleView.showLoad();
        interactor.addSchedule(materialId, startDate, totalDays, start, end, audioType, audioSource);
    }

    /**
     * 总课表
     *
     * @param startDate
     * @param endDate
     */
    @Override
    public void totalSchedule(String startDate, String endDate) {
        scheduleView.showLoad();
        interactor.totalSchedule(startDate, endDate);
    }

    /**
     * 编辑课表
     *
     * @param scheduleId
     * @param materialId
     * @param startDate
     * @param totalDays
     * @param start
     * @param end
     */
    @Override
    public void editSchedule(int scheduleId, int materialId, String startDate, int totalDays, String start, String end) {
        scheduleView.showLoad();
        interactor.editSchedule(scheduleId, materialId, startDate, totalDays, start, end);
    }

    /**
     * 删除课表
     *
     * @param scheduleId
     */
    @Override
    public void deleteSchedule(int scheduleId) {
        scheduleView.showLoad();
        interactor.deleteSchedule(scheduleId);
    }

    /**
     * 获取线上课程
     */
    @Override
    public void myCoursesOnline() {
        interactor.myCoursesOnline();
    }

    /**
     * 获取线下课程
     */
    @Override
    public void myCoursesOffline() {
        interactor.myCoursesOffline();
    }

    /**
     * 我的教材
     */
    @Override
    public void myTeachingMaterials() {
        scheduleView.showLoad();
        interactor.myTeachingMaterials();
    }

    @Override
    public void myCalendar(String date) {
//        scheduleView.showLoad();
        interactor.myCalendar(date);
    }

    @Override
    public void monthCalendar(String date) {
        interactor.monthCalendar(date);
    }


    @Override
    public void Success(int what, Object result) {
        scheduleView.dismissLoad();
        if (result != null) {
            if (what == MethodCode.EVENT_MYSCHEDULE) {
                TotalSchedule totalSchedule = (TotalSchedule) result;
                /**
                 * {@link com.annie.annieforchild.ui.activity.lesson.ScheduleActivity2#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = totalSchedule;
                message.obj2 = date;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_GETALLMATERIALLIST1) {
                List<ClassList> lists = (List<ClassList>) result;
                /**
                 * {@link com.annie.annieforchild.ui.fragment.selectmaterial.SelectGrindEarFragment#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = lists;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_GETALLMATERIALLIST2) {
                List<ClassList> lists = (List<ClassList>) result;
                /**
                 * {@link com.annie.annieforchild.ui.fragment.selectmaterial.SelectReadingFragment#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = lists;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_GETALLMATERIALLIST3) {
                List<ClassList> lists = (List<ClassList>) result;
                /**
                 * {@link com.annie.annieforchild.ui.fragment.selectmaterial.SelectSpokenFragment#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = lists;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_ADDSCHEDULE) {
                /**
                 * {@link com.annie.annieforchild.ui.activity.lesson.AddOnlineScheActivity#onMainEventThread(JTMessage)}
                 * {@link com.annie.annieforchild.ui.activity.lesson.ScheduleActivity2#onMainEventThread(JTMessage)}
                 * {@link com.annie.annieforchild.ui.activity.lesson.TaskDetailsActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = result;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_EDITSCHEDULE) {
                /**
                 * {@link com.annie.annieforchild.ui.activity.lesson.AddOnlineScheActivity#onMainEventThread(JTMessage)}
                 * {@link com.annie.annieforchild.ui.activity.lesson.ScheduleActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = result;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_DELETESCHEDULE) {
                /**
                 * {@link com.annie.annieforchild.ui.activity.lesson.ScheduleActivity2#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = result;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_TOTALSCHEDULE) {
                TotalSchedule totalSchedule = (TotalSchedule) result;
                /**
                 * {@link com.annie.annieforchild.ui.activity.lesson.TotalScheduleActivity#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = totalSchedule;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_MYCOURSESONLINE) {
                List<OnlineCourse> lists = (List<OnlineCourse>) result;
                /**
                 * {@link com.annie.annieforchild.ui.fragment.course.OnlineCourseFragment#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = lists;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_MYTEACHINGMATERIALS) {
                List<Material> lists = (List<Material>) result;
                /**
                 * {@link com.annie.annieforchild.ui.fragment.material.OptionalMaterialFragment#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = lists;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_MYCALENDAR) {
                List<String> lists = (List<String>) result;
                /**
                 * {@link com.annie.annieforchild.ui.activity.lesson.ScheduleActivity2#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = lists;
                EventBus.getDefault().post(message);
            } else if (what == MethodCode.EVENT_MONTHCALENDAR) {
                List<TotalSchedule> lists = (List<TotalSchedule>) result;
                /**
                 * {@link com.annie.annieforchild.ui.activity.lesson.ScheduleActivity2#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = lists;
                EventBus.getDefault().post(message);
            }
        }
    }

    @Override
    public void Error(int what, int status, String error) {
        scheduleView.dismissLoad();
        if (status == 1) {
            //该账号已在别处登陆
            if (!application.getSystemUtils().isReLogin()) {
                application.getSystemUtils().setReLogin(true);
                scheduleView.showInfo(error);

                JTMessage message = new JTMessage();
                message.what = MethodCode.EVENT_RELOGIN;
                message.obj = 1;
                EventBus.getDefault().post(message);

                SharedPreferences preferences = context.getSharedPreferences("userInfo", MODE_PRIVATE | MODE_MULTI_PROCESS);
                SharedPreferences.Editor editor = preferences.edit();
                editor.remove("phone");
                editor.remove("psd");
                editor.commit();
                application.getSystemUtils().getPhoneSN().setUsername(null);
                application.getSystemUtils().getPhoneSN().setLastlogintime(null);
                application.getSystemUtils().getPhoneSN().setSystem(null);
                application.getSystemUtils().getPhoneSN().setBitcode(null);
                application.getSystemUtils().setDefaultUsername(null);
                application.getSystemUtils().setToken(null);
                application.getSystemUtils().getPhoneSN().save();
                application.getSystemUtils().setOnline(false);
                ActivityCollector.finishAll();
                Intent intent2 = new Intent(context, LoginActivity.class);
                context.startActivity(intent2);
                return;
            } else {
                return;
            }
        } else if (status == 2) {
            //升级

        } else if (status == 3) {
            //参数错误
            SystemUtils.setDefaltSn(context, application);
        } else if (status == 4) {
            //服务器错误


        } else if (status == 5) {
            //账号或密码错误

        } else if (status == 6) {
            //获取验证码失败

        } else if (status == 7) {
            Toast.makeText(application.getApplicationContext(), error, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void Fail(int what, String error) {
        if (scheduleView != null) {
            scheduleView.dismissLoad();
        }
        Toast.makeText(application.getApplicationContext(), error, Toast.LENGTH_SHORT).show();
    }
}
