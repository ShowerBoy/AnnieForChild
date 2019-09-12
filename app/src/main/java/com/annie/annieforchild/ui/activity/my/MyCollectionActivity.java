package com.annie.annieforchild.ui.activity.my;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.views.APSTSViewPager;
import com.annie.annieforchild.ui.fragment.collection.GrindEarFragment;
import com.annie.annieforchild.ui.fragment.collection.OtherFragment;
import com.annie.annieforchild.ui.fragment.collection.ReadingFragment;
import com.annie.annieforchild.ui.fragment.collection.SpokenFragment;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;

/**
 * 我的收藏
 * Created by WangLei on 2018/2/12 0012
 */

public class MyCollectionActivity extends BaseActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {
    private ImageView myCollectionBack;
    private TabLayout mSlidingTab;
    private APSTSViewPager mVP;
    private GrindEarFragment grindEarFragment;
    private ReadingFragment readingFragment;
    private SpokenFragment spokenFragment;
    private OtherFragment otherFragment;
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
        mVP.setOffscreenPageLimit(4);
        mVP.setAdapter(fragmentAdapter);
        fragmentAdapter.notifyDataSetChanged();
//        mSlidingTab.setViewPager(mVP);
//        mSlidingTab.setOnPageChangeListener(this);
        mSlidingTab.addTab(mSlidingTab.newTab().setText("磨耳朵"));
        mSlidingTab.addTab(mSlidingTab.newTab().setText("流利读"));
        mSlidingTab.addTab(mSlidingTab.newTab().setText("地道说"));
        mSlidingTab.addTab(mSlidingTab.newTab().setText("其他"));
        mSlidingTab.setupWithViewPager(mVP);//将TabLayout和ViewPager关联起来。
        mVP.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mSlidingTab));
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

        private CollectionFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position >= 0 && position < 4) {
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
                    case 3:
                        if (null == otherFragment) {
                            otherFragment = OtherFragment.instance();
                        }
                        return otherFragment;
                    default:
                        break;
                }
            }
            return null;
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position >= 0 && position < 4) {
                switch (position) {
                    case 0:
                        return "磨耳朵";
                    case 1:
                        return "流利读";
                    case 2:
                        return "地道说";
                    case 3:
                        return "其他";
                    default:
                        break;
                }
            }
            return null;
        }
    }
}
