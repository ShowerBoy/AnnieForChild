package com.annie.annieforchild.ui.fragment.material;

import android.view.View;

import com.annie.annieforchild.R;
import com.annie.baselibrary.base.BaseFragment;

/**
 * 补充教材
 * Created by wanglei on 2018/4/4.
 */

public class SupplementaryMaterialFragment extends BaseFragment {

    public static SupplementaryMaterialFragment instance() {
        SupplementaryMaterialFragment fragment = new SupplementaryMaterialFragment();
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
        return R.layout.activity_supplementary_material_fragment;
    }
}
