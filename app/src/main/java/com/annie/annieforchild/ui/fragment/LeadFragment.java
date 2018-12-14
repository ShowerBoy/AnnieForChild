package com.annie.annieforchild.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.annie.annieforchild.R;
import com.annie.baselibrary.base.BaseFragment;

/**
 * Created by wanglei on 2018/6/20.
 */

public class LeadFragment extends BaseFragment {
    private Bundle bundle;
    private ImageView image;
    private int i;

    public LeadFragment() {
    }

    public static LeadFragment instance(int i) {
        LeadFragment fragment = new LeadFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("tag", i);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initData() {
        bundle = getArguments();
        i = bundle.getInt("tag");
        if (i == 1) {
            image.setImageResource(R.drawable.splash_screen);
        } else if (i == 2) {
            image.setImageResource(R.drawable.splash_screen);
        } else if (i == 3) {
            image.setImageResource(R.drawable.splash_screen);
        }
    }

    @Override
    protected void initView(View view) {
        image = view.findViewById(R.id.lead_fragment_image);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_lead_fragment;
    }
}
