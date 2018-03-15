package com.annie.annieforchild.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.PhoneSN;
import com.annie.annieforchild.presenter.MainPresenter;
import com.annie.annieforchild.presenter.imp.MainPresenterImp;
import com.annie.annieforchild.ui.activity.grindEar.GrindEarActivity;
import com.annie.annieforchild.ui.activity.my.MyMessageActivity;
import com.annie.annieforchild.ui.activity.ReadingActivity;
import com.annie.annieforchild.view.MainView;
import com.annie.baselibrary.base.BaseFragment;
import com.daimajia.slider.library.SliderLayout;

import org.litepal.crud.DataSupport;

import java.util.HashMap;
import java.util.List;

/**
 * 首页
 * Created by WangLei on 2018/1/12
 */

public class FirstFragment extends BaseFragment implements MainView, View.OnClickListener, SearchView.OnQueryTextListener {
    ConstraintLayout grindEarLayout, readingLayout, spokenLayout;
    SwipeRefreshLayout first_refresh_layout;
    RelativeLayout firstMsgLayout, searchLayout;
    SliderLayout imageSlide;
    ImageView signImage;
    RecyclerView myCourse_list;
    LinearLayout clockInLayout, scheduleLayout, squareLayout, eventLayout, matchLayout;
    View error;
    String tag;

    private MainPresenter presenter;
    private HashMap<String, Integer> file_maps;//轮播图图片map

    public FirstFragment() {

    }

    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            tag = bundle.getString("tag");
            showInfo("身份:" + tag);
        }
        file_maps = new HashMap<>();
        presenter = new MainPresenterImp(getContext(), this);
        presenter.initViewAndData();
        presenter.setMyCourseAdapter(myCourse_list);
        List<PhoneSN> list = DataSupport.findAll(PhoneSN.class);
        showInfo(list.size() + "==" + SystemUtils.phoneSN.toString());
    }

    @Override
    protected void initView(View view) {
        first_refresh_layout = view.findViewById(R.id.first_refresh_layout);
        imageSlide = view.findViewById(R.id.image_slide);
        myCourse_list = view.findViewById(R.id.myCourse_list);
        firstMsgLayout = view.findViewById(R.id.first_msg_layout);
        searchLayout = view.findViewById(R.id.search_layout);
        clockInLayout = view.findViewById(R.id.clock_in_layout);
        scheduleLayout = view.findViewById(R.id.schedule_layout);
        squareLayout = view.findViewById(R.id.square_layout);
        eventLayout = view.findViewById(R.id.event_layout);
        matchLayout = view.findViewById(R.id.match_layout);
        grindEarLayout = view.findViewById(R.id.grind_ear_layout);
        readingLayout = view.findViewById(R.id.reading_layout);
        spokenLayout = view.findViewById(R.id.spoken_layout);
        signImage = view.findViewById(R.id.sign_image);
        error = view.findViewById(R.id.network_error_layout);
        clockInLayout.setOnClickListener(this);
        scheduleLayout.setOnClickListener(this);
        squareLayout.setOnClickListener(this);
        eventLayout.setOnClickListener(this);
        matchLayout.setOnClickListener(this);
        firstMsgLayout.setOnClickListener(this);
        grindEarLayout.setOnClickListener(this);
        readingLayout.setOnClickListener(this);
        spokenLayout.setOnClickListener(this);
        signImage.setOnClickListener(this);
        searchLayout.setOnClickListener(this);
        first_refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                first_refresh_layout.setRefreshing(false);
            }
        });
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        myCourse_list.setLayoutManager(manager);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_first_fragment;
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
    public SliderLayout getImageSlide() {
        return imageSlide;
    }

    @Override
    public HashMap<String, Integer> getFile_maps() {
        return file_maps;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.clock_in_layout:
                //今日打卡

                break;
            case R.id.schedule_layout:
                //我的课表

                break;
            case R.id.square_layout:
                //广场

                break;
            case R.id.event_layout:
                //精彩活动

                break;
            case R.id.match_layout:
                //大赛专区

                break;
            case R.id.grind_ear_layout:
                //磨耳朵
                Intent intent = new Intent(getContext(), GrindEarActivity.class);
                startActivity(intent);
                break;
            case R.id.reading_layout:
                //阅读
                Intent intent1 = new Intent(getContext(), ReadingActivity.class);
                startActivity(intent1);
                break;
            case R.id.spoken_layout:
                //口语

                break;
            case R.id.first_msg_layout:
                //信息
                Intent intent2 = new Intent(getContext(), MyMessageActivity.class);
                startActivity(intent2);
                break;
            case R.id.search_layout:
                //搜索

                break;
            case R.id.sign_image:
                //右上角分享

                break;
        }
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        SystemUtils.show(getContext(), s);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
//        SystemUtils.show(getContext(), s);
        return false;
    }
}
