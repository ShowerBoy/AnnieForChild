package com.annie.annieforchild.ui.activity.lesson;

import android.app.Dialog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.CheckDoubleClickListener;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.OnCheckDoubleClick;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.Utils.views.APSTSViewPager;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.task.Task;
import com.annie.annieforchild.bean.task.TaskBean;
import com.annie.annieforchild.presenter.GrindEarPresenter;
import com.annie.annieforchild.presenter.imp.GrindEarPresenterImp;
import com.annie.annieforchild.ui.activity.mains.BankBookActivity;
import com.annie.annieforchild.ui.adapter.TaskAdapter;
import com.annie.annieforchild.ui.fragment.task.TaskFragment;
import com.annie.annieforchild.view.SongView;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.lhh.apst.library.AdvancedPagerSlidingTabStrip;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 作业列表
 * Created by wanglei on 2018/8/21.
 */

public class TaskActivity extends BaseActivity implements SongView, OnCheckDoubleClick, ViewPager.OnPageChangeListener {
    private ImageView back, comingSoon, empty;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView totalFlower;
    private XRecyclerView recycler;
    private LinearLayout taskLayout;
    private AdvancedPagerSlidingTabStrip mTab;
    private APSTSViewPager mVP;
    private GrindEarPresenter presenter;
    private TaskFragment taskFragment1, taskFragment2;
    private TaskFragmentAdapter fragmentAdapter;
    private AlertHelper helper;
    private Dialog dialog;
    private CheckDoubleClickListener listener;
    private TaskAdapter adapter;
    private List<Task> lists;
    private TaskBean taskBean;
    private int fragmentCount = 1;
    private int taskType = 0; //0：课程作业 1：网课作业

    {
        setRegister(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_task;
    }

    @Override
    protected void initView() {
        mTab = findViewById(R.id.task_tab);
        mVP = findViewById(R.id.task_viewpaper);
        back = findViewById(R.id.task_back);
        taskLayout = findViewById(R.id.task_layout);
        comingSoon = findViewById(R.id.task_coming_soon);
        recycler = findViewById(R.id.task_xrecycler);
        totalFlower = findViewById(R.id.task_flower_card);
        swipeRefreshLayout = findViewById(R.id.task_swipe);
        empty = findViewById(R.id.task_empty);
        listener = new CheckDoubleClickListener(this);
        back.setOnClickListener(listener);

//        taskLayout.setVisibility(View.GONE);
//        recycler.setVisibility(View.GONE);
//        comingSoon.setVisibility(View.VISIBLE);
        //TODO:测试
//        fragmentCount = 2;
        //TODO:正式
        if (SystemUtils.userInfo != null) {
            if (SystemUtils.userInfo.getStatus() == 0) {
                if (SystemUtils.userInfo.getIsnetstudent() == 0) {
                    fragmentCount = 1;
                    taskType = 0;
                } else {
                    fragmentCount = 2;
                }
            } else {
                if (SystemUtils.userInfo.getIsnetstudent() == 0) {
                    fragmentCount = 0;
                } else {
                    fragmentCount = 1;
                    taskType = 1;
                }
            }
        }


        if (fragmentCount == 2) {
            taskLayout.setVisibility(View.VISIBLE);
            recycler.setVisibility(View.GONE);
            comingSoon.setVisibility(View.GONE);
            fragmentAdapter = new TaskFragmentAdapter(getSupportFragmentManager());
            mVP.setOffscreenPageLimit(fragmentCount);
            mVP.setAdapter(fragmentAdapter);
            fragmentAdapter.notifyDataSetChanged();
            mTab.setViewPager(mVP);
            mTab.setOnPageChangeListener(this);
        } else if (fragmentCount == 1) {
            taskLayout.setVisibility(View.GONE);
            recycler.setVisibility(View.VISIBLE);
            comingSoon.setVisibility(View.GONE);
        } else {
            taskLayout.setVisibility(View.GONE);
            recycler.setVisibility(View.GONE);
            comingSoon.setVisibility(View.VISIBLE);
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(layoutManager);
//        recycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recycler.setPullRefreshEnabled(false);
        recycler.setLoadingMoreEnabled(false);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.myTask();
            }
        });
    }

    @Override
    protected void initData() {
        lists = new ArrayList<>();
        helper = new AlertHelper(this);
        dialog = helper.LoadingDialog();
        adapter = new TaskAdapter(this, lists);
        recycler.setAdapter(adapter);
        presenter = new GrindEarPresenterImp(this, this);
        presenter.initViewAndData();
        presenter.myTask();
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onCheckDoubleClick(View view) {
        switch (view.getId()) {
            case R.id.task_back:
                finish();
                break;
        }
    }

    /**
     * {@link GrindEarPresenterImp#Success(int, Object)}
     *
     * @param message
     */
    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_MYTASK) {
            taskBean = (TaskBean) message.obj;
            swipeRefreshLayout.setRefreshing(false);
            if (taskBean != null) {
                totalFlower.setText("花卡" + taskBean.getTotalflower() + "分");
                if (fragmentCount == 1) {
                    //课程作业或网课作业
                    if (taskType == 0) {
                        lists.clear();
                        lists.addAll(taskBean.getCourseList());
                    } else {
                        lists.clear();
                        lists.addAll(taskBean.getNetWorkList());
                    }
                    if (lists.size() == 0) {
                        empty.setVisibility(View.VISIBLE);
                    } else {
                        empty.setVisibility(View.GONE);
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        } else if (message.what == MethodCode.EVENT_SUBMITTASK) {
            presenter.myTask();
        }
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
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    class TaskFragmentAdapter extends FragmentStatePagerAdapter {

        public TaskFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position >= 0 && position < fragmentCount) {
                switch (position) {
                    case 0:
                        if (null == taskFragment1) {
                            taskFragment1 = TaskFragment.instance(0);
                        }
                        return taskFragment1;
                    case 1:
                        if (null == taskFragment2) {
                            taskFragment2 = TaskFragment.instance(1);
                        }
                        return taskFragment2;
                    default:
                        break;
                }
            }
            return null;
        }

        @Override
        public int getCount() {
            return fragmentCount;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position >= 0 && position < fragmentCount) {
                switch (position) {
                    case 0:
                        return "课程作业";
                    case 1:
                        return "网课作业";
                    default:
                        break;
                }
            }
            return null;
        }
    }
}
