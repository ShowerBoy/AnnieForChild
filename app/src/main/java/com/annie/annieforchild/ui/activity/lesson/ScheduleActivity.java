package com.annie.annieforchild.ui.activity.lesson;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.views.APSTSViewPager;
import com.annie.annieforchild.Utils.views.weekview.WeekViewEvent;
import com.annie.annieforchild.bean.DateBean;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.WeekBean;
import com.annie.annieforchild.bean.schedule.Schedule;
import com.annie.annieforchild.bean.schedule.TotalSchedule;
import com.annie.annieforchild.presenter.SchedulePresenter;
import com.annie.annieforchild.presenter.imp.SchedulePresenterImp;
import com.annie.annieforchild.ui.adapter.DateRecyclerAdapter;
import com.annie.annieforchild.ui.fragment.schedule.OfflineScheduleFragment;
import com.annie.annieforchild.ui.fragment.schedule.OnlineScheduleFragment;
import com.annie.annieforchild.ui.interfaces.OnRecyclerItemClickListener;
import com.annie.annieforchild.view.ScheduleView;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;
import com.lhh.apst.library.AdvancedPagerSlidingTabStrip;

import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 课表
 * Created by WangLei on 2018/2/23 0023
 */

public class ScheduleActivity extends BaseActivity implements ScheduleView, View.OnClickListener, ViewPager.OnPageChangeListener {
    private ImageView scheduleBack;
    private Button backToday;
    private TextView totalSchedule;
    private RecyclerView dateRecycler;
    private AdvancedPagerSlidingTabStrip mTab;
    private APSTSViewPager mVP;
    private List<DateBean> date_lists;
    private DateRecyclerAdapter adapter;
    private OnlineScheduleFragment onlineScheduleFragment;
    private OfflineScheduleFragment offlineScheduleFragment;
    private ScheduleFragmentAdapter fragmentAdapter;
    private SchedulePresenter presenter;
    private AlertHelper helper;
    private Dialog dialog;
    private int screenwidth;
    private long oneDay = 1000 * 60 * 60 * 24L;

    Calendar calendar;
    TotalSchedule total_Schedule;
    List<WeekViewEvent> events;
    //    WeekViewEvent event;
//    Calendar startCalendar;
//    Calendar endCalendar;
    private List<WeekBean> dateLists;
    List<Schedule> onlineLists;
    List<Schedule> offlineLists;
    int year, month, day;
    String startTime, endTime;
    boolean isLeap;
    int position = 0;

    {
        setRegister(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_schedule;
    }

    @Override
    protected void initView() {
        scheduleBack = findViewById(R.id.schedule_back);
        dateRecycler = findViewById(R.id.date_recycler);
        mTab = findViewById(R.id.schedule_tab_layout);
        mVP = findViewById(R.id.schedule_viewpager);
        backToday = findViewById(R.id.back_today);
        totalSchedule = findViewById(R.id.total_schedule);
        scheduleBack.setOnClickListener(this);
        backToday.setOnClickListener(this);
        totalSchedule.setOnClickListener(this);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        dateRecycler.setLayoutManager(manager);
        WindowManager managers = this.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        managers.getDefaultDisplay().getMetrics(outMetrics);
        screenwidth = outMetrics.widthPixels;

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    protected void initData() {
        onlineLists = new ArrayList<>();
        offlineLists = new ArrayList<>();
        if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
            isLeap = true;
        } else {
            isLeap = false;
        }
        initDatesList();
        presenter = new SchedulePresenterImp(this, this);
        presenter.initViewAndData();
        helper = new AlertHelper(this);
        dialog = helper.LoadingDialog();
        initDateList();
        adapter = new DateRecyclerAdapter(this, date_lists, screenwidth, new OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view) {
                position = dateRecycler.getChildAdapterPosition(view);
                for (int i = 0; i < 30; i++) {
                    date_lists.get(i).setSelect(false);
                }
                date_lists.get(position).setSelect(true);
                adapter.notifyDataSetChanged();
                presenter.getScheduleDetails(date_lists.get(position).getYear() + date_lists.get(position).getMonth() + date_lists.get(position).getDay());
            }

            @Override
            public void onItemLongClick(View view) {

            }
        });
        dateRecycler.setAdapter(adapter);
        fragmentAdapter = new ScheduleFragmentAdapter(getSupportFragmentManager());
        mVP.setOffscreenPageLimit(2);
        mVP.setAdapter(fragmentAdapter);
        fragmentAdapter.notifyDataSetChanged();
        mTab.setViewPager(mVP);
        mTab.setOnPageChangeListener(this);
        presenter.getScheduleDetails(date_lists.get(0).getYear() + date_lists.get(0).getMonth() + date_lists.get(0).getDay());
    }

    /**
     * 初始化顶部日期选择栏
     */
    private void initDateList() {
        date_lists = new ArrayList<>();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy");
        SimpleDateFormat format2 = new SimpleDateFormat("MM");
        SimpleDateFormat format3 = new SimpleDateFormat("dd");
        SimpleDateFormat format4 = new SimpleDateFormat("E");
        long time = System.currentTimeMillis();
        for (int i = 0; i < 30; i++) {
            Date date = new Date(time + oneDay * i);
            DateBean bean = new DateBean();
            bean.setYear(format1.format(date));
            bean.setMonth(format2.format(date));
            bean.setDay(format3.format(date));
            bean.setWeek(format4.format(date));
            if (i == 0) {
                bean.setSelect(true);
            } else {
                bean.setSelect(false);
            }
            date_lists.add(bean);
        }
    }

    private void initDatesList() {
        SimpleDateFormat format2 = new SimpleDateFormat("MM");
        String months = format2.format(new Date());
        startTime = year + months + "01";

        dateLists = new ArrayList<>();
        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
            for (int i = 0; i < 31; i++) {
                WeekBean bean = new WeekBean();
                bean.setYear(year);
                bean.setMonth(month);
                bean.setDay(i + 1);
                dateLists.add(bean);
            }
            endTime = year + months + "31";
        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            for (int i = 0; i < 30; i++) {
                WeekBean bean = new WeekBean();
                bean.setYear(year);
                bean.setMonth(month);
                bean.setDay(i + 1);
                dateLists.add(bean);
            }
            endTime = year + months + "30";
        } else if (month == 2) {
            if (isLeap) {
                for (int i = 0; i < 29; i++) {
                    WeekBean bean = new WeekBean();
                    bean.setYear(year);
                    bean.setMonth(month);
                    bean.setDay(i + 1);
                    dateLists.add(bean);
                }
                endTime = year + months + "29";
            } else {
                for (int i = 0; i < 28; i++) {
                    WeekBean bean = new WeekBean();
                    bean.setYear(year);
                    bean.setMonth(month);
                    bean.setDay(i + 1);
                    dateLists.add(bean);
                }
                endTime = year + months + "28";
            }
        }
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.schedule_back:
                finish();
                break;
            case R.id.back_today:
                for (int i = 0; i < 30; i++) {
                    date_lists.get(i).setSelect(false);
                }
                date_lists.get(0).setSelect(true);
                adapter.notifyDataSetChanged();
                dateRecycler.smoothScrollToPosition(0);
                presenter.getScheduleDetails(date_lists.get(0).getYear() + date_lists.get(0).getMonth() + date_lists.get(0).getDay());
                break;
            case R.id.total_schedule:
                presenter.totalSchedule(startTime, endTime);
//                Intent intent = new Intent(this, TotalScheduleActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("startTime", startTime);
//                bundle.putString("endTime", endTime);
//                intent.putExtras(bundle);
//                startActivity(intent);
                break;
        }
    }

    /**
     * {@link SchedulePresenterImp#Success(int, Object)}
     *
     * @param message
     */
    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_ADDSCHEDULE) {
            presenter.getScheduleDetails(date_lists.get(position).getYear() + date_lists.get(position).getMonth() + date_lists.get(position).getDay());
        } else if (message.what == MethodCode.EVENT_EDITSCHEDULE) {
            presenter.getScheduleDetails(date_lists.get(position).getYear() + date_lists.get(position).getMonth() + date_lists.get(position).getDay());
        } else if (message.what == MethodCode.EVENT_DELETESCHEDULE) {
            showInfo((String) message.obj);
            presenter.getScheduleDetails(date_lists.get(position).getYear() + date_lists.get(position).getMonth() + date_lists.get(position).getDay());
        } else if (message.what == MethodCode.EVENT_TOTALSCHEDULE) {
            total_Schedule = (TotalSchedule) message.obj;
            onlineLists.clear();
            offlineLists.clear();
            onlineLists.addAll(total_Schedule.getOnline());
            offlineLists.addAll(total_Schedule.getOffline());
            refresh();
        }
    }

    private void refresh() {
        events = new ArrayList<>();
        for (int i = 0; i < onlineLists.size(); i++) {
            int month = Integer.parseInt(onlineLists.get(i).getDate().substring(4, 6));
            int date = Integer.parseInt(onlineLists.get(i).getDate().substring(6, 8));
            int startHour = Integer.parseInt(onlineLists.get(i).getStart().split(":")[0]);
            int startMin = Integer.parseInt(onlineLists.get(i).getStart().split(":")[1]);
            int endHour = Integer.parseInt(onlineLists.get(i).getStop().split(":")[0]);
            int endMin = Integer.parseInt(onlineLists.get(i).getStop().split(":")[1]);
            Calendar startCalendar = Calendar.getInstance();
            startCalendar.set(Calendar.DAY_OF_MONTH, date);
            startCalendar.set(Calendar.HOUR_OF_DAY, startHour);
            startCalendar.set(Calendar.MINUTE, startMin);
            startCalendar.set(Calendar.MONTH, month - 1);
            startCalendar.set(Calendar.YEAR, year);
            Calendar endCalendar = Calendar.getInstance();
            endCalendar.set(Calendar.DAY_OF_MONTH, date);
            endCalendar.set(Calendar.HOUR_OF_DAY, endHour);
            endCalendar.set(Calendar.MINUTE, endMin);
            endCalendar.set(Calendar.MONTH, month - 1);
            endCalendar.set(Calendar.YEAR, year);
            WeekViewEvent event = new WeekViewEvent(i, onlineLists.get(i).getDetail(), startCalendar, endCalendar);
            if (onlineLists.get(i).getType() == 1) {
                event.setColor(getResources().getColor(R.color.event_color_01));
            } else {
                event.setColor(getResources().getColor(R.color.event_color_02));
            }
            events.add(event);
        }
        Intent intent = new Intent(this, TotalScheduleActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("events", (Serializable) events);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

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

    class ScheduleFragmentAdapter extends FragmentStatePagerAdapter {

        public ScheduleFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position >= 0 && position < 2) {
                switch (position) {
                    case 0:
                        if (null == offlineScheduleFragment) {
                            offlineScheduleFragment = OfflineScheduleFragment.instance();
                        }
                        return offlineScheduleFragment;
                    case 1:
                        if (null == onlineScheduleFragment) {
                            onlineScheduleFragment = OnlineScheduleFragment.instance();
                        }
                        return onlineScheduleFragment;
                    default:
                        break;
                }
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position >= 0 && position < 2) {
                switch (position) {
                    case 0:
                        return "线下课程";
                    case 1:
                        return "线上课程";
                    default:
                        break;
                }
            }
            return null;
        }
    }
}
