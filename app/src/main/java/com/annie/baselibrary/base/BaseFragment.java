package com.annie.baselibrary.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Mark.Han on 2017/7/10.
 */

public abstract class BaseFragment extends Fragment {

    protected Context mContext;
    protected View mView;
    private Unbinder mUnbinder;
    protected static String Tag;
    private boolean register;

    public void setRegister(boolean register) {
        this.register = register;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (register) {
            EventBus.getDefault().register(this);
        }
        mView = inflater.inflate(getLayoutId(), container, false);
        mUnbinder = ButterKnife.bind(mView);
        initView(mView);
        initData();
        return mView;
    }

    protected abstract void initData();

    protected abstract void initView(View view);

    protected abstract int getLayoutId();

    public static String getFlagTag() {
        return Tag;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (register) {
            EventBus.getDefault().unregister(this);
        }
        mUnbinder.unbind();
    }
}
