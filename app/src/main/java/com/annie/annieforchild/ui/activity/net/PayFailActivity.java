package com.annie.annieforchild.ui.activity.net;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.CheckDoubleClickListener;
import com.annie.annieforchild.Utils.OnCheckDoubleClick;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;

public class PayFailActivity extends BaseActivity implements OnCheckDoubleClick {
    private LinearLayout buy_again;
    CheckDoubleClickListener listner;
    private ImageView back;
    private TextView ok;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_pay_fail;
    }

    @Override
    protected void initView() {
        buy_again = findViewById(R.id.buy_again);
        listner = new CheckDoubleClickListener(this);
        back = findViewById(R.id.back);
        ok=findViewById(R.id.ok);

        back.setOnClickListener(listner);
        ok.setOnClickListener(listner);
        buy_again.setOnClickListener(listner);
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
            case R.id.back:
                finish();
                break;
            case R.id.ok:
                finish();
                break;
            case R.id.buy_again:
//                ActivityCollector.removeActivity(NetSuggestActivity.netSuggestActivity);
                finish();
                break;


        }
    }
}
