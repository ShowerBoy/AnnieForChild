package com.annie.annieforchild.ui.activity.my;

import android.view.View;
import android.widget.ImageView;

import com.annie.annieforchild.R;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;

/**
 * 推荐好友
 * Created by WangLei on 2018/1/17 0017
 */

public class ToFriendActivity extends BaseActivity implements View.OnClickListener{
    private ImageView toFriendBack;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_to_friend;
    }

    @Override
    protected void initView() {
        toFriendBack = findViewById(R.id.to_friend_back);
        toFriendBack.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.to_friend_back:
                finish();
                break;
        }
    }
}
