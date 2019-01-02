package com.annie.annieforchild.ui.activity.net;

import android.app.Dialog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Slide;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.CheckDoubleClickListener;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.OnCheckDoubleClick;
import com.annie.annieforchild.Utils.views.APSTSViewPager;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.bean.net.NetBean;
import com.annie.annieforchild.bean.net.NetWork;
import com.annie.annieforchild.presenter.NetWorkPresenter;
import com.annie.annieforchild.presenter.imp.NetWorkPresenterImp;
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

public class NetWorkActivity extends BaseActivity implements OnCheckDoubleClick, GrindEarView ,ViewPager.OnPageChangeListener{
    private RecyclerView recycler;
    private SliderLayout sliderLayout;
    private AdvancedPagerSlidingTabStrip mTab;
    private APSTSViewPager mVP;
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
        back.setFocusable(true);
        back.setFocusableInTouchMode(true);
        back.requestFocus();
    }

    @Override
    protected void initData() {
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
            presenter.getNetHomeData();
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
                        return "专项课";
                    default:
                        break;
                }
            }
            return null;
        }
    }
}