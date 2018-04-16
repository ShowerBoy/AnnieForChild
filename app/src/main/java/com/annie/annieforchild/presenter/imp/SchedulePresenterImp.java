package com.annie.annieforchild.presenter.imp;

import android.content.Context;

import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.schedule.TotalSchedule;
import com.annie.annieforchild.interactor.ScheduleInteractor;
import com.annie.annieforchild.interactor.imp.ScheduleInteractorImp;
import com.annie.annieforchild.presenter.SchedulePresenter;
import com.annie.annieforchild.ui.fragment.schedule.OfflineScheduleFragment;
import com.annie.annieforchild.ui.fragment.schedule.OnlineScheduleFragment;
import com.annie.annieforchild.view.ScheduleView;
import com.annie.baselibrary.base.BasePresenterImp;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * 课表与课业
 * Created by WangLei on 2018/2/28 0028
 */

public class SchedulePresenterImp extends BasePresenterImp implements SchedulePresenter {
    private Context context;
    private ScheduleView scheduleView;
    private ScheduleInteractor interactor;

    public SchedulePresenterImp(Context context, ScheduleView scheduleView) {
        this.context = context;
        this.scheduleView = scheduleView;
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
        scheduleView.showLoad();
        interactor.mySchedule(date);
    }


    @Override
    public void Success(int what, Object result) {
        scheduleView.dismissLoad();
        if (result != null) {
            if (what == MethodCode.EVENT_MYSCHEDULE) {
                TotalSchedule totalSchedule = (TotalSchedule) result;
                /**
                 * {@link OfflineScheduleFragment#onEventMainThread(JTMessage)}
                 * {@link OnlineScheduleFragment#onEventMainThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = totalSchedule;
                EventBus.getDefault().post(message);
            }
        }
    }

    @Override
    public void Error(int what, String error) {
        scheduleView.dismissLoad();
        scheduleView.showInfo(error);
    }
}
