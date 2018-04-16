package com.annie.annieforchild.ui.activity.lesson;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.views.APSTSViewPager;
import com.annie.annieforchild.ui.fragment.material.OptionalMaterialFragment;
import com.annie.annieforchild.ui.fragment.material.SupplementaryMaterialFragment;
import com.annie.annieforchild.ui.fragment.material.SupportingMaterialFragment;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;
import com.lhh.apst.library.AdvancedPagerSlidingTabStrip;

/**
 * 我的教材
 * Created by wanglei on 2018/4/4.
 */

public class MaterialActivity extends BaseActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {
    private ImageView back;
    private AdvancedPagerSlidingTabStrip mTab;
    private APSTSViewPager mVP;
    private OptionalMaterialFragment optionalMaterialFragment;
    private SupplementaryMaterialFragment supplementaryMaterialFragment;
    private SupportingMaterialFragment supportingMaterialFragment;
    private MaterialFragmentAdapter fragmentAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_material;
    }

    @Override
    protected void initView() {
        back = findViewById(R.id.my_material_back);
        mTab = findViewById(R.id.material_tab_layout);
        mVP = findViewById(R.id.material_viewpager);
        back.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        fragmentAdapter = new MaterialFragmentAdapter(getSupportFragmentManager());
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
            case R.id.my_material_back:
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

    class MaterialFragmentAdapter extends FragmentStatePagerAdapter {

        public MaterialFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position >= 0 && position < 3) {
                switch (position) {
                    case 0:
                        if (null == supportingMaterialFragment) {
                            supportingMaterialFragment = SupportingMaterialFragment.instance();
                        }
                        return supportingMaterialFragment;
                    case 1:
                        if (null == supplementaryMaterialFragment) {
                            supplementaryMaterialFragment = SupplementaryMaterialFragment.instance();
                        }
                        return supplementaryMaterialFragment;
                    case 2:
                        if (null == optionalMaterialFragment) {
                            optionalMaterialFragment = OptionalMaterialFragment.instance();
                        }
                        return optionalMaterialFragment;
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
                        return "配套教材";
                    case 1:
                        return "补充教材";
                    case 2:
                        return "自选教材";
                    default:
                        break;
                }
            }
            return null;
        }
    }
}
