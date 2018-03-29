package com.annie.annieforchild.presenter.imp;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.bean.Banner;
import com.annie.annieforchild.bean.Course;
import com.annie.annieforchild.bean.Course2;
import com.annie.annieforchild.bean.HomeData;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.UserInfo2;
import com.annie.annieforchild.interactor.MainInteractor;
import com.annie.annieforchild.interactor.imp.MainInteractorImp;
import com.annie.annieforchild.presenter.MainPresenter;
import com.annie.annieforchild.ui.adapter.MyCourseAdapter;
import com.annie.annieforchild.view.MainView;
import com.annie.baselibrary.base.BasePresenterImp;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import org.greenrobot.eventbus.EventBus;

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
    private MainInteractor interactor;
    private List<Course> myCourse_lists;
    private List<Course2> myCourse_lists2;
    private List<Banner> bannerList; //banner列表
    private HashMap<Integer, String> file_maps;

    public MainPresenterImp(Context context, MainView mainView) {
        this.context = context;
        this.mainView = mainView;
    }

    private void initImageSlide() {
        for (int name : file_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(context);
            textSliderView
                    .description("")
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle().putInt("extra", name);
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
        mainView.showInfo(bannerList.get(slider.getBundle().getInt("extra")).getUrl());
    }

    @Override
    public void initViewAndData() {
        file_maps = mainView.getFile_maps();
        myCourse_lists = new ArrayList<>();
        myCourse_lists2 = new ArrayList<>();
//        myCourse_lists.add(new Course(0,R.drawable.lesson_grind_ear, "西游记"));
//        myCourse_lists.add(new Course(R.drawable.lesson_spelling, "红楼梦"));
//        myCourse_lists.add(new Course(R.drawable.lesson_story, "三国演义"));
//        myCourse_lists.add(new Course(R.drawable.lesson_grind_ear, "水浒传"));
//        myCourse_lists.add(new Course(R.drawable.lesson_spelling, "论语"));
//        myCourse_lists.add(new Course(R.drawable.lesson_story, "三字经"));
        course_adapter = new MyCourseAdapter(context, myCourse_lists);
        interactor = new MainInteractorImp(context, this);

    }

    @Override
    public void getHomeData(String tag) {
        mainView.showLoad();
        interactor.getHomeData(tag);
    }

    @Override
    public void setMyCourseAdapter(RecyclerView recyclerView) {
        recyclerView.setAdapter(course_adapter);
    }

    @Override
    public void Success(int what, Object result) {
        mainView.dismissLoad();
        if (result != null) {
            if (what == MethodCode.EVENT_GETHOMEDATA) {
                HomeData homeData = (HomeData) result;
                if (homeData.getBannerList() != null) {
                    bannerList = homeData.getBannerList();
                    for (int i = 0; i < bannerList.size(); i++) {
                        file_maps.put(i, bannerList.get(i).getImageUrl());
                    }
                    initImageSlide();
                }
                if (homeData.getMsgList() != null) {
                    String[] msgList = homeData.getMsgList();
                    //
                    msgList = new String[3];
                    msgList[0] = "第一条数据";
                    msgList[1] = "第二条数据";
                    msgList[2] = "第三条数据";
                    //
                    /**
                     * {@link com.annie.annieforchild.ui.fragment.FirstFragment#onMainEventThread(JTMessage)}
                     */
                    JTMessage message = new JTMessage();
                    message.what = what;
                    message.obj = msgList;
                    EventBus.getDefault().post(message);
                }
                if (homeData.getMyCourseList() != null) {
                    myCourse_lists.clear();
//                    myCourse_lists.addAll(homeData.getMyCourseList());
                    course_adapter.notifyDataSetChanged();
                }
            }
        }
    }

    @Override
    public void Error(int what, String error) {
        mainView.showInfo(error);
    }
}
