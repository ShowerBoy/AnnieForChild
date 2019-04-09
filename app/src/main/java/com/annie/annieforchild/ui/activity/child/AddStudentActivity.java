package com.annie.annieforchild.ui.activity.child;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.CheckDoubleClickListener;
import com.annie.annieforchild.Utils.OnCheckDoubleClick;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;

/**
 * 添加学员
 * Created by wanglei on 2019/3/27.
 */

public class AddStudentActivity extends BaseActivity implements OnCheckDoubleClick {
    private ImageView back;
    private RelativeLayout organLayout, unorganLayout;
    private CheckDoubleClickListener listener;
    private String from;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_student;
    }

    @Override
    protected void initView() {
        back = findViewById(R.id.add_student_back);
        organLayout = findViewById(R.id.add_organ_layout);
        unorganLayout = findViewById(R.id.add_unorgan_layout);
        listener = new CheckDoubleClickListener(this);
        back.setOnClickListener(listener);
        organLayout.setOnClickListener(listener);
        unorganLayout.setOnClickListener(listener);
    }

    @Override
    protected void initData() {
        from = getIntent().getStringExtra("from");
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onCheckDoubleClick(View view) {
        switch (view.getId()) {
            case R.id.add_student_back:
                finish();
                break;
            case R.id.add_organ_layout:
                Intent intent = new Intent(this, BindStudentActivity.class);
                intent.putExtra("from", from);
                startActivity(intent);
                break;
            case R.id.add_unorgan_layout:
                Intent intent1 = new Intent(this, AddChildActivity.class);
                intent1.putExtra("from", from);
                startActivity(intent1);
                break;
        }
    }
}
