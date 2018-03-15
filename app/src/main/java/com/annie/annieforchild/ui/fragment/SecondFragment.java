package com.annie.annieforchild.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.annie.annieforchild.R;
import com.annie.annieforchild.ui.activity.lesson.ScheduleActivity;
import com.annie.annieforchild.view.SecondView;
import com.annie.baselibrary.base.BaseFragment;

/**
 * 课业
 * Created by WangLei on 2018/2/23 0023
 */

public class SecondFragment extends BaseFragment implements SecondView, View.OnClickListener {
    private TextView lessonSchedule, lessonCourse, lessonTextbook, lessonTask;
    private String tag;
    @Override
    protected void initData() {
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
        lessonTask = view.findViewById(R.id.lesson_task);
        lessonSchedule.setOnClickListener(this);
        lessonCourse.setOnClickListener(this);
        lessonTextbook.setOnClickListener(this);
        lessonTask.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_second_fragment;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lesson_schedule:
                //课表
                Intent intent = new Intent(getContext(), ScheduleActivity.class);
                startActivity(intent);
                break;
            case R.id.lesson_course:
                //课程

                break;
            case R.id.lesson_textbook:
                //教材

                break;
            case R.id.lesson_task:
                //作业

                break;
        }
    }

    @Override
    public void showInfo(String info) {

    }

    @Override
    public void showLoad() {

    }

    @Override
    public void dismissLoad() {

    }
}
