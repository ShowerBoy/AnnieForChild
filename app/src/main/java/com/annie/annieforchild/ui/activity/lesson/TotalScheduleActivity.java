package com.annie.annieforchild.ui.activity.lesson;

import android.app.Dialog;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.views.WeekViewActivity;
import com.annie.annieforchild.Utils.views.weekview.DateTimeInterpreter;
import com.annie.annieforchild.Utils.views.weekview.MonthLoader;
import com.annie.annieforchild.Utils.views.weekview.WeekView;
import com.annie.annieforchild.Utils.views.weekview.WeekViewEvent;
import com.annie.annieforchild.bean.DateBean;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.WeekBean;
import com.annie.annieforchild.presenter.SchedulePresenter;
import com.annie.annieforchild.presenter.imp.SchedulePresenterImp;
import com.annie.annieforchild.view.ScheduleView;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * 总课表
 * Created by WangLei on 2018/3/8 0008
 */

public class TotalScheduleActivity extends WeekViewActivity implements MonthLoader.MonthChangeListener, WeekView.EventClickListener, WeekView.EmptyViewClickListener, ScheduleView {
    private WeekView weekView;
    private static final int TYPE_DAY_VIEW = 1;
    private static final int TYPE_THREE_DAY_VIEW = 2;
    private static final int TYPE_WEEK_VIEW = 3;
    private int mWeekViewType = TYPE_THREE_DAY_VIEW;
    int year, month, day;
    String startTime, endTime;
    boolean isLeap;

    private AlertHelper helper;
    private Dialog dialog;
    private SchedulePresenter presenter;
    private List<WeekBean> date_lists;
    private long oneDay = 1000 * 60 * 60 * 24L;
    Calendar calendar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_schedule);
        EventBus.getDefault().register(this);
        weekView = findViewById(R.id.weekView);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH);

        helper = new AlertHelper(this);
        dialog = helper.LoadingDialog();


        initData();

        weekView.setNumberOfVisibleDays(5);
        weekView.setMonthChangeListener(this);
        weekView.setOnEventClickListener(this);
        weekView.setEventLongPressListener(this);
        weekView.setEmptyViewLongPressListener(this);
        weekView.setEmptyViewClickListener(this);
        weekView.setDateTimeInterpreter(new DateTimeInterpreter() {
            @Override
            public String interpretDate(Calendar date) {
                if (date.get(Calendar.YEAR) == year) {
                    if (date.get(Calendar.MONTH) == month - 1) {
//                        if (date.get(Calendar.DAY_OF_MONTH)==day){
//
//                        }
                        SimpleDateFormat weekdayNameFormat = new SimpleDateFormat("EEE", Locale.getDefault());
                        String weekday = weekdayNameFormat.format(date.getTime());
                        SimpleDateFormat format = new SimpleDateFormat(" M/d", Locale.getDefault());

                        // All android api level do not have a standard way of getting the first letter of
                        // the week day name. Hence we get the first char programmatically.
                        // Details: http://stackoverflow.com/questions/16959502/get-one-letter-abbreviation-of-week-day-of-a-date-in-java#answer-16959657
                        if (false)
                            weekday = String.valueOf(weekday.charAt(0));
                        String text = weekday.toUpperCase().trim() + "\r\n" + format.format(date.getTime()).trim();
//                        String text = format.format(date.getTime()).trim();
                        return text;
                    } else {
                        return "";
                    }
                } else {
                    return "";
                }
            }

            //String.format("%02d:00", hour)
            //hour > 11 ? (hour - 12) + " PM" : (hour == 0 ? "12 AM" : hour + " AM")
            @Override
            public String interpretTime(int hour) {
                return String.format("%02d:00", hour);
            }

        });
        weekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics()));
        weekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));
        weekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));
        weekView.goToToday();
    }

    private void initData() {
        presenter = new SchedulePresenterImp(this, this);
        presenter.initViewAndData();
        if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
            isLeap = true;
        } else {
            isLeap = false;
        }
        initDateList();
        presenter.totalSchedule(startTime, endTime);
    }

    private void initDateList() {
        SimpleDateFormat format2 = new SimpleDateFormat("MM");
        String months = format2.format(new Date());
        startTime = year + months + "01";

        date_lists = new ArrayList<>();
        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
            for (int i = 0; i < 31; i++) {
                WeekBean bean = new WeekBean();
                bean.setYear(year);
                bean.setMonth(month);
                bean.setDay(i + 1);
                date_lists.add(bean);
            }
            endTime = year + months + "31";
        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            for (int i = 0; i < 30; i++) {
                WeekBean bean = new WeekBean();
                bean.setYear(year);
                bean.setMonth(month);
                bean.setDay(i + 1);
                date_lists.add(bean);
            }
            endTime = year + months + "30";
        } else if (month == 2) {
            if (isLeap) {
                for (int i = 0; i < 29; i++) {
                    WeekBean bean = new WeekBean();
                    bean.setYear(year);
                    bean.setMonth(month);
                    bean.setDay(i + 1);
                    date_lists.add(bean);
                }
                endTime = year + months + "29";
            } else {
                for (int i = 0; i < 28; i++) {
                    WeekBean bean = new WeekBean();
                    bean.setYear(year);
                    bean.setMonth(month);
                    bean.setDay(i + 1);
                    date_lists.add(bean);
                }
                endTime = year + months + "28";
            }
        }
    }

    @Override
    public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {
        if (newYear == year) {
            if (newMonth == month) {
                List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();

                Calendar startTime = Calendar.getInstance();
                startTime.set(Calendar.HOUR_OF_DAY, 2);
                startTime.set(Calendar.MINUTE, 0);
                startTime.set(Calendar.MONTH, newMonth - 1);
//        startTime.set(Calendar.DAY_OF_MONTH, 10);
                startTime.set(Calendar.YEAR, newYear);

                Calendar endTime = Calendar.getInstance();
                endTime.set(Calendar.HOUR_OF_DAY, 4);
                endTime.set(Calendar.MINUTE, 30);
                endTime.set(Calendar.MONTH, newMonth - 1);
//        startTime.set(Calendar.DAY_OF_MONTH, 10);
                endTime.set(Calendar.YEAR, newYear);
//        Calendar endTime = (Calendar) startTime.clone();
//        endTime.add(Calendar.HOUR, 2);
////        endTime.set(Calendar.MONTH, newMonth - 1);
                WeekViewEvent event = new WeekViewEvent(1, getEventTitle(startTime), startTime, endTime);
                event.setColor(getResources().getColor(R.color.event_color_01));
                events.add(event);

//        startTime = Calendar.getInstance();
//        startTime.set(Calendar.HOUR_OF_DAY, 2);
//        startTime.set(Calendar.MINUTE, 30);
//        startTime.set(Calendar.MONTH, newMonth-1);
//        startTime.set(Calendar.YEAR, newYear);
//        endTime = (Calendar) startTime.clone();
//        endTime.set(Calendar.HOUR_OF_DAY, 4);
//        endTime.set(Calendar.MINUTE, 30);
//        endTime.set(Calendar.MONTH, newMonth-1);
//        event = new WeekViewEvent(10, getEventTitle(startTime), startTime, endTime);
//        event.setColor(getResources().getColor(R.color.event_color_02));
//        events.add(event);

                return events;
            } else {
                return new ArrayList<WeekViewEvent>();
            }
        } else {
            return new ArrayList<WeekViewEvent>();
        }
    }

    private void setupDateTimeInterpreter(final boolean shortDate) {
        weekView.setDateTimeInterpreter(new DateTimeInterpreter() {
            @Override
            public String interpretDate(Calendar date) {
                SimpleDateFormat weekdayNameFormat = new SimpleDateFormat("EEE", Locale.getDefault());
                String weekday = weekdayNameFormat.format(date.getTime());
                SimpleDateFormat format = new SimpleDateFormat(" M/d", Locale.getDefault());

                // All android api level do not have a standard way of getting the first letter of
                // the week day name. Hence we get the first char programmatically.
                // Details: http://stackoverflow.com/questions/16959502/get-one-letter-abbreviation-of-week-day-of-a-date-in-java#answer-16959657
                if (shortDate)
                    weekday = String.valueOf(weekday.charAt(0));
                return weekday.toUpperCase() + format.format(date.getTime());
            }

            @Override
            public String interpretTime(int hour) {
                return String.format("%02d:00", hour);
            }
        });
    }

    @Override
    public void onEmptyViewClicked(Calendar time) {
        Toast.makeText(this, time.get(Calendar.MONTH) + 1 + "/" + time.get(Calendar.DAY_OF_MONTH) + " " + time.get(Calendar.HOUR_OF_DAY) + ":" + time.get(Calendar.MINUTE), Toast.LENGTH_SHORT).show();
    }

    @Subscribe
    public void onMainEventThread(JTMessage message) {
//        if (message.what)
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void showInfo(String info) {
        Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoad() {
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }

    @Override
    public void dismissLoad() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
