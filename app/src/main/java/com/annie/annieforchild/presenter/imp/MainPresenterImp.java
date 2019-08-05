package com.annie.annieforchild.presenter.imp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.annie.annieforchild.Utils.ActivityCollector;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.Banner;
import com.annie.annieforchild.bean.HomeData;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.interactor.MainInteractor;
import com.annie.annieforchild.interactor.imp.MainInteractorImp;
import com.annie.annieforchild.presenter.MainPresenter;
import com.annie.annieforchild.ui.activity.login.LoginActivity;
import com.annie.annieforchild.ui.activity.my.WebActivity;
import com.annie.annieforchild.ui.activity.net.NetWorkActivity;
import com.annie.annieforchild.ui.application.MyApplication;
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
import java.util.LinkedHashMap;
import java.util.List;

import static android.content.Context.MODE_MULTI_PROCESS;
import static android.content.Context.MODE_PRIVATE;

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
    private List<Banner> bannerList2; //banner列表
    private HashMap<Integer, String> file_maps;
    private HashMap<Integer, String> file_maps2;
    private MyApplication application;
    private int screenwidth;

    public MainPresenterImp(Context context, MainView mainView, int screenwidth) {
        this.context = context;
        this.mainView = mainView;
        this.screenwidth = screenwidth;
        application = (MyApplication) context.getApplicationContext();
    }

    private void initImageSlide() {
        mainView.getImageSlide().removeAllSliders();
        for (int name : file_maps.keySet()) {
            DefaultSliderView defaultSliderView = new DefaultSliderView(context);
            defaultSliderView.bundle(new Bundle());
            defaultSliderView.getBundle().putInt("extra", name);
            defaultSliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(BaseSliderView baseSliderView) {
                    if (application.getSystemUtils().getTag().equals("游客")) {
                        SystemUtils.toLogin(context);
                        return;
                    }
                    if (application.getSystemUtils().getChildTag() == 0) {
                        SystemUtils.toAddChild(context);
                        return;
                    }
                    if (!bannerList.get(baseSliderView.getBundle().getInt("extra")).getUrl().equals("")) {
                        if (bannerList.get(baseSliderView.getBundle().getInt("extra")).getUrl().equals("1")) {
                            Intent intent = new Intent(context, NetWorkActivity.class);
                            context.startActivity(intent);
                        } else {
                            Intent intent = new Intent(context, WebActivity.class);
                            intent.putExtra("url", bannerList.get(baseSliderView.getBundle().getInt("extra")).getUrl());
                            intent.putExtra("title", "活动");
                            context.startActivity(intent);
                        }
                    }
                }
            });
            defaultSliderView.image(file_maps.get(name));
            mainView.getImageSlide().addSlider(defaultSliderView);
        }
        mainView.getImageSlide().setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Invisible);
        mainView.getImageSlide().setPresetTransformer(SliderLayout.Transformer.DepthPage);
        mainView.getImageSlide().setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mainView.getImageSlide().setCustomAnimation(new DescriptionAnimation());
        mainView.getImageSlide().setDuration(4000);
    }

    private void initImageSlide2() {
        mainView.getImageSlide2().removeAllSliders();
        for (int name : file_maps2.keySet()) {
            DefaultSliderView defaultSliderView = new DefaultSliderView(context);
            defaultSliderView.bundle(new Bundle());
            defaultSliderView.getBundle().putInt("extra", name);
            defaultSliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(BaseSliderView baseSliderView) {
                    if (application.getSystemUtils().getTag().equals("游客")) {
                        SystemUtils.toLogin(context);
                        return;
                    }
                    if (application.getSystemUtils().getChildTag() == 0) {
                        SystemUtils.toAddChild(context);
                        return;
                    }
                    if (!bannerList2.get(baseSliderView.getBundle().getInt("extra")).getUrl().equals("")) {
                        if (bannerList2.get(baseSliderView.getBundle().getInt("extra")).getUrl().equals("1")) {
                            Intent intent = new Intent(context, NetWorkActivity.class);
                            intent.putExtra("to_exp", "1");
                            context.startActivity(intent);
                        } else if (bannerList2.get(baseSliderView.getBundle().getInt("extra")).getUrl().equals("2")) {
                            Intent intent = new Intent(context, NetWorkActivity.class);
                            intent.putExtra("to_exp", "2");
                            context.startActivity(intent);
                        } else {
                            Intent intent = new Intent(context, WebActivity.class);
                            intent.putExtra("url", bannerList2.get(baseSliderView.getBundle().getInt("extra")).getUrl());
                            intent.putExtra("title", "活动");
                            context.startActivity(intent);
                        }
                    }
                }
            });
            defaultSliderView.image(file_maps2.get(name));
            mainView.getImageSlide2().addSlider(defaultSliderView);
        }
        mainView.getImageSlide2().movePrevPosition(false);
        mainView.getImageSlide2().setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Invisible);
//        mainView.getImageSlide2().setPresetTransformer(SliderLayout.Transformer.DepthPage);
        mainView.getImageSlide2().setPresetTransformer(SliderLayout.Transformer.Accordion);
        mainView.getImageSlide2().setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mainView.getImageSlide2().setCurrentPosition(0);
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    public void initViewAndData() {
        file_maps = mainView.getFile_maps();
        file_maps2 = mainView.getFile_maps2();
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
                if (homeData.getPictureList() != null) {
                    bannerList2 = homeData.getPictureList();
                    file_maps2.clear();
                    for (int i = 0; i < bannerList2.size(); i++) {
                        file_maps2.put(i, bannerList2.get(i).getImageUrl());
                    }
                    initImageSlide2();
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
    public void Error(int what, int status, String error) {
        mainView.dismissLoad();
        if (status == 1) {
            //该账号已在别处登陆
            if (!application.getSystemUtils().isReLogin()) {
                application.getSystemUtils().setReLogin(true);
                mainView.showInfo(error);

                JTMessage message = new JTMessage();
                message.what = MethodCode.EVENT_RELOGIN;
                message.obj = 1;
                EventBus.getDefault().post(message);

                SharedPreferences preferences = context.getSharedPreferences("userInfo", MODE_PRIVATE | MODE_MULTI_PROCESS);
                SharedPreferences.Editor editor = preferences.edit();
                editor.remove("phone");
                editor.remove("psd");
                editor.commit();
                application.getSystemUtils().getPhoneSN().setUsername(null);
                application.getSystemUtils().getPhoneSN().setLastlogintime(null);
                application.getSystemUtils().getPhoneSN().setSystem(null);
                application.getSystemUtils().getPhoneSN().setBitcode(null);
                application.getSystemUtils().setDefaultUsername(null);
                application.getSystemUtils().setToken(null);
                application.getSystemUtils().getPhoneSN().save();
                application.getSystemUtils().setOnline(false);
                ActivityCollector.finishAll();
                Intent intent2 = new Intent(context, LoginActivity.class);
                context.startActivity(intent2);
                return;
            } else {
                return;
            }
        } else if (status == 2) {
            //升级

        } else if (status == 3) {
            //参数错误
            SystemUtils.setDefaltSn(context, application);
        } else if (status == 4) {
            //服务器错误

        } else if (status == 5) {
            //账号或密码错误

        } else if (status == 6) {
            //获取验证码失败

        } else if (status == 7) {
            Toast.makeText(application.getApplicationContext(), error, Toast.LENGTH_SHORT).show();
            /**
             * {@link com.annie.annieforchild.ui.fragment.FirstFragment#onMainEventThread(JTMessage)}
             */
            JTMessage message = new JTMessage();
            message.what = MethodCode.EVENT_ERROR;
            message.obj = error;
            EventBus.getDefault().post(message);
        }


    }

    @Override
    public void Fail(int what, String error) {
        if (mainView != null) {
            mainView.dismissLoad();
        }
        Toast.makeText(application.getApplicationContext(), error, Toast.LENGTH_SHORT).show();
    }
}
