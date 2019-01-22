package com.annie.annieforchild.presenter.imp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.bean.Banner;
import com.annie.annieforchild.bean.HomeData;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.interactor.MainInteractor;
import com.annie.annieforchild.interactor.imp.MainInteractorImp;
import com.annie.annieforchild.presenter.MainPresenter;
import com.annie.annieforchild.ui.activity.my.WebActivity;
import com.annie.annieforchild.ui.activity.net.NetWorkActivity;
import com.annie.annieforchild.view.MainView;
import com.annie.baselibrary.base.BasePresenterImp;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.squareup.picasso.Picasso;

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
    private MainInteractor interactor;
    //    private List<Course> myCourse_lists;
//    private List<Course2> myCourse_lists2;
    private List<Banner> bannerList; //banner列表
    private HashMap<Integer, String> file_maps;
    private int screenwidth;

    public MainPresenterImp(Context context, MainView mainView, int screenwidth) {
        this.context = context;
        this.mainView = mainView;
        this.screenwidth = screenwidth;
    }

    private void initImageSlide() {
        mainView.getImageSlide().removeAllSliders();
        for (int name : file_maps.keySet()) {
            DefaultSliderView defaultSliderView = new DefaultSliderView(context);
//            TextSliderView textSliderView = new TextSliderView(context);
//            textSliderView
//                    .image(file_maps.get(name))
//                    .setScaleType(BaseSliderView.ScaleType.Fit)
//                    .setOnSliderClickListener(this);
//            textSliderView.bundle(new Bundle());
//            textSliderView.getBundle().putInt("extra", name);
//            mainView.getImageSlide().addSlider(textSliderView);
            defaultSliderView.bundle(new Bundle());
            defaultSliderView.getBundle().putInt("extra", name);
            defaultSliderView.setOnSliderClickListener(this);
            defaultSliderView.image(file_maps.get(name));
            mainView.getImageSlide().addSlider(defaultSliderView);
        }
        mainView.getImageSlide().setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Invisible);
        mainView.getImageSlide().setPresetTransformer(SliderLayout.Transformer.DepthPage);
        mainView.getImageSlide().setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mainView.getImageSlide().setCustomAnimation(new DescriptionAnimation());
        mainView.getImageSlide().setDuration(4000);
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
//        mainView.showInfo(bannerList.get(slider.getBundle().getInt("extra")).getUrl());
//        Uri uri = Uri.parse(bannerList.get(slider.getBundle().getInt("extra")).getUrl());
//        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//        context.startActivity(intent);
        if (!bannerList.get(slider.getBundle().getInt("extra")).getUrl().equals("")) {
            if (bannerList.get(slider.getBundle().getInt("extra")).getUrl().equals("1")) {
                Intent intent = new Intent(context, NetWorkActivity.class);
                context.startActivity(intent);
            } else {
                Intent intent = new Intent(context, WebActivity.class);
                intent.putExtra("url", bannerList.get(slider.getBundle().getInt("extra")).getUrl());
                intent.putExtra("title", "活动");
                context.startActivity(intent);
            }
        }
    }

    @Override
    public void initViewAndData() {
        file_maps = mainView.getFile_maps();
//        myCourse_lists2 = new ArrayList<>();
//        course_adapter = new MyCourseAdapter(context, screenwidth, myCourse_lists2);
        interactor = new MainInteractorImp(context, this);
    }

    @Override
    public void getHomeData(String tag) {
        interactor.getHomeData(tag);
    }

    @Override
    public void setMyCourseAdapter(RecyclerView recyclerView) {

    }

    @Override
    public void Success(int what, Object result) {
        mainView.dismissLoad();
        if (result != null) {
            if (what == MethodCode.EVENT_GETHOMEDATA) {
                HomeData homeData = (HomeData) result;
                if (homeData.getBannerList() != null) {
                    bannerList = homeData.getBannerList();
                    file_maps.clear();
                    for (int i = 0; i < bannerList.size(); i++) {
                        file_maps.put(i, bannerList.get(i).getImageUrl());
                    }
                    initImageSlide();
                }
                /**
                 * {@link com.annie.annieforchild.ui.fragment.FirstFragment#onMainEventThread(JTMessage)}
                 */
                JTMessage message = new JTMessage();
                message.what = what;
                message.obj = homeData;
                EventBus.getDefault().post(message);
//                if (homeData.getMyCourseList() != null) {
//                    myCourse_lists2.clear();
//                    myCourse_lists2.addAll(homeData.getMyCourseList());
//                    course_adapter.notifyDataSetChanged();
//                }
            }
        }
    }

    @Override
    public void Error(int what, String error) {
        mainView.showInfo(error);
        mainView.dismissLoad();
        /**
         * {@link com.annie.annieforchild.ui.fragment.FirstFragment#onMainEventThread(JTMessage)}
         */
        JTMessage message = new JTMessage();
        message.what = MethodCode.EVENT_ERROR;
        message.obj = error;
        EventBus.getDefault().post(message);
    }
}
