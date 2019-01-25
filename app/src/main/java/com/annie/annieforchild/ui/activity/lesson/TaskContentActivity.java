package com.annie.annieforchild.ui.activity.lesson;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.EventLog;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.CheckDoubleClickListener;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.OnCheckDoubleClick;
import com.annie.annieforchild.Utils.views.APSTSViewPager;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.task.TaskContent;
import com.annie.annieforchild.bean.task.TaskDetails;
import com.annie.annieforchild.presenter.GrindEarPresenter;
import com.annie.annieforchild.presenter.imp.GrindEarPresenterImp;
import com.annie.annieforchild.ui.activity.CameraActivity;
import com.annie.annieforchild.ui.fragment.task.TaskContentFragment;
import com.annie.annieforchild.ui.fragment.task.TaskFragment;
import com.annie.annieforchild.view.SongView;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;
import com.lhh.apst.library.AdvancedPagerSlidingTabStrip;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * 作业内容
 * Created by wanglei on 2019/1/19.
 */

public class TaskContentActivity extends CameraActivity implements SongView, ViewPager.OnPageChangeListener, OnCheckDoubleClick {
    private ImageView back;
    private TaskContentFragment fragment1, fragment2, fragment3, fragment4;
    private AdvancedPagerSlidingTabStrip tab;
    private APSTSViewPager mVP;
    private GrindEarPresenter presenter;
    private CheckDoubleClickListener listener;
    private TaskContentAdapter fragmentAdapter;
    //    private List<String> imageList;
    public static List<String> pathList1, pathList2, pathList3, pathList4; //用户选择图片路径列表
    public static int imageNum1 = 0, imageNum2 = 0, imageNum3 = 0, imageNum4 = 0; //选择图片数量
    private Intent intent;
    private int classid, type;
    private String week = "", taskTime = "";
    private AlertHelper helper;
    private Dialog dialog;
    private TaskContent taskContent;
    private TaskDetails taskDetails;
    private int pagePosition = 0, taskId1, taskId2, taskId3, taskId4;

    {
        setRegister(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_task_content;
    }

    @Override
    protected void initView() {
        back = findViewById(R.id.task_content_back);
        tab = findViewById(R.id.task_content_tab);
        mVP = findViewById(R.id.task_content_viewpager);
        listener = new CheckDoubleClickListener(this);
        back.setOnClickListener(listener);

        intent = getIntent();
        if (intent != null) {
            classid = intent.getIntExtra("classid", 0);
            type = intent.getIntExtra("type", 0);
            if (type == 0) {
                taskTime = intent.getStringExtra("taskTime");
            } else {
                week = intent.getStringExtra("week");
            }
        }

        fragmentAdapter = new TaskContentAdapter(getSupportFragmentManager());
        mVP.setOffscreenPageLimit(4);
        mVP.setAdapter(fragmentAdapter);
        fragmentAdapter.notifyDataSetChanged();
        tab.setViewPager(mVP);
        tab.setOnPageChangeListener(this);
    }

    @Override
    protected void initData() {
        pathList1 = new ArrayList<>();
        pathList2 = new ArrayList<>();
        pathList3 = new ArrayList<>();
        pathList4 = new ArrayList<>();
        helper = new AlertHelper(this);
        dialog = helper.LoadingDialog();
//        presenter = new GrindEarPresenterImp(this, this);
//        presenter.initViewAndData();
//        if (type == 0) {
//            presenter.taskDetails(classid, type, "", taskTime);
//        } else {
//            presenter.taskDetails(classid, type, week, "");
//        }
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onCheckDoubleClick(View view) {
        switch (view.getId()) {
            case R.id.task_content_back:
                finish();
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        pagePosition = position;
        JTMessage message = new JTMessage();
        message.what = MethodCode.EVENT_TASKDATA;
        message.obj = pagePosition;
        EventBus.getDefault().post(message);
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

    @Override
    protected void onImageSelect(Bitmap bitmap, String path) {
//        headbitmap = bitmap;
        if (pagePosition == 0) {
            pathList1.add(path);
            imageNum1++;
        } else if (pagePosition == 1) {
            pathList2.add(path);
            imageNum2++;
        } else if (pagePosition == 2) {
            pathList3.add(path);
            imageNum3++;
        } else if (pagePosition == 3) {
            pathList4.add(path);
            imageNum4++;
        }
        JTMessage message = new JTMessage();
        message.what = MethodCode.EVENT_TASKIMAGE;
        message.obj = pagePosition;
        EventBus.getDefault().post(message);
//        presenter.uploadTaskImage(taskid, path, type);

//        imageList.add(path);
//        uploadAdapter.notifyDataSetChanged();
//        SystemUtils.show(this, path);
    }

    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_TASKDETAILS) {
            taskContent = (TaskContent) message.obj;
            if (taskContent != null) {
                taskId1 = taskContent.getInclassList().getTaskid();
                taskId2 = taskContent.getPrepareList().getTaskid();
                taskId3 = taskContent.getSeriesList().getTaskid();
                taskId4 = taskContent.getOtherList().getTaskid();
            }
        } else if (message.what == MethodCode.EVENT_SELECT) {
            int position = (int) message.obj;
            if (pagePosition == 0) {
                pathList1.remove(position);
                imageNum1--;
            } else if (pagePosition == 1) {
                pathList2.remove(position);
                imageNum2--;
            } else if (pagePosition == 2) {
                pathList3.remove(position);
                imageNum3--;
            } else if (pagePosition == 3) {
                pathList4.remove(position);
                imageNum4--;
            }
            JTMessage message2 = new JTMessage();
            message2.what = MethodCode.EVENT_TASKIMAGE;
            message2.obj = pagePosition;
            EventBus.getDefault().post(message2);
        }
    }

    class TaskContentAdapter extends FragmentStatePagerAdapter {

        public TaskContentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position >= 0 && position < 4) {
                switch (position) {
                    case 0:
                        if (null == fragment1) {
                            fragment1 = TaskContentFragment.instance(position, classid, type, taskTime, week);
                        }
                        return fragment1;
                    case 1:
                        if (null == fragment2) {
                            fragment2 = TaskContentFragment.instance(position, classid, type, taskTime, week);
                        }
                        return fragment2;
                    case 2:
                        if (null == fragment3) {
                            fragment3 = TaskContentFragment.instance(position, classid, type, taskTime, week);
                        }
                        return fragment3;
                    case 3:
                        if (null == fragment4) {
                            fragment4 = TaskContentFragment.instance(position, classid, type, taskTime, week);
                        }
                        return fragment4;
                    default:
                        break;
                }
            }
            return null;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position >= 0 && position < 4) {
                switch (position) {
                    case 0:
                        return "随堂作业";
                    case 1:
                        return "预习作业";
                    case 2:
                        return "系列作业";
                    case 3:
                        return "其他作业";
                    default:
                        break;
                }
            }
            return null;
        }

        @Override
        public int getCount() {
            return 4;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        pathList1.clear();
        pathList2.clear();
        pathList3.clear();
        pathList4.clear();
        imageNum1 = 0;
        imageNum2 = 0;
        imageNum3 = 0;
        imageNum4 = 0;
    }
}
