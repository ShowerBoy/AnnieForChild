package com.annie.annieforchild.ui.activity.grindEar;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.song.SongClassify;
import com.annie.annieforchild.presenter.imp.GrindEarPresenterImp;
import com.annie.annieforchild.ui.activity.my.WebActivity;
import com.annie.annieforchild.view.GrindEarView;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;
import com.daimajia.slider.library.SliderLayout;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
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

public class GrindEarActivity extends BaseActivity implements GrindEarView, View.OnClickListener {
    private ImageView grindEarBack, meiriyishi, meiriyige, iWantGrindEar, iWantSing, grindEarCheck;
    private SliderLayout grindEarSlide;
    private LinearLayout songLayout, poetryLayout, dialogueLayout, picturebookLayout;
    private String listeningUrl, animationUrl;
    private AlertHelper helper;
    private Dialog dialog;
    private GrindEarPresenterImp presenter;
    private List<SongClassify> songClassifyList;
    private List<SongClassify> poetryClassifyList;
    private List<SongClassify> dialogueClassifyList;
    private List<SongClassify> picturebookClassifyList;
    private List<SongClassify> singClassifyList;
    private HashMap<Integer, String> file_maps;//轮播图图片map

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
//        listening100 = findViewById(R.id.listening100);
//        animation100 = findViewById(R.id.animation100);
        iWantSing = findViewById(R.id.i_want_sing);
        songLayout = findViewById(R.id.song_layout);
        poetryLayout = findViewById(R.id.poetry_layout);
        dialogueLayout = findViewById(R.id.dialogue_layout);
        picturebookLayout = findViewById(R.id.picturebook_layout);
        grindEarCheck = findViewById(R.id.grind_ear_check);
        iWantGrindEar.setOnClickListener(this);
        iWantSing.setOnClickListener(this);
        grindEarBack.setOnClickListener(this);
        songLayout.setOnClickListener(this);
        poetryLayout.setOnClickListener(this);
        dialogueLayout.setOnClickListener(this);
        picturebookLayout.setOnClickListener(this);
        grindEarCheck.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        songClassifyList = new ArrayList<>();
        poetryClassifyList = new ArrayList<>();
        dialogueClassifyList = new ArrayList<>();
        picturebookClassifyList = new ArrayList<>();
        singClassifyList = new ArrayList<>();
        helper = new AlertHelper(this);
        dialog = helper.LoadingDialog();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        file_maps = new HashMap<>();
        presenter = new GrindEarPresenterImp(this, this);
        presenter.initViewAndData();
        presenter.getListening();
        //1:听儿歌 2：听诗歌 3：听对话 4：听绘本
        presenter.getMusicClasses();
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.i_want_grindear:
                //我的磨耳朵
                if (SystemUtils.tag.equals("会员")) {
                    intent.setClass(this, MyGrindEarActivity.class);
                    startActivity(intent);
                } else {
                    SystemUtils.toLogin(this);
                }
                break;
            case R.id.i_want_sing:
                //我要唱歌
//                intent.setClass(this, SingingActivity.class);
                if (SystemUtils.tag.equals("会员")) {
//                    intent.setClass(this, ExerciseTestActivity.class);
//                    startActivity(intent);
                    intent.setClass(this, ListenSongActivity.class);
                    Bundle bundle1 = new Bundle();
                    bundle1.putInt("type", 5);
                    bundle1.putSerializable("ClassifyList", (Serializable) singClassifyList);
                    intent.putExtras(bundle1);
                    startActivity(intent);
                } else {
                    SystemUtils.toLogin(this);
                }
                break;
            case R.id.grind_ear_back:
                finish();
                break;
            case R.id.grind_ear_check:
                //榜单
                if (SystemUtils.tag.equals("会员")) {
                    intent.setClass(this, RankingListActivity.class);
                    startActivity(intent);
                } else {
                    SystemUtils.toLogin(this);
                }
                break;
            case R.id.song_layout:
                //听儿歌
                if (SystemUtils.tag.equals("游客")) {
                    SystemUtils.toLogin(this);
                    return;
                }
                intent.setClass(this, ListenSongActivity.class);
                Bundle bundle1 = new Bundle();
                bundle1.putInt("type", 1);
                bundle1.putSerializable("ClassifyList", (Serializable) songClassifyList);
                intent.putExtras(bundle1);
                startActivity(intent);
                break;
            case R.id.poetry_layout:
                //听诗歌
                if (SystemUtils.tag.equals("游客")) {
                    SystemUtils.toLogin(this);
                    return;
                }
                intent.setClass(this, ListenSongActivity.class);
                Bundle bundle2 = new Bundle();
                bundle2.putInt("type", 2);
                bundle2.putSerializable("ClassifyList", (Serializable) poetryClassifyList);
                intent.putExtras(bundle2);
                startActivity(intent);
                break;
            case R.id.dialogue_layout:
                //听对话
                if (SystemUtils.tag.equals("游客")) {
                    SystemUtils.toLogin(this);
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
                //听绘本
                if (SystemUtils.tag.equals("游客")) {
                    SystemUtils.toLogin(this);
                    return;
                }
                intent.setClass(this, ListenSongActivity.class);
                Bundle bundle4 = new Bundle();
                bundle4.putInt("type", 4);
                bundle4.putSerializable("ClassifyList", (Serializable) picturebookClassifyList);
                intent.putExtras(bundle4);
                startActivity(intent);
                break;
//            case R.id.listening100:
//                //磨100
//                intent.setClass(this, WebActivity.class);
//                intent.putExtra("url", listeningUrl);
//                startActivity(intent);
//                break;
//            case R.id.animation100:
//                //动画100
//                intent.setClass(this, WebActivity.class);
//                intent.putExtra("url", animationUrl);
//                startActivity(intent);
//                break;
        }
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
    public void setPictureUrl(String listening, String animation) {
        this.listeningUrl = listening;
        this.animationUrl = animation;
    }

}
