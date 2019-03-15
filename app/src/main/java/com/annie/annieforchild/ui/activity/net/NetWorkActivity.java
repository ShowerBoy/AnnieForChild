package com.annie.annieforchild.ui.activity.net;

import android.app.Dialog;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Slide;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.CheckDoubleClickListener;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.OnCheckDoubleClick;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.Utils.views.APSTSViewPager;
import com.annie.annieforchild.Utils.views.WrapContentHeightViewPager;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.net.NetBean;
import com.annie.annieforchild.bean.net.NetWork;
import com.annie.annieforchild.presenter.NetWorkPresenter;
import com.annie.annieforchild.presenter.imp.NetWorkPresenterImp;
import com.annie.annieforchild.ui.activity.my.WebActivity;
import com.annie.annieforchild.ui.adapter.NetWorkAdapter;
import com.annie.annieforchild.ui.fragment.net.NetExperienceFragment;
import com.annie.annieforchild.ui.fragment.net.NetSpecialFragment;
import com.annie.annieforchild.ui.fragment.net.NetSuggestFragment;
import com.annie.annieforchild.view.GrindEarView;
import com.annie.annieforchild.view.info.ViewInfo;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;
import com.bumptech.glide.Glide;
import com.daimajia.slider.library.SliderLayout;
import com.lhh.apst.library.AdvancedPagerSlidingTabStrip;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 网课
 * Created by wanglei on 2018/9/22.
 */

public class NetWorkActivity extends BaseActivity implements OnCheckDoubleClick, GrindEarView, ViewPager.OnPageChangeListener {
    private RecyclerView recycler;
    private SliderLayout sliderLayout;
    private AdvancedPagerSlidingTabStrip mTab;
    public static APSTSViewPager mVP;
    private ImageView back;
    private List<NetBean> lists;
    private CheckDoubleClickListener listener;
    private NetExperienceFragment netExperienceFragment;
    private NetSpecialFragment netSpecialFragment;
    private NetSuggestFragment netSuggestFragment;
    private NetWorkFragmentAdapter fragmentAdapter;
    private HashMap<Integer, String> file_maps;//轮播图图片map
    private NetWorkPresenter presenter;
    private NetWorkAdapter adapter;
    private AlertHelper helper;
    private Dialog dialog;
    private LinearLayout net_suggest_bt_layout;
    private Button to_NetExperience, to_NetTest;


    {
        setRegister(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_network;
    }

    @Override
    protected void initView() {
        back = findViewById(R.id.network_back);
        sliderLayout = findViewById(R.id.network_slide);
//        recycler = findViewById(R.id.net_recycler);
        mTab = findViewById(R.id.net_tab_layout);
        mVP = findViewById(R.id.net_viewpager);
        listener = new CheckDoubleClickListener(this);
        back.setOnClickListener(listener);

//        LinearLayoutManager manager = new LinearLayoutManager(this);
//        manager.setOrientation(LinearLayoutManager.VERTICAL);
//        recycler.setLayoutManager(manager);
//        recycler.setNestedScrollingEnabled(false);
        net_suggest_bt_layout = findViewById(R.id.net_suggest_bt_layout);
        net_suggest_bt_layout.setVisibility(View.VISIBLE);
        to_NetExperience = findViewById(R.id.to_NetExperience);
        to_NetTest = findViewById(R.id.to_NetTest);
        to_NetExperience.setOnClickListener(listener);
        to_NetTest.setOnClickListener(listener);

        back.setFocusable(true);
        back.setFocusableInTouchMode(true);
        back.requestFocus();
    }

    @Override
    protected void initData() {
        lists = new ArrayList<>();
        helper = new AlertHelper(this);
        dialog = helper.LoadingDialog();
        file_maps = new HashMap<>();
        fragmentAdapter = new NetWorkFragmentAdapter(getSupportFragmentManager());
        mVP.setOffscreenPageLimit(3);
        mVP.setAdapter(fragmentAdapter);
        fragmentAdapter.notifyDataSetChanged();
        mTab.setViewPager(mVP);
        mTab.setOnPageChangeListener(this);
//        lists = new ArrayList<>();
//        adapter = new NetWorkAdapter(this, lists);
//        recycler.setAdapter(adapter);
        presenter = new NetWorkPresenterImp(this, this);
        presenter.initViewAndData();
        presenter.getNetHomeData();

        String to_exp = getIntent().getStringExtra("to_exp");
        if (to_exp != null) {
            if (to_exp.equals("1")) {
                mVP.setCurrentItem(1);
            }
        }
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onCheckDoubleClick(View view) {
        switch (view.getId()) {
            case R.id.network_back:
                finish();
                break;
            case R.id.to_NetExperience:
                mVP.setCurrentItem(1);
                break;
            case R.id.to_NetTest://跳转到H5页面
                Intent intent = new Intent(this, WebActivity.class);
                intent.putExtra("url", "http://study.anniekids.org/questionnaire/index.html?name=" + application.getSystemUtils().getDefaultUsername());
//                intent.putExtra("url", "https://demoapi.anniekids.net/Api/ShareApi/WeiClass");
//                intent.putExtra("aabb",1);//标题是否取消1：取消
                intent.putExtra("title", "");
                startActivity(intent);

//                Toast.makeText(this, "敬请期待", Toast.LENGTH_SHORT).show();
//                NetWorkActivity.mVP.setCurrentItem(2);
//                NetWorkActivity.mVP.setNoFocus(false);
                break;
        }
    }

    /**
     * {@link NetWorkPresenterImp#Success(int, Object)}
     *
     * @param message
     */
    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_GETNETHOMEDATA) {
//            NetWork netWork = (NetWork) message.obj;
//            lists.clear();
//            lists.addAll(netWork.getNetList());
//            adapter.notifyDataSetChanged();
        } else if (message.what == MethodCode.EVENT_PAY) {
            int data = (int) message.obj;
            if (data == 4) {
                mVP.setCurrentItem(1);
            } else {
                presenter.getNetHomeData();
            }
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

    @Override
    public SliderLayout getImageSlide() {
        return sliderLayout;
    }

    @Override
    public HashMap<Integer, String> getFile_maps() {
        return file_maps;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                net_suggest_bt_layout.setVisibility(View.VISIBLE);
                break;
            case 1:
                net_suggest_bt_layout.setVisibility(View.GONE);
                break;
            case 2:
                net_suggest_bt_layout.setVisibility(View.GONE);
                break;

        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    class NetWorkFragmentAdapter extends FragmentStatePagerAdapter {

        public NetWorkFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position >= 0 && position < 3) {
                switch (position) {
                    case 0:
                        if (null == netSuggestFragment) {
                            netSuggestFragment = NetSuggestFragment.instance();
                        }
                        return netSuggestFragment;
                    case 1:
                        if (null == netExperienceFragment) {
                            netExperienceFragment = NetExperienceFragment.instance();
                        }
                        return netExperienceFragment;
                    case 2:
                        if (null == netSpecialFragment) {
                            netSpecialFragment = NetSpecialFragment.instance();
                        }
                        return netSpecialFragment;
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
                        return "网课介绍";
                    case 1:
                        return "体验课";
                    case 2:
                        return "综合课";
                    default:
                        break;
                }
            }
            return null;
        }
    }
}
