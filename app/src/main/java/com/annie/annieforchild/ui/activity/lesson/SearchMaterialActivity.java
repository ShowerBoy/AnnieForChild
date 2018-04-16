package com.annie.annieforchild.ui.activity.lesson;

import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.annie.annieforchild.R;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;

/**
 * 搜索教材
 * Created by wanglei on 2018/4/8.
 */

public class SearchMaterialActivity extends BaseActivity implements View.OnClickListener {
    private SearchView searchView;
    private ListView selectMaterialList;
    private ImageView back;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search_material;
    }

    @Override
    protected void initView() {
        searchView = findViewById(R.id.search_material);
        back = findViewById(R.id.search_material_back);
        searchView.setIconifiedByDefault(false);
        back.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        searchView.clearFocus();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_material_back:
                finish();
                break;
        }
    }
}
