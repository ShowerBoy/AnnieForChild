package com.annie.annieforchild.ui.activity.grindEar;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.CheckDoubleClickListener;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.OnCheckDoubleClick;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.Utils.service.MusicService;
import com.annie.annieforchild.bean.Collection;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.grindear.GrindEarData;
import com.annie.annieforchild.bean.radio.RadioBean;
import com.annie.annieforchild.bean.song.Song;
import com.annie.annieforchild.bean.song.SongClassify;
import com.annie.annieforchild.presenter.CollectionPresenter;
import com.annie.annieforchild.presenter.imp.CollectionPresenterImp;
import com.annie.annieforchild.presenter.imp.GrindEarPresenterImp;
import com.annie.annieforchild.ui.activity.GlobalSearchActivity;
import com.annie.annieforchild.ui.activity.pk.MusicPlayActivity;
import com.annie.annieforchild.ui.adapter.RadioAdapter;
import com.annie.annieforchild.view.GrindEarView;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;
import com.daimajia.slider.library.SliderLayout;

import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * 磨耳朵
 * Created by WangLei on 2018/1/18 0018
 */

public class GrindEarActivity extends BaseActivity implements GrindEarView, OnCheckDoubleClick {
    private RecyclerView recycler;
    private AnimationDrawable musicBtn;
    private ImageView grindEarBack, iWantGrindEar, iWantSing, music, search;
    private SliderLayout grindEarSlide;
    private LinearLayout songLayout, poetryLayout, picturebookLayout, accompLayout, dialogueLayout, animationLayout;
    private RelativeLayout recentlyPlay, myCollection;
    private AlertHelper helper;
    private Dialog dialog;
    private GrindEarPresenterImp presenter;
    private CollectionPresenter presenter2;
    //    private GrindEarAdapter adapter;
    private RadioAdapter adapter;
    private List<SongClassify> songClassifyList;
    private List<SongClassify> poetryClassifyList;
    private List<SongClassify> dialogueClassifyList;
    private List<SongClassify> picturebookClassifyList;
    private List<SongClassify> singClassifyList;
    private List<SongClassify> animationClassifyList;
    //    private List<Song> lists;
    private List<RadioBean> lists;
    private List<Collection> collectList;
    private HashMap<Integer, String> file_maps;//轮播图图片map
    private CheckDoubleClickListener listener;
    private int classId = -12000;

    {
        setRegister(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_grind_ear;
    }

    @Override
    protected void initView() {
        grindEarBack = findViewById(R.id.grind_ear_back);
        grindEarSlide = findViewById(R.id.grind_ear_slide);
        iWantGrindEar = findViewById(R.id.i_want_grindear);
        music = findViewById(R.id.grind_music);
        recycler = findViewById(R.id.radio_recycler);
        animationLayout = findViewById(R.id.animation_layout);
        iWantSing = findViewById(R.id.i_want_sing);
        songLayout = findViewById(R.id.song_layout);
        poetryLayout = findViewById(R.id.poetry_layout);
        dialogueLayout = findViewById(R.id.dialogue_layout);
        picturebookLayout = findViewById(R.id.picturebook_layout);
        accompLayout = findViewById(R.id.accomp_layout);
        search = findViewById(R.id.grind_ear_search);
        recentlyPlay = findViewById(R.id.recently_play);
        myCollection = findViewById(R.id.my_collection);
        listener = new CheckDoubleClickListener(this);
        iWantGrindEar.setOnClickListener(listener);
        iWantSing.setOnClickListener(listener);
        grindEarBack.setOnClickListener(listener);
        songLayout.setOnClickListener(listener);
        poetryLayout.setOnClickListener(listener);
        dialogueLayout.setOnClickListener(listener);
        picturebookLayout.setOnClickListener(listener);
        accompLayout.setOnClickListener(listener);
        animationLayout.setOnClickListener(listener);
        music.setOnClickListener(listener);
        search.setOnClickListener(listener);
        recentlyPlay.setOnClickListener(listener);
        myCollection.setOnClickListener(listener);

        musicBtn = (AnimationDrawable) music.getDrawable();
        musicBtn.setOneShot(false);
        if (MusicService.isPlay) {
            musicBtn.start();
        } else {
            musicBtn.stop();
        }

//        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(layoutManager);
        recycler.setNestedScrollingEnabled(false);
    }

    @Override
    protected void initData() {
        lists = new ArrayList<>();
        songClassifyList = new ArrayList<>();
        poetryClassifyList = new ArrayList<>();
        dialogueClassifyList = new ArrayList<>();
        picturebookClassifyList = new ArrayList<>();
        singClassifyList = new ArrayList<>();
        animationClassifyList = new ArrayList<>();
        collectList = new ArrayList<>();
        file_maps = new HashMap<>();
        presenter = new GrindEarPresenterImp(this, this);
        presenter.initViewAndData();
        presenter2 = new CollectionPresenterImp(this, this);
        presenter2.initViewAndData();
        helper = new AlertHelper(this);
        dialog = helper.LoadingDialog();
//        adapter = new GrindEarAdapter(this, lists, new OnRecyclerItemClickListener() {
//            @Override
//            public void onItemClick(View view) {
//                int position = recycler.getChildAdapterPosition(view);
//                if (lists.get(position).getJurisdiction() == 0) {
//                    if (lists.get(position).getIsusenectar() == 1) {
//                        SystemUtils.setBackGray(GrindEarActivity.this, true);
//                        SystemUtils.getSuggestPopup(GrindEarActivity.this, "需要" + lists.get(position).getNectar() + "花蜜才能解锁哦", "解锁", "取消", presenter, -1, lists.get(position).getNectar(), lists.get(position).getBookName(), lists.get(position).getBookId(), classId).showAtLocation(SystemUtils.popupView, Gravity.CENTER, 0, 0);
//                    }
//                } else {
//                    Intent intent = new Intent(GrindEarActivity.this, PracticeActivity.class);
//                    intent.putExtra("song", lists.get(position));
//                    intent.putExtra("type", 0);
//                    intent.putExtra("audioType", 0);
//                    intent.putExtra("audioSource", 0);
//                    intent.putExtra("bookType", 0);
//                    startActivity(intent);
//                }
//            }
//
//            @Override
//            public void onItemLongClick(View view) {
//
//            }
//        });
        adapter = new RadioAdapter(this, lists, presenter);

        recycler.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        presenter.getListening();
        //1:听儿歌 2：听诗歌 3：听对话 4：听绘本
        presenter.getMusicClasses();
    }


    /**
     * {@link GrindEarPresenterImp#Success(int, Object)}
     *
     * @param message
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_GETMUSICCLASSES1) {
            songClassifyList.clear();
            songClassifyList.addAll((List<SongClassify>) message.obj);
        } else if (message.what == MethodCode.EVENT_GETMUSICCLASSES2) {
            poetryClassifyList.clear();
            poetryClassifyList.addAll((List<SongClassify>) message.obj);
        } else if (message.what == MethodCode.EVENT_GETMUSICCLASSES3) {
            animationClassifyList.clear();
            animationClassifyList.addAll((List<SongClassify>) message.obj);
        } else if (message.what == MethodCode.EVENT_GETMUSICCLASSES4) {
            picturebookClassifyList.clear();
            picturebookClassifyList.addAll((List<SongClassify>) message.obj);
        } else if (message.what == MethodCode.EVENT_GETMUSICCLASSES5) {
            singClassifyList.clear();
            singClassifyList.addAll((List<SongClassify>) message.obj);
        } else if (message.what == MethodCode.EVENT_GETMUSICCLASSES11) {
            dialogueClassifyList.clear();
            dialogueClassifyList.addAll((List<SongClassify>) message.obj);
        } else if (message.what == MethodCode.EVENT_GETLISTENING) {
            GrindEarData grindEarData = (GrindEarData) message.obj;
            lists.clear();
            lists.addAll(grindEarData.getRadio());
            adapter.notifyDataSetChanged();
        } else if (message.what == MethodCode.EVENT_UNLOCKBOOK + 9000 + classId) {
            showInfo((String) message.obj);
            presenter.getListening();
            presenter.getMusicClasses();
        } else if (message.what == MethodCode.EVENT_MUSIC) {
            if (musicBtn != null) {
                if ((boolean) (message.obj)) {
                    musicBtn.start();
                } else {
                    musicBtn.stop();
                }
            }
        } else if (message.what == MethodCode.EVENT_GETRADIO) {
            List<Song> lists = (List<Song>) message.obj;
            /**
             *
             musicTitle = bundle.getString("name");
             musicImageUrl = bundle.getString("image");
             musicList = (List<Song>) bundle.getSerializable("list");
             origin = bundle.getInt("origin");
             audioType = bundle.getInt("audioType");
             audioSource = bundle.getInt("audioSource");
             resourceId = bundle.getInt("resourceId");
             isCollect = bundle.getInt("isCollect");
             musicPosition = bundle.getInt("musicPosition");
             homeworkid = bundle.getInt("homeworkid");
             */
            Collections.shuffle(lists);
            int musicPosition = 0;
            if (MusicService.isPlay) {
//                mBinder.bStop();
                if (musicService != null) {
                    musicService.stop();
                }
            }
            Intent intent = new Intent(this, MusicPlayActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("type", "radio");
            bundle.putInt("resourceId", lists.get(musicPosition).getBookId());
            bundle.putSerializable("list", (Serializable) lists);
            bundle.putString("name", lists.get(musicPosition).getBookName());
            bundle.putString("image", lists.get(musicPosition).getBookImageUrl());
            bundle.putInt("isCollect", lists.get(musicPosition).getIsCollected());
            bundle.putInt("collectType", 1);
            intent.putExtras(bundle);
            startActivity(intent);
        } else if (message.what == MethodCode.EVENT_MYCOLLECTIONS1) {
            dismissLoad();
            List<Song> lists2 = new ArrayList<>();
            collectList.clear();
            collectList.addAll((List<Collection>) message.obj);
            if (collectList.size() != 0) {
                for (int i = 0; i < collectList.size(); i++) {
                    if (collectList.get(i).getAudioSource() == 1 || collectList.get(i).getAudioSource() == 2 || collectList.get(i).getAudioSource() == 3 || collectList.get(i).getAudioSource() == 4) {
                        Song song = new Song();
                        song.setBookId(collectList.get(i).getCourseId());
                        song.setBookName(collectList.get(i).getName());
                        song.setBookImageUrl(collectList.get(i).getImageUrl());
                        song.setPath(collectList.get(i).getPath());
                        song.setIsCollected(collectList.get(i).getIsCollected());
                        lists2.add(song);
                    }
                }
                Intent intent1 = new Intent(this, MusicPlayActivity.class);
                if (lists2.size() != 0) {
                    int musicPosition = 0;
                    Bundle bundle = new Bundle();
                    bundle.putInt("resourceId", lists2.get(musicPosition).getBookId());
                    bundle.putSerializable("list", (Serializable) lists2);
                    bundle.putString("name", lists2.get(musicPosition).getBookName());
                    bundle.putString("type", "collection");
                    bundle.putString("image", lists2.get(musicPosition).getBookImageUrl());
                    bundle.putInt("isCollect", lists2.get(musicPosition).getIsCollected());
                    bundle.putInt("audioSource", collectList.get(musicPosition).getAudioSource());
                    bundle.putInt("collectType", 1);
                    intent1.putExtras(bundle);
                }
                if (MusicService.isPlay) {
//                    mBinder.bStop();
                    if (musicService != null) {
                        musicService.stop();
                    }
//                    MusicService.stop();
                }
                startActivity(intent1);
            } else {
                showInfo("暂无收藏");
            }
        } else if (message.what == MethodCode.EVENT_LOADING) {
            dismissLoad();
        }
    }

    @Override
    protected BasePresenter getPresenter() {
        return presenter;
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
            if (dialog.getOwnerActivity() != null && !dialog.getOwnerActivity().isFinishing()) {
                dialog.dismiss();
            }
        }
    }

    @Override
    public SliderLayout getImageSlide() {
        return grindEarSlide;
    }

    @Override
    public HashMap<Integer, String> getFile_maps() {
        return file_maps;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onCheckDoubleClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.i_want_grindear:
                //我的磨耳朵
                if (application.getSystemUtils().getTag().equals("游客")) {
                    SystemUtils.toLogin(this);
                    return;
                }
                if (application.getSystemUtils().getChildTag() == 0) {
                    SystemUtils.toAddChild(this);
                    return;
                }
                intent.setClass(this, MyGrindEarActivity.class);
                startActivity(intent);
                break;
            case R.id.i_want_sing:
                //我要唱歌
//                intent.setClass(this, SingingActivity.class);
                if (application.getSystemUtils().getTag().equals("游客")) {
                    SystemUtils.toLogin(this);
                    return;
                }
                if (application.getSystemUtils().getChildTag() == 0) {
                    SystemUtils.toAddChild(this);
                    return;
                }
                if (singClassifyList == null || singClassifyList.size() == 0) {
                    showInfo("请稍后");
                    return;
                }
                showLoad();
                intent.setClass(this, ListenSongActivity.class);
                Bundle bundle1 = new Bundle();
                bundle1.putInt("type", 5);
                bundle1.putSerializable("ClassifyList", (Serializable) singClassifyList);
                intent.putExtras(bundle1);
                startActivity(intent);
                break;
            case R.id.grind_ear_back:
                finish();
                break;
            case R.id.song_layout:
                //听儿歌
                if (application.getSystemUtils().getTag().equals("游客")) {
                    SystemUtils.toLogin(this);
                    return;
                }
                if (application.getSystemUtils().getChildTag() == 0) {
                    SystemUtils.toAddChild(this);
                    return;
                }
                if (songClassifyList == null || songClassifyList.size() == 0) {
                    showInfo("请稍后");
                    return;
                }
                showLoad();
                intent.setClass(this, ListenSongActivity.class);
                Bundle bundle2 = new Bundle();
                bundle2.putInt("type", 1);
                bundle2.putSerializable("ClassifyList", (Serializable) songClassifyList);
                intent.putExtras(bundle2);
                startActivity(intent);
                break;
            case R.id.poetry_layout:
                //听诗歌
                if (application.getSystemUtils().getTag().equals("游客")) {
                    SystemUtils.toLogin(this);
                    return;
                }
                if (application.getSystemUtils().getChildTag() == 0) {
                    SystemUtils.toAddChild(this);
                    return;
                }
                if (poetryClassifyList == null || poetryClassifyList.size() == 0) {
                    showInfo("请稍后");
                    return;
                }
                showLoad();
                intent.setClass(this, ListenSongActivity.class);
                Bundle bundle6 = new Bundle();
                bundle6.putInt("type", 2);
                bundle6.putSerializable("ClassifyList", (Serializable) poetryClassifyList);
                intent.putExtras(bundle6);
                startActivity(intent);
                break;
            case R.id.animation_layout:
                //看动画
                if (application.getSystemUtils().getTag().equals("游客")) {
                    SystemUtils.toLogin(this);
                    return;
                }
                if (application.getSystemUtils().getChildTag() == 0) {
                    SystemUtils.toAddChild(this);
                    return;
                }
                if (animationClassifyList == null || animationClassifyList.size() == 0) {
                    showInfo("请稍后");
                    return;
                }
                showLoad();
                intent.setClass(this, ListenSongActivity.class);
                Bundle bundle3 = new Bundle();
                bundle3.putInt("type", 0);
                bundle3.putSerializable("ClassifyList", (Serializable) animationClassifyList);
                intent.putExtras(bundle3);
                startActivity(intent);
                break;
            case R.id.picturebook_layout:
                //听故事
                if (application.getSystemUtils().getTag().equals("游客")) {
                    SystemUtils.toLogin(this);
                    return;
                }
                if (application.getSystemUtils().getChildTag() == 0) {
                    SystemUtils.toAddChild(this);
                    return;
                }
                if (picturebookClassifyList == null || picturebookClassifyList.size() == 0) {
                    showInfo("请稍后");
                    return;
                }
                showLoad();
                intent.setClass(this, ListenSongActivity.class);
                Bundle bundle4 = new Bundle();
                bundle4.putInt("type", 4);
                bundle4.putSerializable("ClassifyList", (Serializable) picturebookClassifyList);
                intent.putExtras(bundle4);
                startActivity(intent);
                break;
            case R.id.accomp_layout:
                //伴奏
                if (application.getSystemUtils().getTag().equals("游客")) {
                    SystemUtils.toLogin(this);
                    return;
                }
                if (application.getSystemUtils().getChildTag() == 0) {
                    SystemUtils.toAddChild(this);
                    return;
                }
                if (singClassifyList == null || singClassifyList.size() == 0) {
                    showInfo("请稍后");
                    return;
                }
                showLoad();
                intent.setClass(this, ListenSongActivity.class);
                Bundle bundle5 = new Bundle();
                bundle5.putInt("type", 5);
                bundle5.putSerializable("ClassifyList", (Serializable) singClassifyList);
                intent.putExtras(bundle5);
                startActivity(intent);
                break;
            case R.id.dialogue_layout:
                //听对话
                if (application.getSystemUtils().getTag().equals("游客")) {
                    SystemUtils.toLogin(this);
                    return;
                }
                if (application.getSystemUtils().getChildTag() == 0) {
                    SystemUtils.toAddChild(this);
                    return;
                }
                if (dialogueClassifyList == null || dialogueClassifyList.size() == 0) {
                    showInfo("请稍后");
                    return;
                }
                showLoad();
                intent.setClass(this, ListenSongActivity.class);
                Bundle bundle7 = new Bundle();
                bundle7.putInt("type", 3);
                bundle7.putSerializable("ClassifyList", (Serializable) dialogueClassifyList);
                intent.putExtras(bundle7);
                startActivity(intent);
                break;
            case R.id.grind_music:
                Intent intent1 = new Intent(this, MusicPlayActivity.class);
                startActivity(intent1);
                break;
            case R.id.grind_ear_search:
                Intent intent2 = new Intent(this, GlobalSearchActivity.class);
                startActivity(intent2);
                break;
            case R.id.recently_play:
                //最近播放
                if (application.getSystemUtils().getTag().equals("游客")) {
                    SystemUtils.toLogin(this);
                    return;
                }
                if (application.getSystemUtils().getChildTag() == 0) {
                    SystemUtils.toAddChild(this);
                    return;
                }
                if (application.getSystemUtils().getPlayLists() != null && application.getSystemUtils().getPlayLists().size() != 0) {
                    if (MusicService.isPlay) {
//                        mBinder.bStop();
                        if (musicService != null) {
                            musicService.stop();
                        }
//                        MusicService.stop();
                    }
                    Intent intent4 = new Intent(this, MusicPlayActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("resourceId", application.getSystemUtils().getPlayLists().get(0).getBookId());
                    bundle.putSerializable("list", (Serializable) application.getSystemUtils().getPlayLists());
                    bundle.putString("type", "collection");
                    bundle.putString("name", application.getSystemUtils().getPlayLists().get(0).getBookName());
                    bundle.putString("image", application.getSystemUtils().getPlayLists().get(0).getBookImageUrl());
                    bundle.putInt("isCollect", application.getSystemUtils().getPlayLists().get(0).getIsCollected());
                    intent4.putExtras(bundle);
                    startActivity(intent4);
                } else {
                    showInfo("暂无播放历史");
                }
                break;
            case R.id.my_collection:
                //我的收藏
                if (application.getSystemUtils().getTag().equals("游客")) {
                    SystemUtils.toLogin(this);
                    return;
                }
                if (application.getSystemUtils().getChildTag() == 0) {
                    SystemUtils.toAddChild(this);
                    return;
                }
                showLoad();
                presenter2.getMyCollections(1);
                break;
        }

    }
}
