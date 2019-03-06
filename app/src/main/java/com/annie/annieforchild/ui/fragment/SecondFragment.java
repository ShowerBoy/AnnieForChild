package com.annie.annieforchild.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.CheckDoubleClickListener;
import com.annie.annieforchild.Utils.OnCheckDoubleClick;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.ui.activity.PhotoActivity;
import com.annie.annieforchild.ui.activity.lesson.MaterialActivity;
import com.annie.annieforchild.ui.activity.lesson.ScheduleActivity;
import com.annie.annieforchild.ui.activity.lesson.ScheduleActivity2;
import com.annie.annieforchild.ui.activity.lesson.TaskActivity;
import com.annie.annieforchild.ui.activity.my.MyCourseActivity;
import com.annie.annieforchild.ui.activity.net.NetWorkActivity;
import com.annie.annieforchild.view.SecondView;
import com.annie.baselibrary.base.BaseFragment;

import java.util.Calendar;

/**
 * 课业
 * Created by WangLei on 2018/2/23 0023
 */

public class SecondFragment extends BaseFragment implements SecondView, OnCheckDoubleClick {
    private ImageView lessonSchedule, lessonCourse, lessonTextbook, lessonNetClass, imageView03;
    //    private RelativeLayout followTaskLayout, seriesTaskLayout, optionalTaskLayout;
    private Calendar calendar;
    private String tag;
    private String week;
    private CheckDoubleClickListener listener;

    @Override
    protected void initData() {
        calendar = Calendar.getInstance();
        Bundle bundle = getArguments();
        if (bundle != null) {
            tag = bundle.getString("tag");
        }
    }

    @Override
    protected void initView(View view) {
        lessonSchedule = view.findViewById(R.id.lesson_schedule);
        lessonCourse = view.findViewById(R.id.lesson_course);
        lessonTextbook = view.findViewById(R.id.lesson_textbook);
        lessonNetClass = view.findViewById(R.id.lesson_netclass);
        imageView03 = view.findViewById(R.id.image_03);
//        followTaskLayout = view.findViewById(R.id.follow_task_layout);
//        seriesTaskLayout = view.findViewById(R.id.series_task_layout);
//        optionalTaskLayout = view.findViewById(R.id.optional_task_layout);
        listener = new CheckDoubleClickListener(this);
        lessonSchedule.setOnClickListener(listener);
        lessonCourse.setOnClickListener(listener);
        lessonTextbook.setOnClickListener(listener);
        lessonNetClass.setOnClickListener(listener);
        imageView03.setOnClickListener(listener);
//        followTaskLayout.setOnClickListener(listener);
//        seriesTaskLayout.setOnClickListener(listener);
//        optionalTaskLayout.setOnClickListener(listener);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_second_fragment;
    }

    @Override
    public void showInfo(String info) {
        Toast.makeText(getContext(), info, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoad() {

    }

    @Override
    public void dismissLoad() {

    }

    @Override
    public void onCheckDoubleClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.lesson_schedule:
                //我的课表
                if (application.getSystemUtils().getTag().equals("游客")) {
                    SystemUtils.toLogin(getContext());
                    return;
                }
                if (application.getSystemUtils().getChildTag() == 0) {
                    showInfo("请先添加学员");
                    SystemUtils.toAddChild(getContext());
                    return;
                }
                intent.setClass(getContext(), ScheduleActivity2.class);
                startActivity(intent);
                break;
            case R.id.lesson_course:
                //我的作业
                if (application.getSystemUtils().getTag().equals("游客")) {
                    SystemUtils.toLogin(getContext());
                    return;
                }
                if (application.getSystemUtils().getChildTag() == 0) {
                    showInfo("请先添加学员");
                    SystemUtils.toAddChild(getContext());
                    return;
                }
//                intent.setClass(getContext(), CourseActivity.class);
                intent.setClass(getContext(), TaskActivity.class);
                startActivity(intent);
                break;
            case R.id.lesson_textbook:
                //我的教材
                if (application.getSystemUtils().getTag().equals("游客")) {
                    SystemUtils.toLogin(getContext());
                    return;
                }
                if (application.getSystemUtils().getChildTag() == 0) {
                    showInfo("请先添加学员");
                    SystemUtils.toAddChild(getContext());
                    return;
                }
                intent.setClass(getContext(), MaterialActivity.class);
                startActivity(intent);
                break;
            case R.id.image_03:
//                SystemUtils.setBackGray(getActivity(), true);
//                SystemUtils.getPhotoPopup(getContext(), "0").showAtLocation(SystemUtils.popupView, Gravity.CENTER, 0, 0);
                intent.setClass(getContext(), PhotoActivity.class);
                intent.putExtra("url", "0");
                intent.putExtra("delete", "0");
                startActivity(intent);
                break;
            case R.id.lesson_netclass:
                if (application.getSystemUtils().getTag().equals("游客")) {
                    SystemUtils.toLogin(getContext());
                    return;
                }
                if (application.getSystemUtils().getChildTag() == 0) {
                    showInfo("请先添加学员");
                    SystemUtils.toAddChild(getContext());
                    return;
                }
                intent.setClass(getContext(), MyCourseActivity.class);
                startActivity(intent);
                break;
        }
    }
}
