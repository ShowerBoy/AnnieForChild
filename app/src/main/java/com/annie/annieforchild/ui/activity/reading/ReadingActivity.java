package com.annie.annieforchild.ui.activity.reading;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.CheckDoubleClickListener;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.OnCheckDoubleClick;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.ReadingData;
import com.annie.annieforchild.bean.song.Song;
import com.annie.annieforchild.bean.song.SongClassify;
import com.annie.annieforchild.presenter.GrindEarPresenter;
import com.annie.annieforchild.presenter.imp.GrindEarPresenterImp;
import com.annie.annieforchild.ui.activity.GlobalSearchActivity;
import com.annie.annieforchild.ui.activity.grindEar.ListenSongActivity;
import com.annie.annieforchild.ui.activity.pk.MusicPlayActivity2;
import com.annie.annieforchild.ui.activity.pk.PracticeActivity;
import com.annie.annieforchild.ui.adapter.GrindEarAdapter;
import com.annie.annieforchild.ui.interfaces.OnRecyclerItemClickListener;
import com.annie.annieforchild.view.GrindEarView;
import com.annie.baselibrary.base.BaseMusicActivity;
import com.annie.baselibrary.base.BasePresenter;
import com.daimajia.slider.library.SliderLayout;

import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.jiguang.analytics.android.api.CountEvent;
import cn.jiguang.analytics.android.api.JAnalyticsInterface;

/**
 * 阅读
 * Created by WangLei on 2018/1/19 0019
 */

public class ReadingActivity extends BaseMusicActivity implements OnCheckDoubleClick, GrindEarView {
    private RecyclerView recycler;
    private AnimationDrawable musicBtn;
    private ImageView readingBack, myLevel, letsReading, music, search;
    private SliderLayout readingSlide;
    private LinearLayout huibenLayout, xugouLayout, feixugouLayout, zhangjieLayout;
    private HashMap<Integer, String> file_maps;//轮播图图片map
    private GrindEarPresenter presenter;
    private List<SongClassify> huibenClassifyList;
    private List<SongClassify> xugouClassifyList;
    private List<SongClassify> feixugouClassifyList;
    private List<SongClassify> zhangjieClassifyList;
    private List<SongClassify> readClassifyList;
    private List<Song> meiriyiduList;
    private AlertHelper helper;
    private Dialog dialog;
    private List<Song> lists;
    private CheckDoubleClickListener listener;
    private GrindEarAdapter adapter;
    private int classId = -13000;

    {
        setRegister(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_reading;
    }

    @Override
    protected void initView() {
        readingBack = findViewById(R.id.reading_back);
        huibenLayout = findViewById(R.id.huiben_layout);
        xugouLayout = findViewById(R.id.xugou_layout);
        feixugouLayout = findViewById(R.id.feixugou_layout);
        zhangjieLayout = findViewById(R.id.zhangjie_layout);
        readingSlide = findViewById(R.id.reading_slide);
        myLevel = findViewById(R.id.my_level);
        letsReading = findViewById(R.id.lets_reading);
        music = findViewById(R.id.reading_music);
        search = findViewById(R.id.reading_search);
        recycler = findViewById(R.id.reading_recommend_recycler);
        listener = new CheckDoubleClickListener(this);
        readingBack.setOnClickListener(listener);
        huibenLayout.setOnClickListener(listener);
        xugouLayout.setOnClickListener(listener);
        feixugouLayout.setOnClickListener(listener);
        zhangjieLayout.setOnClickListener(listener);
        myLevel.setOnClickListener(listener);
        letsReading.setOnClickListener(listener);
        music.setOnClickListener(listener);
        search.setOnClickListener(listener);

        musicBtn = (AnimationDrawable) music.getDrawable();
        musicBtn.setOneShot(false);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(layoutManager);
        recycler.setNestedScrollingEnabled(false);
    }

    @Override
    protected void initData() {
        lists = new ArrayList<>();
        meiriyiduList = new ArrayList<>();
        huibenClassifyList = new ArrayList<>();
        xugouClassifyList = new ArrayList<>();
        feixugouClassifyList = new ArrayList<>();
        zhangjieClassifyList = new ArrayList<>();
        readClassifyList = new ArrayList<>();
        file_maps = new HashMap<>();
        helper = new AlertHelper(this);
        dialog = helper.LoadingDialog();
        adapter = new GrindEarAdapter(this, lists, new OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view) {
                CountEvent Event_706 = new CountEvent(MethodCode.A0706);
                JAnalyticsInterface.onEvent(ReadingActivity.this, Event_706);
                int position = recycler.getChildAdapterPosition(view);
                if (lists.get(position).getJurisdiction() == 0) {
                    if (lists.get(position).getIsusenectar() == 1) {
                        SystemUtils.setBackGray(ReadingActivity.this, true);
                        SystemUtils.getSuggestPopup(ReadingActivity.this, "需要" + lists.get(position).getNectar() + "花蜜才能解锁哦", "解锁", "取消", presenter, -1, lists.get(position).getNectar(), lists.get(position).getBookName(), lists.get(position).getBookId(), classId).showAtLocation(SystemUtils.popupView, Gravity.CENTER, 0, 0);
                    }
                } else {
                    Intent intent = new Intent(ReadingActivity.this, PracticeActivity.class);
                    intent.putExtra("song", lists.get(position));
                    intent.putExtra("type", 0);
                    intent.putExtra("audioType", 1);
                    intent.putExtra("audioSource", 0);
                    intent.putExtra("bookType", 1);
                    startActivity(intent);
                }
            }

            @Override
            public void onItemLongClick(View view) {

            }
        });
        recycler.setAdapter(adapter);
        presenter = new GrindEarPresenterImp(this, this);
        presenter.initViewAndData();
        presenter.getReading();
        presenter.getReadingClasses();
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    /**
     * {@link GrindEarPresenterImp#Success(int, Object)}
     *
     * @param message
     */
    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_GETMUSICCLASSES6) {
            huibenClassifyList.clear();
            huibenClassifyList.addAll((List<SongClassify>) message.obj);
        } else if (message.what == MethodCode.EVENT_GETMUSICCLASSES7) {
            xugouClassifyList.clear();
            xugouClassifyList.addAll((List<SongClassify>) message.obj);
        } else if (message.what == MethodCode.EVENT_GETMUSICCLASSES8) {
            feixugouClassifyList.clear();
            feixugouClassifyList.addAll((List<SongClassify>) message.obj);
        } else if (message.what == MethodCode.EVENT_GETMUSICCLASSES9) {
            zhangjieClassifyList.clear();
            zhangjieClassifyList.addAll((List<SongClassify>) message.obj);
        } else if (message.what == MethodCode.EVENT_GETMUSICCLASSES10) {
            readClassifyList.clear();
            readClassifyList.addAll((List<SongClassify>) message.obj);
        } else if (message.what == MethodCode.EVENT_GETREADING) {
            ReadingData readingData = (ReadingData) message.obj;
            lists.clear();
            lists.addAll(readingData.getRecommendlist());
            adapter.notifyDataSetChanged();
        } else if (message.what == MethodCode.EVENT_UNLOCKBOOK + 9000 + classId) {
            showInfo((String) message.obj);
            presenter.getReading();
            presenter.getReadingClasses();
        } else if (message.what == MethodCode.EVENT_MUSIC) {
            if (musicBtn != null) {
                if ((boolean) (message.obj)) {
                    musicBtn.start();
                } else {
                    musicBtn.stop();
                }
            }
        } else if (message.what == MethodCode.EVENT_LOADING) {
            dismissLoad();
        }
    }

    @Override
    public void showInfo(String info) {
        Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
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
    public SliderLayout getImageSlide() {
        return readingSlide;
    }

    @Override
    public HashMap<Integer, String> getFile_maps() {
        return file_maps;
    }


    @Override
    public void onCheckDoubleClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.reading_back:
                finish();
                break;
            case R.id.huiben_layout:
                //绘本
                CountEvent Event_702 = new CountEvent(MethodCode.A0702);
                JAnalyticsInterface.onEvent(this, Event_702);
                if (application.getSystemUtils().getTag().equals("游客")) {
                    SystemUtils.toLogin(this);
                    return;
                }
                if (application.getSystemUtils().getChildTag() == 0) {
                    SystemUtils.toAddChild(this);
                    return;
                }
                if (huibenClassifyList == null || huibenClassifyList.size() == 0) {
                    showInfo("请稍后");
                    return;
                }
                showLoad();
                intent.setClass(this, ListenSongActivity.class);
                Bundle bundle1 = new Bundle();
                bundle1.putInt("type", 6);
                bundle1.putSerializable("ClassifyList", (Serializable) huibenClassifyList);
                intent.putExtras(bundle1);
                startActivity(intent);
                break;
            case R.id.xugou_layout:
                //分级读物
                CountEvent Event_703 = new CountEvent(MethodCode.A0703);
                JAnalyticsInterface.onEvent(this, Event_703);
                if (application.getSystemUtils().getTag().equals("游客")) {
                    SystemUtils.toLogin(this);
                    return;
                }
                if (application.getSystemUtils().getChildTag() == 0) {
                    SystemUtils.toAddChild(this);
                    return;
                }
                if (xugouClassifyList == null || xugouClassifyList.size() == 0) {
                    showInfo("请稍后");
                    return;
                }
                showLoad();
                intent.setClass(this, ListenSongActivity.class);
                Bundle bundle2 = new Bundle();
                bundle2.putInt("type", 7);
                bundle2.putSerializable("ClassifyList", (Serializable) xugouClassifyList);
                intent.putExtras(bundle2);
                startActivity(intent);
                break;
            case R.id.feixugou_layout:
                //桥梁书
                CountEvent Event_704 = new CountEvent(MethodCode.A0704);
                JAnalyticsInterface.onEvent(this, Event_704);
                if (application.getSystemUtils().getTag().equals("游客")) {
                    SystemUtils.toLogin(this);
                    return;
                }
                if (application.getSystemUtils().getChildTag() == 0) {
                    SystemUtils.toAddChild(this);
                    return;
                }
                if (feixugouClassifyList == null || feixugouClassifyList.size() == 0) {
                    showInfo("请稍后");
                    return;
                }
                showLoad();
                intent.setClass(this, ListenSongActivity.class);
                Bundle bundle3 = new Bundle();
                bundle3.putInt("type", 8);
                bundle3.putSerializable("ClassifyList", (Serializable) feixugouClassifyList);
                intent.putExtras(bundle3);
                startActivity(intent);
                break;
            case R.id.zhangjie_layout:
                //章节书
                CountEvent Event_705 = new CountEvent(MethodCode.A0705);
                JAnalyticsInterface.onEvent(this, Event_705);
                if (application.getSystemUtils().getTag().equals("游客")) {
                    SystemUtils.toLogin(this);
                    return;
                }
                if (application.getSystemUtils().getChildTag() == 0) {
                    SystemUtils.toAddChild(this);
                    return;
                }
                if (zhangjieClassifyList == null || zhangjieClassifyList.size() == 0) {
                    showInfo("请稍后");
                    return;
                }
                showLoad();
                intent.setClass(this, ListenSongActivity.class);
                Bundle bundle4 = new Bundle();
                bundle4.putInt("type", 9);
                bundle4.putSerializable("ClassifyList", (Serializable) zhangjieClassifyList);
                intent.putExtras(bundle4);
                startActivity(intent);
                break;
            case R.id.my_level:
                //阅读存折
                if (application.getSystemUtils().getTag().equals("游客")) {
                    SystemUtils.toLogin(this);
                    return;
                }
                if (application.getSystemUtils().getChildTag() == 0) {
                    SystemUtils.toAddChild(this);
                    return;
                }
                intent.setClass(this, MyReadingActivity.class);
                startActivity(intent);
                break;
            case R.id.lets_reading:
                //我要朗读
                if (application.getSystemUtils().getTag().equals("游客")) {
                    SystemUtils.toLogin(this);
                    return;
                }
                if (application.getSystemUtils().getChildTag() == 0) {
                    SystemUtils.toAddChild(this);
                    return;
                }
                if (readClassifyList == null || readClassifyList.size() == 0) {
                    showInfo("请稍后");
                    return;
                }
                showLoad();
                intent.setClass(this, ListenSongActivity.class);
                Bundle bundle5 = new Bundle();
                bundle5.putInt("type", 10);
                bundle5.putSerializable("ClassifyList", (Serializable) readClassifyList);
                intent.putExtras(bundle5);
                startActivity(intent);
                break;
            case R.id.reading_music:
                Intent intent1 = new Intent(this, MusicPlayActivity2.class);
                intent1.putExtra("radioDismiss", 1);
                startActivity(intent1);
                break;
            case R.id.reading_search:
                Intent intent2 = new Intent(this, GlobalSearchActivity.class);
                startActivity(intent2);
                break;
        }
    }


    @Override
    public void onPublish(int progress) {

    }

    @Override
    public void onChange(int position) {
        if (musicBtn != null) {
            if (musicService.isPlaying()) {
                musicBtn.start();
            } else {
                musicBtn.stop();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        allowBindService();
        JAnalyticsInterface.onPageStart(getApplicationContext(),this.getClass().getCanonicalName());
    }


    @Override
    protected void onPause() {
        super.onPause();
        allowUnBindService();
        JAnalyticsInterface.onPageEnd(getApplicationContext(),this.getClass().getCanonicalName());
    }
}
