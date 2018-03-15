package com.annie.annieforchild.ui.activity.my;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.views.APSTSViewPager;
import com.annie.annieforchild.ui.fragment.help.FeedBackFragment;
import com.annie.annieforchild.ui.fragment.help.HelpFragment;
import com.annie.annieforchild.ui.fragment.message.GroupMsgFragment;
import com.annie.annieforchild.ui.fragment.message.NoticeFragment;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;
import com.lhh.apst.library.AdvancedPagerSlidingTabStrip;

/**
 * 帮助与反馈
 * Created by WangLei on 2018/1/17 0017
 */

public class HelpActivity extends BaseActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {
    private ImageView helpBack;
    private AdvancedPagerSlidingTabStrip mTab;
    private APSTSViewPager mVP;
    private HelpFragment helpFragment;
    private FeedBackFragment feedBackFragment;
    private HelpFragmentAdapter fragmentAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_help;
    }

    @Override
    protected void initView() {
        helpBack = findViewById(R.id.help_back);
        mTab = findViewById(R.id.help_tab_layout);
        mVP = findViewById(R.id.help_viewpager);
        helpBack.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        fragmentAdapter = new HelpFragmentAdapter(getSupportFragmentManager());
        mVP.setOffscreenPageLimit(2);
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
            case R.id.help_back:
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

    class HelpFragmentAdapter extends FragmentStatePagerAdapter {

        public HelpFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position >= 0 && position < 2) {
                switch (position) {
                    case 0:
                        if (null == helpFragment) {
                            helpFragment = HelpFragment.instance();
                        }
                        return helpFragment;
                    case 1:
                        if (null == feedBackFragment) {
                            feedBackFragment = FeedBackFragment.instance();
                        }
                        return feedBackFragment;
                    default:
                        break;
                }
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position >= 0 && position < 2) {
                switch (position) {
                    case 0:
                        return "帮助";
                    case 1:
                        return "反馈";
                    default:
                        break;
                }
            }
            return null;
        }
    }
}
