package com.annie.annieforchild.presenter.imp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.annie.annieforchild.R;
import com.annie.annieforchild.bean.Course;
import com.annie.annieforchild.presenter.SecondPresenter;
import com.annie.annieforchild.ui.adapter.MyCourseAdapter;
import com.annie.annieforchild.view.SecondView;
import com.annie.baselibrary.base.BasePresenterImp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WangLei on 2018/2/2 0002
 */

public class SecondPresenterImp extends BasePresenterImp implements SecondPresenter {
    private Context context;
    private SecondView secondView;
    private List<Course> myLesson_lists;
    private MyCourseAdapter adapter;

    public SecondPresenterImp(Context context, SecondView secondView) {
        this.context = context;
        this.secondView = secondView;
    }

    @Override
    public void initViewAndData() {
        myLesson_lists = new ArrayList<>();
        myLesson_lists.add(new Course(R.drawable.lesson_grind_ear, "西游记"));
        myLesson_lists.add(new Course(R.drawable.lesson_spelling, "红楼梦"));
        myLesson_lists.add(new Course(R.drawable.lesson_story, "三国演义"));
        myLesson_lists.add(new Course(R.drawable.lesson_grind_ear, "水浒传"));
        myLesson_lists.add(new Course(R.drawable.lesson_spelling, "论语"));
        myLesson_lists.add(new Course(R.drawable.lesson_story, "三字经"));
        adapter = new MyCourseAdapter(context, myLesson_lists);
    }

    @Override
    public void setMyLessonAdapter(RecyclerView myLesson_list) {
        myLesson_list.setAdapter(adapter);
    }

    @Override
    public void Success(int what, Object result) {

    }
}
