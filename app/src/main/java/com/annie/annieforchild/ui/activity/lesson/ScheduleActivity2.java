package com.annie.annieforchild.ui.activity.lesson;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.CheckDoubleClickListener;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.OnCheckDoubleClick;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.Utils.decorators.EventDecorator;
import com.annie.annieforchild.Utils.decorators.MySelectorDecorator;
import com.annie.annieforchild.Utils.views.RecyclerLinearLayoutManager;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.schedule.TotalSchedule;
import com.annie.annieforchild.presenter.GrindEarPresenter;
import com.annie.annieforchild.presenter.SchedulePresenter;
import com.annie.annieforchild.presenter.imp.GrindEarPresenterImp;
import com.annie.annieforchild.presenter.imp.SchedulePresenterImp;
import com.annie.annieforchild.ui.adapter.MonthScheduleAdapter;
import com.annie.annieforchild.ui.adapter.ScheduleAdapter;
import com.annie.annieforchild.view.ScheduleView;
import com.annie.annieforchild.view.SongView;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;
import com.annie.baselibrary.utils.NetUtils.request.FastJsonRequest;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by wanglei on 2018/8/7.
 */

public class ScheduleActivity2 extends BaseActivity implements OnDateSelectedListener, OnCheckDoubleClick, OnDateSetListener, ScheduleView {
    private MaterialCalendarView materialCalendar;
    private TextView yearAndMonth, addSchedule, emptyText;
    private ImageView back, empty;
    private RecyclerView recycler, recycler2;
    private TotalSchedule totalSchedule;
    private ScheduleAdapter adapter;
    private MonthScheduleAdapter monthAdapter;
    private List<TotalSchedule> lists;
    private SchedulePresenter presenter;
    private TimePickerDialog timePickerDialog;
    private SimpleDateFormat sf;
    private String year, month, day, date = null;
    private AlertHelper helper;
    private Dialog dialog;
    long tenYears = 10L * 365 * 1000 * 60 * 60 * 24L;
    long oneYears = 10L * 365 * 1000 * 60 * 60 * 24L;
    private CheckDoubleClickListener listener;

    {
        setRegister(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_schedule2;
    }

    @Override
    protected void initView() {
        back = findViewById(R.id.schedule_back2);
        materialCalendar = findViewById(R.id.calendarView);
        recycler = findViewById(R.id.schedule_recycler);
        yearAndMonth = findViewById(R.id.year_month);
        addSchedule = findViewById(R.id.add_schedule2);
        empty = findViewById(R.id.empty_schedule);
        emptyText = findViewById(R.id.empty_text);
        recycler2 = findViewById(R.id.schedule_recycler2);
        listener = new CheckDoubleClickListener(this);
        back.setOnClickListener(listener);
        yearAndMonth.setOnClickListener(listener);
        addSchedule.setOnClickListener(listener);
        RecyclerLinearLayoutManager linearLayoutManager = new RecyclerLinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.setScrollEnabled(false);
        recycler.setLayoutManager(linearLayoutManager);

        RecyclerLinearLayoutManager linearLayoutManager2 = new RecyclerLinearLayoutManager(this);
        linearLayoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager2.setScrollEnabled(false);
        recycler2.setLayoutManager(linearLayoutManager2);
//        recycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    @Override
    protected void initData() {
        lists = new ArrayList<>();
        helper = new AlertHelper(this);
        dialog = helper.LoadingDialog();
        presenter = new SchedulePresenterImp(this, this);
        presenter.initViewAndData();
        sf = new SimpleDateFormat("yyyy-MM-dd");
        materialCalendar.setTileHeightDp(35);
        materialCalendar.setTopbarVisible(false);
        materialCalendar.setPagingEnabled(false);
        materialCalendar.setOnDateChangedListener(this);

        presenter.myCalendar(sf.format(new Date()).replace("-", ""));
//        materialCalendar.setShowOtherDates(MaterialCalendarView.SHOW_DEFAULTS);
        totalSchedule = new TotalSchedule();
        adapter = new ScheduleAdapter(this, totalSchedule, presenter, 0);
        monthAdapter = new MonthScheduleAdapter(this, lists, presenter);
        recycler.setAdapter(adapter);
        recycler2.setAdapter(monthAdapter);

        Calendar instance = Calendar.getInstance();
        materialCalendar.setSelectedDate(instance.getTime());

        Calendar instance1 = Calendar.getInstance();
        instance1.set(instance1.get(Calendar.YEAR) - 10, Calendar.JANUARY, 1);

        Calendar instance2 = Calendar.getInstance();
        instance2.set(instance2.get(Calendar.YEAR) + 10, Calendar.DECEMBER, 31);

//        materialCalendar.addDecorator(new EventDecorator(ContextCompat.getColor(this, R.color.text_orange), dates));

        materialCalendar.addDecorators(
                new MySelectorDecorator(this)
        );

        materialCalendar.state().edit()
                .setMinimumDate(instance1.getTime())
                .setMaximumDate(instance2.getTime())
                .commit();
        year = sf.format(new Date()).split("-")[0];
        month = sf.format(new Date()).split("-")[1];
        day = sf.format(new Date()).split("-")[2];
        yearAndMonth.setText(instance.get(Calendar.YEAR) + "年" + (instance.get(Calendar.MONTH) + 1) + "月");

        timePickerDialog = new TimePickerDialog.Builder()
                .setType(Type.YEAR_MONTH)
                .setThemeColor(R.color.black)
                .setTitleStringId("日期选择")
                .setMinMillseconds(System.currentTimeMillis() - tenYears)
                .setMaxMillseconds(System.currentTimeMillis() + oneYears)
                .setCurrentMillseconds(System.currentTimeMillis())
                .setWheelItemTextSize(16)
                .setCallBack(this)
                .build();
        date = sf.format(new Date()).replace("-", "");
//        presenter.getScheduleDetails(sf.format(new Date()).replace("-", ""));
        presenter.monthCalendar(sf.format(new Date()).replace("-", ""));
        recycler.setVisibility(View.GONE);
        recycler2.setVisibility(View.VISIBLE);
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView materialCalendarView, @NonNull CalendarDay calendarDay, boolean b) {
        date = sf.format(calendarDay.getDate()).replace("-", "");
        presenter.getScheduleDetails(date);
        recycler.setVisibility(View.VISIBLE);
        recycler2.setVisibility(View.GONE);
    }

    @Override
    public void onDateSet(TimePickerDialog timePickerDialog, long l) {
        year = sf.format(new Date(l)).split("-")[0];
        month = sf.format(new Date(l)).split("-")[1];
        yearAndMonth.setText(year + "年" + Integer.parseInt(month) + "月");
        Calendar instance = Calendar.getInstance();
        instance.set(Integer.parseInt(year), Integer.parseInt(month) - 1, Integer.parseInt(day));
        materialCalendar.setSelectedDate(instance.getTime());
        materialCalendar.state().edit().commit();
        date = year + month + day;
        presenter.getScheduleDetails(date);
        recycler.setVisibility(View.VISIBLE);
        recycler2.setVisibility(View.GONE);
//        materialCalendar.goToNext();
    }

    /**
     * {@link SchedulePresenterImp#Success(int, Object)}
     *
     * @param message
     */
    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_MYCALENDAR) {
            List<String> lists = (List<String>) message.obj;
            if (lists != null && lists.size() != 0) {
                ArrayList<CalendarDay> dates = new ArrayList<>();
                for (int i = 0; i < lists.size(); i++) {
                    String y = lists.get(i).split("-")[0];
                    String m = lists.get(i).split("-")[1];
                    String d = lists.get(i).split("-")[2];
                    Calendar calen = Calendar.getInstance();
                    calen.set(Integer.parseInt(y), Integer.parseInt(m) - 1, Integer.parseInt(d));
                    CalendarDay day = CalendarDay.from(calen);
                    dates.add(day);
                }
                materialCalendar.addDecorator(new EventDecorator(ContextCompat.getColor(this, R.color.text_orange), dates));
            }
        } else if (message.what == MethodCode.EVENT_MYSCHEDULE) {
            TotalSchedule schedule = (TotalSchedule) message.obj;
            if (schedule.getOffline() != null) {
                totalSchedule.setOffline(schedule.getOffline());
            } else {
                totalSchedule.setOffline(new ArrayList<>());
            }
            if (schedule.getOnline() != null) {
                totalSchedule.setOnline(schedule.getOnline());
            } else {
                totalSchedule.setOnline(new ArrayList<>());
            }
            if (schedule.getFamily() != null) {
                totalSchedule.setFamily(schedule.getFamily());
            } else {
                totalSchedule.setFamily(new ArrayList<>());
            }
            if (schedule.getTeacher() != null) {
                totalSchedule.setTeacher(schedule.getTeacher());
            } else {
                totalSchedule.setTeacher(new ArrayList<>());
            }
            if (schedule.getOffline() != null && schedule.getOffline().size() != 0
                    || schedule.getOnline() != null && schedule.getOnline().size() != 0
                    || schedule.getFamily() != null && schedule.getFamily().size() != 0
                    || schedule.getTeacher() != null && schedule.getTeacher().size() != 0) {
                empty.setVisibility(View.GONE);
                emptyText.setVisibility(View.GONE);
            } else {
                empty.setVisibility(View.VISIBLE);
                emptyText.setVisibility(View.VISIBLE);
            }
            adapter.notifyDataSetChanged();
            date = (String) message.obj2;
            presenter.myCalendar(date);
        } else if (message.what == MethodCode.EVENT_ADDSCHEDULE) {
            presenter.getScheduleDetails(date);
        } else if (message.what == MethodCode.EVENT_MONTHCALENDAR) {
            List<TotalSchedule> list = (List<TotalSchedule>) message.obj;
            if (list != null && list.size() != 0) {
                lists.clear();
                lists.addAll(list);
                monthAdapter.notifyDataSetChanged();
            } else {
                recycler2.setVisibility(View.GONE);
                empty.setVisibility(View.VISIBLE);
                emptyText.setVisibility(View.VISIBLE);
            }
        } else if (message.what == MethodCode.EVENT_DELETESCHEDULE) {
            showInfo((String) message.obj);
            presenter.myCalendar(date);
            presenter.getScheduleDetails(date);
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        materialCalendar.setFocusable(true);
        materialCalendar.requestFocus();
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

    @Override
    public void onCheckDoubleClick(View view) {
        switch (view.getId()) {
            case R.id.schedule_back2:
                finish();
                break;
            case R.id.year_month:
                timePickerDialog.show(getSupportFragmentManager(), "year_month");
                break;
            case R.id.add_schedule2:
                if (date != null) {
                    Intent intent = new Intent(this, SelectMaterialActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("date", date);
                    bundle.putSerializable("schedule", null);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                break;
        }
    }
}
