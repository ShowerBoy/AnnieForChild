package com.annie.annieforchild.ui.activity.net;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.annie.annieforchild.R;
import com.annie.annieforchild.Utils.SystemUtils;
import com.annie.annieforchild.bean.net.Gift;
import com.annie.annieforchild.ui.adapter.GiftAdapter;
import com.annie.annieforchild.ui.interfaces.OnRecyclerItemClickListener;
import com.annie.baselibrary.base.BaseActivity;
import com.annie.baselibrary.base.BasePresenter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanglei on 2018/10/24.
 */

public class GiftActivity extends BaseActivity implements View.OnClickListener {
    private ImageView back;
    private TextView confirm;
    private RecyclerView recycler;
    private GiftAdapter adapter;
    private Intent intent;
    private Bundle bundle;
    private List<Gift> lists;
    private List<Gift> selectList;
    private int count, currentCount;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_gift;
    }

    @Override
    protected void initView() {
        back = findViewById(R.id.gift_back);
        recycler = findViewById(R.id.gift_recycler);
        confirm = findViewById(R.id.gift_confirm_btn);
        back.setOnClickListener(this);
        confirm.setOnClickListener(this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(manager);
        recycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    @Override
    protected void initData() {
        intent = getIntent();
        bundle = intent.getExtras();
        lists = ((List<Gift>) bundle.getSerializable("gift"));
        selectList = (List<Gift>) bundle.getSerializable("select");
        count = bundle.getInt("count");
        currentCount = selectList.size();
        adapter = new GiftAdapter(this, lists, new OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view) {
                int position = recycler.getChildAdapterPosition(view);
                if (SystemUtils.userInfo != null) {
                    if (SystemUtils.userInfo.getIsfirstbuy() == 1) {
                        if (lists.get(position).getIsmust() == 1) {
                            return;
                        }
                    }
                }
                if (lists.get(position).isSelect()) {
                    for (int i = 0; i < selectList.size(); i++) {
                        if (selectList.get(i).getId() == lists.get(position).getId()) {
                            selectList.remove(i);
                            currentCount--;
                        }
                    }
//                    if (selectList.contains(lists.get(position))) {
//                        selectList.remove(lists.get(position));
//                    }
                    lists.get(position).setSelect(false);
                } else {
                    currentCount++;
                    if (currentCount > count) {
                        SystemUtils.show(GiftActivity.this, "最多只能选择" + count + "个礼包");
                        currentCount--;
                    } else {
                        lists.get(position).setSelect(true);
                        selectList.add(lists.get(position));
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onItemLongClick(View view) {

            }
        });
        recycler.setAdapter(adapter);
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.gift_back:
                finish();
                break;
            case R.id.gift_confirm_btn:
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("list", (Serializable) lists);
                bundle.putSerializable("select", (Serializable) selectList);
                intent.putExtras(bundle);
                setResult(2, intent);
                finish();
                break;
        }
    }
}
