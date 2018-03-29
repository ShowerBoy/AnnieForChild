package com.annie.annieforchild.ui.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.login.PhoneSN;
import com.annie.annieforchild.presenter.MainPresenter;
import com.annie.annieforchild.presenter.imp.MainPresenterImp;
import com.annie.annieforchild.ui.activity.grindEar.GrindEarActivity;
import com.annie.annieforchild.ui.activity.lesson.CourseActivity;
import com.annie.annieforchild.ui.activity.lesson.ScheduleActivity;
import com.annie.annieforchild.ui.activity.mains.TodayClockInActivity;
import com.annie.annieforchild.ui.activity.my.MyMessageActivity;
import com.annie.annieforchild.ui.activity.ReadingActivity;
import com.annie.annieforchild.view.MainView;
import com.annie.baselibrary.base.BaseFragment;
import com.daimajia.slider.library.SliderLayout;

import org.greenrobot.eventbus.Subscribe;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 首页
 * Created by WangLei on 2018/1/12
 */

public class FirstFragment extends BaseFragment implements MainView, View.OnClickListener, SearchView.OnQueryTextListener {
    private ConstraintLayout grindEarLayout, readingLayout, spokenLayout;
    private SwipeRefreshLayout first_refresh_layout;
    private RelativeLayout firstMsgLayout, searchLayout, lessonLayout;
    private SliderLayout imageSlide;
    private ImageView signImage;
    private ViewFlipper msgFlipper;
    private RecyclerView myCourse_list;
    private LinearLayout clockInLayout, scheduleLayout, squareLayout, eventLayout, matchLayout;
    private List<TextView> msgText;
    private View error;
    private String tag;
    private AlertHelper helper;
    private Dialog dialog;
    private MainPresenter presenter;
    private HashMap<Integer, String> file_maps;//轮播图图片map

    {
        setRegister(true);
    }

    public FirstFragment() {

    }

    @Override
    protected void initData() {
        msgText = new ArrayList<>();
        helper = new AlertHelper(getActivity());
        dialog = helper.LoadingDialog();
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
        presenter.getHomeData(tag);
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
        msgFlipper = view.findViewById(R.id.msg_flipper);
        lessonLayout = view.findViewById(R.id.lesson_layout);
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
        lessonLayout.setOnClickListener(this);
        first_refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                first_refresh_layout.setRefreshing(false);
//                presenter.getHomeData(tag);
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


    /**
     * {@link MainPresenterImp#Success(int, Object)}
     *
     * @param message
     */
    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_GETHOMEDATA) {
            String[] msgList = (String[]) message.obj;
            for (int i = 0; i < msgList.length; i++) {
                TextView textView = new TextView(getContext());
                textView.setText(msgList[i]);
                textView.setTextSize(14);
                textView.setTextColor(getResources().getColor(R.color.text_black));
                textView.setMaxEms(10);
                textView.setSingleLine(true);
                textView.setEllipsize(TextUtils.TruncateAt.END);
                msgText.add(textView);
            }
            for (int i = 0; i < msgText.size(); i++) {
                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                msgFlipper.addView(msgText.get(i), params);
            }
            if (first_refresh_layout.isRefreshing()) {
                first_refresh_layout.setRefreshing(false);
            }
        }
    }

    @Override
    public SliderLayout getImageSlide() {
        return imageSlide;
    }

    @Override
    public HashMap<Integer, String> getFile_maps() {
        return file_maps;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.clock_in_layout:
                //今日打卡
                if (tag.equals("游客")) {
                    showInfo("请登录");
                    return;
                }
                intent.setClass(getContext(), TodayClockInActivity.class);
                startActivity(intent);
                break;
            case R.id.schedule_layout:
                //我的课表
                intent.setClass(getContext(), ScheduleActivity.class);
                startActivity(intent);
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
                intent.setClass(getContext(), GrindEarActivity.class);
                startActivity(intent);
                break;
            case R.id.reading_layout:
                //阅读
                intent.setClass(getContext(), ReadingActivity.class);
                startActivity(intent);
                break;
            case R.id.spoken_layout:
                //口语

                break;
            case R.id.first_msg_layout:
                //信息
                intent.setClass(getContext(), MyMessageActivity.class);
                startActivity(intent);
                break;
            case R.id.search_layout:
                //搜索

                break;
            case R.id.sign_image:
                //右上角分享

                break;
            case R.id.lesson_layout:
                //我的课程
                intent.setClass(getContext(), CourseActivity.class);
                startActivity(intent);
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

    @Override
    public void showInfo(String info) {
        Toast.makeText(getContext(), info, Toast.LENGTH_SHORT).show();
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
}
