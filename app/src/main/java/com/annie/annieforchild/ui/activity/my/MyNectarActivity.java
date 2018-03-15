package com.annie.annieforchild.ui.activity.my;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.views.APSTSViewPager;
import com.annie.annieforchild.bean.UserInfo;
import com.annie.annieforchild.ui.fragment.nectar.IncomeFragment;
import com.annie.annieforchild.ui.fragment.nectar.OutcomeFragment;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;
import com.bumptech.glide.Glide;
import com.lhh.apst.library.AdvancedPagerSlidingTabStrip;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 我的花蜜
 * Created by WangLei on 2018/3/7 0007
 */

public class MyNectarActivity extends BaseActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {
    private ImageView myNectarBack, myNectarHelp;
    private CircleImageView myNectarHeadpic;
    private TextView exchange, myNectarName, myNectarNum;
    private AdvancedPagerSlidingTabStrip mTab;
    private APSTSViewPager mVP;
    private IncomeFragment incomeFragment;
    private OutcomeFragment outcomeFragment;
    private MyNectarFragmentAdapter fragmentAdapter;
    private UserInfo userInfo;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_nectar;
    }

    @Override
    protected void initView() {
        myNectarBack = findViewById(R.id.my_nectar_back);
        myNectarHelp = findViewById(R.id.my_nectar_help);
        myNectarHeadpic = findViewById(R.id.my_nectar_headpic);
        exchange = findViewById(R.id.exchange);
        myNectarName = findViewById(R.id.my_nectar_name);
        myNectarNum = findViewById(R.id.my_nectar_num);
        mTab = findViewById(R.id.my_nectar_tab_layout);
        mVP = findViewById(R.id.my_nectar_viewpager);
        myNectarBack.setOnClickListener(this);
        if (getIntent() != null) {
            Bundle bundle = getIntent().getExtras();
            userInfo = (UserInfo) bundle.getSerializable("userinfo");
        }
    }

    @Override
    protected void initData() {
        initialize();
        fragmentAdapter = new MyNectarFragmentAdapter(getSupportFragmentManager());
        mVP.setOffscreenPageLimit(2);
        mVP.setAdapter(fragmentAdapter);
        fragmentAdapter.notifyDataSetChanged();
        mTab.setViewPager(mVP);
        mTab.setOnPageChangeListener(this);
    }

    private void initialize() {
        if (userInfo != null) {
            Glide.with(this).load(userInfo.getAvatar()).into(myNectarHeadpic);
            myNectarName.setText(userInfo.getName());
            myNectarNum.setText(userInfo.getNectar() != null ? userInfo.getNectar() + "花蜜" : "0花蜜");
        }
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.my_nectar_back:
                finish();
                break;
            case R.id.my_nectar_help:
                //帮助

                break;
            case R.id.exchange:
                //兑换

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

    class MyNectarFragmentAdapter extends FragmentStatePagerAdapter {

        public MyNectarFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position >= 0 && position < 2) {
                switch (position) {
                    case 0:
                        if (null == incomeFragment) {
                            incomeFragment = IncomeFragment.instance();
                        }
                        return incomeFragment;
                    case 1:
                        if (null == outcomeFragment) {
                            outcomeFragment = OutcomeFragment.instance();
                        }
                        return outcomeFragment;
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
                        return "收入";
                    case 1:
                        return "支出";
                    default:
                        break;
                }
            }
            return null;
        }
    }
}
