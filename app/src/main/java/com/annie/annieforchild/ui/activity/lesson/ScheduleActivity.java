package com.annie.annieforchild.ui.activity.lesson;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
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
import com.annie.annieforchild.bean.DateBean;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.presenter.SchedulePresenter;
import com.annie.annieforchild.presenter.imp.SchedulePresenterImp;
import com.annie.annieforchild.ui.activity.my.MyMessageActivity;
import com.annie.annieforchild.ui.adapter.DateRecyclerAdapter;
import com.annie.annieforchild.ui.fragment.message.GroupMsgFragment;
import com.annie.annieforchild.ui.fragment.message.NoticeFragment;
import com.annie.annieforchild.ui.fragment.schedule.OfflineFragment;
import com.annie.annieforchild.ui.fragment.schedule.OnlineFragment;
import com.annie.annieforchild.ui.interfaces.OnRecyclerItemClickListener;
import com.annie.annieforchild.view.ScheduleView;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;
import com.lhh.apst.library.AdvancedPagerSlidingTabStrip;

import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    private OnlineFragment onlineFragment;
    private OfflineFragment offlineFragment;
    private ScheduleFragmentAdapter fragmentAdapter;
    private SchedulePresenter presenter;
    private AlertHelper helper;
    private Dialog dialog;
    private int screenwidth;
    private long oneDay = 1000 * 60 * 60 * 24L;

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
    }

    @Override
    protected void initData() {
        presenter = new SchedulePresenterImp(this, this);
        presenter.initViewAndData();
        helper = new AlertHelper(this);
        dialog = helper.LoadingDialog();
        initDateList();
        adapter = new DateRecyclerAdapter(this, date_lists, screenwidth, new OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view) {
                int position = dateRecycler.getChildAdapterPosition(view);
                for (int i = 0; i < 30; i++) {
                    date_lists.get(i).setSelect(false);
                }
                date_lists.get(position).setSelect(true);
                adapter.notifyDataSetChanged();
                presenter.getScheduleDetails(date_lists.get(position).getMonth() + date_lists.get(position).getDay());
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
        presenter.getScheduleDetails(date_lists.get(0).getMonth() + date_lists.get(0).getDay());
    }

    /**
     * 初始化顶部日期选择栏
     */
    private void initDateList() {
        date_lists = new ArrayList<>();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy");
        SimpleDateFormat format2 = new SimpleDateFormat("MM");
        SimpleDateFormat format3 = new SimpleDateFormat("dd");
        SimpleDateFormat format4 = new SimpleDateFormat("EEEE");
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
                presenter.getScheduleDetails(date_lists.get(0).getMonth() + date_lists.get(0).getDay());
                break;
            case R.id.total_schedule:
                Intent intent = new Intent(this, TotalScheduleActivity.class);
                startActivity(intent);
                break;
        }
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
                        if (null == offlineFragment) {
                            offlineFragment = OfflineFragment.instance();
                        }
                        return offlineFragment;
                    case 1:
                        if (null == onlineFragment) {
                            onlineFragment = OnlineFragment.instance();
                        }
                        return onlineFragment;
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
