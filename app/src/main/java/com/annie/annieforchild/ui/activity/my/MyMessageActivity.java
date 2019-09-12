package com.annie.annieforchild.ui.activity.my;

import android.app.Dialog;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.AlertHelper;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.Utils.views.APSTSViewPager;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.annieforchild.presenter.MessagePresenter;
import com.annie.annieforchild.presenter.imp.MessagePresenterImp;
import com.annie.annieforchild.ui.fragment.message.GroupMsgFragment;
import com.annie.annieforchild.ui.fragment.message.NoticeFragment;
import com.annie.annieforchild.view.info.ViewInfo;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;

import org.greenrobot.eventbus.Subscribe;

/**
 * 我的消息
 * Created by WangLei on 2018/1/17 0017
 */

public class MyMessageActivity extends BaseActivity implements ViewInfo, View.OnClickListener, ViewPager.OnPageChangeListener {
    private ImageView myMessageBack;
    private TabLayout pagerTab;
    private APSTSViewPager mVP;
    private NoticeFragment noticeFragment;
    private GroupMsgFragment groupMsgFragment;
    private MessageFragmentAdapter fragmentAdapter;
    private MessagePresenter presenter;
    private AlertHelper helper;
    private Dialog dialog;

    {
        setRegister(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_message;
    }

    @Override
    protected void initView() {
        myMessageBack = findViewById(R.id.my_message_back);
        pagerTab = findViewById(R.id.message_tab_layout);
        mVP = findViewById(R.id.message_viewpager);
        myMessageBack.setOnClickListener(this);
        helper = new AlertHelper(this);
        dialog = helper.LoadingDialog();
    }

    @Override
    protected void initData() {
        presenter = new MessagePresenterImp(this, this);
        presenter.initViewAndData();
        fragmentAdapter = new MessageFragmentAdapter(getSupportFragmentManager());
        mVP.setOffscreenPageLimit(2);
        mVP.setAdapter(fragmentAdapter);
        fragmentAdapter.notifyDataSetChanged();
//        pagerTab.setViewPager(mVP);
//        pagerTab.setOnPageChangeListener(this);
        pagerTab.addTab(pagerTab.newTab().setText("通知"));
        pagerTab.addTab(pagerTab.newTab().setText("群消息"));
        pagerTab.setupWithViewPager(mVP);//将TabLayout和ViewPager关联起来。
        mVP.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(pagerTab));
//        presenter.getMyMessages();
        presenter.getMessagesList();
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.my_message_back:
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

    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_CHOOSEGIFT) {
            presenter.getMessagesList();
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

    class MessageFragmentAdapter extends FragmentStatePagerAdapter {

        public MessageFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position >= 0 && position < 2) {
                switch (position) {
                    case 0:
                        if (null == noticeFragment) {
                            noticeFragment = NoticeFragment.instance();
                        }
                        return noticeFragment;
                    case 1:
                        if (null == groupMsgFragment) {
                            groupMsgFragment = GroupMsgFragment.instance();
                        }
                        return groupMsgFragment;
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
                        return "通知";
                    case 1:
                        return "群消息";
                    default:
                        break;
                }
            }
            return null;
        }
    }
}
