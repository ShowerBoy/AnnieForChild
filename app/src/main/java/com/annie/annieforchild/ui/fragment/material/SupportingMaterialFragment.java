package com.annie.annieforchild.ui.fragment.material;

import android.view.View;

import com.annie.annieforchild.R;
import com.annie.baselibrary.base.BaseFragment;

/**
 * 配套教材
 * Created by wanglei on 2018/4/4.
 */

public class SupportingMaterialFragment extends BaseFragment{

    public static SupportingMaterialFragment instance() {
        SupportingMaterialFragment fragment = new SupportingMaterialFragment();
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
        return R.layout.activity_supporting_material_fragment;
    }
}
