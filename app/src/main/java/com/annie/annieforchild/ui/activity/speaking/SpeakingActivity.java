package com.annie.annieforchild.ui.activity.speaking;

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
import com.annie.annieforchild.Utils.service.MusicService;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.ReadingData;
import com.annie.annieforchild.bean.song.Song;
import com.annie.annieforchild.bean.song.SongClassify;
import com.annie.annieforchild.presenter.GrindEarPresenter;
import com.annie.annieforchild.presenter.imp.GrindEarPresenterImp;
import com.annie.annieforchild.ui.activity.GlobalSearchActivity;
import com.annie.annieforchild.ui.activity.grindEar.ListenSongActivity;
import com.annie.annieforchild.ui.activity.pk.PracticeActivity;
import com.annie.annieforchild.ui.activity.reading.ReadingActivity;
import com.annie.annieforchild.ui.adapter.GrindEarAdapter;
import com.annie.annieforchild.ui.interfaces.OnRecyclerItemClickListener;
import com.annie.annieforchild.view.GrindEarView;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;
import com.daimajia.slider.library.SliderLayout;

import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by wanglei on 2018/11/8.
 */

public class SpeakingActivity extends BaseActivity implements OnCheckDoubleClick, GrindEarView {
    private RecyclerView recycler;
    private AnimationDrawable musicBtn;
    private ImageView speakingBack, music, search;
    private SliderLayout speakingSlide;
    private LinearLayout huibenLayout, zhutiLayout, jiaojiLayout, donghuaLayout, yanjiangLayout;
    private HashMap<Integer, String> file_maps;//轮播图图片map
    private GrindEarPresenter presenter;
    private List<SongClassify> huibenSpeakingList;
    private List<SongClassify> zhutiSpeakingList;
    private List<SongClassify> jiaojiSpeakingList;
    private List<SongClassify> animationSpeakingList;
    private List<SongClassify> yanjiangSpeakingList;
    private GrindEarAdapter adapter;
    private CheckDoubleClickListener listener;
    private AlertHelper helper;
    private Dialog dialog;
    private List<Song> lists;

    {
        setRegister(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_speaking;
    }

    @Override
    protected void initView() {
        recycler = findViewById(R.id.speaking_recommend_recycler);
        speakingBack = findViewById(R.id.speaking_back);
        huibenLayout = findViewById(R.id.huiben_speaking_layout);
        zhutiLayout = findViewById(R.id.zhuti_speaking_layout);
        jiaojiLayout = findViewById(R.id.jiaoji_speaking_layout);
        donghuaLayout = findViewById(R.id.donghua_speaking_layout);
        yanjiangLayout = findViewById(R.id.yanjiang_speaking_layout);
        speakingSlide = findViewById(R.id.speaking_slide);
        music = findViewById(R.id.speaking_music);
        search = findViewById(R.id.speaking_search);
        listener = new CheckDoubleClickListener(this);
        speakingBack.setOnClickListener(listener);
        huibenLayout.setOnClickListener(listener);
        zhutiLayout.setOnClickListener(listener);
        jiaojiLayout.setOnClickListener(listener);
        donghuaLayout.setOnClickListener(listener);
        yanjiangLayout.setOnClickListener(listener);
        search.setOnClickListener(listener);

        musicBtn = (AnimationDrawable) music.getDrawable();
        musicBtn.setOneShot(false);
        if (MusicService.isPlay) {
            musicBtn.start();
        } else {
            musicBtn.stop();
        }

        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(layoutManager);
        recycler.setNestedScrollingEnabled(false);
    }

    @Override
    protected void initData() {
        lists = new ArrayList<>();
        huibenSpeakingList = new ArrayList<>();
        zhutiSpeakingList = new ArrayList<>();
        jiaojiSpeakingList = new ArrayList<>();
        animationSpeakingList = new ArrayList<>();
        yanjiangSpeakingList = new ArrayList<>();
        file_maps = new HashMap<>();
        helper = new AlertHelper(this);
        dialog = helper.LoadingDialog();

        adapter = new GrindEarAdapter(this, lists, new OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view) {
                int position = recycler.getChildAdapterPosition(view);
//                if (lists.get(position).getJurisdiction() == 0) {
//                    if (lists.get(position).getIsusenectar() == 1) {
//                        SystemUtils.setBackGray(SpeakingActivity.this, true);
//                        SystemUtils.getSuggestPopup(SpeakingActivity.this, "需要" + lists.get(position).getNectar() + "花蜜才能解锁哦", "解锁", "取消", presenter, -1, lists.get(position).getNectar(), lists.get(position).getBookName(), lists.get(position).getBookId(), classId).showAtLocation(SystemUtils.popupView, Gravity.CENTER, 0, 0);
//                    }
//                } else {
                Intent intent = new Intent(SpeakingActivity.this, PracticeActivity.class);
                intent.putExtra("song", lists.get(position));
                intent.putExtra("type", 0);
                intent.putExtra("audioType", 1);
                intent.putExtra("audioSource", 0);
                intent.putExtra("bookType", 1);
                startActivity(intent);
//                }
            }

            @Override
            public void onItemLongClick(View view) {

            }
        });
        recycler.setAdapter(adapter);

        presenter = new GrindEarPresenterImp(this, this);
        presenter.initViewAndData();
        presenter.getSpeaking();
        presenter.getSpokenClasses();
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }


    @Override
    public void onCheckDoubleClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.speaking_back:
                finish();
                break;
            case R.id.huiben_speaking_layout:
                if (SystemUtils.tag.equals("游客")) {
                    SystemUtils.toLogin(this);
                    return;
                }
                if (SystemUtils.childTag == 0) {
                    SystemUtils.toAddChild(this);
                    return;
                }
                intent.setClass(this, ListenSongActivity.class);
                Bundle bundle2 = new Bundle();
                bundle2.putInt("type", 11);
                bundle2.putSerializable("ClassifyList", (Serializable) huibenSpeakingList);
                intent.putExtras(bundle2);
                startActivity(intent);
                break;
            case R.id.zhuti_speaking_layout:
                if (SystemUtils.tag.equals("游客")) {
                    SystemUtils.toLogin(this);
                    return;
                }
                if (SystemUtils.childTag == 0) {
                    SystemUtils.toAddChild(this);
                    return;
                }
                intent.setClass(this, ListenSongActivity.class);
                Bundle bundle3 = new Bundle();
                bundle3.putInt("type", 12);
                bundle3.putSerializable("ClassifyList", (Serializable) zhutiSpeakingList);
                intent.putExtras(bundle3);
                startActivity(intent);
                break;
            case R.id.jiaoji_speaking_layout:
                if (SystemUtils.tag.equals("游客")) {
                    SystemUtils.toLogin(this);
                    return;
                }
                if (SystemUtils.childTag == 0) {
                    SystemUtils.toAddChild(this);
                    return;
                }
                intent.setClass(this, ListenSongActivity.class);
                Bundle bundle4 = new Bundle();
                bundle4.putInt("type", 13);
                bundle4.putSerializable("ClassifyList", (Serializable) jiaojiSpeakingList);
                intent.putExtras(bundle4);
                startActivity(intent);
                break;
            case R.id.donghua_speaking_layout:
                if (SystemUtils.tag.equals("游客")) {
                    SystemUtils.toLogin(this);
                    return;
                }
                if (SystemUtils.childTag == 0) {
                    SystemUtils.toAddChild(this);
                    return;
                }
                intent.setClass(this, ListenSongActivity.class);
                Bundle bundle5 = new Bundle();
                bundle5.putInt("type", 14);
                bundle5.putSerializable("ClassifyList", (Serializable) animationSpeakingList);
                intent.putExtras(bundle5);
                startActivity(intent);
                break;
            case R.id.yanjiang_speaking_layout:
                if (SystemUtils.tag.equals("游客")) {
                    SystemUtils.toLogin(this);
                    return;
                }
                if (SystemUtils.childTag == 0) {
                    SystemUtils.toAddChild(this);
                    return;
                }
                intent.setClass(this, ListenSongActivity.class);
                Bundle bundle6 = new Bundle();
                bundle6.putInt("type", 15);
                bundle6.putSerializable("ClassifyList", (Serializable) yanjiangSpeakingList);
                intent.putExtras(bundle6);
                startActivity(intent);
                break;
            case R.id.speaking_search:
                Intent intent2 = new Intent(this, GlobalSearchActivity.class);
                startActivity(intent2);
                break;
        }
    }

    /**
     * {@link GrindEarPresenterImp#Success(int, Object)}
     *
     * @param message
     */
    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_GETSPEAKING) {
            ReadingData readingData = (ReadingData) message.obj;
            lists.clear();
            lists.addAll(readingData.getRecommendlist());
            adapter.notifyDataSetChanged();
        } else if (message.what == MethodCode.EVENT_GETSPOKENCLASSES1) {
            huibenSpeakingList.clear();
            huibenSpeakingList.addAll((List<SongClassify>) message.obj);
        } else if (message.what == MethodCode.EVENT_GETSPOKENCLASSES2) {
            zhutiSpeakingList.clear();
            zhutiSpeakingList.addAll((List<SongClassify>) message.obj);
        } else if (message.what == MethodCode.EVENT_GETSPOKENCLASSES3) {
            jiaojiSpeakingList.clear();
            jiaojiSpeakingList.addAll((List<SongClassify>) message.obj);
        } else if (message.what == MethodCode.EVENT_GETSPOKENCLASSES4) {
            animationSpeakingList.clear();
            animationSpeakingList.addAll((List<SongClassify>) message.obj);
        } else if (message.what == MethodCode.EVENT_GETSPOKENCLASSES5) {
            yanjiangSpeakingList.clear();
            yanjiangSpeakingList.addAll((List<SongClassify>) message.obj);
        }
    }

    @Override
    public SliderLayout getImageSlide() {
        return speakingSlide;
    }

    @Override
    public HashMap<Integer, String> getFile_maps() {
        return file_maps;
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
}
