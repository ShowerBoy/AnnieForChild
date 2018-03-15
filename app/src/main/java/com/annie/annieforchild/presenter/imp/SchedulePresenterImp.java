package com.annie.annieforchild.presenter.imp;

import android.content.Context;

import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.presenter.SchedulePresenter;
import com.annie.annieforchild.view.ScheduleView;
import com.annie.baselibrary.base.BasePresenterImp;

import org.greenrobot.eventbus.EventBus;

import java.util.logging.Handler;

/**
 * 课表
 * Created by WangLei on 2018/2/28 0028
 */

public class SchedulePresenterImp extends BasePresenterImp implements SchedulePresenter {
    private Context context;
    private ScheduleView scheduleView;

    public SchedulePresenterImp(Context context, ScheduleView scheduleView) {
        this.context = context;
        this.scheduleView = scheduleView;
    }

    @Override
    public void initViewAndData() {

    }

    /**
     * 根据日期获取课程详情
     * {@link com.annie.annieforchild.ui.fragment.schedule.OfflineFragment#onEventMainThread(JTMessage)}
     * {@link com.annie.annieforchild.ui.fragment.schedule.OnlineFragment#onEventMainThread(JTMessage)}
     */
    @Override
    public void getScheduleDetails(String time) {
        scheduleView.showLoad();
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                JTMessage message = new JTMessage();
                message.what = MethodCode.EVENT_SCHEDULE;
                message.obj = time;
                EventBus.getDefault().post(message);
                scheduleView.dismissLoad();
            }
        }, 1000);
    }


    @Override
    public void Success(int what, Object result) {

    }
}
