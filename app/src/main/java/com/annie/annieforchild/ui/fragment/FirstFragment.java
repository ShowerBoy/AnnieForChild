package com.annie.annieforchild.ui.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.CheckDoubleClickListener;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.OnCheckDoubleClick;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.Utils.service.MusicService;
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
import com.annie.annieforchild.ui.activity.MainActivity;
import com.annie.annieforchild.ui.activity.grindEar.GrindEarActivity;
import com.annie.annieforchild.ui.activity.lesson.ScheduleActivity2;
import com.annie.annieforchild.ui.activity.mains.BankBookActivity;
import com.annie.annieforchild.ui.activity.mains.SquareActivity;
import com.annie.annieforchild.ui.activity.my.WebActivity;
import com.annie.annieforchild.ui.activity.net.NetWorkActivity;
import com.annie.annieforchild.ui.activity.pk.MusicPlayActivity;
import com.annie.annieforchild.ui.activity.pk.PracticeActivity;
import com.annie.annieforchild.ui.activity.reading.ReadingActivity;
import com.annie.annieforchild.ui.activity.speaking.SpeakingActivity;
import com.annie.annieforchild.ui.application.MyApplication;
import com.annie.annieforchild.view.MainView;
import com.annie.baselibrary.base.BaseFragment;
import com.bumptech.glide.Glide;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import org.greenrobot.eventbus.Subscribe;
import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.content.Context.MODE_MULTI_PROCESS;
import static android.content.Context.MODE_PRIVATE;

/**
 * 首页
 * Created by WangLei on 2018/1/12
 */

public class FirstFragment extends BaseFragment implements MainView, BaseSliderView.OnSliderClickListener, OnCheckDoubleClick, SearchView.OnQueryTextListener {
    private SwipeRefreshLayout first_refresh_layout;
    private RelativeLayout firstMsgLayout, searchLayout, meiriyigeLayout, meiriyishiLayout, meiriyiduLayout;
    private AnimationDrawable musicBtn;
    //    private GridView recommendGrid;
//    private RecommendAdapter recommend_adapter;
    private TextView moreMoerduo, moreReading, moreSpoken, moerduoText1, moerduoText2, moerduoText3, readingText1, readingText2, readingText3, spokenText1, spokenText2, spokenText3, meiriyigeText, meiriyigeCount, meiriyishiText, meiriyishiCount, meiriyiduText, meiriyiduCount, grindText, readingText, speakingText;
    private SliderLayout imageSlide;
    private ImageView signImage, grindEarLayout, readingLayout, spokenLayout, moerduoImage1, moerduoImage2, moerduoImage3, readingImage1, readingImage2, readingImage3, spokenImage1, spokenImage2, spokenImage3, meiriyigeImage, meiriyishiImage, meiriyiduImage, grindLock1, grindLock2, grindLock3, readingLock1, readingLock2, readingLock3, speakingLock1, speakingLock2, speakingLock3, meiriyigeLock, meiriyishiLock, meiriyiduLock, freeImage1, freeImage2, freeImage3;
    private LinearLayout clockInLayout, scheduleLayout, eventLayout, matchLayout, iWantLayout, moerduoLinear, readingLinear, speakingLinear;
    private List<Song> moerduoList, readingList, speakingList, freeList;
    public static List<SongClassify> spokenList;
    private Song meiriyige, meiriyishi, meiriyidu;
    private View error;
    private String tag;
    private AlertHelper helper;
    private Dialog dialog;
    private MainPresenter presenter;
    private GrindEarPresenter presenter2;
    private HashMap<Integer, String> file_maps;//轮播图图片map
    private int screenwidth;
    private CheckDoubleClickListener listener;
    private int classId = -10000;

    {
        setRegister(true);
    }

    public FirstFragment() {

    }

    @Override
    protected void initData() {
        spokenList = new ArrayList<>();
        moerduoList = new ArrayList<>();
        readingList = new ArrayList<>();
        speakingList = new ArrayList<>();
//        freeList = new ArrayList<>();
        helper = new AlertHelper(getActivity());
        dialog = helper.LoadingDialog();
        Bundle bundle = getArguments();
        if (bundle != null) {
            tag = bundle.getString("tag");
        }

        file_maps = new HashMap<>();

//        recommend_adapter = new RecommendAdapter(getContext(), recommend_list);
//        recommendGrid.setAdapter(recommend_adapter);

        WindowManager managers = getActivity().getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        managers.getDefaultDisplay().getMetrics(outMetrics);
        screenwidth = outMetrics.widthPixels;
        presenter = new MainPresenterImp(getContext(), this, screenwidth);
        presenter2 = new GrindEarPresenterImp(getContext(), this);
        presenter.initViewAndData();
        presenter2.initViewAndData();
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
        imageSlide.addOnPageChangeListener(new ViewPagerEx.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
//        firstMsgLayout = view.findViewById(R.id.first_msg_layout);
        searchLayout = view.findViewById(R.id.search_layout);
//        recommendGrid = view.findViewById(R.id.recommendGrid);
        clockInLayout = view.findViewById(R.id.clock_in_layout);
        scheduleLayout = view.findViewById(R.id.schedule_layout);
        speakingText = view.findViewById(R.id.speaking_textline);
        grindText = view.findViewById(R.id.grind_textline);
        readingText = view.findViewById(R.id.reading_textline);
        eventLayout = view.findViewById(R.id.event_layout);
        matchLayout = view.findViewById(R.id.match_layout);
        grindEarLayout = view.findViewById(R.id.grind_ear_layout);
        readingLayout = view.findViewById(R.id.reading_layout);
        spokenLayout = view.findViewById(R.id.spoken_layout);
        signImage = view.findViewById(R.id.sign_image);
//        msgFlipper = view.findViewById(R.id.msg_flipper);
        error = view.findViewById(R.id.network_error_layout);
        moreMoerduo = view.findViewById(R.id.more_moerduo);
        moreReading = view.findViewById(R.id.more_reading);
        moreSpoken = view.findViewById(R.id.more_spoken);
        moerduoText1 = view.findViewById(R.id.main_moerduo_text1);
        moerduoText2 = view.findViewById(R.id.main_moerduo_text2);
        moerduoText3 = view.findViewById(R.id.main_moerduo_text3);
        readingText1 = view.findViewById(R.id.main_reading_text1);
        readingText2 = view.findViewById(R.id.main_reading_text2);
        readingText3 = view.findViewById(R.id.main_reading_text3);
        spokenText1 = view.findViewById(R.id.main_spoken_text1);
        spokenText2 = view.findViewById(R.id.main_spoken_text2);
        spokenText3 = view.findViewById(R.id.main_spoken_text3);
        moerduoImage1 = view.findViewById(R.id.main_moerduo_image1);
        moerduoImage2 = view.findViewById(R.id.main_moerduo_image2);
        moerduoImage3 = view.findViewById(R.id.main_moerduo_image3);
        readingImage1 = view.findViewById(R.id.main_reading_image1);
        readingImage2 = view.findViewById(R.id.main_reading_image2);
        readingImage3 = view.findViewById(R.id.main_reading_image3);
        spokenImage1 = view.findViewById(R.id.main_spoken_image1);
        spokenImage2 = view.findViewById(R.id.main_spoken_image2);
        spokenImage3 = view.findViewById(R.id.main_spoken_image3);
        meiriyigeImage = view.findViewById(R.id.main_meiriyige_image);
        meiriyigeCount = view.findViewById(R.id.main_meiriyige_count);
        meiriyigeText = view.findViewById(R.id.main_meiriyige_text);
        meiriyishiImage = view.findViewById(R.id.main_meiriyishi_image);
        meiriyishiText = view.findViewById(R.id.main_meiriyishi_text);
        meiriyishiCount = view.findViewById(R.id.main_meiriyishi_count);
        meiriyiduImage = view.findViewById(R.id.main_meiriyidu_image);
        meiriyiduText = view.findViewById(R.id.main_meiriyidu_text);
        meiriyiduCount = view.findViewById(R.id.main_meiriyidu_count);
        meiriyigeLayout = view.findViewById(R.id.meiriyige_layout);
        meiriyishiLayout = view.findViewById(R.id.meiriyishi_layout);
        meiriyiduLayout = view.findViewById(R.id.meiriyidu_layout);
        iWantLayout = view.findViewById(R.id.i_want_layout);
        freeImage1 = view.findViewById(R.id.main_free_image1);
        freeImage2 = view.findViewById(R.id.main_free_image2);
        freeImage3 = view.findViewById(R.id.main_free_image3);
        moerduoLinear = view.findViewById(R.id.moerduo_linear);
        readingLinear = view.findViewById(R.id.reading_linear);
        speakingLinear = view.findViewById(R.id.speaking_linear);
        listener = new CheckDoubleClickListener(this);
        moerduoImage1.setOnClickListener(listener);
        moerduoImage2.setOnClickListener(listener);
        moerduoImage3.setOnClickListener(listener);
        readingImage1.setOnClickListener(listener);
        readingImage2.setOnClickListener(listener);
        readingImage3.setOnClickListener(listener);
        spokenImage1.setOnClickListener(listener);
        spokenImage2.setOnClickListener(listener);
        spokenImage3.setOnClickListener(listener);
        freeImage1.setOnClickListener(listener);
        freeImage2.setOnClickListener(listener);
        freeImage3.setOnClickListener(listener);
        meiriyigeLayout.setOnClickListener(listener);
        meiriyishiLayout.setOnClickListener(listener);
        meiriyiduLayout.setOnClickListener(listener);
        moreMoerduo.setOnClickListener(listener);
        moreReading.setOnClickListener(listener);
        moreSpoken.setOnClickListener(listener);

        clockInLayout.setOnClickListener(listener);
        scheduleLayout.setOnClickListener(listener);
        eventLayout.setOnClickListener(listener);
        matchLayout.setOnClickListener(listener);
//        firstMsgLayout.setOnClickListener(this);
        grindEarLayout.setOnClickListener(listener);
        readingLayout.setOnClickListener(listener);
        spokenLayout.setOnClickListener(listener);
        signImage.setOnClickListener(listener);
        searchLayout.setOnClickListener(listener);
        musicBtn = (AnimationDrawable) signImage.getDrawable();
        musicBtn.setOneShot(false);
        first_refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getHomeData(tag);
                if (SystemUtils.childTag == 1) {
                    presenter2.getMusicList(-1);
                }
            }
        });
        searchLayout.setFocusable(true);
        searchLayout.setFocusableInTouchMode(true);
        searchLayout.requestFocus();

        if (MusicService.isPlay) {
            musicBtn.start();
        } else {
            musicBtn.stop();
        }
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
            moerduoList.clear();
            moerduoList.addAll(homeData.getMoerduo() != null ? homeData.getMoerduo() : new ArrayList<>());
            readingList.clear();
            readingList.addAll(homeData.getReading() != null ? homeData.getReading() : new ArrayList<>());
            speakingList.clear();
            speakingList.addAll(homeData.getSpeaking() != null ? homeData.getSpeaking() : new ArrayList<>());
//            freeList.clear();
//            freeList.addAll(homeData.getFreelist());
            meiriyige = homeData.getMeiriyige();
            meiriyishi = homeData.getMeiriyishi();
            meiriyidu = homeData.getMeiriyidu();
            initial();
            if (SystemUtils.childTag == 0) {
                iWantLayout.setVisibility(View.GONE);
            } else {
                iWantLayout.setVisibility(View.VISIBLE);
            }
//            msgFlipper.removeAllViews();
//            for (int i = 0; i < msgText.size(); i++) {
//                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//                msgFlipper.addView(msgText.get(i), params);
//            }

//            recommend_list.clear();
//            recommend_list.addAll(homeData.getRecommendList());
//            recommend_adapter.notifyDataSetChanged();
        } else if (message.what == MethodCode.EVENT_GETSPOKENCLASSES) {
            spokenList.clear();
            spokenList.addAll((List<SongClassify>) message.obj);
        } else if (message.what == MethodCode.EVENT_ERROR) {
            if (first_refresh_layout.isRefreshing()) {
                first_refresh_layout.setRefreshing(false);
            }
        } else if (message.what == MethodCode.EVENT_UNLOCKBOOK + 9000 + classId) {
            showInfo((String) message.obj);
            presenter.getHomeData(tag);
            if (SystemUtils.childTag == 1) {
                presenter2.getMusicList(-1);
            }
        } else if (message.what == MethodCode.EVENT_MUSIC) {
            if (musicBtn != null) {
                if ((boolean) (message.obj)) {
                    musicBtn.start();
                } else {
                    musicBtn.stop();
                }
            }
        }
    }

    private void initial() {
        if (moerduoList.size() == 3) {
            grindText.setVisibility(View.VISIBLE);
            moerduoLinear.setVisibility(View.VISIBLE);
            Glide.with(this).load(moerduoList.get(0).getBookImageUrl()).placeholder(R.drawable.image_loading).error(R.drawable.image_loading).into(moerduoImage1);
            Glide.with(this).load(moerduoList.get(1).getBookImageUrl()).placeholder(R.drawable.image_loading).error(R.drawable.image_loading).into(moerduoImage2);
            Glide.with(this).load(moerduoList.get(2).getBookImageUrl()).placeholder(R.drawable.image_loading).error(R.drawable.image_loading).into(moerduoImage3);
            moerduoText1.setText(moerduoList.get(0).getBookName());
            moerduoText2.setText(moerduoList.get(1).getBookName());
            moerduoText3.setText(moerduoList.get(2).getBookName());

        } else {
            grindText.setVisibility(View.GONE);
            moerduoLinear.setVisibility(View.GONE);
        }
        if (readingList.size() == 3) {
            readingText.setVisibility(View.VISIBLE);
            readingLinear.setVisibility(View.VISIBLE);
            Glide.with(this).load(readingList.get(0).getBookImageUrl()).placeholder(R.drawable.image_loading).error(R.drawable.image_loading).into(readingImage1);
            Glide.with(this).load(readingList.get(1).getBookImageUrl()).placeholder(R.drawable.image_loading).error(R.drawable.image_loading).into(readingImage2);
            Glide.with(this).load(readingList.get(2).getBookImageUrl()).placeholder(R.drawable.image_loading).error(R.drawable.image_loading).into(readingImage3);
            readingText1.setText(readingList.get(0).getBookName());
            readingText2.setText(readingList.get(1).getBookName());
            readingText3.setText(readingList.get(2).getBookName());

        } else {
            readingText.setVisibility(View.GONE);
            readingLinear.setVisibility(View.GONE);
        }
        if (speakingList.size() == 3) {
            speakingLinear.setVisibility(View.VISIBLE);
            speakingText.setVisibility(View.VISIBLE);
            Glide.with(this).load(speakingList.get(0).getBookImageUrl()).placeholder(R.drawable.image_loading).error(R.drawable.image_loading).into(spokenImage1);
            Glide.with(this).load(speakingList.get(1).getBookImageUrl()).placeholder(R.drawable.image_loading).error(R.drawable.image_loading).into(spokenImage2);
            Glide.with(this).load(speakingList.get(2).getBookImageUrl()).placeholder(R.drawable.image_loading).error(R.drawable.image_loading).into(spokenImage3);
            spokenText1.setText(speakingList.get(0).getBookName());
            spokenText2.setText(speakingList.get(1).getBookName());
            spokenText3.setText(speakingList.get(2).getBookName());
//            if (speakingList.get(0).getJurisdiction() == 0) {
//                speakingLock1.setVisibility(View.VISIBLE);
//                if (speakingList.get(0).getIsusenectar() == 0) {
//                    speakingLock1.setImageResource(R.drawable.icon_lock_book_f);
//                } else {
//                    speakingLock1.setImageResource(R.drawable.icon_lock_book_t);
//                }
//            } else {
//                speakingLock1.setVisibility(View.GONE);
//            }
//            if (speakingList.get(1).getJurisdiction() == 0) {
//                speakingLock2.setVisibility(View.VISIBLE);
//                if (speakingList.get(1).getIsusenectar() == 0) {
//                    speakingLock2.setImageResource(R.drawable.icon_lock_book_f);
//                } else {
//                    speakingLock2.setImageResource(R.drawable.icon_lock_book_t);
//                }
//            } else {
//                speakingLock2.setVisibility(View.GONE);
//            }
//            if (speakingList.get(2).getJurisdiction() == 0) {
//                speakingLock3.setVisibility(View.VISIBLE);
//                if (speakingList.get(2).getIsusenectar() == 0) {
//                    speakingLock3.setImageResource(R.drawable.icon_lock_book_f);
//                } else {
//                    speakingLock3.setImageResource(R.drawable.icon_lock_book_t);
//                }
//            } else {
//                speakingLock3.setVisibility(View.GONE);
//            }
        } else {
            speakingLinear.setVisibility(View.GONE);
            speakingText.setVisibility(View.GONE);
        }
        Glide.with(this).load(meiriyige.getBookImageUrl()).placeholder(R.drawable.image_loading).error(R.drawable.image_loading).into(meiriyigeImage);
        Glide.with(this).load(meiriyishi.getBookImageUrl()).placeholder(R.drawable.image_loading).error(R.drawable.image_loading).into(meiriyishiImage);
        Glide.with(this).load(meiriyidu.getBookImageUrl()).placeholder(R.drawable.image_loading).error(R.drawable.image_loading).into(meiriyiduImage);
        meiriyigeText.setText(meiriyige.getBookName());
        meiriyigeCount.setText(meiriyige.getCount() + "次阅读");
        meiriyishiText.setText(meiriyishi.getBookName());
        meiriyishiCount.setText(meiriyishi.getCount() + "次阅读");
        meiriyiduText.setText(meiriyidu.getBookName());
        meiriyiduCount.setText(meiriyidu.getCount() + "次阅读");
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

    @Override
    public void onCheckDoubleClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.clock_in_layout:
                //存折
                if (tag.equals("游客")) {
                    SystemUtils.toLogin(getContext());
                    return;
                }
                if (SystemUtils.childTag == 0) {
                    SystemUtils.toAddChild(getContext());
                    return;
                }
//                intent = new Intent(getContext(), WebActivity.class);
//                intent.putExtra("url", SystemUtils.mainUrl + "Signin/today?username=" + SystemUtils.defaultUsername);
//                intent.putExtra("title", "今日统计");
                intent.setClass(getContext(), BankBookActivity.class);
                startActivity(intent);
                break;
            case R.id.schedule_layout:
                //我的课表
                if (tag.equals("游客")) {
                    SystemUtils.toLogin(getContext());
                    return;
                }
                if (SystemUtils.childTag == 0) {
                    SystemUtils.toAddChild(getContext());
                    return;
                }
                intent.setClass(getContext(), ScheduleActivity2.class);
                startActivity(intent);
                break;
            case R.id.event_layout:
                //网课
                if (tag.equals("游客")) {
                    SystemUtils.toLogin(getContext());
                    return;
                }
                if (SystemUtils.childTag == 0) {
                    SystemUtils.toAddChild(getContext());
                    return;
                }
                intent.setClass(getContext(), NetWorkActivity.class);
                startActivity(intent);

//                Intent intent3 = new Intent(getContext(), WebActivity.class);
//                intent3.putExtra("url", "file:////android_asset/test/index_faq.html");
//                intent3.putExtra("title", "测试");
//                startActivity(intent3);

                break;
            case R.id.match_layout:
                //广场
                if (tag.equals("游客")) {
                    SystemUtils.toLogin(getContext());
                    return;
                }
                if (SystemUtils.childTag == 0) {
                    SystemUtils.toAddChild(getContext());
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
                if (tag.equals("游客")) {
                    SystemUtils.toLogin(getContext());
                    return;
                }
                if (SystemUtils.childTag == 0) {
                    SystemUtils.toAddChild(getContext());
                    return;
                }
                intent.setClass(getContext(), GrindEarActivity.class);
                startActivity(intent);
                break;
            case R.id.reading_layout:
                //阅读
//                if (SystemUtils.tag.equals("游客")) {
//                    SystemUtils.toLogin(getContext());
//                    return;
//                }
                if (tag.equals("游客")) {
                    SystemUtils.toLogin(getContext());
                    return;
                }
                if (SystemUtils.childTag == 0) {
                    SystemUtils.toAddChild(getContext());
                    return;
                }
                intent.setClass(getContext(), ReadingActivity.class);
                startActivity(intent);
                break;
            case R.id.spoken_layout:
                //口语
//                if (SystemUtils.tag.equals("游客")) {
//                    SystemUtils.toLogin(getContext());
//                    return;
//                }
//                if (SystemUtils.childTag == 0) {
//                    SystemUtils.toAddChild(getContext());
//                    return;
//                }
//                if (spokenList.size() != 0) {
//                    intent.setClass(getContext(), ListenSongActivity.class);
//                    Bundle bundle5 = new Bundle();
//                    bundle5.putInt("type", 11);
//                    bundle5.putSerializable("ClassifyList", (Serializable) spokenList);
//                    intent.putExtras(bundle5);
//                    startActivity(intent);
//                } else {
//                    showInfo("请稍后");
//                }
                if (tag.equals("游客")) {
                    SystemUtils.toLogin(getContext());
                    return;
                }
                if (SystemUtils.childTag == 0) {
                    SystemUtils.toAddChild(getContext());
                    return;
                }
                intent.setClass(getContext(), SpeakingActivity.class);
                startActivity(intent);
                break;
            case R.id.search_layout:
                //搜索
                if (SystemUtils.tag.equals("游客")) {
                    SystemUtils.toLogin(getContext());
                    return;
                }
                if (SystemUtils.childTag == 0) {
                    SystemUtils.toAddChild(getContext());
                    return;
                }
                Intent intent2 = new Intent(getContext(), GlobalSearchActivity.class);
                startActivity(intent2);
                break;
            case R.id.sign_image:
                //右上角签到
                //TODO:
//                SystemUtils.setBackGray(getActivity(), true);
//                SystemUtils.getNectarCongratulation(getActivity(), 1).showAtLocation(SystemUtils.popupView, Gravity.CENTER, 0, 0);
                if (tag.equals("游客")) {
                    SystemUtils.toLogin(getContext());
                    return;
                }
                if (SystemUtils.childTag == 0) {
                    SystemUtils.toAddChild(getContext());
                    return;
                }
                Intent intent1 = new Intent(getContext(), MusicPlayActivity.class);
                startActivity(intent1);

//                Intent intent1 = new Intent(getContext(), XiangXueActivity.class);
//                startActivity(intent1);

//                Uri uri = Uri.parse("http://m.anniekids.org/1.html");

//                Intent intent1 = new Intent(Intent.ACTION_VIEW, uri);
//                startActivity(intent1);

//                Intent intent1 = new Intent(getContext(), WebActivity.class);
//                intent1.putExtra("url", "http://m.anniekids.org/1.html");
//                intent1.putExtra("title", "我的课程推荐");
//                startActivity(intent1);

//                Intent intent1 = new Intent(getContext(), CalendarActivity.class);
//                startActivity(intent1);


//                if (SystemUtils.tag.equals("游客")) {
//                    SystemUtils.toLogin(getContext());
//                    return;
//                }
//                if (SystemUtils.childTag == 0) {
//                    SystemUtils.toAddChild(getContext());
//                    return;
//                }
//                Intent intent1 = new Intent(getContext(), WebActivity.class);
//                intent1.putExtra("url", SystemUtils.mainUrl + "Signin/index?username=" + SystemUtils.defaultUsername);
//                intent1.putExtra("title", "签到");
//                intent1.putExtra("share", 1);
//                startActivity(intent1);
                break;
            case R.id.more_moerduo:
                //我要磨耳朵更多
                intent.setClass(getContext(), GrindEarActivity.class);
                startActivity(intent);
                break;
            case R.id.more_reading:
                //我要阅读更多
                intent.setClass(getContext(), ReadingActivity.class);
                startActivity(intent);
                break;
            case R.id.more_spoken:
                //我要练口语更多
                intent.setClass(getContext(), SpeakingActivity.class);
                startActivity(intent);
//                if (SystemUtils.tag.equals("游客")) {
//                    SystemUtils.toLogin(getContext());
//                    return;
//                }
//                if (SystemUtils.childTag == 0) {
//                    SystemUtils.toAddChild(getContext());
//                    return;
//                }
//                if (spokenList.size() != 0) {
//                    intent.setClass(getContext(), ListenSongActivity.class);
//                    Bundle bundle5 = new Bundle();
//                    bundle5.putInt("type", 11);
//                    bundle5.putSerializable("ClassifyList", (Serializable) spokenList);
//                    intent.putExtras(bundle5);
//                    startActivity(intent);
//                } else {
//                    showInfo("请稍后");
//                }
                break;
//            case R.id.main_free_image1:
//                intent = new Intent(getContext(), PracticeActivity.class);
//                intent.putExtra("song", freeList.get(0));
//                intent.putExtra("type", 0);
//                intent.putExtra("audioType", 0);
//                intent.putExtra("audioSource", 0);
//                startActivity(intent);
//                break;
//            case R.id.main_free_image2:
//                intent = new Intent(getContext(), PracticeActivity.class);
//                intent.putExtra("song", freeList.get(1));
//                intent.putExtra("type", 0);
//                intent.putExtra("audioType", 0);
//                intent.putExtra("audioSource", 0);
//                startActivity(intent);
//                break;
//            case R.id.main_free_image3:
//                intent = new Intent(getContext(), PracticeActivity.class);
//                intent.putExtra("song", freeList.get(2));
//                intent.putExtra("type", 0);
//                intent.putExtra("audioType", 0);
//                intent.putExtra("audioSource", 0);
//                startActivity(intent);
//                break;
            case R.id.main_moerduo_image1:
                if (moerduoList.get(0).getJurisdiction() == 0) {
                    if (moerduoList.get(0).getIsusenectar() == 1) {
                        SystemUtils.setBackGray(getActivity(), true);
                        SystemUtils.getSuggestPopup(getContext(), "需要" + moerduoList.get(0).getNectar() + "花蜜才能解锁哦", "解锁", "取消", presenter2, -1, moerduoList.get(0).getNectar(), moerduoList.get(0).getBookName(), moerduoList.get(0).getBookId(), classId).showAtLocation(SystemUtils.popupView, Gravity.CENTER, 0, 0);
                    }
                } else {
                    intent = new Intent(getContext(), PracticeActivity.class);
                    intent.putExtra("song", moerduoList.get(0));
                    intent.putExtra("type", 0);
                    intent.putExtra("audioType", 0);
                    intent.putExtra("audioSource", 0);
                    intent.putExtra("bookType", 0);
                    intent.putExtra("collectType", 1);
                    startActivity(intent);
                }
                break;
            case R.id.main_moerduo_image2:
                if (moerduoList.get(1).getJurisdiction() == 0) {
                    if (moerduoList.get(1).getIsusenectar() == 1) {
                        SystemUtils.setBackGray(getActivity(), true);
                        SystemUtils.getSuggestPopup(getContext(), "需要" + moerduoList.get(1).getNectar() + "花蜜才能解锁哦", "解锁", "取消", presenter2, -1, moerduoList.get(1).getNectar(), moerduoList.get(1).getBookName(), moerduoList.get(1).getBookId(), classId).showAtLocation(SystemUtils.popupView, Gravity.CENTER, 0, 0);
                    }
                } else {
                    intent = new Intent(getContext(), PracticeActivity.class);
                    intent.putExtra("song", moerduoList.get(1));
                    intent.putExtra("type", 0);
                    intent.putExtra("audioType", 0);
                    intent.putExtra("audioSource", 0);
                    intent.putExtra("bookType", 0);
                    intent.putExtra("collectType", 1);
                    startActivity(intent);
                }
                break;
            case R.id.main_moerduo_image3:
                if (moerduoList.get(2).getJurisdiction() == 0) {
                    if (moerduoList.get(2).getIsusenectar() == 1) {
                        SystemUtils.setBackGray(getActivity(), true);
                        SystemUtils.getSuggestPopup(getContext(), "需要" + moerduoList.get(2).getNectar() + "花蜜才能解锁哦", "解锁", "取消", presenter2, -1, moerduoList.get(2).getNectar(), moerduoList.get(2).getBookName(), moerduoList.get(2).getBookId(), classId).showAtLocation(SystemUtils.popupView, Gravity.CENTER, 0, 0);
                    }
                } else {
                    intent = new Intent(getContext(), PracticeActivity.class);
                    intent.putExtra("song", moerduoList.get(2));
                    intent.putExtra("type", 0);
                    intent.putExtra("audioType", 0);
                    intent.putExtra("audioSource", 0);
                    intent.putExtra("bookType", 0);
                    intent.putExtra("collectType", 1);
                    startActivity(intent);
                }
                break;
            case R.id.main_reading_image1:
                if (readingList.get(0).getJurisdiction() == 0) {
                    if (readingList.get(0).getIsusenectar() == 1) {
                        SystemUtils.setBackGray(getActivity(), true);
                        SystemUtils.getSuggestPopup(getContext(), "需要" + readingList.get(0).getNectar() + "花蜜才能解锁哦", "解锁", "取消", presenter2, -1, readingList.get(0).getNectar(), readingList.get(0).getBookName(), readingList.get(0).getBookId(), classId).showAtLocation(SystemUtils.popupView, Gravity.CENTER, 0, 0);
                    }
                } else {
                    intent = new Intent(getContext(), PracticeActivity.class);
                    intent.putExtra("song", readingList.get(0));
                    intent.putExtra("type", 0);
                    intent.putExtra("audioType", 1);
                    intent.putExtra("audioSource", 0);
                    intent.putExtra("bookType", 1);
                    startActivity(intent);
                }
                break;
            case R.id.main_reading_image2:
                if (readingList.get(1).getJurisdiction() == 0) {
                    if (readingList.get(1).getIsusenectar() == 1) {
                        SystemUtils.setBackGray(getActivity(), true);
                        SystemUtils.getSuggestPopup(getContext(), "需要" + readingList.get(1).getNectar() + "花蜜才能解锁哦", "解锁", "取消", presenter2, -1, readingList.get(1).getNectar(), readingList.get(1).getBookName(), readingList.get(1).getBookId(), classId).showAtLocation(SystemUtils.popupView, Gravity.CENTER, 0, 0);
                    }
                } else {
                    intent = new Intent(getContext(), PracticeActivity.class);
                    intent.putExtra("song", readingList.get(1));
                    intent.putExtra("type", 0);
                    intent.putExtra("audioType", 1);
                    intent.putExtra("audioSource", 0);
                    intent.putExtra("bookType", 1);
                    startActivity(intent);
                }
                break;
            case R.id.main_reading_image3:
                if (readingList.get(2).getJurisdiction() == 0) {
                    if (readingList.get(2).getIsusenectar() == 1) {
                        SystemUtils.setBackGray(getActivity(), true);
                        SystemUtils.getSuggestPopup(getContext(), "需要" + readingList.get(2).getNectar() + "花蜜才能解锁哦", "解锁", "取消", presenter2, -1, readingList.get(2).getNectar(), readingList.get(2).getBookName(), readingList.get(2).getBookId(), classId).showAtLocation(SystemUtils.popupView, Gravity.CENTER, 0, 0);
                    }
                } else {
                    intent = new Intent(getContext(), PracticeActivity.class);
                    intent.putExtra("song", readingList.get(2));
                    intent.putExtra("type", 0);
                    intent.putExtra("audioType", 1);
                    intent.putExtra("audioSource", 0);
                    intent.putExtra("bookType", 1);
                    startActivity(intent);
                }
                break;
            case R.id.main_spoken_image1:
                if (speakingList.get(0).getJurisdiction() == 0) {
                    if (speakingList.get(0).getIsusenectar() == 1) {
                        SystemUtils.setBackGray(getActivity(), true);
                        SystemUtils.getSuggestPopup(getContext(), "需要" + speakingList.get(0).getNectar() + "花蜜才能解锁哦", "解锁", "取消", presenter2, -1, speakingList.get(0).getNectar(), speakingList.get(0).getBookName(), speakingList.get(0).getBookId(), classId).showAtLocation(SystemUtils.popupView, Gravity.CENTER, 0, 0);
                    }
                } else {
                    intent = new Intent(getContext(), PracticeActivity.class);
                    intent.putExtra("song", speakingList.get(0));
                    intent.putExtra("type", 0);
                    intent.putExtra("audioType", 2);
                    intent.putExtra("audioSource", 0);
                    intent.putExtra("bookType", 1);
                    startActivity(intent);
                }
                break;
            case R.id.main_spoken_image2:
                if (speakingList.get(1).getJurisdiction() == 0) {
                    if (speakingList.get(1).getIsusenectar() == 1) {
                        SystemUtils.setBackGray(getActivity(), true);
                        SystemUtils.getSuggestPopup(getContext(), "需要" + speakingList.get(1).getNectar() + "花蜜才能解锁哦", "解锁", "取消", presenter2, -1, speakingList.get(1).getNectar(), speakingList.get(1).getBookName(), speakingList.get(1).getBookId(), classId).showAtLocation(SystemUtils.popupView, Gravity.CENTER, 0, 0);
                    }
                } else {
                    intent = new Intent(getContext(), PracticeActivity.class);
                    intent.putExtra("song", speakingList.get(1));
                    intent.putExtra("type", 0);
                    intent.putExtra("audioType", 2);
                    intent.putExtra("audioSource", 0);
                    intent.putExtra("bookType", 1);
                    startActivity(intent);
                }
                break;
            case R.id.main_spoken_image3:
                if (speakingList.get(2).getJurisdiction() == 0) {
                    if (speakingList.get(2).getIsusenectar() == 1) {
                        SystemUtils.setBackGray(getActivity(), true);
                        SystemUtils.getSuggestPopup(getContext(), "需要" + speakingList.get(2).getNectar() + "花蜜才能解锁哦", "解锁", "取消", presenter2, -1, speakingList.get(2).getNectar(), speakingList.get(2).getBookName(), speakingList.get(2).getBookId(), classId).showAtLocation(SystemUtils.popupView, Gravity.CENTER, 0, 0);
                    }
                } else {
                    intent = new Intent(getContext(), PracticeActivity.class);
                    intent.putExtra("song", speakingList.get(2));
                    intent.putExtra("type", 0);
                    intent.putExtra("audioType", 2);
                    intent.putExtra("audioSource", 0);
                    intent.putExtra("bookType", 1);
                    startActivity(intent);
                }
                break;
            case R.id.meiriyige_layout:
                if (SystemUtils.tag.equals("游客")) {
                    SystemUtils.toLogin(getContext());
                    return;
                }
                if (SystemUtils.childTag == 0) {
                    SystemUtils.toAddChild(getContext());
                    return;
                }
                if (meiriyige.getJurisdiction() == 0) {
                    if (meiriyige.getIsusenectar() == 1) {
                        SystemUtils.setBackGray(getActivity(), true);
                        SystemUtils.getSuggestPopup(getContext(), "需要" + meiriyige.getNectar() + "花蜜才能解锁哦", "解锁", "取消", presenter2, -1, meiriyige.getNectar(), meiriyige.getBookName(), meiriyige.getBookId(), classId).showAtLocation(SystemUtils.popupView, Gravity.CENTER, 0, 0);
                    }
                } else {
                    intent = new Intent(getContext(), PracticeActivity.class);
                    intent.putExtra("song", meiriyige);
                    intent.putExtra("type", 1);
                    intent.putExtra("audioType", 0);
                    intent.putExtra("audioSource", 1);
                    intent.putExtra("bookType", 0);
                    intent.putExtra("collectType", 1);
                    startActivity(intent);
                }
                break;
            case R.id.meiriyishi_layout:
                if (SystemUtils.tag.equals("游客")) {
                    SystemUtils.toLogin(getContext());
                    return;
                }
                if (SystemUtils.childTag == 0) {
                    SystemUtils.toAddChild(getContext());
                    return;
                }
                if (meiriyishi.getJurisdiction() == 0) {
                    if (meiriyishi.getIsusenectar() == 1) {
                        SystemUtils.setBackGray(getActivity(), true);
                        SystemUtils.getSuggestPopup(getContext(), "需要" + meiriyishi.getNectar() + "花蜜才能解锁哦", "解锁", "取消", presenter2, -1, meiriyishi.getNectar(), meiriyishi.getBookName(), meiriyishi.getBookId(), classId).showAtLocation(SystemUtils.popupView, Gravity.CENTER, 0, 0);
                    }
                } else {
                    intent = new Intent(getContext(), PracticeActivity.class);
                    intent.putExtra("song", meiriyishi);
                    intent.putExtra("type", 1);
                    intent.putExtra("audioType", 1);
                    intent.putExtra("audioSource", 1);
                    intent.putExtra("bookType", 1);
                    startActivity(intent);
                }
                break;
            case R.id.meiriyidu_layout:
                if (SystemUtils.tag.equals("游客")) {
                    SystemUtils.toLogin(getContext());
                    return;
                }
                if (SystemUtils.childTag == 0) {
                    SystemUtils.toAddChild(getContext());
                    return;
                }
                if (meiriyidu.getJurisdiction() == 0) {
                    if (meiriyidu.getIsusenectar() == 1) {
                        SystemUtils.setBackGray(getActivity(), true);
                        SystemUtils.getSuggestPopup(getContext(), "需要" + meiriyidu.getNectar() + "花蜜才能解锁哦", "解锁", "取消", presenter2, -1, meiriyidu.getNectar(), meiriyidu.getBookName(), meiriyidu.getBookId(), classId).showAtLocation(SystemUtils.popupView, Gravity.CENTER, 0, 0);
                    }
                } else {
                    intent = new Intent(getContext(), PracticeActivity.class);
                    intent.putExtra("song", meiriyidu);
                    intent.putExtra("type", 1);
                    intent.putExtra("audioType", 2);
                    intent.putExtra("audioSource", 1);
                    intent.putExtra("bookType", 1);
                    startActivity(intent);
                }
                break;
        }
    }

    @Override
    public void onSliderClick(BaseSliderView baseSliderView) {
    }

    @Override
    public void onResume(){
        super.onResume();
        SharedPreferences preferences = getContext().getSharedPreferences("userInfo", MODE_PRIVATE |MODE_MULTI_PROCESS);
        if (preferences.getString("token", null) != null && preferences.getString("defaultUsername", null) != null) {
            SystemUtils.childTag=preferences.getInt("childTag", 0);
            SystemUtils.token=preferences.getString("token", null);
            SystemUtils.defaultUsername=preferences.getString("defaultUsername", null);
        }
        Log.e("------",SystemUtils.childTag+"///"+SystemUtils.token+"///"+SystemUtils.defaultUsername);
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }
}
