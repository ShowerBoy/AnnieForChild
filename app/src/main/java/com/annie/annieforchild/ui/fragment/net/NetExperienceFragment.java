package com.annie.annieforchild.ui.fragment.net;

import android.view.View;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.MethodCode;
import com.annie.annieforchild.bean.JTMessage;
import com.annie.baselibrary.base.BaseFragment;

import org.greenrobot.eventbus.Subscribe;

/**
 * 体验课
 * Created by wanglei on 2018/11/14.
 */

public class NetExperienceFragment extends BaseFragment {

    {
        setRegister(true);
    }

    public static NetExperienceFragment instance() {
        NetExperienceFragment fragment = new NetExperienceFragment();
        return fragment;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_net_experience_fragment;
    }

    @Subscribe
    public void onMainEventThread(JTMessage message) {
        if (message.what == MethodCode.EVENT_GETNETHOMEDATA) {

        }
    }
}
