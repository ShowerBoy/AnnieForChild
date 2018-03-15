package com.annie.annieforchild.ui.activity.my;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.annie.annieforchild.R;
import com.annie.annieforchild.ui.adapter.MyFooterRecyclerAdapter;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;

/**
 * 关于
 * Created by WangLei on 2018/1/17 0017
 */

public class AboutActivity extends BaseActivity implements View.OnClickListener {
    private ImageView aboutBack;
    private RecyclerView recyclerView;
    private MyFooterRecyclerAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    protected void initView() {
        aboutBack = findViewById(R.id.about_back);
        recyclerView = findViewById(R.id.about_recycler);
        aboutBack.setOnClickListener(this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        adapter = new MyFooterRecyclerAdapter(this);
        recyclerView.setAdapter(adapter);
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
            case R.id.about_back:
                finish();
                break;
        }
    }
}
