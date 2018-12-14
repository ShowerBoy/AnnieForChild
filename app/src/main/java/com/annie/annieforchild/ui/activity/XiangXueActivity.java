package com.annie.annieforchild.ui.activity;

import com.airbnb.lottie.LottieAnimationView;
import com.annie.annieforchild.R;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;

/**
 * Created by wanglei on 2018/11/21.
 */

public class XiangXueActivity extends BaseActivity {
    private LottieAnimationView lottie;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_xiang_xue;
    }

    @Override
    protected void initView() {
        lottie = findViewById(R.id.lottie_view);
    }

    @Override
    protected void initData() {
//        lottie.setImageAssetsFolder("images/");
//        lottie.setAnimation("coin.json");
//        lottie.loop(true);
//        lottie.playAnimation();
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }
}
