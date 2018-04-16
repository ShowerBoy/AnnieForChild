package com.annie.annieforchild.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.ui.activity.lesson.CourseActivity;
import com.annie.annieforchild.ui.activity.lesson.FollowTaskActivity;
import com.annie.annieforchild.ui.activity.lesson.MaterialActivity;
import com.annie.annieforchild.ui.activity.lesson.ScheduleActivity;
import com.annie.annieforchild.view.SecondView;
import com.annie.baselibrary.base.BaseFragment;

import java.util.Calendar;

/**
 * 课业
 * Created by WangLei on 2018/2/23 0023
 */

public class SecondFragment extends BaseFragment implements SecondView, View.OnClickListener {
    private ImageView lessonSchedule, lessonCourse, lessonTextbook;
    private RelativeLayout followTaskLayout, seriesTaskLayout, optionalTaskLayout;
    private TextView todayDate;
    private Calendar calendar;
    private String tag;
    private String week;

    @Override
    protected void initData() {
        calendar = Calendar.getInstance();
        Bundle bundle = getArguments();
        if (bundle != null) {
            tag = bundle.getString("tag");
        }
        int w = calendar.get(Calendar.DAY_OF_WEEK);
        switch (w) {
            case 1:
                week = "星期日";
                break;
            case 2:
                week = "星期一";
                break;
            case 3:
                week = "星期二";
                break;
            case 4:
                week = "星期三";
                break;
            case 5:
                week = "星期四";
                break;
            case 6:
                week = "星期五";
                break;
            case 7:
                week = "星期六";
                break;
        }
        todayDate.setText((calendar.get(Calendar.MONTH) + 1) + "月" + calendar.get(Calendar.DAY_OF_MONTH) + "日" + "\n" + week);
    }

    @Override
    protected void initView(View view) {
        lessonSchedule = view.findViewById(R.id.lesson_schedule);
        lessonCourse = view.findViewById(R.id.lesson_course);
        lessonTextbook = view.findViewById(R.id.lesson_textbook);
        followTaskLayout = view.findViewById(R.id.follow_task_layout);
        seriesTaskLayout = view.findViewById(R.id.series_task_layout);
        optionalTaskLayout = view.findViewById(R.id.optional_task_layout);
        todayDate = view.findViewById(R.id.today_date);
        lessonSchedule.setOnClickListener(this);
        lessonCourse.setOnClickListener(this);
        lessonTextbook.setOnClickListener(this);
        followTaskLayout.setOnClickListener(this);
        seriesTaskLayout.setOnClickListener(this);
        optionalTaskLayout.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_second_fragment;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.lesson_schedule:
                //我的课表
                intent.setClass(getContext(), ScheduleActivity.class);
                startActivity(intent);
                break;
            case R.id.lesson_course:
                //我的课程
                intent.setClass(getContext(), CourseActivity.class);
                startActivity(intent);
                break;
            case R.id.lesson_textbook:
                //我的教材
                intent.setClass(getContext(), MaterialActivity.class);
                startActivity(intent);
                break;
            case R.id.follow_task_layout:
                //随堂作业
                intent.setClass(getContext(), FollowTaskActivity.class);
                startActivity(intent);
                break;
            case R.id.series_task_layout:
                //系列作业

                break;
            case R.id.optional_task_layout:
                //自选作业

                break;
        }
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
}
