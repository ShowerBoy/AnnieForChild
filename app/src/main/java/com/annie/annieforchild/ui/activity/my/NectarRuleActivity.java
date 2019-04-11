package com.annie.annieforchild.ui.activity.my;

import android.view.View;
import android.widget.ImageView;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.CheckDoubleClickListener;
import com.annie.annieforchild.Utils.OnCheckDoubleClick;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;

/**
 * Created by wanglei on 2019/4/9.
 */

public class NectarRuleActivity extends BaseActivity implements OnCheckDoubleClick {
    private ImageView back;
    private CheckDoubleClickListener listener;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_nectar_rule;
    }

    @Override
    protected void initView() {
        listener = new CheckDoubleClickListener(this);
        back = findViewById(R.id.nectar_rule_back);
        back.setOnClickListener(listener);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onCheckDoubleClick(View view) {
        switch (view.getId()) {
            case R.id.nectar_rule_back:
                finish();
                break;
        }
    }
}
