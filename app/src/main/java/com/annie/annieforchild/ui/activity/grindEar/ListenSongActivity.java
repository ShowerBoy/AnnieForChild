package com.annie.annieforchild.ui.activity.grindEar;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.views.APSTSViewPager;
import com.annie.annieforchild.bean.song.Song;
import com.annie.annieforchild.bean.song.SongClassify;
import com.annie.annieforchild.ui.activity.GlobalSearchActivity;
import com.annie.annieforchild.ui.activity.speaking.MySpeakingActivity;
import com.annie.annieforchild.ui.adapter.SongAdapter;
import com.annie.annieforchild.ui.fragment.song.AnimationFragment;
import com.annie.annieforchild.ui.fragment.song.ListenSongFragment;
import com.annie.annieforchild.view.SongView;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * 听儿歌
 * Created by WangLei on 2018/3/13 0013
 */

public class ListenSongActivity extends BaseActivity implements SongView, View.OnClickListener {
    private ImageView back, search;
    private TextView listenTitle;
    private ArrayList<SongClassify> lists;
    private Intent intent;
    private Bundle bundle;
    private AlertHelper helper;
    private Dialog dialog;
    private int type, audioType, audioSource;

    //    private AdvancedPagerSlidingTabStrip mTab;
    private TabLayout mTab;
    private APSTSViewPager mVP;
    private ListenSongFragmentAdapter fragmentAdapter;
    private ListenSongFragment listenSongFragment;
    private AnimationFragment animationFragment;
    private Boolean[] bool;
    private List<ListenSongFragment> fragments;
    private List<AnimationFragment> fragments2;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_song;
    }

    @Override
    protected void initView() {
        back = findViewById(R.id.song_back);
        listenTitle = findViewById(R.id.listen_title);
        search = findViewById(R.id.song_search);

        //
        mTab = findViewById(R.id.song_tab_layout);
        mVP = findViewById(R.id.song_viewpager);
        //
        back.setOnClickListener(this);
        search.setOnClickListener(this);

        intent = getIntent();
        if (intent != null) {
            bundle = intent.getExtras();
            type = bundle.getInt("type");
            if (type == 1) {
                listenTitle.setText("听儿歌");
                lists = (ArrayList<SongClassify>) getIntent().getSerializableExtra("ClassifyList");
                audioType = 0;
                audioSource = 1;
            } else if (type == 2) {
                listenTitle.setText("听诗歌");
                lists = (ArrayList<SongClassify>) getIntent().getSerializableExtra("ClassifyList");
                audioType = 0;
                audioSource = 2;
            } else if (type == 3) {
                listenTitle.setText("看动画");
                lists = (ArrayList<SongClassify>) getIntent().getSerializableExtra("ClassifyList");
                audioType = 0;
                audioSource = 3;
            } else if (type == 4) {
                listenTitle.setText("听故事");
                lists = (ArrayList<SongClassify>) getIntent().getSerializableExtra("ClassifyList");
                audioType = 0;
                audioSource = 4;
            } else if (type == 5) {
                listenTitle.setText("卡拉OK");
                lists = (ArrayList<SongClassify>) getIntent().getSerializableExtra("ClassifyList");
                audioType = 0;
                audioSource = 9;
            } else if (type == 6) {
                listenTitle.setText("绘本");
                lists = (ArrayList<SongClassify>) getIntent().getSerializableExtra("ClassifyList");
                audioType = 1;
                audioSource = 5;
            } else if (type == 7) {
                listenTitle.setText("分级读物");
                lists = (ArrayList<SongClassify>) getIntent().getSerializableExtra("ClassifyList");
                audioType = 1;
                audioSource = 6;
            } else if (type == 8) {
                listenTitle.setText("桥梁书");
                lists = (ArrayList<SongClassify>) getIntent().getSerializableExtra("ClassifyList");
                audioType = 1;
                audioSource = 7;
            } else if (type == 9) {
                listenTitle.setText("章节书");
                lists = (ArrayList<SongClassify>) getIntent().getSerializableExtra("ClassifyList");
                audioType = 1;
                audioSource = 8;
            } else if (type == 10) {
                listenTitle.setText("我要朗读");
                lists = (ArrayList<SongClassify>) getIntent().getSerializableExtra("ClassifyList");
                audioType = 1;
                audioSource = 11;
            } else if (type == 11) {
                listenTitle.setText("地道说");
                lists = (ArrayList<SongClassify>) getIntent().getSerializableExtra("ClassifyList");
                audioType = 2;
                audioSource = 10;
            }
        }

        if (lists.size() != 0) {
            lists.get(0).setSelected(true);
        }

        //
        initTab();
        //
    }

    private void initTab() {
        bool = new Boolean[lists.size()];
        for (int i = 0; i < bool.length; i++) {
            bool[i] = false;
        }
        fragments = new ArrayList<>(lists.size());
        fragments2 = new ArrayList<>(lists.size());

        fragmentAdapter = new ListenSongFragmentAdapter(getSupportFragmentManager());
        mVP.setOffscreenPageLimit(lists.size());
        mVP.setAdapter(fragmentAdapter);
        fragmentAdapter.notifyDataSetChanged();

        mTab.setupWithViewPager(mVP);
        mTab.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    @Override
    protected void initData() {
        helper = new AlertHelper(this);
        dialog = helper.LoadingDialog();
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.song_back:
                finish();
                break;
            case R.id.song_search:
                Intent intent = new Intent(this, GlobalSearchActivity.class);
                startActivity(intent);
                break;
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

    class ListenSongFragmentAdapter extends FragmentStatePagerAdapter {

        public ListenSongFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (type == 3) {
                if (bool[position]) {
                    return fragments2.get(position);
                } else {
//                    listenSongFragment = ListenSongFragment.instance(Integer.parseInt(lists.get(position).getClassId()), lists.get(position).getTitle(), audioType, audioSource, type);
                    animationFragment = AnimationFragment.instance(lists.get(position).getTitle(), Integer.parseInt(lists.get(position).getClassId()), audioType, audioSource, type);
                    fragments2.add(position, animationFragment);
                    bool[position] = true;
                    return animationFragment;
                }
            } else {
                if (bool[position]) {
                    return fragments.get(position);
                } else {
                    listenSongFragment = ListenSongFragment.instance(Integer.parseInt(lists.get(position).getClassId()), lists.get(position).getTitle(), audioType, audioSource, type);
                    fragments.add(position, listenSongFragment);
                    bool[position] = true;
                    return listenSongFragment;
                }
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return lists.get(position).getTitle();
        }

        @Override
        public int getCount() {
            return lists.size();
        }
    }
}
