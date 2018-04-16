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
import com.annie.annieforchild.ui.adapter.SongAdapter;
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
    private ImageView back;
    private TextView listenTitle;
    private ArrayList<SongClassify> lists;
    private List<Song> songsList;
    private Intent intent;
    private Bundle bundle;
    private AlertHelper helper;
    private Dialog dialog;
    private int type;

    //
//    private AdvancedPagerSlidingTabStrip mTab;
    private TabLayout mTab;
    private APSTSViewPager mVP;
    private ListenSongFragmentAdapter fragmentAdapter;
    private ListenSongFragment listenSongFragment;
    private Boolean[] bool;
    private List<ListenSongFragment> fragments;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_song;
    }

    @Override
    protected void initView() {
        back = findViewById(R.id.song_back);
        listenTitle = findViewById(R.id.listen_title);

        //
        mTab = findViewById(R.id.song_tab_layout);
        mVP = findViewById(R.id.song_viewpager);
        //
        back.setOnClickListener(this);

        intent = getIntent();
        if (intent != null) {
            bundle = intent.getExtras();
            type = bundle.getInt("type");
            if (type == 1) {
                listenTitle.setText("听儿歌");
                lists = (ArrayList<SongClassify>) getIntent().getSerializableExtra("ClassifyList");
            } else if (type == 2) {
                listenTitle.setText("听诗歌");
                lists = (ArrayList<SongClassify>) getIntent().getSerializableExtra("ClassifyList");
            } else if (type == 3) {
                listenTitle.setText("听对话");
                lists = (ArrayList<SongClassify>) getIntent().getSerializableExtra("ClassifyList");
            } else if (type == 4) {
                listenTitle.setText("听绘本");
                lists = (ArrayList<SongClassify>) getIntent().getSerializableExtra("ClassifyList");
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
            if (bool[position]) {
                return fragments.get(position);
            } else {
                listenSongFragment = ListenSongFragment.instance(lists.get(position).getCalssId());
                fragments.add(position, listenSongFragment);
                bool[position] = true;
                return listenSongFragment;
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
