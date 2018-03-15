package com.annie.annieforchild.ui.activity.grindEar;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.annie.annieforchild.R;
import com.annie.annieforchild.presenter.imp.GrindEarPresenterImp;
import com.annie.annieforchild.view.GrindEarView;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;
import com.daimajia.slider.library.SliderLayout;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.HashMap;

/**
 * 磨耳朵
 * Created by WangLei on 2018/1/18 0018
 */

public class GrindEarActivity extends BaseActivity implements GrindEarView, View.OnClickListener {
    ImageView grindEarBack;
    RelativeLayout topLayout;
    SliderLayout grindEarSlide;
    TextView grindEarCheck, iWantGrindEar, iWantSing;
    XRecyclerView recommendRecycler;
    LinearLayout songLayout, poetryLayout, dialogueLayout, picturebookLayout;

    private GrindEarPresenterImp presenter;
    private HashMap<String, String> file_maps;//轮播图图片map

    @Override
    protected int getLayoutId() {
        return R.layout.activity_grind_ear;
    }

    @Override
    protected void initView() {
        grindEarBack = findViewById(R.id.grind_ear_back);
        topLayout = findViewById(R.id.top_layout);
        grindEarSlide = findViewById(R.id.grind_ear_slide);
        grindEarCheck = findViewById(R.id.grind_ear_check);
        iWantGrindEar = findViewById(R.id.i_want_grindear);
        iWantSing = findViewById(R.id.i_want_sing);
        recommendRecycler = findViewById(R.id.recommend_recycler);
        songLayout = findViewById(R.id.song_layout);
        poetryLayout = findViewById(R.id.poetry_layout);
        dialogueLayout = findViewById(R.id.dialogue_layout);
        picturebookLayout = findViewById(R.id.picturebook_layout);
        iWantGrindEar.setOnClickListener(this);
        iWantSing.setOnClickListener(this);
        grindEarBack.setOnClickListener(this);
        grindEarCheck.setOnClickListener(this);
        songLayout.setOnClickListener(this);
        poetryLayout.setOnClickListener(this);
        dialogueLayout.setOnClickListener(this);
        picturebookLayout.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recommendRecycler.setLayoutManager(layoutManager);
        recommendRecycler.setPullRefreshEnabled(false);
        recommendRecycler.setLoadingMoreEnabled(false);
        recommendRecycler.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        file_maps = new HashMap<>();
        presenter = new GrindEarPresenterImp(this, this);
        presenter.initViewAndData();
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.i_want_grindear:
                intent.setClass(this, MyGrindEarActivity.class);
                startActivity(intent);
                break;
            case R.id.i_want_sing:
                intent.setClass(this, SingingActivity.class);
                startActivity(intent);
                break;
            case R.id.grind_ear_back:
                finish();
                break;
            case R.id.grind_ear_check:
                //榜单
                intent.setClass(this, RankingListActivity.class);
                startActivity(intent);
                break;
            case R.id.song_layout:
                //听儿歌
                intent.setClass(this, SongActivity.class);
                startActivity(intent);
                break;
            case R.id.poetry_layout:
                //听诗歌

                break;
            case R.id.dialogue_layout:
                //听对话

                break;
            case R.id.picturebook_layout:
                //听绘本

                break;
        }
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void showInfo(String info) {

    }

    @Override
    public void showLoad() {

    }

    @Override
    public void dismissLoad() {

    }

    @Override
    public SliderLayout getImageSlide() {
        return grindEarSlide;
    }

    @Override
    public HashMap<String, String> getFile_maps() {
        return file_maps;
    }

}
