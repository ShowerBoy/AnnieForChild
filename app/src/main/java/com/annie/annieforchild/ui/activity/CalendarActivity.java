package com.annie.annieforchild.ui.activity;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.Utils.decorators.EventDecorator;
import com.annie.annieforchild.Utils.decorators.MySelectorDecorator;
import com.annie.annieforchild.Utils.decorators.OneDayDecorator;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by wanglei on 2018/8/6.h
 */

public class CalendarActivity extends BaseActivity implements OnDateSelectedListener {
    private MaterialCalendarView calendar;
    private final OneDayDecorator oneDayDecorator = new OneDayDecorator();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_calendar;
    }

    @Override
    protected void initView() {
        calendar = findViewById(R.id.calendarView);
    }

    @Override
    protected void initData() {
        calendar.setOnDateChangedListener(this);
        calendar.setShowOtherDates(MaterialCalendarView.SHOW_ALL);

        Calendar instance = Calendar.getInstance();
        calendar.setSelectedDate(instance.getTime());

        Calendar instance1 = Calendar.getInstance();
        instance1.set(instance1.get(Calendar.YEAR), Calendar.JANUARY, 1);

        Calendar instance2 = Calendar.getInstance();
        instance2.set(instance2.get(Calendar.YEAR), Calendar.DECEMBER, 31);


        Calendar calen = Calendar.getInstance();
//        calen.add(Calendar.MONTH, 0);
        ArrayList<CalendarDay> dates = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            CalendarDay day = CalendarDay.from(calen);
//            dates.add(day);
//            calen.add(Calendar.DATE, 5);
//        }
        calen.set(calen.get(Calendar.YEAR), Calendar.SEPTEMBER, 1);
        CalendarDay day = CalendarDay.from(calen);
        dates.add(day);

        calendar.addDecorator(new EventDecorator(ContextCompat.getColor(this, R.color.text_orange), dates));

        calendar.addDecorators(
                new MySelectorDecorator(this),
                oneDayDecorator
        );

        calendar.state().edit()
                .setMinimumDate(instance1.getTime())
                .setMaximumDate(instance2.getTime())
                .commit();

        calendar.setTileHeightDp(36);
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView materialCalendarView, @NonNull CalendarDay calendarDay, boolean b) {
        SystemUtils.show(this, SimpleDateFormat.getDateInstance().format(calendarDay.getDate()));
    }
}
