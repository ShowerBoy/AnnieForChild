package com.annie.annieforchild.presenter.imp;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.annie.annieforchild.R;
import com.annie.annieforchild.bean.Course;
import com.annie.annieforchild.bean.UserInfo2;
import com.annie.annieforchild.presenter.MainPresenter;
import com.annie.annieforchild.ui.adapter.MyCourseAdapter;
import com.annie.annieforchild.view.MainView;
import com.annie.baselibrary.base.BasePresenterImp;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 首页
 * Created by WangLei on 2018/1/15 0015
 */

public class MainPresenterImp extends BasePresenterImp implements MainPresenter, BaseSliderView.OnSliderClickListener {
    private Context context;
    private MainView mainView;
    private MyCourseAdapter course_adapter;
    private List<Course> myCourse_lists;
    private HashMap<String, Integer> file_maps;

    public MainPresenterImp(Context context, MainView mainView) {
        this.context = context;
        this.mainView = mainView;
    }

    private void initImageSlide() {
        for (String name : file_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(context);
            textSliderView
                    .description("")
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle().putString("extra", name);
            mainView.getImageSlide().addSlider(textSliderView);
        }
//        mainView.getImageSlide().setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Invisible);
        mainView.getImageSlide().setPresetTransformer(SliderLayout.Transformer.DepthPage);
        mainView.getImageSlide().setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mainView.getImageSlide().setCustomAnimation(new DescriptionAnimation());
        mainView.getImageSlide().setDuration(4000);
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        mainView.showInfo(slider.getBundle().getString("extra"));
    }

    @Override
    public void initViewAndData() {
        file_maps = mainView.getFile_maps();
        file_maps.put("0", R.drawable.imagetext);
        file_maps.put("1", R.drawable.imagetext);
        file_maps.put("2", R.drawable.imagetext);
        file_maps.put("3", R.drawable.imagetext);
        initImageSlide();
        myCourse_lists = new ArrayList<>();
        myCourse_lists.add(new Course(R.drawable.lesson_grind_ear, "西游记"));
        myCourse_lists.add(new Course(R.drawable.lesson_spelling, "红楼梦"));
        myCourse_lists.add(new Course(R.drawable.lesson_story, "三国演义"));
        myCourse_lists.add(new Course(R.drawable.lesson_grind_ear, "水浒传"));
        myCourse_lists.add(new Course(R.drawable.lesson_spelling, "论语"));
        myCourse_lists.add(new Course(R.drawable.lesson_story, "三字经"));
        course_adapter = new MyCourseAdapter(context, myCourse_lists);

    }

    @Override
    public void setMyCourseAdapter(RecyclerView recyclerView) {
        recyclerView.setAdapter(course_adapter);
    }

    @Override
    public void Success(int what, Object result) {

    }
}
