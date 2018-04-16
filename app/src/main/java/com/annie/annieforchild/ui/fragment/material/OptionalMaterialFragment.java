package com.annie.annieforchild.ui.fragment.material;

import android.view.View;

import com.annie.annieforchild.R;
import com.annie.baselibrary.base.BaseFragment;

/**
 * 自选教材
 * Created by wanglei on 2018/4/4.
 */

public class OptionalMaterialFragment extends BaseFragment {

    public static OptionalMaterialFragment instance() {
        OptionalMaterialFragment fragment = new OptionalMaterialFragment();
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
        return R.layout.activity_optional_material_fragment;
    }
}
