package com.annie.annieforchild.ui.activity.grindEar;

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
import android.widget.TextView;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.CheckDoubleClickListener;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.OnCheckDoubleClick;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.Utils.service.MusicService;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.book.Line;
import com.annie.annieforchild.bean.grindear.GrindEarData;
import com.annie.annieforchild.bean.song.Song;
import com.annie.annieforchild.bean.song.SongClassify;
import com.annie.annieforchild.presenter.imp.GrindEarPresenterImp;
import com.annie.annieforchild.ui.activity.GlobalSearchActivity;
import com.annie.annieforchild.ui.activity.pk.MusicPlayActivity;
import com.annie.annieforchild.ui.activity.pk.PracticeActivity;
import com.annie.annieforchild.ui.adapter.GrindEarAdapter;
import com.annie.annieforchild.ui.interfaces.OnRecyclerItemClickListener;
import com.annie.annieforchild.view.GrindEarView;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;
import com.daimajia.slider.library.SliderLayout;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 磨耳朵
 * Created by WangLei on 2018/1/18 0018
 */

public class GrindEarActivity extends BaseActivity implements GrindEarView, OnCheckDoubleClick {
    private RecyclerView recycler;
    private AnimationDrawable musicBtn;
    private ImageView grindEarBack, iWantGrindEar, iWantSing, music;
    private SliderLayout grindEarSlide;
    private LinearLayout songLayout, poetryLayout, dialogueLayout, picturebookLayout, accompLayout;
    private AlertHelper helper;
    private Dialog dialog;
    private GrindEarPresenterImp presenter;
    private GrindEarAdapter adapter;
    private List<SongClassify> songClassifyList;
    private List<SongClassify> poetryClassifyList;
    private List<SongClassify> dialogueClassifyList;
    private List<SongClassify> picturebookClassifyList;
    private List<SongClassify> singClassifyList;
    private List<Song> lists;
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
        recycler = findViewById(R.id.recommend_recycler);

        iWantSing = findViewById(R.id.i_want_sing);
        songLayout = findViewById(R.id.song_layout);
        poetryLayout = findViewById(R.id.poetry_layout);
        dialogueLayout = findViewById(R.id.dialogue_layout);
        picturebookLayout = findViewById(R.id.picturebook_layout);
        accompLayout = findViewById(R.id.accomp_layout);
        listener = new CheckDoubleClickListener(this);
        iWantGrindEar.setOnClickListener(listener);
        iWantSing.setOnClickListener(listener);
        grindEarBack.setOnClickListener(listener);
        songLayout.setOnClickListener(listener);
        poetryLayout.setOnClickListener(listener);
        dialogueLayout.setOnClickListener(listener);
        picturebookLayout.setOnClickListener(listener);
        accompLayout.setOnClickListener(listener);
        music.setOnClickListener(listener);

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
    }

    @Override
    protected void initData() {
        lists = new ArrayList<>();
        songClassifyList = new ArrayList<>();
        poetryClassifyList = new ArrayList<>();
        dialogueClassifyList = new ArrayList<>();
        picturebookClassifyList = new ArrayList<>();
        singClassifyList = new ArrayList<>();
        helper = new AlertHelper(this);
        dialog = helper.LoadingDialog();
        adapter = new GrindEarAdapter(this, lists, new OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view) {
                int position = recycler.getChildAdapterPosition(view);
                if (lists.get(position).getJurisdiction() == 0) {
                    if (lists.get(position).getIsusenectar() == 1) {
                        SystemUtils.setBackGray(GrindEarActivity.this, true);
                        SystemUtils.getSuggestPopup(GrindEarActivity.this, "需要" + lists.get(position).getNectar() + "花蜜才能解锁哦", "解锁", "取消", presenter, -1, lists.get(position).getNectar(), lists.get(position).getBookName(), lists.get(position).getBookId(), classId).showAtLocation(SystemUtils.popupView, Gravity.CENTER, 0, 0);
                    }
                } else {
                    Intent intent = new Intent(GrindEarActivity.this, PracticeActivity.class);
                    intent.putExtra("song", lists.get(position));
                    intent.putExtra("type", 0);
                    intent.putExtra("audioType", 0);
                    intent.putExtra("audioSource", 0);
                    intent.putExtra("bookType", 0);
                    startActivity(intent);
                }
            }

            @Override
            public void onItemLongClick(View view) {

            }
        });
        recycler.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        file_maps = new HashMap<>();
        presenter = new GrindEarPresenterImp(this, this);
        presenter.initViewAndData();
        presenter.getListening();
        //1:听儿歌 2：听诗歌 3：听对话 4：听绘本
        presenter.getMusicClasses();
    }


    /**
     * {@link GrindEarPresenterImp#Success(int, Object)}
     *
     * @param message
     */
    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_GETMUSICCLASSES1) {
            songClassifyList.clear();
            songClassifyList.addAll((List<SongClassify>) message.obj);
        } else if (message.what == MethodCode.EVENT_GETMUSICCLASSES2) {
            poetryClassifyList.clear();
            poetryClassifyList.addAll((List<SongClassify>) message.obj);
        } else if (message.what == MethodCode.EVENT_GETMUSICCLASSES3) {
            dialogueClassifyList.clear();
            dialogueClassifyList.addAll((List<SongClassify>) message.obj);
        } else if (message.what == MethodCode.EVENT_GETMUSICCLASSES4) {
            picturebookClassifyList.clear();
            picturebookClassifyList.addAll((List<SongClassify>) message.obj);
        } else if (message.what == MethodCode.EVENT_GETMUSICCLASSES5) {
            singClassifyList.clear();
            singClassifyList.addAll((List<SongClassify>) message.obj);
        } else if (message.what == MethodCode.EVENT_GETLISTENING) {
            GrindEarData grindEarData = (GrindEarData) message.obj;
            lists.clear();
            lists.addAll(grindEarData.getRecommendlist());
            adapter.notifyDataSetChanged();
        } else if (message.what == MethodCode.EVENT_UNLOCKBOOK + 9000 + classId) {
            showInfo((String) message.obj);
            presenter.getListening();
            presenter.getMusicClasses();
        } else if (message.what == MethodCode.EVENT_MUSIC) {
            if (musicBtn != null) {
                if ((boolean) (message.obj)) {
                    musicBtn.start();
                }else{
                    musicBtn.stop();
                }
            }
        }
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
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
        return grindEarSlide;
    }

    @Override
    public HashMap<Integer, String> getFile_maps() {
        return file_maps;
    }

    @Override
    public void onCheckDoubleClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.i_want_grindear:
                //我的磨耳朵
                if (SystemUtils.tag.equals("游客")) {
                    SystemUtils.toLogin(this);
                    return;
                }
                if (SystemUtils.childTag == 0) {
                    SystemUtils.toAddChild(this);
                    return;
                }
                intent.setClass(this, MyGrindEarActivity.class);
                startActivity(intent);
                break;
            case R.id.i_want_sing:
                //我要唱歌
//                intent.setClass(this, SingingActivity.class);
                if (SystemUtils.tag.equals("游客")) {
                    SystemUtils.toLogin(this);
                    return;
                }
                if (SystemUtils.childTag == 0) {
                    SystemUtils.toAddChild(this);
                    return;
                }
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
                bundle2.putInt("type", 1);
                bundle2.putSerializable("ClassifyList", (Serializable) songClassifyList);
                intent.putExtras(bundle2);
                startActivity(intent);
                break;
            case R.id.poetry_layout:
                //听诗歌
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
                bundle6.putInt("type", 2);
                bundle6.putSerializable("ClassifyList", (Serializable) poetryClassifyList);
                intent.putExtras(bundle6);
                startActivity(intent);
                break;
            case R.id.dialogue_layout:
                //看动画
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
                bundle3.putInt("type", 3);
                bundle3.putSerializable("ClassifyList", (Serializable) dialogueClassifyList);
                intent.putExtras(bundle3);
                startActivity(intent);
                break;
            case R.id.picturebook_layout:
                //听故事
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
                bundle4.putInt("type", 4);
                bundle4.putSerializable("ClassifyList", (Serializable) picturebookClassifyList);
                intent.putExtras(bundle4);
                startActivity(intent);
                break;
            case R.id.accomp_layout:
                //伴奏
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
                bundle5.putInt("type", 5);
                bundle5.putSerializable("ClassifyList", (Serializable) singClassifyList);
                intent.putExtras(bundle5);
                startActivity(intent);
                break;
            case R.id.grind_music:
                Intent intent1 = new Intent(this, MusicPlayActivity.class);
                startActivity(intent1);
                break;
        }
    }
}
