package com.annie.annieforchild.ui.activity.grindEar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.views.APSTSViewPager;
import com.annie.annieforchild.ui.fragment.song.Mo1Fragment;
import com.annie.annieforchild.ui.fragment.song.Mo2Fragment;
import com.annie.annieforchild.ui.fragment.song.Mobao1Fragment;
import com.annie.annieforchild.ui.fragment.song.Mobao2Fragment;
import com.annie.annieforchild.ui.fragment.song.Mobao3Fragment;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;
import com.lhh.apst.library.AdvancedPagerSlidingTabStrip;

/**
 * 听儿歌
 * Created by WangLei on 2018/3/13 0013
 */

public class SongActivity extends BaseActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {
    private ImageView back;
    private Mobao1Fragment mobao1Fragment;
    private Mobao2Fragment mobao2Fragment;
    private Mobao3Fragment mobao3Fragment;
    private Mo1Fragment mo1Fragment;
    private Mo2Fragment mo2Fragment;
    private AdvancedPagerSlidingTabStrip mTab;
    private APSTSViewPager mVP;
    private SongFragmentAdapter fragmentAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_song;
    }

    @Override
    protected void initView() {
        back = findViewById(R.id.song_back);
        mTab = findViewById(R.id.song_tab_layout);
        mVP = findViewById(R.id.song_viewpager);
        back.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        fragmentAdapter = new SongFragmentAdapter(getSupportFragmentManager());
        mVP.setOffscreenPageLimit(5);
        mVP.setAdapter(fragmentAdapter);
        fragmentAdapter.notifyDataSetChanged();
        mTab.setViewPager(mVP);
        mTab.setOnPageChangeListener(this);
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
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    class SongFragmentAdapter extends FragmentStatePagerAdapter {

        public SongFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position >= 0 && position < 5) {
                switch (position) {
                    case 0:
                        if (null == mobao1Fragment) {
                            mobao1Fragment = Mobao1Fragment.instance();
                        }
                        return mobao1Fragment;
                    case 1:
                        if (null == mobao2Fragment) {
                            mobao2Fragment = Mobao2Fragment.instance();
                        }
                        return mobao2Fragment;
                    case 2:
                        if (null == mobao3Fragment) {
                            mobao3Fragment = Mobao3Fragment.instance();
                        }
                        return mobao3Fragment;
                    case 3:
                        if (null == mo1Fragment) {
                            mo1Fragment = Mo1Fragment.instance();
                        }
                        return mo1Fragment;
                    case 4:
                        if (null == mo2Fragment) {
                            mo2Fragment = Mo2Fragment.instance();
                        }
                        return mo2Fragment;
                    default:
                        break;
                }
            }
            return null;
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position >= 0 && position < 5) {
                switch (position) {
                    case 0:
                        return "磨宝1";
                    case 1:
                        return "磨宝2";
                    case 2:
                        return "磨宝3";
                    case 3:
                        return "磨1";
                    case 4:
                        return "磨2";
                    default:
                        break;
                }
            }
            return null;
        }
    }
}
