package com.annie.annieforchild.ui.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.GridView;
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
import com.annie.annieforchild.bean.HomeData;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.song.SongClassify;
import com.annie.annieforchild.bean.tongzhi.Msgs;
import com.annie.annieforchild.bean.login.PhoneSN;
import com.annie.annieforchild.bean.song.Song;
import com.annie.annieforchild.presenter.GrindEarPresenter;
import com.annie.annieforchild.presenter.MainPresenter;
import com.annie.annieforchild.presenter.imp.GrindEarPresenterImp;
import com.annie.annieforchild.presenter.imp.MainPresenterImp;
import com.annie.annieforchild.ui.activity.GlobalSearchActivity;
import com.annie.annieforchild.ui.activity.grindEar.GrindEarActivity;
import com.annie.annieforchild.ui.activity.grindEar.ListenSongActivity;
import com.annie.annieforchild.ui.activity.grindEar.MeiriyiActivity;
import com.annie.annieforchild.ui.activity.lesson.CourseActivity;
import com.annie.annieforchild.ui.activity.lesson.ScheduleActivity;
import com.annie.annieforchild.ui.activity.mains.EventActivity;
import com.annie.annieforchild.ui.activity.mains.SquareActivity;
import com.annie.annieforchild.ui.activity.my.MyMessageActivity;
import com.annie.annieforchild.ui.activity.my.WebActivity;
import com.annie.annieforchild.ui.activity.reading.ReadingActivity;
import com.annie.annieforchild.ui.adapter.RecommendAdapter;
import com.annie.annieforchild.ui.fragment.spoken.SpokenActivity;
import com.annie.annieforchild.view.MainView;
import com.annie.baselibrary.base.BaseFragment;
import com.daimajia.slider.library.SliderLayout;

import org.greenrobot.eventbus.Subscribe;
import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 首页
 * Created by WangLei on 2018/1/12
 */

public class FirstFragment extends BaseFragment implements MainView, View.OnClickListener, SearchView.OnQueryTextListener {
    private SwipeRefreshLayout first_refresh_layout;
    private RelativeLayout firstMsgLayout, searchLayout, lessonLayout;
    private GridView recommendGrid;
    private RecommendAdapter recommend_adapter;
    private SliderLayout imageSlide;
    private ImageView signImage, grindEarLayout, readingLayout, spokenLayout, wodekecheng;
    private ViewFlipper msgFlipper;
    private RecyclerView myCourse_list;
    private LinearLayout clockInLayout, scheduleLayout, eventLayout, matchLayout, grindEar100, reading100, spoken100, word100;
    private List<TextView> msgText;
    private List<Song> recommend_list;
    private List<SongClassify> spokenList;
    private View error;
    private String tag;
    private AlertHelper helper;
    private Dialog dialog;
    private MainPresenter presenter;
    private GrindEarPresenter presenter2;
    private HashMap<Integer, String> file_maps;//轮播图图片map
    private int screenwidth;

    {
        setRegister(true);
    }

    public FirstFragment() {

    }

    @Override
    protected void initData() {
        recommend_list = new ArrayList<>();
        spokenList = new ArrayList<>();
        msgText = new ArrayList<>();
        helper = new AlertHelper(getActivity());
        dialog = helper.LoadingDialog();
        Bundle bundle = getArguments();
        if (bundle != null) {
            tag = bundle.getString("tag");
        }

        if (tag.equals("游客")) {
            wodekecheng.setVisibility(View.GONE);
            lessonLayout.setVisibility(View.GONE);
        } else {
            wodekecheng.setVisibility(View.VISIBLE);
            lessonLayout.setVisibility(View.VISIBLE);
        }
        if (SystemUtils.childTag == 0) {
            wodekecheng.setVisibility(View.GONE);
            lessonLayout.setVisibility(View.GONE);
        }
        file_maps = new HashMap<>();

        recommend_adapter = new RecommendAdapter(getContext(), recommend_list);
        recommendGrid.setAdapter(recommend_adapter);

        WindowManager managers = getActivity().getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        managers.getDefaultDisplay().getMetrics(outMetrics);
        screenwidth = outMetrics.widthPixels;
        presenter = new MainPresenterImp(getContext(), this, screenwidth);
        presenter2 = new GrindEarPresenterImp(getContext(), this);
        presenter.initViewAndData();
        presenter2.initViewAndData();
        presenter.setMyCourseAdapter(myCourse_list);
        List<PhoneSN> list = DataSupport.findAll(PhoneSN.class);
//        showInfo(list.size() + "==" + SystemUtils.phoneSN.toString());
        presenter.getHomeData(tag);
        if (SystemUtils.childTag == 1) {
            presenter2.getMusicList(-1);
        }

//        Dialog dialog = SystemUtils.CoundownDialog(getActivity());
//        dialog.show();
    }

    @Override
    protected void initView(View view) {
        first_refresh_layout = view.findViewById(R.id.first_refresh_layout);
        imageSlide = view.findViewById(R.id.image_slide);
        myCourse_list = view.findViewById(R.id.myCourse_list);
        firstMsgLayout = view.findViewById(R.id.first_msg_layout);
        searchLayout = view.findViewById(R.id.search_layout);
        recommendGrid = view.findViewById(R.id.recommendGrid);
        clockInLayout = view.findViewById(R.id.clock_in_layout);
        scheduleLayout = view.findViewById(R.id.schedule_layout);
        eventLayout = view.findViewById(R.id.event_layout);
        matchLayout = view.findViewById(R.id.match_layout);
        grindEarLayout = view.findViewById(R.id.grind_ear_layout);
        readingLayout = view.findViewById(R.id.reading_layout);
        spokenLayout = view.findViewById(R.id.spoken_layout);
        signImage = view.findViewById(R.id.sign_image);
        msgFlipper = view.findViewById(R.id.msg_flipper);
        lessonLayout = view.findViewById(R.id.lesson_layout);
        error = view.findViewById(R.id.network_error_layout);
        grindEar100 = view.findViewById(R.id.grind_ear100_layout);
        reading100 = view.findViewById(R.id.reading100_layout);
        spoken100 = view.findViewById(R.id.spoken100_layout);
        word100 = view.findViewById(R.id.word100_layout);
        wodekecheng = view.findViewById(R.id.wodekecheng);
        clockInLayout.setOnClickListener(this);
        scheduleLayout.setOnClickListener(this);
        eventLayout.setOnClickListener(this);
        matchLayout.setOnClickListener(this);
        firstMsgLayout.setOnClickListener(this);
        grindEarLayout.setOnClickListener(this);
        readingLayout.setOnClickListener(this);
        spokenLayout.setOnClickListener(this);
        signImage.setOnClickListener(this);
        searchLayout.setOnClickListener(this);
        lessonLayout.setOnClickListener(this);
        grindEar100.setOnClickListener(this);
        reading100.setOnClickListener(this);
        spoken100.setOnClickListener(this);
        word100.setOnClickListener(this);
        wodekecheng.setOnClickListener(this);
        first_refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getHomeData(tag);
                if (SystemUtils.childTag == 1) {
                    presenter2.getMusicList(-1);
                }
            }
        });
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        myCourse_list.setLayoutManager(manager);
        searchLayout.setFocusable(true);
        searchLayout.setFocusableInTouchMode(true);
        searchLayout.requestFocus();
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
            if (first_refresh_layout.isRefreshing()) {
                first_refresh_layout.setRefreshing(false);
            }
            HomeData homeData = (HomeData) message.obj;
            List<Msgs> msgList = homeData.getMsgList();
            if (msgList.size() == 0) {
                msgList = new ArrayList<>();
                Msgs msgs = new Msgs();
                msgs.setTitle("暂无数据");
                msgList.add(msgs);
            }
            msgText.clear();
            for (int i = 0; i < msgList.size(); i++) {
                TextView textView = new TextView(getContext());
                textView.setText(msgList.get(i).getTitle());
                textView.setTextSize(14);
                textView.setTextColor(getResources().getColor(R.color.text_black));
                textView.setMaxEms(10);
                textView.setSingleLine(true);
                textView.setEllipsize(TextUtils.TruncateAt.END);
                msgText.add(textView);
            }
            msgFlipper.removeAllViews();
            for (int i = 0; i < msgText.size(); i++) {
                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                msgFlipper.addView(msgText.get(i), params);
            }

            recommend_list.clear();
            recommend_list.addAll(homeData.getRecommendList());
            recommend_adapter.notifyDataSetChanged();
        } else if (message.what == MethodCode.EVENT_GETSPOKENCLASSES) {
            spokenList.clear();
            spokenList.addAll((List<SongClassify>) message.obj);
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
                    SystemUtils.toLogin(getContext());
                    return;
                }
                if (SystemUtils.childTag == 0) {
                    showInfo("请添加学员");
                    return;
                }
//                intent.setClass(getContext(), TodayClockInActivity.class);
//                startActivity(intent);
                intent = new Intent(getContext(), WebActivity.class);
                intent.putExtra("url", SystemUtils.mainUrl + "Signin/today?username=" + SystemUtils.defaultUsername);
                intent.putExtra("title", "今日统计");


                startActivity(intent);
                break;
            case R.id.schedule_layout:
                //我的课表
                if (tag.equals("游客")) {
                    SystemUtils.toLogin(getContext());
                    return;
                }
                if (SystemUtils.childTag == 0) {
                    showInfo("请添加学员");
                    return;
                }
                intent.setClass(getContext(), ScheduleActivity.class);
                startActivity(intent);
                break;
            case R.id.event_layout:
                //精彩活动
                intent.setClass(getContext(), EventActivity.class);
                startActivity(intent);
                break;
            case R.id.match_layout:
                //广场
                if (tag.equals("游客")) {
                    SystemUtils.toLogin(getContext());
                    return;
                }
                if (SystemUtils.childTag == 0) {

                    showInfo("请添加学员");
                    return;
                }
                intent.setClass(getContext(), SquareActivity.class);
                startActivity(intent);
                break;
            case R.id.grind_ear_layout:
                //磨耳朵
//                if (SystemUtils.tag.equals("游客")) {
//                    SystemUtils.toLogin(getContext());
//                    return;
//                }
                intent.setClass(getContext(), GrindEarActivity.class);
                startActivity(intent);
                break;
            case R.id.reading_layout:
                //阅读
//                if (SystemUtils.tag.equals("游客")) {
//                    SystemUtils.toLogin(getContext());
//                    return;
//                }
                intent.setClass(getContext(), ReadingActivity.class);
                startActivity(intent);
                break;
            case R.id.spoken_layout:
                //口语
                if (SystemUtils.tag.equals("游客")) {
                    SystemUtils.toLogin(getContext());
                    return;
                }
                if (SystemUtils.childTag == 0) {
                    showInfo("请添加学员");
                    return;
                }
                if (spokenList.size() != 0) {
                    intent.setClass(getContext(), ListenSongActivity.class);
                    Bundle bundle5 = new Bundle();
                    bundle5.putInt("type", 11);
                    bundle5.putSerializable("ClassifyList", (Serializable) spokenList);
                    intent.putExtras(bundle5);
                    startActivity(intent);
                }
                break;
            case R.id.first_msg_layout:
                //信息
                if (SystemUtils.tag.equals("游客")) {
                    SystemUtils.toLogin(getContext());
                    return;
                }
                if (SystemUtils.childTag == 0) {
                    showInfo("请添加学员");
                    return;
                }
                intent.setClass(getContext(), MyMessageActivity.class);
                startActivity(intent);
                break;
            case R.id.search_layout:
                //搜索
                if (SystemUtils.tag.equals("游客")) {
                    SystemUtils.toLogin(getContext());
                    return;
                }
                if (SystemUtils.childTag == 0) {
                    showInfo("请添加学员");
                    return;
                }
                Intent intent2 = new Intent(getContext(), GlobalSearchActivity.class);
                startActivity(intent2);
                break;
            case R.id.sign_image:
                //右上角签到
                //TODO:
//                Intent intent1 = new Intent(getContext(), ExerciseTestActivity.class);
//                startActivity(intent1);
                if (SystemUtils.tag.equals("游客")) {
                    SystemUtils.toLogin(getContext());
                    return;
                }
                if (SystemUtils.childTag == 0) {
                    showInfo("请添加学员");
                    return;
                }
                Intent intent1 = new Intent(getContext(), WebActivity.class);
                intent1.putExtra("url", SystemUtils.mainUrl + "Signin/index?username=" + SystemUtils.defaultUsername);
                intent1.putExtra("title", "签到");
                intent1.putExtra("share", 1);
                startActivity(intent1);
                break;
            case R.id.lesson_layout:
                //我的课程
                if (SystemUtils.tag.equals("游客")) {
                    SystemUtils.toLogin(getContext());
                    return;
                }
                if (SystemUtils.childTag == 0) {
                    showInfo("请添加学员");
                    return;
                }
                intent.setClass(getContext(), CourseActivity.class);
                startActivity(intent);
                break;
            case R.id.grind_ear100_layout:
                //磨耳朵100

                break;
            case R.id.reading100_layout:
                //阅读100

                break;
            case R.id.spoken100_layout:
                //口语100

                break;
            case R.id.word100_layout:
                //单词100

                break;
            case R.id.wodekecheng:
                //我的课程推荐
                intent.setClass(getContext(), WebActivity.class);
                intent.putExtra("url", "https://mp.weixin.qq.com/s/Wtkns9YyzcmyNsnkgoFLMg");
                intent.putExtra("title", "我的课程推荐");
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
