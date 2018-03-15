package com.annie.annieforchild.ui.activity.my;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.views.APSTSViewPager;
import com.annie.annieforchild.ui.fragment.collection.GrindEarFragment;
import com.annie.annieforchild.ui.fragment.collection.ReadingFragment;
import com.annie.annieforchild.ui.fragment.collection.SpokenFragment;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;
import com.lhh.apst.library.AdvancedPagerSlidingTabStrip;

/**
 * 我的收藏
 * Created by WangLei on 2018/2/12 0012
 */

public class MyCollectionActivity extends BaseActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {
    private ImageView myCollectionBack;
    private AdvancedPagerSlidingTabStrip mSlidingTab;
    private APSTSViewPager mVP;
    private GrindEarFragment grindEarFragment;
    private ReadingFragment readingFragment;
    private SpokenFragment spokenFragment;
    private CollectionFragmentAdapter fragmentAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_collection;
    }

    @Override
    protected void initView() {
        myCollectionBack = findViewById(R.id.my_collection_back);
        mSlidingTab = findViewById(R.id.collection_tab_layout);
        mVP = findViewById(R.id.collection_viewpager);
        myCollectionBack.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        fragmentAdapter = new CollectionFragmentAdapter(getSupportFragmentManager());
        mVP.setOffscreenPageLimit(3);
        mVP.setAdapter(fragmentAdapter);
        fragmentAdapter.notifyDataSetChanged();
        mSlidingTab.setViewPager(mVP);
        mSlidingTab.setOnPageChangeListener(this);
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.my_collection_back:
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

    class CollectionFragmentAdapter extends FragmentStatePagerAdapter {

        public CollectionFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position >= 0 && position < 3) {
                switch (position) {
                    case 0:
                        if (null == grindEarFragment) {
                            grindEarFragment = GrindEarFragment.instance();
                        }
                        return grindEarFragment;
                    case 1:
                        if (null == readingFragment) {
                            readingFragment = ReadingFragment.instance();
                        }
                        return readingFragment;
                    case 2:
                        if (null == spokenFragment) {
                            spokenFragment = SpokenFragment.instance();
                        }
                        return spokenFragment;
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

        @Override
        public CharSequence getPageTitle(int position) {
            if (position >= 0 && position < 3) {
                switch (position) {
                    case 0:
                        return "磨耳朵";
                    case 1:
                        return "阅读";
                    case 2:
                        return "口语";
                    default:
                        break;
                }
            }
            return null;
        }
    }
}
