package com.annie.annieforchild.ui.activity.mains;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.views.APSTSViewPager;
import com.annie.annieforchild.ui.fragment.square.CensusFragment;
import com.annie.annieforchild.ui.fragment.square.RankingListFragment;
import com.annie.annieforchild.ui.fragment.square.WorkshowFragment;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;
import com.lhh.apst.library.AdvancedPagerSlidingTabStrip;

/**
 * 广场
 * Created by wanglei on 2018/5/11.
 */

public class SquareActivity extends BaseActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {
    private ImageView back;
    private AdvancedPagerSlidingTabStrip mTab;
    private APSTSViewPager mVP;
    private RankingListFragment rankingListFragment;
    private CensusFragment censusFragment;
    private WorkshowFragment workshowFragment;
    private SquareFragmentAdapter fragmentAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_square;
    }

    @Override
    protected void initView() {
        back = findViewById(R.id.square_back);
        mTab = findViewById(R.id.square_tab_layout);
        mVP = findViewById(R.id.square_viewpager);
        back.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        fragmentAdapter = new SquareFragmentAdapter(getSupportFragmentManager());
        mVP.setOffscreenPageLimit(3);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.square_back:
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

    class SquareFragmentAdapter extends FragmentStatePagerAdapter {

        public SquareFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position >= 0 && position < 3) {
                switch (position) {
                    case 0:
                        if (null == rankingListFragment) {
                            rankingListFragment = RankingListFragment.instance();
                        }
                        return rankingListFragment;
                    case 1:
                        if (null == censusFragment) {
                            censusFragment = CensusFragment.instance();
                        }
                        return censusFragment;
                    case 2:
                        if (null == workshowFragment) {
                            workshowFragment = WorkshowFragment.instance();
                        }
                        return workshowFragment;
                    default:
                        break;
                }
            }
            return null;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position >= 0 && position < 3) {
                switch (position) {
                    case 0:
                        return "排行榜";
                    case 1:
                        return "统计";
                    case 2:
                        return "作品展示";
                    default:
                        break;
                }
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}
