package com.annie.annieforchild.ui.fragment;

import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.presenter.SecondPresenter;
import com.annie.annieforchild.view.SecondView;
import com.annie.baselibrary.base.BaseFragment;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 课业2
 * Created by WangLei on 2018/1/12 0012
 */

public class SecondFragment2 extends BaseFragment implements SecondView, OnDateSelectedListener, View.OnClickListener {
    private static final DateFormat FORMATTER = SimpleDateFormat.getDateInstance();
    private SwipeRefreshLayout second_refresh_layout;
    private MaterialCalendarView calendarView;
    private TextView lessonOffline, lessonOnline, amPlanTime, amPlanName, amPlanPlace, pmPlanTime, pmPlanName, pmPlanPlace;
    private ImageView amPlanFinish, amPlanCheckLesson, pmPlanFinish, pmPlanCheckLesson;
    private RecyclerView myLessonList;
    private SecondPresenter presenter;

    public SecondFragment2() {

    }

    @Override
    protected void initData() {
//        presenter = new SecondPresenterImp(getContext(), this);
//        presenter.initViewAndData();
    }

    @Override
    protected void initView(View view) {
        second_refresh_layout = view.findViewById(R.id.second_refresh_layout);
        lessonOffline = view.findViewById(R.id.lesson_offline);
        lessonOnline = view.findViewById(R.id.lesson_online);
        amPlanTime = view.findViewById(R.id.am_plan_time);
        amPlanName = view.findViewById(R.id.am_plan_name);
        amPlanPlace = view.findViewById(R.id.am_plan_place);
        amPlanFinish = view.findViewById(R.id.am_plan_finish);
        amPlanCheckLesson = view.findViewById(R.id.am_plan_check_lesson);
        pmPlanTime = view.findViewById(R.id.pm_plan_time);
        pmPlanName = view.findViewById(R.id.pm_plan_name);
        pmPlanPlace = view.findViewById(R.id.pm_plan_place);
        pmPlanFinish = view.findViewById(R.id.pm_plan_finish);
        pmPlanCheckLesson = view.findViewById(R.id.pm_plan_check_lesson);
        myLessonList = view.findViewById(R.id.myLesson_list);
        calendarView = view.findViewById(R.id.calendarView);
        second_refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                second_refresh_layout.setRefreshing(false);
            }
        });
        calendarView.setSelectedDate(new Date());
        calendarView.setOnDateChangedListener(this);
        lessonOffline.setOnClickListener(this);
        lessonOnline.setOnClickListener(this);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        myLessonList.setLayoutManager(manager);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_second_fragment2;
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView materialCalendarView, @NonNull CalendarDay calendarDay, boolean b) {
        SystemUtils.show(getContext(), getSelectedDatesString());
    }

    private String getSelectedDatesString() {
        CalendarDay date = calendarView.getSelectedDate();
        if (date == null) {
            return "No Selection";
        }
        return FORMATTER.format(date.getDate());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lesson_offline:
                lessonOffline.setTextColor(getResources().getColor(R.color.button_orange));
                lessonOnline.setTextColor(getResources().getColor(R.color.text_black));
                offLine();
                break;
            case R.id.lesson_online:
                lessonOnline.setTextColor(getResources().getColor(R.color.button_orange));
                lessonOffline.setTextColor(getResources().getColor(R.color.text_black));
                onLine();
                break;
        }
    }

    private void onLine() {

    }

    private void offLine() {

    }

    @Override
    public void showInfo(String info) {
        SystemUtils.show(getContext(), info);
    }

    @Override
    public void showLoad() {

    }

    @Override
    public void dismissLoad() {

    }
}
